import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_compose_lambda/app_nav/nav_const.dart';
import 'package:flutter_compose_lambda/pages/detail_page.dart';
import 'package:flutter_compose_lambda/pages/overview_page.dart';

Route<dynamic> appRouter(RouteSettings settings) {
  switch (settings.name) {
    case ROOT:
    case OVERVIEW:
      return MaterialPageRoute<void>(
          settings: settings,
          builder: (BuildContext context) =>
              OverviewPage(title: "News report"));
    case DETAIL:
      return MaterialPageRoute<void>(
          settings: settings,
          builder: (BuildContext context) =>
              DetailPage(title: "The guy, occupying the Oval"));
    default:
      throw Exception("Unexpected route ${settings.name}");
  }
}
