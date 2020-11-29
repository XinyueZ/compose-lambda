import 'package:flutter/material.dart';
import 'package:flutter_compose_lambda/network/network_module.dart';
import 'package:flutter_compose_lambda/repositories/repository_module.dart';
import 'package:provider/provider.dart';
import 'package:provider/single_child_widget.dart';

class AppProviders extends StatelessWidget {
  const AppProviders({
    @required this.child,
  }) : assert(child is Widget);
  final Widget child;

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: <SingleChildWidget>[
        ...networkModule,
        ...repositoryModule,
      ],
      child: child,
    );
  }
}
