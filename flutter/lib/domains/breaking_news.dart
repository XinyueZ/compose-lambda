import 'package:flutter_compose_lambda/hive_models/news.dart';
import 'package:json_annotation/json_annotation.dart';

part 'breaking_news.g.dart';

@JsonSerializable()
class BreakingNews {
  const BreakingNews({
    this.title,
    this.description,
    this.image,
  });
  final String title;
  final String description;
  final String image;

  factory BreakingNews.fromHive(News news) => BreakingNews(
        title: news.title,
        description: news.description,
        image: news.image,
      );
  News toHive() => News()
    ..type = 0
    ..title = title
    ..description = description
    ..image = image;

  factory BreakingNews.fromJson(Map<String, dynamic> json) =>
      _$BreakingNewsFromJson(json);
  Map<String, dynamic> toJson() => _$BreakingNewsToJson(this);
}
