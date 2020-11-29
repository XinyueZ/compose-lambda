import 'package:flutter_compose_lambda/pages/blocs/breaking_news_bloc.dart';
import 'package:flutter_compose_lambda/repositories/breaking_news_repository.dart';
import 'package:provider/provider.dart';
import 'package:provider/single_child_widget.dart';

List<SingleChildWidget> pageModule = <SingleChildWidget>[
  ChangeNotifierProxyProvider<BreakingNewsRepository, BreakingNewsBloc>(
      create: (_) => null,
      update: (
        _,
        BreakingNewsRepository breakingNewsRepository,
        BreakingNewsBloc previous,
      ) {
        if (previous != null) {
          return previous;
        }
        return BreakingNewsBloc(
          breakingNewsRepository: breakingNewsRepository,
        );
      }),
];
