package ru.netology.geo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import static org.junit.jupiter.api.Assertions.fail;

public class GeoServiceTest {
    @Test
    public void localHostTest() {
        GeoService geoService = new GeoServiceImpl();
        assertThat(geoService.byIp("127.0.0.1").getCountry(), equalTo(null));
    }

    private static Stream<String> russianLocationTest() {
        return Stream.of("172.0.32.11", "172.0.00.00", "172.3.52.96");
    }

    @ParameterizedTest
    @MethodSource("russianLocationTest")
    public void russianLocationTest(String ip) {
        GeoService geoService = new GeoServiceImpl();
        assertThat(geoService.byIp(ip).getCountry(), equalTo(Country.RUSSIA));
    }

    private static Stream<String> notRussianLocationTest() {
        return Stream.of("96.44.183.149", "96.0.00.00", "96.69.05.423");
    }

    @ParameterizedTest
    @MethodSource("notRussianLocationTest")
    public void notRussianLocationTest(String ip) {
        GeoService geoService = new GeoServiceImpl();
        assertThat(geoService.byIp(ip).getCountry(), equalTo(Country.USA));
    }

    @Test
    public void locationByCoordinateTest() {
        double latitude = 3.25;
        double longitude = 2.32;

        GeoService geoService = new GeoServiceImpl();
        try {
            geoService.byCoordinates(latitude, longitude);
            fail();
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), is("Not implemented"));
        }
    }
}
