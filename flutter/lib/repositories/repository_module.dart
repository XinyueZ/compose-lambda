import 'package:flutter_compose_lambda/network/rest_client.dart';
import 'package:flutter_compose_lambda/repositories/news_repository.dart';
import 'package:flutter_compose_lambda/repositories/news_storage_repository.dart';
import 'package:flutter_compose_lambda/repositories/preferences_repository.dart';
import 'package:provider/provider.dart';
import 'package:provider/single_child_widget.dart';

List<SingleChildWidget> repositoryModule = <SingleChildWidget>[
  Provider<PreferencesRepository>(
    create: (_) => PreferencesRepositoryImpl(),
  ),
  ProxyProvider<RestClient, NewsRepository>(
      create: (_) => null,
      update: (
        _,
        RestClient restClient,
        NewsRepository previous,
      ) {
        if (previous != null) {
          return previous;
        }
        return NewsRepositoryImpl(
          restClient: restClient,
          newsStorageRepository: NewsStorageRepositoryImpl(),
        );
      }),
];
