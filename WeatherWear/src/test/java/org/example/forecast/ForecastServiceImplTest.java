package org.example.forecast;

import org.example.clients.ApiClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.http.HttpResponse;
import java.net.http.HttpRequest;



class ForecastServiceImplTest {
    @Mock
    private ApiClient apiClient;
    private ForecastServiceImpl forecastService;
    private String validJson = Files.readString(Path.of("src/test/java/org/example/JSONs/Forecast.json"));
    private String invalidJson = Files.readString(Path.of("src/test/java/org/example/JSONs/InvalidJson.json"));

    @Mock
    private HttpResponse<String> httpResponse;

    ForecastServiceImplTest() throws IOException {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        apiClient = mock(ApiClient.class);
        forecastService = new ForecastServiceImpl(apiClient);
    }

    @Test
    void testGetWeatherByIp() {
        when(apiClient.getHttpResponse(any(HttpRequest.class))).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn(validJson);

        forecastService.getWeather("IP");

        Assertions.assertEquals("Luqa", forecastService.getName());
        Assertions.assertEquals(23.0, forecastService.getTempc_current());
        Assertions.assertEquals(0.03, forecastService.getPrecipitation_current());
    }

    @Test
    void testGetWeatherByMetarAndDate() {
        when(apiClient.getHttpResponse(any(HttpRequest.class))).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn(validJson);

        forecastService.getWeather("metar", "date");

        Assertions.assertEquals("Luqa", forecastService.getName());
        Assertions.assertEquals(20.8, forecastService.getTempc_forecast());
        Assertions.assertEquals(4.66, forecastService.getPrecipitation_forecast());
    }


    @Test
    void testAssignValuesFromHttpResponseJsonException() {
        when(apiClient.getHttpResponse(any(HttpRequest.class))).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn(invalidJson);

        Assertions.assertThrows(RuntimeException.class, () -> forecastService.getWeather("metar", "date"));
    }

}