package ru.netology.i18n;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static ru.netology.entity.Country.*;

public class LocalizationServiceTest {
    private static Stream<Country> localeTestForInternationalCase() {
        return Stream.of(GERMANY, USA, BRAZIL);
    }

    @ParameterizedTest
    @MethodSource("localeTestForInternationalCase")
    void localeTestForInternationalCase(Country country) {
        LocalizationService service = new LocalizationServiceImpl();
        assertThat(service.locale(country), equalTo("Welcome"));
    }

    @Test
    void localeTestForRussianCase() {
        LocalizationService service = new LocalizationServiceImpl();
        assertThat(service.locale(RUSSIA), equalTo("Добро пожаловать"));
    }
}
