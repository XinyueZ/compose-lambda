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
    log("parseRouteInformation, routeInformation: $routeInformation");
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
    log("restoreRouteInformation, path: $path");
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
  Future<void> setInitialRoutePath(AppRouterPath path) async {
    log("setInitialRoutePath, path: $path");
    return _appRouterPath = path;
  }

  @override
  Future<void> setNewRoutePath(AppRouterPath path) async {
    log("setNewRoutePath, path: $path");
    return _appRouterPath = path;
  }

  @override
  AppRouterPath get currentConfiguration {
    log("currentConfiguration, _appRouterPath: $_appRouterPath");
    return _appRouterPath;
  }

  @override
  Widget build(BuildContext context) {
    return Navigator(
      key: navigatorKey,
      onPopPage: (Route<dynamic> route, dynamic result) {
        if (!route.didPop(result)) {
          log("! onPopPage, route: $route, result: $result");
          return false;
        }
        log("onPopPage, route: $route, result: $result");
        _appRouterPath = null;
        notifyListeners();
        return true;
      },
      transitionDelegate: const AppTransitionDelegate(),
      pages: <Page>[
        /// The [OverviewPage] is the default page.
        OverviewPage(
          key: ValueKey<String>("Overview"),
          title: "News report",
          onSelected: () {
            _appRouterPath = DetailPath();
            notifyListeners();
          },
        ),

        if (_appRouterPath is DetailPath)
          DetailPage(
            key: ValueKey<String>('Detail'),
            title: "The guy, occupying the Oval",
          )
      ],
    );
  }
}

class AppTransitionDelegate extends TransitionDelegate<dynamic> {
  const AppTransitionDelegate() : super();

  @override
  Iterable<RouteTransitionRecord> resolve({
    List<RouteTransitionRecord> newPageRouteHistory,
    Map<RouteTransitionRecord, RouteTransitionRecord>
        locationToExitingPageRoute,
    Map<RouteTransitionRecord, List<RouteTransitionRecord>>
        pageRouteToPagelessRoutes,
  }) {
    final results = <RouteTransitionRecord>[];

    void handleExitingRoute(RouteTransitionRecord location, bool isLast) {
      final exitingPageRoute = locationToExitingPageRoute[location];
      if (exitingPageRoute == null) return;
      if (exitingPageRoute.isWaitingForExitingDecision) {
        final hasPagelessRoute =
            pageRouteToPagelessRoutes.containsKey(exitingPageRoute);
        final isLastExitingPageRoute =
            isLast && !locationToExitingPageRoute.containsKey(exitingPageRoute);
        if (isLastExitingPageRoute && !hasPagelessRoute) {
          exitingPageRoute.markForPop(exitingPageRoute.route.currentResult);
        } else {
          exitingPageRoute
              .markForComplete(exitingPageRoute.route.currentResult);
        }
        if (hasPagelessRoute) {
          final pagelessRoutes = pageRouteToPagelessRoutes[exitingPageRoute];
          for (final pagelessRoute in pagelessRoutes) {
            assert(pagelessRoute.isWaitingForExitingDecision);
            if (isLastExitingPageRoute &&
                pagelessRoute == pagelessRoutes.last) {
              pagelessRoute.markForPop(pagelessRoute.route.currentResult);
            } else {
              pagelessRoute.markForComplete(pagelessRoute.route.currentResult);
            }
          }
        }
      }
      results.add(exitingPageRoute);
      handleExitingRoute(exitingPageRoute, isLast);
    }

    handleExitingRoute(null, newPageRouteHistory.isEmpty);

    for (final pageRoute in newPageRouteHistory) {
      final isLastIteration = newPageRouteHistory.last == pageRoute;
      if (pageRoute.isWaitingForEnteringDecision) {
        if (!locationToExitingPageRoute.containsKey(pageRoute) &&
            isLastIteration) {
          pageRoute.markForPush();
        } else {
          pageRoute.markForAdd();
        }
      }
      results.add(pageRoute);
      handleExitingRoute(pageRoute, isLastIteration);
    }
    return results;
  }
}
