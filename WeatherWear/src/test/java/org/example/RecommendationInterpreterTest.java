package org.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class RecommendationInterpreterTest {

    /*@BeforeEach - lepe citelne, vytvari globalni promenne
    @AfterEach - teardown, realne to je do garbage collectoru
    @BeforeAll
    @AfterAll*/

    /*
    GOOD UNIT TEST
    test ma nice jmeno
    test one thing
    always returns same result
    has no conditional logic
    independent on other tests
    understandable so it can act as documentation
     */

    /*
    BAD UNIT TEST
    communicates to database
    communicate across network
    interacts with file system
    must be executed after specific test
    requires configuration on environment
     */

    @Test
    void TestGetWearTipLowTemp() {
        String expected = "It is cold so you should wear warm clothing";

        String actual = RecommendationInterpreter.getWearTip(14.9);

        assertEquals(expected, actual);
    }

    @Test
    void testGetWearTipMiddleTemp() {
        String expected = "It is cold so you should wear warm clothing";

        String actual = RecommendationInterpreter.getWearTip(15);

        assertEquals(expected, actual);
    }

    @Test
    void testGetWearTipHighTemp() {
        String expected = "It is warm so you should wear light clothing";

        String actual = RecommendationInterpreter.getWearTip(16);

        assertEquals(expected, actual);
    }

    @Test
    void testGetRainTipRaining() {
        String expected = "It is currently raining so you do need an umbrella";

        String actual = RecommendationInterpreter.getRainTip(2);

        assertEquals(expected, actual);
    }

    @Test
    void testGetRainTipNotRaining() {
        String expected = "It is not raining so you do not need an umbrella";

        String actual = RecommendationInterpreter.getRainTip(0);

        assertEquals(expected, actual);
    }
}