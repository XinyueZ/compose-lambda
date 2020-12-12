import 'package:hive/hive.dart';

part 'news.g.dart';

@HiveType(typeId: 0)
class News extends HiveObject {
  @HiveField(0)
  int type;

  @HiveField(1)
  String title;

  @HiveField(2)
  String description;

  @HiveField(3)
  String image;
}
