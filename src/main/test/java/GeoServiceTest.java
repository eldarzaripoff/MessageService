import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.netology.entity.Country;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import static org.junit.jupiter.api.Assertions.fail;

public class GeoServiceTest {
    @ParameterizedTest
    @ValueSource(strings = {"127.0.0.1", "172.0.32.11", "96.44.183.149", "172.0.00.00", "96.00.000.000"})
    public void locationByIPTest(String ip) {
        GeoService geoService = new GeoServiceImpl();

        if (ip.startsWith("127.")) {
            assertThat(geoService.byIp(ip).getCountry(), equalTo(null));
        }

        if (ip.startsWith("172.")) {
            assertThat(geoService.byIp(ip).getCountry(), equalTo(Country.RUSSIA));
        }
        if (ip.startsWith("96")) {
            assertThat(geoService.byIp(ip).getCountry(), equalTo(Country.USA));
        }
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
