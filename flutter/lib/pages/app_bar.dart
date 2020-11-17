import 'package:flutter/material.dart';
import 'package:flutter_compose_lambda/app_theme.dart';
import 'package:provider/provider.dart';

class TopAppBar extends AppBar {
  TopAppBar({
    @required BuildContext context,
    @required String title,
  }) : super(
            title: Text(
              title,
              maxLines: 1,
              softWrap: false,
              overflow: TextOverflow.clip,
              style: Theme.of(context).textTheme.headline6,
            ),
            actions: <Widget>[
              Row(
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
                      ? "Dark"
                      : "Light")
                ],
              ),
            ]);
}
