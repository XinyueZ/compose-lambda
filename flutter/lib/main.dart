import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_compose_lambda/app_theme.dart';
import 'package:flutter_compose_lambda/pages/overview_page.dart';

void main() {
  runApp(ThemeApp(home: OverviewPage(title: 'News report')));
}
