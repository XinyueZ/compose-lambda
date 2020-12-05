import 'package:flutter/foundation.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_compose_lambda/domains/breaking_news.dart';
import 'package:flutter_compose_lambda/repositories/news_repository.dart';

class NewsBloc extends ChangeNotifier {
  NewsBloc({
    @required NewsRepository breakingNewsRepository,
  })  : assert(breakingNewsRepository is NewsRepository),
        _breakingNewsRepository = breakingNewsRepository;

  final NewsRepository _breakingNewsRepository;

  AsyncSnapshot<BreakingNews> breakingNewsState =
      const AsyncSnapshot<BreakingNews>.nothing();

  Future<void> fetchBreakingNews() async {
    breakingNewsState = const AsyncSnapshot<BreakingNews>.withData(
      ConnectionState.waiting,
      null,
    );
    notifyListeners();

    try {
      final result = await _breakingNewsRepository.fetchBreakingNews();
      if (result.isError) {
        breakingNewsState = AsyncSnapshot<BreakingNews>.withError(
          ConnectionState.done,
          result.asError.error,
        );
      }

      if (result.isValue) {
        breakingNewsState = AsyncSnapshot<BreakingNews>.withData(
          ConnectionState.done,
          result.asValue.value,
        );
      }
    } catch (e) {
      breakingNewsState = AsyncSnapshot<BreakingNews>.withError(
        ConnectionState.done,
        e,
      );
    } finally {
      notifyListeners();
    }
  }
}
