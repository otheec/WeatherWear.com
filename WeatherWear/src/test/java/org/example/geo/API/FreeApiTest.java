package org.example.geo.API;

import org.example.clients.ApiClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FreeApiTest {
    @Mock
    private ApiClient apiClient;

    @Mock
    private HttpResponse<String> httpResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetGeolocation() {
        when(apiClient.getHttpResponse(any(HttpRequest.class))).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn("{\"ipAddress\":\"0.0.0.0\",\"cityName\":\"Msida\",\"countryName\":\"Malta\"}");
        FreeApi freeApi = new FreeApi(apiClient);

        freeApi.getGeolocation();

        Assertions.assertEquals("0.0.0.0", freeApi.getIp());
        Assertions.assertEquals("Malta", freeApi.getCountry());
        Assertions.assertEquals("Msida", freeApi.getCity());
    }

    @Test
    public void testJsonParsingException() {
        when(apiClient.getHttpResponse(any(HttpRequest.class))).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn("invalid json data");

        FreeApi freeApi = new FreeApi(apiClient);

        Assertions.assertThrows(RuntimeException.class, freeApi::getGeolocation);
    }
}