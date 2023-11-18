package org.example.clients;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public interface ApiClient {
    public HttpResponse<String> getHttpResponse(HttpRequest request);
}
