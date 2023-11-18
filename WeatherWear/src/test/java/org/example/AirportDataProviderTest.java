package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;


class AirportDataProviderTest {

    private final String ICAOpatter = "^[a-zA-Z]{4}$";
    private final String datePatter = "\\d{4}-\\d{2}-\\d{2}";
    private final String localDatePatter = "yyyy-MM-dd";

    @Test
    void testInputFormatValidatorAirportValid() {
        boolean expected = true;

        boolean actual = AirportDataProvider.inputFormatValidator(ICAOpatter, "LMML");

        assertEquals(expected, actual);
    }

    @Test
    void testInputFormatValidatorAirportInvalidShorter() {
        boolean expected = false;

        boolean actual = AirportDataProvider.inputFormatValidator(ICAOpatter, "LML");

        assertEquals(expected, actual);
    }

    @Test
    void testInputFormatValidatorAirportInvalidLonger() {
        boolean expected = false;

        boolean actual = AirportDataProvider.inputFormatValidator(ICAOpatter, "LMLLL");

        assertEquals(expected, actual);
    }

    @Test
    void testInputFormatValidatorAirportInvalidEmpty() {
        boolean expected = false;

        boolean actual = AirportDataProvider.inputFormatValidator(ICAOpatter, "");

        assertEquals(expected, actual);
    }

    @Test
    void testInputFormatValidatorDateValid() {
        boolean expected = true;

        boolean actual = AirportDataProvider.inputFormatValidator(datePatter, "1999-12-10");

        assertEquals(expected, actual);
    }

    @Test
    void testInputFormatValidatorDateInvalidYear() {
        boolean expected = false;

        boolean actual = AirportDataProvider.inputFormatValidator(datePatter, "200-12-30");

        assertEquals(expected, actual);
    }

    @Test
    void testInputFormatValidatorDateInvalidMonth() {
        boolean expected = false;

        boolean actual = AirportDataProvider.inputFormatValidator(datePatter, "2000-113-30");

        assertEquals(expected, actual);
    }

    @Test
    void testDateCheckerToday() {
        boolean expected = true;
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(localDatePatter);
        String formattedDate = today.format(formatter);

        boolean actual = AirportDataProvider.dateChecker(formattedDate);

        assertEquals(expected, actual);
    }

    @Test
    void testDateCheckerYesterday() {
        boolean expected = false;
        LocalDate today = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(localDatePatter);
        String formattedDate = today.format(formatter);

        boolean actual = AirportDataProvider.dateChecker(formattedDate);

        assertEquals(expected, actual);
    }

    @Test
    void testDateCheckerOver10DaysLimit() {
        boolean expected = false;
        LocalDate today = LocalDate.now().plusDays(11);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(localDatePatter);
        String formattedDate = today.format(formatter);

        boolean actual = AirportDataProvider.dateChecker(formattedDate);

        assertEquals(expected, actual);
    }

    //used chat gpt for the lambda function to test exception
    @Test
    void testDateCheckerParseException() {
        assertThrows(RuntimeException.class, () -> {AirportDataProvider.dateChecker(localDatePatter);});
    }

    @Test
    void testDateProviderValidToday() {
        InputStream originalIn = System.in;
        String input = LocalDate.now().format(DateTimeFormatter.ofPattern(localDatePatter));
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        String result = AirportDataProvider.dateProvider();

        assertEquals(input, result);
        // Reset System.in to its original state
        System.setIn(originalIn);
    }

    @Test
    void testDateProviderInvalidPlus10Days() {
        InputStream originalIn = System.in;
        String input = LocalDate.now().plusDays(10).format(DateTimeFormatter.ofPattern(localDatePatter));
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        String result = AirportDataProvider.dateProvider();

        assertEquals(input, result);

        System.setIn(originalIn);
    }

    @Test
    void testDateProviderInvalidPlus11Days() {
        InputStream originalIn = System.in;
        String input = LocalDate.now().plusDays(11).format(DateTimeFormatter.ofPattern(localDatePatter));
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        String result = AirportDataProvider.dateProvider();

        assertNull(result);

        System.setIn(originalIn);
    }

    @Test
    void testDateProviderInvalidMinus11Days() {
        InputStream originalIn = System.in;
        String input = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern(localDatePatter));
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        String result = AirportDataProvider.dateProvider();

        assertNull(result);

        System.setIn(originalIn);
    }


    @Test
    void testICAOProviderValid() {
        InputStream originalIn = System.in;
        String input = "LMML";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        String result = AirportDataProvider.ICAOProvider();

        assertEquals(input, result);

        System.setIn(originalIn);
    }


    @Test
    void testICAOProviderInvalid() {
        InputStream originalIn = System.in;
        String input = "ABCDE";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        String result = AirportDataProvider.ICAOProvider();

        assertNull(result);

        System.setIn(originalIn);
    }
}