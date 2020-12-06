import 'package:async/async.dart';
import 'package:flutter_compose_lambda/domains/breaking_news.dart';
import 'package:flutter_compose_lambda/domains/premium_news.dart';
import 'package:flutter_compose_lambda/hive_models/news.dart';
import 'package:hive/hive.dart';

abstract class NewsStorageRepository {
  Future<Result<PremiumNews>> get premiumNewsStorage;
  Future<void> saveBreakingNews(BreakingNews breakingNews);
  Future<Result<BreakingNews>> get breakingNewsStorage;
  Future<void> savePremiumNews(PremiumNews premiumNews);
}

class NewsStorageRepositoryImpl implements NewsStorageRepository {
  @override
  Future<Result<BreakingNews>> get breakingNewsStorage async {
    final box = await Hive.openBox<News>('news_storage');
    final news = box.get(0); // 0 is breaking-news
    return ValueResult(BreakingNews.fromHive(news));
  }

  @override
  Future<void> saveBreakingNews(BreakingNews breakingNews) async {
    final box = await Hive.openBox<News>('news_storage');
    box.put(0, breakingNews.toHive());
  }

  @override
  Future<Result<PremiumNews>> get premiumNewsStorage async {
    final box = await Hive.openBox<News>('news_storage');
    final news = box.get(1); // 0 is premium-news
    return ValueResult(PremiumNews.fromHive(news));
  }

  @override
  Future<void> savePremiumNews(PremiumNews premiumNews) async {
    final box = await Hive.openBox<News>('news_storage');
    box.put(1, premiumNews.toHive());
  }
}
