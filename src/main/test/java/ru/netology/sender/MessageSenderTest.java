package ru.netology.sender;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MessageSenderTest {
    private static Stream<String> forRussianIPMessageSenderTest() {
        return Stream.of("172.123.12.19", "172.0.32.11", "172.0.00.00");
    }
    @ParameterizedTest
    @MethodSource("forRussianIPMessageSenderTest")
    public void forRussianIPmessageSenderTest(String ip) {
        GeoService geoservice = Mockito.mock(GeoService.class);
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        MessageSenderImpl sender = new MessageSenderImpl(geoservice, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String excepted;
        excepted = "Добро пожаловать";
        Mockito.when(geoservice.byIp(ip)).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        assertThat(sender.send(headers), equalTo(excepted));
    }

    private static Stream<String> forAmericanIPMessageSenderTest() {
        return Stream.of("96.44.183.149", "96.00.00.000", "96.52.423.325");
    }
    @ParameterizedTest
    @MethodSource("forAmericanIPMessageSenderTest")
    public void forAmericanIPmessageSenderTest(String ip) {
        GeoService geoservice = Mockito.mock(GeoService.class);
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        MessageSenderImpl sender = new MessageSenderImpl(geoservice, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String excepted;
        excepted = "Welcome";
        Mockito.when(geoservice.byIp(ip)).thenReturn(new Location("New York", Country.USA, null, 0));
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");
        assertThat(sender.send(headers), equalTo(excepted));
    }
}
