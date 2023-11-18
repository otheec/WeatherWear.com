package org.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AirportDataProvider {

    public static String dateProvider(){
        Scanner scanner = new Scanner(System.in);
        String ret;

        System.out.println("Enter date in format: YYYY-MM-DD");

        ret = scanner.nextLine();

        if (inputFormatValidator("\\d{4}-\\d{2}-\\d{2}", ret) && dateChecker(ret)) {
            System.out.println("Your input date: " + ret);
            return ret;
        } else {
            System.out.println("Invalid date format. Please enter a date in the format YYYY-MM-DD.");
            return null;
        }
    }

    public static String ICAOProvider() {
        Scanner scanner = new Scanner(System.in);
        String ret;

        System.out.println("Enter ICAO (4 symbols)");

        ret = scanner.nextLine();

        if (inputFormatValidator("^[a-zA-Z]{4}$", ret)) {
            System.out.println("Your input date: " + ret);
            return ret;
        } else {
            System.out.println("Invalid date format. Please enter a date in the format YYYY-MM-DD.");
            return null;
        }
    }

    //this method is from chatgpt
    public static boolean inputFormatValidator(String pattern, String data) {
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(data);
            return matcher.matches();
    }

    public static boolean dateChecker(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar yesterday = Calendar.getInstance();
        Calendar endLimit = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        endLimit.add(Calendar.DAY_OF_MONTH, 10);

        Date parsedDate;

        try {
            parsedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return parsedDate.after(yesterday.getTime()) && parsedDate.before(endLimit.getTime());
    }
}
