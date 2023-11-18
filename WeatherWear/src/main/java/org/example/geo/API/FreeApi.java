package org.example.geo.API;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.clients.ApiClient;
import org.example.geo.GeoServiceImpl;

import java.net.URI;
import java.net.http.HttpRequest;

public class FreeApi extends GeoServiceImpl {
    public static final String url = "https://freeipapi.com/api/json/";

    public FreeApi(ApiClient apiClient) {
        super(url, apiClient);
    }

    protected HttpRequest getRequest(String url) {
        return HttpRequest.newBuilder()
        .uri(URI.create(url))
        .GET()
        .build();
    }

    protected void fetchGeolocation(JsonNode geolocation) {
        if (geolocation != null) {
            setIp(geolocation.get("ipAddress").asText());
            setCity(geolocation.get("cityName").asText());
            setCountry(geolocation.get("countryName").asText());
        }
    }
}
