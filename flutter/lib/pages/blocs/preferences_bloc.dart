import 'package:flutter/foundation.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_compose_lambda/repositories/preferences_repository.dart';

class PreferencesBloc extends ChangeNotifier {
  PreferencesBloc({
    @required PreferencesRepository preferencesRepository,
  })  : assert(preferencesRepository is PreferencesRepository),
        _preferencesRepository = preferencesRepository;

  final PreferencesRepository _preferencesRepository;

  AsyncSnapshot<bool> followSystemTheme = const AsyncSnapshot<bool>.nothing();

  Future<void> fetchFollowSystemTheme() async {
    followSystemTheme = AsyncSnapshot<bool>.withData(
      ConnectionState.done,
      await _preferencesRepository.followSystemTheme,
    );
    notifyListeners();
  }

  Future<void> setFollowSystemTheme(bool value) async {
    await _preferencesRepository.setFollowSystemTheme(value);
    fetchFollowSystemTheme();
  }
}
