import 'dart:async';
import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_compose_lambda/domains/breaking_news.dart';
import 'package:flutter_compose_lambda/domains/premium_news.dart';
import 'package:flutter_compose_lambda/pages/app_bar.dart';
import 'package:flutter_compose_lambda/pages/blocs/news_bloc.dart';
import 'package:flutter_compose_lambda/pages/blocs/preferences_bloc.dart';
import 'package:provider/provider.dart';

class DetailPage extends StatefulWidget {
  DetailPage({
    Key key,
    @required this.news,
  }) : super(key: key);

  final dynamic news;

  @override
  _DetailPageState createState() => _DetailPageState();
}

class _DetailPageState extends State<DetailPage> {
  double _delta = 0;

  @override
  void initState() {
    super.initState();
    scheduleMicrotask(() {
      if (widget.news is BreakingNews) {
        Provider.of<NewsBloc>(context, listen: false).fetchBreakingNewsDetail();
      }
      if (widget.news is PremiumNews) {
        Provider.of<NewsBloc>(context, listen: false).fetchPremiumNewsDetail();
      }
    });
  }

  Iterable<String> _parseNewsDetail() {
    if (widget.news is BreakingNews &&
        Provider.of<NewsBloc>(context).breakingNewsDetail.hasData) {
      final data = Provider.of<NewsBloc>(context).breakingNewsDetail.data;
      return data;
    }

    if (widget.news is PremiumNews &&
        Provider.of<NewsBloc>(context).premiumNewsDetail.hasData) {
      final data = Provider.of<NewsBloc>(context).premiumNewsDetail.data;
      return data;
    }
    return ["", "", null];
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: TopAppBar(
        context: context,
        title: _parseNewsDetail().first,
        enablePreferences: true,
        enableSwitchTheme: !PreferencesBloc.isFollowSystemTheme(context),
      ),
      body: _buildBody(context),
    );
  }

  Widget _buildBody(BuildContext context) {
    return Padding(
      padding: EdgeInsets.symmetric(horizontal: 16),
      child: SingleChildScrollView(
        child: Column(
          children: <Widget>[
            const SizedBox(
              height: 16,
            ),
            if (_parseNewsDetail().last != null)
              ClipRRect(
                borderRadius: BorderRadius.circular(10.0),
                child: Image.network(
                  _parseNewsDetail().last,
                ),
              ),
            const SizedBox(
              height: 16,
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: <Widget>[
                TextButton(
                    onPressed: () {
                      if (_delta > 0) {
                        setState(() {
                          _delta -= 1;
                        });
                      }
                    },
                    child: Text(
                      "smaller",
                      textAlign: TextAlign.center,
                    )),
                TextButton(
                    onPressed: () {
                      setState(() {
                        _delta += 1;
                      });
                    },
                    child: Text(
                      "larger",
                      textAlign: TextAlign.center,
                    )),
              ],
            ),
            const SizedBox(
              height: 16,
            ),
            Text(
              _parseNewsDetail().first,
              textAlign: TextAlign.center,
              style: Theme.of(context).textTheme.headline5.copyWith(
                    fontWeight: FontWeight.bold,
                    fontSize:
                        Theme.of(context).textTheme.headline5.fontSize + _delta,
                  ),
            ),
            const SizedBox(
              height: 16,
            ),
            Text(
              _parseNewsDetail().toList()[1],
              style: Theme.of(context).textTheme.bodyText1.copyWith(
                    fontFamily: "monospace",
                    letterSpacing: 1.2,
                    fontSize:
                        Theme.of(context).textTheme.bodyText1.fontSize + _delta,
                  ),
            ),
            const SizedBox(
              height: 16,
            ),
          ],
        ),
      ),
    );
  }
}
