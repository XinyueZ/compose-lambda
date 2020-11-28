import 'dart:async';

import 'package:http/http.dart';
import 'package:meta/meta.dart';

class RestClient {
  RestClient({
    @required String baseUrl,
    @required Client client,
  })  : assert(baseUrl != null && client != null),
        _entryPoint = baseUrl,
        _skyHttpClient = client;

  final Client _skyHttpClient;
  final String _entryPoint;

  final Map<String, String> _authHeader = <String, String>{_authKey: ""};

  static const String _authKey = "Authorization";
  Map<String, String> get _basicHeader => <String, String>{
        'Accept': 'application/json',
        "Content-Type": "application/json",
      };

  Future<Response> get(String endpoint, {Map<String, String> headers}) async {
    return await _skyHttpClient.get(
      _entryPoint + endpoint,
      headers: _buildFullHeader(headers: headers),
    );
  }

  Future<Response> post(
    String endpoint, {
    Map<String, String> headers,
    Object body,
  }) async {
    return await _skyHttpClient.post(
      _entryPoint + endpoint,
      headers: _buildFullHeader(headers: headers),
      body: body,
    );
  }

  Future<Response> put(
    String endpoint, {
    Map<String, String> headers,
    Object body,
  }) async {
    return await _skyHttpClient.put(
      _entryPoint + endpoint,
      headers: _buildFullHeader(headers: headers),
      body: body,
    );
  }

  Future<Response> delete(
    String endpoint, {
    Map<String, String> headers,
    Object body,
  }) async {
    return await _skyHttpClient.delete(
      _entryPoint + endpoint,
      headers: _buildFullHeader(headers: headers),
    );
  }

  Map<String, String> _buildBasicHeader({Map<String, String> headers}) =>
      <String, String>{..._authHeader, ...?headers};

  Map<String, String> _buildFullHeader({Map<String, String> headers}) =>
      _buildBasicHeader(headers: <String, String>{
        ..._basicHeader,
        ...?headers,
      });
}
