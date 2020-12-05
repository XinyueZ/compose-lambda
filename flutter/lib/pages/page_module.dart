import 'package:flutter_compose_lambda/pages/blocs/news_bloc.dart';
import 'package:flutter_compose_lambda/repositories/news_repository.dart';
import 'package:provider/provider.dart';
import 'package:provider/single_child_widget.dart';

List<SingleChildWidget> pageModule = <SingleChildWidget>[
  ChangeNotifierProxyProvider<NewsRepository, NewsBloc>(
      create: (_) => null,
      update: (
        _,
        NewsRepository newsRepository,
        NewsBloc previous,
      ) {
        if (previous != null) {
          return previous;
        }
        return NewsBloc(
          newsRepository: newsRepository,
        );
      }),
];
