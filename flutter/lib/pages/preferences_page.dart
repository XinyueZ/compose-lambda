import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_compose_lambda/pages/app_bar.dart';
import 'package:flutter_compose_lambda/pages/blocs/preferences_bloc.dart';
import 'package:provider/provider.dart';

class PreferencesPage extends StatefulWidget {
  @override
  _PreferencesPageState createState() => _PreferencesPageState();
}

class _PreferencesPageState extends State<PreferencesPage> {
  @override
  void initState() {
    super.initState();
    scheduleMicrotask(() async {
      Provider.of<PreferencesBloc>(context, listen: false)
          .fetchFollowSystemTheme();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: TopAppBar(
        context: context,
        title: "Preferences",
        enablePreferences: false,
        enableSwitchTheme: !PreferencesBloc.isFollowSystemTheme(context),
      ),
      body: _buildBody(context),
    );
  }

  Widget _buildBody(BuildContext context) {
    return Column(
      children: <Widget>[
        ListTile(
          title: Text(
            "Follow system theme",
            style: Theme.of(context).textTheme.subtitle1,
          ),
          trailing: Switch(
            value: PreferencesBloc.isFollowSystemTheme(context),
            onChanged: (final bool value) {
              Provider.of<PreferencesBloc>(
                context,
                listen: false,
              ).setFollowSystemTheme(value);
            },
          ),
        )
      ],
    );
  }
}
