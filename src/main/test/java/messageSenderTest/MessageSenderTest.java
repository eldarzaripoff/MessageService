package messageSenderTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MessageSenderTest {
    @ParameterizedTest
    @ValueSource(strings = {"172.123.12.19", "172.0.32.11", "96.44.183.149"})
    public void messageSenderTest(String ip) {

        GeoService geoservice = Mockito.mock(GeoService.class);
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        MessageSenderImpl sender = new MessageSenderImpl(geoservice, localizationService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String excepted;

        if (ip.startsWith("96.")) {
            excepted = "Welcome";
            Mockito.when(geoservice.byIp(ip)).thenReturn(new Location("New York", Country.USA, null, 0));
            Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");
            assertThat(sender.send(headers), equalTo(excepted));
        }

        if (ip.startsWith("172.")) {
            excepted = "Добро пожаловать";
            Mockito.when(geoservice.byIp(ip)).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
            Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
            assertThat(sender.send(headers), equalTo(excepted));
        }
    }
}
