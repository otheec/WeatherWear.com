package org.example.geo.API;

import org.example.clients.ApiClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class IpApiTest {
    @Mock
    private ApiClient apiClient;

    @Mock
    private HttpResponse<String> httpResponse;

    @Test
    public void testGetGeolocation() {
        MockitoAnnotations.openMocks(this);
        when(apiClient.getHttpResponse(any(HttpRequest.class))).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn("{\"query\":\"0.0.0.0\",\"city\":\"Msida\",\"country\":\"Malta\"}");
        IpApi ipApi = new IpApi(apiClient);

        ipApi.getGeolocation();

        Assertions.assertEquals("0.0.0.0", ipApi.getIp());
        Assertions.assertEquals("Malta", ipApi.getCountry());
        Assertions.assertEquals("Msida", ipApi.getCity());
    }
}