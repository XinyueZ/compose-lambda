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

  factory BreakingNews.fromJson(Map<String, dynamic> json) =>
      _$BreakingNewsFromJson(json);
  Map<String, dynamic> toJson() => _$BreakingNewsToJson(this);
}
