// Dart imports:
import 'dart:async';
import 'dart:io';

// Flutter imports:
import 'package:flutter/material.dart';
// Package imports:
import 'package:http/http.dart';

class AppHttpClient extends BaseClient {
  AppHttpClient({
    @required Client client,
  })  : assert(client != null),
        _client = client;

  final Client _client;

  @override
  Future<StreamedResponse> send(BaseRequest request) async {
    try {
      for (var i = 0; i < 3; i++) {
        final response = await _send(request);
        if (response.statusCode >= 300 && response.statusCode <= 499) {
          // Need exclude: 404, 409, 412, they are reserved code by MOIA Fleet.
          // Check https://github.com/moia-dev/fleet-backend/blob/develop/docs/fleet-docs-swagger_app.json
          switch (response.statusCode) {
            case HttpStatus.forbidden:
            case HttpStatus.notFound:
            case HttpStatus.conflict:
            case HttpStatus.preconditionFailed:
            case HttpStatus.tooManyRequests: // Don't retry if too many request
            case HttpStatus.unauthorized:
            case HttpStatus.badRequest:
              return response;
          }
          continue;
        } else {
          return response;
        }
      }
    } catch (e) {
      throw ClientException(e.toString());
    }

    final otherException =
        ClientException("Invalid request, no response from server.");
    throw otherException;
  }

  Future<StreamedResponse> _send(BaseRequest request) async {
    request.headers["Content-Type"] =
        request.headers["Content-Type"].replaceAll("; charset=utf-8", "");
    final ongoingRequest = _client.send(request);
    final response = await ongoingRequest.timeout(
      const Duration(
        seconds: 30,
      ),
    );
    return response;
  }
}
