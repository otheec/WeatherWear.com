package org.example.UI;

import org.example.AirportDataProvider;
import org.example.forecast.ForecastService;
import org.example.RecommendationInterpreter;
import org.example.geo.GeoService;

import java.util.Objects;
import java.util.Scanner;

public class CLI {

    private final ForecastService forecastService;
    private final GeoService geoServiceMain;
    private final GeoService geoServiceBackUP;
    private final Scanner scanner;

    public CLI(ForecastService forecastService, GeoService geoServiceMain, GeoService geoServiceBackUP, Scanner scanner) {
        this.forecastService = forecastService;
        this.geoServiceMain = geoServiceMain;
        this.geoServiceBackUP = geoServiceBackUP;
        this.scanner = scanner;
    }

    public void menu() {
        int end = 1;
        do {
            printMenuOptions();

            switch (getSelectedOption()) {
                case 1:
                    getCurrentWeather();
                    break;
                case 2:
                    getForecastWeather();
                    break;
                case 3:
                    end = 0;
            }
        } while (end == 1);
    }


    private void printMenuOptions(){
        /*System.out.println("WeatherWear.com");
        System.out.println("---------------");
        System.out.println("1. Recommend clothing for current location");
        System.out.println("2. Recommend clothing for future location");
        System.out.println("3. Exit");
        System.out.println("\nEnter choice: __");*/
        System.out.println("WeatherWear.com\n---------------\n1. Recommend clothing for current location\n2. Recommend clothing for future location\n3. Exit\n\nEnter choice: __");
    }

    private int getSelectedOption() {
        int number = -1;
        do {
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                System.out.println("You entered: " + number);
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        } while (number != 1 && number != 2 && number != 3);
        return number;
    }

    private void getCurrentWeather() {
        try {
            geoServiceMain.getGeolocation();
            printCurrentWeather(geoServiceMain);
        } catch (RuntimeException e) {
            geoServiceBackUP.getGeolocation();
            printCurrentWeather(geoServiceBackUP);
        }
        System.out.println(RecommendationInterpreter.getWearTip(forecastService.getTempc_current()));
        System.out.println(RecommendationInterpreter.getRainTip(forecastService.getPrecipitation_current()));
    }

    private void getForecastWeather() {
        String date;
        String ICAO;
        do {
            date = AirportDataProvider.dateProvider();
        } while (date == null);
        do {
            ICAO = AirportDataProvider.ICAOProvider();
        } while (ICAO == null);
        forecastService.getWeather(ICAO, date);
        System.out.println("Airport: " + forecastService.getName());
        System.out.println(RecommendationInterpreter.getWearTip(forecastService.getTempc_forecast()));
        System.out.println(RecommendationInterpreter.getRainTip(forecastService.getPrecipitation_forecast()));
    }

    private void printCurrentWeather(GeoService geoService) {
        forecastService.getWeather(geoService.getIp());
        System.out.println("City: " + geoService.getCity() + ", " + geoService.getCountry());
    }
}
