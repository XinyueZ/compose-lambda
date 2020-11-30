import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_compose_lambda/app_provider.dart';
import 'package:flutter_compose_lambda/app_theme.dart';

void main() {
  runApp(
    AppProvider(
      child: ThemeApp(),
    ),
  );
}
