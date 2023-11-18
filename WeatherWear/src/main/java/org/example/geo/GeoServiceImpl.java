package org.example.geo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.clients.ApiClient;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public abstract class GeoServiceImpl implements GeoService{
    private final String url;
    private String ip;
    private String city;
    private String country;
    public String getIp() {
        return ip;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    private final ApiClient apiClient;

    public GeoServiceImpl(String url, ApiClient apiClient) {
        this.apiClient = apiClient;
        this.url = url;
    }

    protected void setIp(String ip) {
        this.ip = ip;
    }

    protected void setCity(String city) {
        this.city = city;
    }

    protected void setCountry(String country) {
        this.country = country;
    }

    public void getGeolocation() {
        fetchGeolocation(getJsonNodeFromHttpResponse(Objects.requireNonNull(apiClient.getHttpResponse(getRequest(url)))));
    }

    private JsonNode getJsonNodeFromHttpResponse(HttpResponse<String> response) {
        JsonNode ret;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ret = objectMapper.readTree(response.body());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    protected abstract void fetchGeolocation(JsonNode geolocation);
    protected abstract HttpRequest getRequest(String url);
}
