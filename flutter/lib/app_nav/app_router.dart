import 'dart:developer';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_compose_lambda/app_nav/app_router_path.dart';
import 'package:flutter_compose_lambda/app_nav/nav_const.dart';
import 'package:flutter_compose_lambda/pages/detail_page.dart';
import 'package:flutter_compose_lambda/pages/overview_page.dart';

class AppRouteInformationParser extends RouteInformationParser<AppRouterPath> {
  @override
  Future<AppRouterPath> parseRouteInformation(
      RouteInformation routeInformation) async {
    switch (routeInformation.location) {
      case ROOT:
      case OVERVIEW:
        return OverviewPath();
      case DETAIL:
        return DetailPath();
    }
    throw Exception(
        "parseRouteInformation: unknown information caused by $routeInformation");
  }

  @override
  RouteInformation restoreRouteInformation(AppRouterPath path) {
    final typeOfPath = path.runtimeType;
    switch (typeOfPath) {
      case OverviewPath:
        return RouteInformation(location: OVERVIEW);
      case DetailPath:
        return RouteInformation(location: DETAIL);
        ;
    }
    throw Exception("restoreRouteInformation: unknown path caused by $path");
  }
}

class AppRouterDelegate extends RouterDelegate<AppRouterPath>
    with ChangeNotifier, PopNavigatorRouterDelegateMixin<AppRouterPath> {
  AppRouterPath _appRouterPath;

  @override
  GlobalKey<NavigatorState> get navigatorKey => GlobalKey<NavigatorState>();

  @override
  Future<void> setInitialRoutePath(AppRouterPath path) async =>
      _appRouterPath = path;

  @override
  Future<void> setNewRoutePath(AppRouterPath path) async =>
      _appRouterPath = path;

  @override
  AppRouterPath get currentConfiguration => _appRouterPath;

  @override
  Widget build(BuildContext context) {
    return Navigator(
      key: navigatorKey,
      onPopPage: (Route<dynamic> route, dynamic result) {
        log("onPopPage, route: $route, result: $result");
        if (!route.didPop(result)) {
          return false;
        }
        _appRouterPath = OverviewPath();
        notifyListeners();
        return true;
      },
      pages: [
        /// The [OverviewPage] is the default page.
        MaterialPage<dynamic>(
          child: OverviewPage(
            key: ValueKey<String>("Overview"),
            title: "News report",
            onSelected: () {
              _appRouterPath = DetailPath();
              notifyListeners();
            },
          ),
        ),
        if (_appRouterPath is DetailPath)
          MaterialPage<dynamic>(
              key: ValueKey<String>('Detail'),
              child: BookDetailsPage(
                title: "The guy, occupying the Oval",
              )),
      ],
    );
  }
}
