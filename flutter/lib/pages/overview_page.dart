import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_compose_lambda/app_nav/nav_const.dart';
import 'package:flutter_compose_lambda/pages/app_bar.dart';
import 'package:flutter_compose_lambda/pages/blocs/breaking_news_bloc.dart';
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
    scheduleMicrotask(() =>
        Provider.of<BreakingNewsBloc>(context, listen: false)
            .fetchBreakingNews());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: TopAppBar(
        context: context,
        title: widget.title,
      ),
      body: _buildBody(context),
    );
  }

  Widget _buildBody(BuildContext context) {
    final data = <dynamic>[
      Provider.of<BreakingNewsBloc>(context, listen: false),
      MapEntry("The guy, occupying the Oval", 'assets/trump_dump.png'),
      MapEntry("Loser or winner ?", 'assets/trump_dump.png'),
    ];

    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 8.0),
      child: ListView.separated(
        separatorBuilder: (BuildContext context, int index) => const Divider(
          height: 3.0,
          color: Colors.transparent,
        ),
        itemCount: data.length,
        itemBuilder: (BuildContext context, int index) {
          return _buildOverviewCard(
            context,
            index == 0
                ? _buildBreakingNewsContent(context)
                : _buildOverviewCardContent(
                    context,
                    data[index] as MapEntry<String, String>,
                  ),
          );
        },
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
      child: InkWell(
        child: content,
        onTap: () {
          Navigator.of(
            context,
            rootNavigator: true,
          ).pushNamed(
            DETAIL,
          );
        },
      ),
    );
  }

  Widget _buildOverviewCardContent(
    BuildContext context,
    MapEntry<String, String> entry,
  ) {
    return Row(
      children: <Widget>[
        ClipRRect(
          borderRadius: BorderRadius.circular(8.0),
          child: Image.asset(
            entry.value,
            width: 120,
          ),
        ),
        const SizedBox(width: 16),
        Text(
          entry.key,
          textAlign: TextAlign.start,
          style: Theme.of(context).textTheme.subtitle1,
        ),
      ],
    );
  }

  Widget _buildBreakingNewsContent(BuildContext context) {
    Widget _c() {
      final breakingNewsState =
          Provider.of<BreakingNewsBloc>(context).breakingNewsState;

      if (breakingNewsState.hasError) {
        return Row(
          children: <Widget>[
            const SizedBox(width: 16),
            IconButton(
              icon: Icon(Icons.refresh),
              onPressed: () {
                Provider.of<BreakingNewsBloc>(context, listen: false)
                    .fetchBreakingNews();
              },
            ),
            Text(
              breakingNewsState.error.toString(),
              textAlign: TextAlign.start,
              style: Theme.of(context).textTheme.subtitle1,
            ),
          ],
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
        default:
          return Row(
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
          );
      }
    }

    return Container(
        child: _c(),
        decoration: BoxDecoration(
          color: Theme.of(context).errorColor,
          borderRadius: BorderRadius.circular((8.0)),
        ));
  }
}
