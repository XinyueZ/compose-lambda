import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_compose_lambda/app_nav/nav_const.dart';
import 'package:flutter_compose_lambda/pages/app_bar.dart';
import 'package:flutter_compose_lambda/pages/blocs/news_bloc.dart';
import 'package:flutter_compose_lambda/pages/blocs/preferences_bloc.dart';
import 'package:provider/provider.dart';

class OverviewPage extends StatefulWidget {
  OverviewPage({
    Key key,
    @required this.title,
  }) : super(key: key);

  final String title;

  @override
  _OverviewPageState createState() => _OverviewPageState();
}

class _OverviewPageState extends State<OverviewPage> {
  @override
  void initState() {
    super.initState();
    scheduleMicrotask(() async {
      Provider.of<NewsBloc>(context, listen: false).fetchBreakingNews();
      Provider.of<NewsBloc>(context, listen: false).fetchPremiumNews();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: TopAppBar(
        context: context,
        title: widget.title,
        enablePreferences: true,
        enableSwitchTheme: !PreferencesBloc.isFollowSystemTheme(context),
      ),
      body: _buildBody(context),
    );
  }

  Widget _buildBody(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 8.0),
      child: Column(
        children: <Widget>[
          _buildOverviewCard(
            context,
            _buildBreakingNewsContent(context),
          ),
          const SizedBox(height: 3.0),
          _buildOverviewCard(
            context,
            _buildPremiumNewsContent(context),
          ),
        ],
      ),
    );
  }

  Widget _buildOverviewCard(
    BuildContext context,
    Widget content,
  ) {
    return Card(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(8),
      ),
      color: Theme.of(context).cardTheme.color,
      elevation: 5,
      child: content,
    );
  }

  Widget _buildBreakingNewsContent(BuildContext context) {
    Widget _c() {
      final breakingNewsState =
          Provider.of<NewsBloc>(context).breakingNewsState;

      if (breakingNewsState.hasError) {
        return ListTile(
          onTap: () {
            Provider.of<NewsBloc>(context, listen: false).fetchBreakingNews();
          },
          leading: Icon(Icons.refresh),
          title: Text(breakingNewsState.error.toString(),
              textAlign: TextAlign.start,
              style: Theme.of(context).textTheme.subtitle1.copyWith(
                    color: Colors.white,
                  )),
        );
      }

      switch (breakingNewsState.connectionState) {
        case ConnectionState.waiting:
          return Center(
            child: Padding(
              padding: const EdgeInsets.all(10.0),
              child: SizedBox(
                width: 25,
                height: 25,
                child: CircularProgressIndicator(
                  strokeWidth: 1.5,
                ),
              ),
            ),
          );
        case ConnectionState.done:
          return InkWell(
            onTap: () {
              _gotoDetail(breakingNewsState.data);
            },
            child: Row(
              children: <Widget>[
                ClipRRect(
                  borderRadius: BorderRadius.circular(8.0),
                  child: Image.network(
                    breakingNewsState.data.image,
                    width: 120,
                  ),
                ),
                const SizedBox(width: 16),
                Expanded(
                  child: Text(breakingNewsState.data.title,
                      textAlign: TextAlign.start,
                      style: Theme.of(context).textTheme.subtitle1.copyWith(
                            color: Colors.white,
                          )),
                ),
              ],
            ),
          );
        default:
          return const SizedBox.shrink();
      }
    }

    return Container(
        child: _c(),
        decoration: BoxDecoration(
          color: Theme.of(context).colorScheme.error,
          borderRadius: BorderRadius.circular((8.0)),
        ));
  }

  Widget _buildPremiumNewsContent(BuildContext context) {
    Widget _c() {
      final premiumNewsState = Provider.of<NewsBloc>(context).premiumNewsState;

      if (premiumNewsState.hasError) {
        return ListTile(
          onTap: () {
            Provider.of<NewsBloc>(context, listen: false).fetchPremiumNews();
          },
          leading: Icon(Icons.refresh),
          title: Text(premiumNewsState.error.toString(),
              textAlign: TextAlign.start,
              style: Theme.of(context).textTheme.subtitle1.copyWith(
                    color: Colors.white,
                  )),
        );
      }

      switch (premiumNewsState.connectionState) {
        case ConnectionState.waiting:
          return Center(
            child: Padding(
              padding: const EdgeInsets.all(10.0),
              child: SizedBox(
                width: 25,
                height: 25,
                child: CircularProgressIndicator(
                  strokeWidth: 1.5,
                ),
              ),
            ),
          );
        case ConnectionState.done:
          return InkWell(
            onTap: () {
              _gotoDetail(premiumNewsState.data);
            },
            child: Row(
              children: <Widget>[
                ClipRRect(
                  borderRadius: BorderRadius.circular(8.0),
                  child: Image.network(
                    premiumNewsState.data.image,
                    width: 120,
                  ),
                ),
                const SizedBox(width: 16),
                Expanded(
                  child: Text(premiumNewsState.data.title,
                      textAlign: TextAlign.start,
                      style: Theme.of(context).textTheme.subtitle1.copyWith(
                            color: Colors.white,
                          )),
                ),
              ],
            ),
          );
        default:
          return const SizedBox.shrink();
      }
    }

    return Container(
        child: _c(),
        decoration: BoxDecoration(
          color: Theme.of(context).colorScheme.secondaryVariant,
          borderRadius: BorderRadius.circular((8.0)),
        ));
  }

  void _gotoDetail(dynamic data) {
    Navigator.of(
      context,
      rootNavigator: true,
    ).pushNamed(
      DETAIL,
      arguments: data,
    );
  }
}
