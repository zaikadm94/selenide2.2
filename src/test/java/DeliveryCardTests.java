
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTests {


    public String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));

    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999/");
    }

    @Test

    public void validValueTest() {
        $("[data-test-id='city'] input").setValue("Воронеж");
        $("[data-test-id='date'] input").doubleClick().sendKeys(generateDate(4, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Дмитрий Иванов");
        $("[data-test-id='phone'] input").setValue("+79104567823");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(14));
        $("[data-test-id='notification']").shouldHave(text("Успешно!\n" +
                "Встреча успешно забронирована на " + generateDate(4, "dd.MM.yyyy"))).shouldBe(visible);


    }


    @Test

    public void dropDownTest() {
        $("[data-test-id='city'] input").setValue("Во");
        $(withText("Воронеж")).click();
        $("[data-test-id='date'] input").click();
        if (!generateDate(7, "MM").equals(generateDate(3, "MM"))) {
            $("[data-step='1']").click();
        }
        $$(".calendar__day").findBy(text("19")).click();
        $("[data-test-id='name'] input").setValue("Дмитрий Иванов");
        $("[data-test-id='phone'] input").setValue("+79104567823");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(14));
        $("[data-test-id='notification']").shouldHave(text("Успешно!\n" +
                "Встреча успешно забронирована на " + generateDate(7, "dd.MM.yyyy"))).shouldBe(visible);


    }
}
