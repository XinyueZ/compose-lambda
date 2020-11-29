import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_compose_lambda/app_nav/app_router.dart';
import 'package:provider/provider.dart';

class ThemeApp extends StatelessWidget {
  const ThemeApp({
    @required this.child,
  }) : assert(child is Widget);

  final Widget child;

  @override
  Widget build(BuildContext context) {
    return ListenableProvider<AppThemeModel>(
      create: (_) => AppThemeModel(),
      builder: (BuildContext context, Widget widget) {
        final isDark = Provider.of<AppThemeModel>(context).isDark;
        return MaterialApp(
          theme: isDark ? _appDarkTheme : _appLightTheme,
          onGenerateRoute: appRouter,
          title: "News report",
          home: child,
        );
      },
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
