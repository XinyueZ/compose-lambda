import 'package:flutter/foundation.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_compose_lambda/domains/breaking_news.dart';
import 'package:flutter_compose_lambda/domains/premium_news.dart';
import 'package:flutter_compose_lambda/repositories/news_repository.dart';

class NewsBloc extends ChangeNotifier {
  NewsBloc({
    @required NewsRepository newsRepository,
  })  : assert(newsRepository is NewsRepository),
        _newsRepository = newsRepository;

  final NewsRepository _newsRepository;

  AsyncSnapshot<BreakingNews> breakingNewsState =
      const AsyncSnapshot<BreakingNews>.nothing();

  AsyncSnapshot<PremiumNews> premiumNewsState =
      const AsyncSnapshot<PremiumNews>.nothing();

  AsyncSnapshot<Iterable<String>> breakingNewsDetail =
      const AsyncSnapshot<Iterable<String>>.nothing();

  AsyncSnapshot<Iterable<String>> premiumNewsDetail =
      const AsyncSnapshot<Iterable<String>>.nothing();

  Future<void> fetchBreakingNews() async {
    breakingNewsState = const AsyncSnapshot<BreakingNews>.withData(
      ConnectionState.waiting,
      null,
    );
    notifyListeners();

    try {
      final result = await _newsRepository.fetchBreakingNews();
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

  Future<void> fetchPremiumNews() async {
    premiumNewsState = const AsyncSnapshot<PremiumNews>.withData(
      ConnectionState.waiting,
      null,
    );
    notifyListeners();

    try {
      final result = await _newsRepository.fetchPremiumNews();
      if (result.isError) {
        premiumNewsState = AsyncSnapshot<PremiumNews>.withError(
          ConnectionState.done,
          result.asError.error,
        );
      }

      if (result.isValue) {
        premiumNewsState = AsyncSnapshot<PremiumNews>.withData(
          ConnectionState.done,
          result.asValue.value,
        );
      }
    } catch (e) {
      premiumNewsState = AsyncSnapshot<PremiumNews>.withError(
        ConnectionState.done,
        e,
      );
    } finally {
      notifyListeners();
    }
  }

  Future<void> fetchBreakingNewsDetail() async {
    final result = await _newsRepository.breakingNewsStorage;
    final breakingNews = result.asValue.value;
    breakingNewsDetail = AsyncSnapshot<Iterable<String>>.withData(
      ConnectionState.done,
      [
        breakingNews.title,
        breakingNews.description,
        breakingNews.image,
      ],
    );
    notifyListeners();
  }

  Future<void> fetchPremiumNewsDetail() async {
    final result = await _newsRepository.premiumNewsStorage;
    final premiumNews = result.asValue.value;
    premiumNewsDetail = AsyncSnapshot<Iterable<String>>.withData(
      ConnectionState.done,
      [
        premiumNews.title,
        premiumNews.description,
        premiumNews.image,
      ],
    );
    notifyListeners();
  }
}
