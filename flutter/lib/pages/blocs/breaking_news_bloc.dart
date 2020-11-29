import 'package:flutter/foundation.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_compose_lambda/domains/breaking_news.dart';
import 'package:flutter_compose_lambda/repositories/breaking_news_repository.dart';

class BreakingNewsBloc extends ChangeNotifier {
  BreakingNewsBloc({
    @required BreakingNewsRepository breakingNewsRepository,
  })  : assert(breakingNewsRepository is BreakingNewsRepository),
        _breakingNewsRepository = breakingNewsRepository;

  final BreakingNewsRepository _breakingNewsRepository;

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
