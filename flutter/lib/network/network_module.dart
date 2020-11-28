import 'package:flutter_compose_lambda/network/app_http_client.dart';
import 'package:flutter_compose_lambda/network/rest_client.dart';
import 'package:http/http.dart';
import 'package:provider/provider.dart';
import 'package:provider/single_child_widget.dart';

List<SingleChildWidget> networkProviders = <SingleChildWidget>[
  Provider<AppHttpClient>(
    create: (_) => AppHttpClient(
      client: Client(),
    ),
  ),
  ProxyProvider<Client, RestClient>(
      create: (_) => null,
      update: (
        _,
        Client client,
        RestClient previous,
      ) {
        if (previous != null) {
          return previous;
        }
        return RestClient(
          baseUrl: "https://dl.dropboxusercontent.com",
          client: client,
        );
      }),
];
