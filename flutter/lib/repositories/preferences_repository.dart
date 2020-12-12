import 'package:hive/hive.dart';

abstract class PreferencesRepository {
  Future<bool> get followSystemTheme;
  Future<void> setFollowSystemTheme(bool value);
}

class PreferencesRepositoryImpl implements PreferencesRepository {
  static const String kAppPreferences = "app_preferences";
  static const String kFollowSystemKey = "key_follow_system_key";

  @override
  Future<bool> get followSystemTheme async {
    final box = await Hive.openBox<dynamic>(kAppPreferences);
    return box.get(kFollowSystemKey, defaultValue: true) as bool;
  }

  @override
  Future<void> setFollowSystemTheme(bool value) async {
    final box = await Hive.openBox<dynamic>(kAppPreferences);
    await box.put(kFollowSystemKey, value);
  }
}
