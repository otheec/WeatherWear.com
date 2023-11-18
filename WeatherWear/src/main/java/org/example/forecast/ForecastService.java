package org.example.forecast;

import java.net.http.HttpResponse;

public interface ForecastService {
    public double getTempc_current();

    public double getPrecipitation_current();

    public double getTempc_forecast();

    public double getPrecipitation_forecast();

    public String getName();

    public void getWeather(String metar, String date);

    public void getWeather(String IPAddress);
}
