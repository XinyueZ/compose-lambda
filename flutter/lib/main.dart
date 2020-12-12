import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_compose_lambda/app_provider.dart';
import 'package:flutter_compose_lambda/app_theme.dart';
import 'package:flutter_compose_lambda/hive_models/news.dart';
import 'package:hive/hive.dart';
import 'package:hive_flutter/hive_flutter.dart';

void main() async {
  await initAppStorage();

  runApp(
    AppProvider(
      child: ThemeApp(),
    ),
  );
}

Future initAppStorage() async {
  await Hive.initFlutter();
  Hive.registerAdapter(NewsAdapter());
}
