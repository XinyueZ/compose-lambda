// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'premium_news.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

PremiumNews _$PremiumNewsFromJson(Map<String, dynamic> json) {
  return PremiumNews(
    title: json['title'] as String,
    description: json['description'] as String,
    image: json['image'] as String,
  );
}

Map<String, dynamic> _$PremiumNewsToJson(PremiumNews instance) =>
    <String, dynamic>{
      'title': instance.title,
      'description': instance.description,
      'image': instance.image,
    };
