import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_compose_lambda/transitions/customized_transition_route.dart';

class OverviewPage extends Page<dynamic> {
  OverviewPage({
    @required LocalKey key,
    @required this.title,
    @required this.onSelected,
  })  : assert(key is LocalKey),
        assert(title is String),
        assert(onSelected is VoidCallback),
        super(key: key);

  final String title;
  final VoidCallback onSelected;

  @override
  Route createRoute(BuildContext context) {
    return ScaleTransitionPageRoute<void>(
      // settings: this,
      builder: (BuildContext context) => OverviewPageWidget(
        key: key,
        title: title,
        onSelected: onSelected,
      ),
    );
  }
}

class OverviewPageWidget extends StatelessWidget {
  OverviewPageWidget({
    Key key,
    @required this.title,
    @required this.onSelected,
  })  : assert(title is String),
        assert(onSelected is VoidCallback),
        super(key: key);

  final String title;
  final VoidCallback onSelected;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: _buildAppBar(context),
      body: _buildBody(context),
    );
  }

  PreferredSizeWidget _buildAppBar(BuildContext context) {
    return AppBar(
      backgroundColor: Colors.purple,
      title: Text(
        title,
        style: Theme.of(context).textTheme.headline6.copyWith(
              color: Colors.white,
            ),
      ),
    );
  }

  Widget _buildBody(BuildContext context) {
    final data = <MapEntry<String, String>>[
      MapEntry("The guy, occupying the Oval", 'assets/trump_dump.png'),
      MapEntry("Loser or winner ?", 'assets/trump_dump.png'),
    ];

    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: ListView.separated(
        separatorBuilder: (BuildContext context, int index) => const Divider(
          height: 12.0,
          color: Colors.transparent,
        ),
        padding: const EdgeInsets.only(
          left: 5,
          right: 5,
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
      elevation: 5,
      child: InkWell(
        child: content,
        onTap: () {
          onSelected();
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
        Image.asset(
          entry.value,
          width: 120,
          height: 90,
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
