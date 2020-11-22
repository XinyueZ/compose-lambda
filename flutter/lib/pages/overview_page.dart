import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_compose_lambda/app_nav/nav_const.dart';
import 'package:flutter_compose_lambda/pages/app_bar.dart';

class OverviewPage extends StatelessWidget {
  OverviewPage({
    Key key,
    @required this.title,
  }) : super(key: key);

  final String title;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: TopAppBar(
        context: context,
        title: title,
      ),
      body: _buildBody(context),
    );
  }

  Widget _buildBody(BuildContext context) {
    final data = <MapEntry<String, String>>[
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
            _buildOverviewCardContent(
              context,
              data[index],
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
      color: Theme.of(context).cardTheme.color,
      elevation: 4,
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
}
