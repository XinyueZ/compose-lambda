import 'dart:convert';
import 'dart:io';

import 'package:async/async.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_compose_lambda/domains/breaking_news.dart';
import 'package:flutter_compose_lambda/domains/premium_news.dart';
import 'package:flutter_compose_lambda/network/rest_client.dart';

abstract class NewsRepository {
  Future<Result<PremiumNews>> fetchPremiumNews();
  Future<Result<BreakingNews>> fetchBreakingNews();
}

class NewsRepositoryImpl implements NewsRepository {
  const NewsRepositoryImpl({
    @required RestClient restClient,
  })  : assert(restClient is RestClient),
        _restClient = restClient;

  final RestClient _restClient;

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
    return Result.value(BreakingNews.fromJson(jsonBox));
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
    return Result.value(PremiumNews.fromJson(jsonBox));
  }
}
