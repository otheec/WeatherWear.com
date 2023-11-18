package org.example;

import org.example.UI.CLI;
import org.example.clients.ApiClientImpl;
import org.example.forecast.ForecastServiceImpl;
import org.example.geo.API.FreeApi;
import org.example.geo.API.IpApi;

import java.net.http.HttpClient;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CLI cli = new CLI(new ForecastServiceImpl(new ApiClientImpl(HttpClient.newHttpClient())), new IpApi(new ApiClientImpl(HttpClient.newHttpClient())), new FreeApi(new ApiClientImpl(HttpClient.newHttpClient())), new Scanner(System.in));
        cli.menu();
    }
}