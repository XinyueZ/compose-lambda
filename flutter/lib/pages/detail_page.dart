import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_compose_lambda/domains/breaking_news.dart';
import 'package:flutter_compose_lambda/domains/premium_news.dart';
import 'package:flutter_compose_lambda/pages/app_bar.dart';
import 'package:flutter_compose_lambda/pages/blocs/news_bloc.dart';
import 'package:flutter_compose_lambda/pages/blocs/preferences_bloc.dart';
import 'package:provider/provider.dart';

class DetailPage extends StatelessWidget {
  DetailPage({
    Key key,
    @required this.news,
  }) : super(key: key);

  final dynamic news;

  @override
  Widget build(BuildContext context) {
    final detailWidget = DetailWidget(news: news);
    // return detailWidget;

    return Scaffold(
      appBar: TopAppBar(
        context: context,
        title: detailWidget.parseNewsDetail(context).first,
        enablePreferences: true,
        enableSwitchTheme: !PreferencesBloc.isFollowSystemTheme(context),
      ),
      body: detailWidget,
    );
  }
}

class DetailWidget extends StatefulWidget {
  DetailWidget({
    Key key,
    @required this.news,
  }) : super(key: key);

  final dynamic news;

  Iterable<String> parseNewsDetail(BuildContext context) {
    if (news is BreakingNews && Provider.of<NewsBloc>(context).breakingNewsDetail.hasData) {
      final data = Provider.of<NewsBloc>(context).breakingNewsDetail.data;
      return data;
    }

    if (news is PremiumNews && Provider.of<NewsBloc>(context).premiumNewsDetail.hasData) {
      final data = Provider.of<NewsBloc>(context).premiumNewsDetail.data;
      return data;
    }
    return ["", "", null];
  }

  @override
  _DetailWidgetState createState() => _DetailWidgetState();
}

class _DetailWidgetState extends State<DetailWidget> {
  double _delta = 0;

  @override
  void didChangeDependencies() {
    if (widget.news is BreakingNews) {
      Provider.of<NewsBloc>(context, listen: false).fetchBreakingNewsDetail();
    }
    if (widget.news is PremiumNews) {
      Provider.of<NewsBloc>(context, listen: false).fetchPremiumNewsDetail();
    }
    super.didChangeDependencies();
  }

  @override
  Widget build(BuildContext context) {
    return _buildBody(context);
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
            if (widget.parseNewsDetail(context).last != null)
              ClipRRect(
                borderRadius: BorderRadius.circular(10.0),
                child: Image.network(
                  widget.parseNewsDetail(context).last,
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
              widget.parseNewsDetail(context).first,
              textAlign: TextAlign.center,
              style: Theme.of(context).textTheme.headline5.copyWith(
                    fontWeight: FontWeight.bold,
                    fontSize: Theme.of(context).textTheme.headline5.fontSize + _delta,
                  ),
            ),
            const SizedBox(
              height: 16,
            ),
            Text(
              widget.parseNewsDetail(context).toList()[1],
              style: Theme.of(context).textTheme.bodyText1.copyWith(
                    fontFamily: "monospace",
                    letterSpacing: 1.2,
                    fontSize: Theme.of(context).textTheme.bodyText1.fontSize + _delta,
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
