package org.example.geo;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.clients.ApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GeoServiceImplTest {

    //TODO test uspesny
    @Mock
    private ApiClient mockedApiClient;
    @Mock
    private HttpRequest mockedRequest;
    @Mock
    private HttpResponse<String> mockedResponse;

    private GeoServiceImpl geoService;

    @BeforeEach
    void setUp() {
        geoService = geoService();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetGeolocationWithNullHttpResponse() {
        when(mockedApiClient.getHttpResponse(mockedRequest)).thenReturn(null);
        assertThrows(NullPointerException.class, geoService::getGeolocation);
    }

    private GeoServiceImpl geoService() {
        return new GeoServiceImpl("www.adddress.com", mockedApiClient) {
            @Override
            protected void fetchGeolocation(JsonNode geolocation) {

            }
            @Override
            protected HttpRequest getRequest(String url) {
                return mockedRequest;
            }
        };
    }
}