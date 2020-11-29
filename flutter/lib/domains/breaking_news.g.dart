// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'breaking_news.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

BreakingNews _$BreakingNewsFromJson(Map<String, dynamic> json) {
  return BreakingNews(
    title: json['title'] as String,
    description: json['description'] as String,
    image: json['image'] as String,
  );
}

Map<String, dynamic> _$BreakingNewsToJson(BreakingNews instance) =>
    <String, dynamic>{
      'title': instance.title,
      'description': instance.description,
      'image': instance.image,
    };
