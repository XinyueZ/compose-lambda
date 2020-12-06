import 'package:flutter/material.dart';
import 'package:flutter_compose_lambda/app_theme.dart';
import 'package:flutter_compose_lambda/network/network_module.dart';
import 'package:flutter_compose_lambda/pages/page_module.dart';
import 'package:flutter_compose_lambda/repositories/repository_module.dart';
import 'package:provider/provider.dart';
import 'package:provider/single_child_widget.dart';

class AppProvider extends StatelessWidget {
  const AppProvider({
    @required this.child,
  }) : assert(child is Widget);
  final Widget child;

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: <SingleChildWidget>[
        ListenableProvider<AppThemeModel>(create: (_) => AppThemeModel()),
        ...networkModule,
        ...repositoryModule,
        ...pageModule,
      ],
      child: child,
    );
  }
}
