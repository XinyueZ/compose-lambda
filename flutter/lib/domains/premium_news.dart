import 'package:flutter_compose_lambda/hive_models/news.dart';
import 'package:json_annotation/json_annotation.dart';

part 'premium_news.g.dart';

@JsonSerializable()
class PremiumNews {
  const PremiumNews({
    this.title,
    this.description,
    this.image,
  });
  final String title;
  final String description;
  final String image;

  factory PremiumNews.fromHive(News news) => PremiumNews(
        title: news.title,
        description: news.description,
        image: news.image,
      );
  News toHive() => News()
    ..type = 1
    ..title = title
    ..description = description
    ..image = image;

  factory PremiumNews.fromJson(Map<String, dynamic> json) =>
      _$PremiumNewsFromJson(json);
  Map<String, dynamic> toJson() => _$PremiumNewsToJson(this);
}
