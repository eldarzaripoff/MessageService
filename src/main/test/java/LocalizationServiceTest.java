import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static ru.netology.entity.Country.RUSSIA;

public class LocalizationServiceTest {
    @ParameterizedTest
    @EnumSource(Country.class)
    void localeTest(Country country) {
        LocalizationService service = new LocalizationServiceImpl();
        if (country == RUSSIA) {
            assertThat(service.locale(country), equalTo("Добро пожаловать"));
        } else {
            assertThat(service.locale(country), equalTo("Welcome"));
        }
    }
}
