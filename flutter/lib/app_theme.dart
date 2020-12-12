import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_compose_lambda/app_nav/app_router.dart';
import 'package:flutter_compose_lambda/pages/blocs/preferences_bloc.dart';
import 'package:provider/provider.dart';

class ThemeApp extends StatelessWidget {
  const ThemeApp({
    this.child,
  });

  final Widget child;

  @override
  Widget build(BuildContext context) {
    final isDarkTheme = Provider.of<AppThemeModel>(context).isDark;
    final isFollowSystemTheme = PreferencesBloc.isFollowSystemTheme(context);
    var themeMode = isDarkTheme ? ThemeMode.dark : ThemeMode.light;
    if (isFollowSystemTheme) {
      themeMode = ThemeMode.system;
    }
    return MaterialApp(
      theme: _appLightTheme,
      darkTheme: _appDarkTheme,
      themeMode: themeMode,
      onGenerateRoute: appRouter,
      onGenerateTitle: (context) => "News report",
      home: child,
    );
  }
}

ThemeData get _appLightTheme {
  final colors = ColorScheme(
    primary: Colors.indigoAccent,
    primaryVariant: Colors.indigo[900],
    secondary: Colors.teal,
    secondaryVariant: Colors.teal[900],
    background: Colors.white,
    surface: Colors.white,
    error: Colors.red,
    onPrimary: Colors.white,
    onSecondary: Colors.black,
    onBackground: Colors.black,
    onSurface: Colors.black,
    onError: Colors.white,
    brightness: Brightness.light,
  );
  return ThemeData.from(
    colorScheme: colors,
    textTheme: Typography.blackMountainView,
  );
}

ThemeData get _appDarkTheme {
  final colors = ColorScheme(
    primary: Colors.indigoAccent,
    primaryVariant: Colors.indigo[900],
    secondary: Colors.tealAccent,
    secondaryVariant: Colors.teal[900],
    background: Colors.black,
    surface: Colors.black,
    error: Colors.red[900],
    onPrimary: Colors.black,
    onSecondary: Colors.white,
    onBackground: Colors.white,
    onSurface: Colors.white,
    onError: Colors.black,
    brightness: Brightness.dark,
  );
  return ThemeData.from(
    colorScheme: colors,
    textTheme: Typography.whiteMountainView,
  );
}

class AppThemeModel extends ChangeNotifier {
  bool isDark = false;

  void onThemeChanged({@required bool isDarkTheme}) {
    assert(isDarkTheme != null);
    isDark = isDarkTheme;
    notifyListeners();
  }
}
