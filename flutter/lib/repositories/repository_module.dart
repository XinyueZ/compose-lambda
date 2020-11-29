import 'package:flutter_compose_lambda/network/rest_client.dart';
import 'package:flutter_compose_lambda/repositories/breaking_news_repository.dart';
import 'package:provider/provider.dart';
import 'package:provider/single_child_widget.dart';

List<SingleChildWidget> repositoryModule = <SingleChildWidget>[
  ProxyProvider<RestClient, BreakingNewsRepository>(
      create: (_) => null,
      update: (
        _,
        RestClient restClient,
        BreakingNewsRepository previous,
      ) {
        if (previous != null) {
          return previous;
        }
        return BreakingNewsRepositoryImpl(
          restClient: restClient,
        );
      }),
];
