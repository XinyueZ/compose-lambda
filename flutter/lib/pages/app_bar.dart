import 'package:flutter/material.dart';
import 'package:flutter_compose_lambda/app_nav/nav_const.dart';
import 'package:flutter_compose_lambda/app_theme.dart';
import 'package:provider/provider.dart';

class TopAppBar extends AppBar {
  TopAppBar({
    @required BuildContext context,
    @required String title,
    @required bool enableSwitchTheme,
    @required bool enablePreferences,
  })  : assert(context is BuildContext),
        assert(title is String),
        assert(enableSwitchTheme is bool),
        assert(enablePreferences is bool),
        super(
            title: Text(
              title,
              maxLines: 1,
              softWrap: false,
              overflow: TextOverflow.ellipsis,
              style: Theme.of(context).textTheme.headline6,
            ),
            actions: <Widget>[
              if (enableSwitchTheme) _buildSwitchTheme(context),
              if (enablePreferences) _buildPreferences(context),
            ]);

  static Widget _buildSwitchTheme(BuildContext context) {
    return Row(
      children: <Widget>[
        Switch(
          value: Provider.of<AppThemeModel>(
            context,
            listen: true,
          ).isDark,
          onChanged: (final bool value) {
            Provider.of<AppThemeModel>(
              context,
              listen: false,
            ).onThemeChanged(
              isDarkTheme: value,
            );
          },
        ),
        Text(Provider.of<AppThemeModel>(
          context,
          listen: true,
        ).isDark
            ? "Light"
            : "Dark"),
        const SizedBox(width: 16),
      ],
    );
  }

  static Widget _buildPreferences(BuildContext context) {
    return IconButton(
        icon: Icon(Icons.settings),
        onPressed: () {
          Navigator.of(
            context,
            rootNavigator: true,
          ).pushNamed(PREFERENCES);
        });
  }
}
