package org.example;

public class RecommendationInterpreter {
    private static final double tempc_threshhold = 15;
    private static final double precipitation_threshhold = 0;
    public static String getWearTip(double tempc){
        if (tempc > tempc_threshhold) {
            return "It is warm so you should wear light clothing";
        } else return "It is cold so you should wear warm clothing";
    }

    public static String getRainTip(double precipitation){
        if (precipitation != precipitation_threshhold) {
            return  "It is currently raining so you do need an umbrella";
        } else return  "It is not raining so you do not need an umbrella";
    }
}
