import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_compose_lambda/app_nav/nav_const.dart';
import 'package:flutter_compose_lambda/pages/detail_page.dart';
import 'package:flutter_compose_lambda/pages/overview_page.dart';
import 'package:flutter_compose_lambda/transitions/customized_transition_route.dart';

Route<dynamic> appRouter(RouteSettings settings) {
  switch (settings.name) {
    case ROOT:
    case OVERVIEW:

      ///[SlideTransitionPageRoute] or nothing to animate with [NoTransitionPageRoute]
      return ScaleTransitionPageRoute<void>(
          settings: settings,
          builder: (BuildContext context) =>
              OverviewPage(title: "News report"));
    case DETAIL:

      ///[SlideTransitionPageRoute] or nothing to animate with [NoTransitionPageRoute]
      return ScaleTransitionPageRoute<void>(
          settings: settings,
          builder: (BuildContext context) =>
              DetailPage(news: settings.arguments));
    default:
      throw Exception("Unexpected route ${settings.name}");
  }
}
