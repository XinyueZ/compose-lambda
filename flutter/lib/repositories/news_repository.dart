import 'dart:convert';
import 'dart:io';

import 'package:async/async.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_compose_lambda/domains/breaking_news.dart';
import 'package:flutter_compose_lambda/domains/premium_news.dart';
import 'package:flutter_compose_lambda/network/rest_client.dart';
import 'package:flutter_compose_lambda/repositories/news_storage_repository.dart';

abstract class NewsRepository extends NewsStorageRepository {
  Future<Result<PremiumNews>> fetchPremiumNews();
  Future<Result<BreakingNews>> fetchBreakingNews();
}

class NewsRepositoryImpl implements NewsRepository {
  const NewsRepositoryImpl({
    @required RestClient restClient,
    @required NewsStorageRepository newsStorageRepository,
  })  : assert(restClient is RestClient),
        assert(newsStorageRepository is NewsStorageRepository),
        _restClient = restClient,
        _newsStorageRepository = newsStorageRepository;

  final RestClient _restClient;
  final NewsStorageRepository _newsStorageRepository;

  @override
  Future<Result<BreakingNews>> fetchBreakingNews() async {
    final result =
        await _restClient.get("/s/7fv2e7hsyz21rza/breaking-news.json");

    if (result.statusCode != HttpStatus.ok) {
      throw Exception(
          "Failed to get answer from Telematics API. Status code: ${result.statusCode}. Body: ${result.body}");
    }

    final now = DateTime.now().millisecondsSinceEpoch;

    if (now % 3 == 0) {
      return Result.error(Exception("Cannot fetch data."));
    }

    if (now % 5 == 0) {
      throw Exception("Fatal issue!");
    }

    final jsonBox = jsonDecode(result.body) as Map<String, dynamic>;
    final breakingNews = BreakingNews.fromJson(jsonBox);
    saveBreakingNews(breakingNews);
    return Result.value(breakingNews);
  }

  @override
  Future<Result<PremiumNews>> fetchPremiumNews() async {
    final result =
        await _restClient.get("/s/ibjhxji5o237497/premium-news.json");

    if (result.statusCode != HttpStatus.ok) {
      throw Exception(
          "Failed to get answer from Telematics API. Status code: ${result.statusCode}. Body: ${result.body}");
    }

    final now = DateTime.now().millisecondsSinceEpoch;

    if (now % 3 == 0) {
      return Result.error(Exception("Cannot fetch data."));
    }

    if (now % 5 == 0) {
      throw Exception("Fatal issue!");
    }

    final jsonBox = jsonDecode(result.body) as Map<String, dynamic>;
    final premiumNews = PremiumNews.fromJson(jsonBox);
    savePremiumNews(premiumNews);
    return Result.value(premiumNews);
  }

  @override
  Future<Result<BreakingNews>> get breakingNewsStorage async =>
      _newsStorageRepository.breakingNewsStorage;
  @override
  Future<void> saveBreakingNews(BreakingNews breakingNews) async {
    _newsStorageRepository.saveBreakingNews(breakingNews);
  }

  @override
  Future<Result<PremiumNews>> get premiumNewsStorage async =>
      _newsStorageRepository.premiumNewsStorage;

  @override
  Future<void> savePremiumNews(PremiumNews premiumNews) async {
    _newsStorageRepository.savePremiumNews(premiumNews);
  }
}
