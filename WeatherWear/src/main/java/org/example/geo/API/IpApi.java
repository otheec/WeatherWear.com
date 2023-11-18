package org.example.geo.API;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.clients.ApiClient;
import org.example.geo.GeoServiceImpl;

import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;

public class IpApi extends GeoServiceImpl {
    public static final String url = "http://ip-api.com/json/?fields=status,message,country,city,query";

    public IpApi(ApiClient apiClient) {
        super(url, apiClient);
    }

    protected HttpRequest getRequest(String url) {
        return HttpRequest.newBuilder()
                .timeout(Duration.ofSeconds(3)) //3 secs request timeout
                .uri(URI.create(url))
                .GET()
                .build();
    }

    protected void fetchGeolocation(JsonNode geolocation) {
        if (geolocation != null) {
            setIp(geolocation.get("query").asText());
            setCity(geolocation.get("city").asText());
            setCountry(geolocation.get("country").asText());
        }
    }
}
