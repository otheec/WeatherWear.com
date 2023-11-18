package org.example.UI;

import org.example.AirportDataProvider;
import org.example.forecast.ForecastService;
import org.example.geo.GeoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

class CLITest {
    @Mock
    private ForecastService mockForecastService;

    @Mock
    private GeoService mockGeoServiceMain;

    @Mock
    private GeoService mockGeoServiceBackup;
    @Mock
    private Scanner scanner;

    @InjectMocks
    private CLI cli;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cli = new CLI(mockForecastService, mockGeoServiceMain, mockGeoServiceBackup, scanner);
    }

    @Test
    void testGetCurrentWeatherBackup() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        doThrow(RuntimeException.class).when(mockGeoServiceMain).getGeolocation();

        invokePrivateMethod(cli, "getCurrentWeather");

        verify(mockGeoServiceBackup, times(1)).getGeolocation();
    }

    @Test
    void testGetCurrentWeather() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        when(mockGeoServiceMain.getIp()).thenReturn("123.456.789.0");
        when(mockGeoServiceMain.getCity()).thenReturn("TestCity");
        when(mockGeoServiceMain.getCountry()).thenReturn("TestCountry");
        when(mockForecastService.getTempc_current()).thenReturn(25.0);
        when(mockForecastService.getPrecipitation_current()).thenReturn(0.1);

        invokePrivateMethod(cli, "getCurrentWeather");

        verify(mockForecastService).getWeather("123.456.789.0");
        verify(mockGeoServiceMain).getIp();
        verify(mockGeoServiceMain).getCity();
    }

    @Test
    void testGetForecastWeather() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        when(mockForecastService.getName()).thenReturn("TestCity");
        when(mockForecastService.getTempc_forecast()).thenReturn(25.0);
        when(mockForecastService.getPrecipitation_forecast()).thenReturn(0.1);
        mockStatic(AirportDataProvider.class);
        when(AirportDataProvider.ICAOProvider()).thenReturn("LMML");
        when(AirportDataProvider.dateProvider()).thenReturn("2023-11-11");

        invokePrivateMethod(cli, "getForecastWeather");

        verify(mockForecastService).getWeather("LMML", "2023-11-11");
        verify(mockForecastService).getName();
    }

    @Test
    void testGetSelectedOption1() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        when(scanner.hasNextInt()).thenReturn(true);
        when(scanner.nextInt()).thenReturn(1);

        int actual = (int) invokePrivateMethod(cli, "getSelectedOption");

        assertEquals(1,actual);
    }

    @Test
    void testGetSelectedOption2() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        String input = "2";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        when(scanner.hasNextInt()).thenReturn(true);
        when(scanner.nextInt()).thenReturn(2);

        int actual = (int) invokePrivateMethod(cli, "getSelectedOption");

        assertEquals(2,actual);
    }

    @Test
    void testGetSelectedOption3() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        String input = "3";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        when(scanner.hasNextInt()).thenReturn(true);
        when(scanner.nextInt()).thenReturn(3);

        int actual = (int) invokePrivateMethod(cli, "getSelectedOption");

        assertEquals(3,actual);
    }

    //invoke private methods to let them be able to test them
    //from GPT
    private Object invokePrivateMethod(Object object, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
        Method method = object.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        return method.invoke(object);
    }
}