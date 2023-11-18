package org.example.clients;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeoutException;

public class ApiClientImpl implements ApiClient {
    private final HttpClient httpClient;

    public ApiClientImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public HttpResponse<String> getHttpResponse(HttpRequest request) {
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return (response.statusCode() == 200) ? response : null;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException();
        }
    }
}
