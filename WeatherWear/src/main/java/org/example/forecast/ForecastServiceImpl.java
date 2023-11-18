package org.example.forecast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.clients.ApiClient;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class ForecastServiceImpl implements ForecastService{
    private double tempc_current;
    private double precipitation_current;
    private double tempc_forecast;
    private double precipitation_forecast;
    private String name; //for airports
    private final ApiClient apiClient;

    public ForecastServiceImpl(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public double getTempc_current() {
        return tempc_current;
    }

    public double getPrecipitation_current() {
        return precipitation_current;
    }

    public double getTempc_forecast() {
        return tempc_forecast;
    }

    public double getPrecipitation_forecast() {
        return precipitation_forecast;
    }

    public String getName() {
        return name;
    }

    public void getWeather(String metar, String date) {
        assignValuesFromHttpResponse(Objects.requireNonNull(apiClient.getHttpResponse(getRequest("metar%3A" + metar + "&days=1&dt=" + date))));
    }

    public void getWeather(String IPAddress) {
        assignValuesFromHttpResponse(Objects.requireNonNull(apiClient.getHttpResponse(getRequest(IPAddress))));

        HttpRequest request = getRequest(IPAddress);
        HttpResponse<String> response = apiClient.getHttpResponse(Objects.requireNonNull(request));
        assignValuesFromHttpResponse(response);

    }

    private void assignValuesFromHttpResponse(HttpResponse<String> response) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(response.body());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        tempc_current = rootNode.get("current").get("temp_c").asDouble();
        precipitation_current = rootNode.get("current").get("precip_mm").asDouble();
        tempc_forecast = rootNode.get("forecast").get("forecastday").get(0).get("day").get("avgtemp_c").asDouble();
        precipitation_forecast = rootNode.get("forecast").get("forecastday").get(0).get("day").get("totalprecip_mm").asDouble();
        name = rootNode.get("location").get("name").asText();
    }

    private HttpRequest getRequest(String q) {
        String uri = "https://weatherapi-com.p.rapidapi.com/forecast.json?q=" + q;

        return HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("X-RapidAPI-Key", "e3d57177d6msh2a2941a01bda8ccp1ca667jsnfa65e5ce8e1c")
                .header("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
    }
}
