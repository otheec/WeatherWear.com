package org.example.clients;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ApiClientImplTest {
    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpResponse<String> httpResponse;
    @InjectMocks
    private ApiClientImpl apiClient;
    private final HttpRequest request = HttpRequest.newBuilder().uri(new URI("http://example.com")).build();

    //exception because of the new URI
    ApiClientImplTest() throws URISyntaxException {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetHttpResponseValid() throws Exception {
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);
        when(httpResponse.statusCode()).thenReturn(200);

        HttpResponse<String> response = apiClient.getHttpResponse(request);

        assertNotNull(response);
        verify(httpClient, times(1)).send(eq(request), any(HttpResponse.BodyHandler.class));
    }

    @Test
    void testGetHttpResponseInvalid() throws Exception {
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);
        when(httpResponse.statusCode()).thenReturn(404);

        HttpResponse<String> response = apiClient.getHttpResponse(request);

        assertNull(response);
        verify(httpClient, times(1)).send(eq(request), any(HttpResponse.BodyHandler.class));
    }

    @Test
    void testGetHttpResponseThrowInterruptedException() throws Exception {
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new InterruptedException());

        assertThrows(RuntimeException.class, () -> apiClient.getHttpResponse(request));

        verify(httpClient, times(1)).send(eq(request), any(HttpResponse.BodyHandler.class));
    }

    @Test
    void testGetHttpResponseThrowIOException() throws Exception {
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException());

        assertThrows(RuntimeException.class, () -> apiClient.getHttpResponse(request));

        verify(httpClient, times(1)).send(eq(request), any(HttpResponse.BodyHandler.class));
    }
}