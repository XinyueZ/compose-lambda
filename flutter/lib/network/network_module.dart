import 'package:flutter_compose_lambda/network/app_http_client.dart';
import 'package:flutter_compose_lambda/network/rest_client.dart';
import 'package:http/http.dart';
import 'package:provider/provider.dart';
import 'package:provider/single_child_widget.dart';

List<SingleChildWidget> networkModule = <SingleChildWidget>[
  Provider<RestClient>(
    create: (_) => RestClient(
      baseUrl: "https://dl.dropboxusercontent.com/",
      client: AppHttpClient(
        client: Client(),
      ),
    ),
  ),
];
