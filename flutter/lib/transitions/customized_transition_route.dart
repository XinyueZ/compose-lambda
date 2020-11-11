import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class NoTransitionPageRoute<T> extends MaterialPageRoute<T> {
  NoTransitionPageRoute({
    @required WidgetBuilder builder,
    RouteSettings settings,
  }) : super(
            builder: builder,
            settings: settings,
            maintainState: true,
            fullscreenDialog: true);

  @override
  Duration get transitionDuration => const Duration(seconds: 0);

  @override
  Widget buildTransitions(BuildContext context, Animation<double> animation,
          Animation<double> secondaryAnimation, Widget child) =>
      Container(child: child);
}

class ScaleTransitionPageRoute<T> extends MaterialPageRoute<T> {
  ScaleTransitionPageRoute({
    @required WidgetBuilder builder,
    RouteSettings settings,
  }) : super(
            builder: builder,
            settings: settings,
            maintainState: true,
            fullscreenDialog: true);

  @override
  Duration get transitionDuration => const Duration(seconds: 1);

  Widget _buildScaleTransition(Widget child) {
    return ScaleTransition(
      scale: CurvedAnimation(parent: animation, curve: Curves.elasticInOut),
      child: child,
    );
  }

  @override
  Widget buildTransitions(BuildContext context, Animation<double> animation,
      Animation<double> secondaryAnimation, Widget child) {
    return _buildScaleTransition(child);
  }
}

class SlideTransitionPageRoute<T> extends MaterialPageRoute<T> {
  SlideTransitionPageRoute({
    @required WidgetBuilder builder,
    RouteSettings settings,
  }) : super(
            builder: builder,
            settings: settings,
            maintainState: true,
            fullscreenDialog: true);

  @override
  Duration get transitionDuration => const Duration(seconds: 1);

  Widget _buildSlideTransition(Widget child) {
    final begin = Offset(0.0, 1.0);
    final end = Offset.zero;
    final curve = Curves.ease;

    final tween = Tween(begin: begin, end: end).chain(CurveTween(curve: curve));

    return SlideTransition(
      position: animation.drive(tween),
      child: child,
    );
  }

  @override
  Widget buildTransitions(BuildContext context, Animation<double> animation,
      Animation<double> secondaryAnimation, Widget child) {
    return _buildSlideTransition(child);
  }
}
