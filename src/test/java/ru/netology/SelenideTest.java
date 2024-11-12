package ru.netology;

import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.ownText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class SelenideTest{
    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void openBrowser() {
        open("http://localhost:9999");
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").sendKeys(Keys.CONTROL,"a" + Keys.DELETE);
    }

    @Test
    void shouldValidValues() {
        $("[data-test-id='city'] [placeholder='Город']").val("Тверь");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(generateDate(3));
        $("[data-test-id='name'] [name='name']").val("Иван Смирнов");
        $("[data-test-id='phone'] [name='phone']").val("+79001178141");
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
        $(".button").click();
        $("[data-test-id='notification'] [class='notification__title']").shouldBe(text("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id='notification'] [class='notification__content']").shouldBe(text("Встреча успешно забронирована на " + generateDate(3)));
    }

    @Test
    void shouldInvalidValueOfTheCity() {
        $("[data-test-id='city'] [placeholder='Город']").val("Ржев");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(generateDate(3));
        $("[data-test-id='name'] [name='name']").val("Иван Смирнов");
        $("[data-test-id='phone'] [name='phone']").val("+79001178141");
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
        $(".button").click();
        $("[data-test-id='city'].input_invalid [class='input__sub']").shouldHave(ownText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldInvalidTimeValue() {
        $("[data-test-id='city'] [placeholder='Город']").val("Тверь");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(generateDate(0));
        $("[data-test-id='name'] [name='name']").val("Иван Смирнов");
        $("[data-test-id='phone'] [name='phone']").val("+79001178141");
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
        $(".button").click();
        $("[data-test-id='date'] .input_invalid [class='input__sub']").shouldHave(ownText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldInvalidNameValue() {
        $("[data-test-id='city'] [placeholder='Город']").val("Тверь");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(generateDate(3));
        $("[data-test-id='name'] [name='name']").val("Ivan Smirnov");
        $("[data-test-id='phone'] [name='phone']").val("+79001178141");
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid [class='input__sub']").shouldBe(ownText("Имя и Фамилия указаные неверно."));
    }

    @Test
    void shouldInvalidPhoneValue() {
        $("[data-test-id='city'] [placeholder='Город']").val("Тверь");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(generateDate(3));
        $("[data-test-id='name'] [name='name']").val("Иван Смирнов");
        $("[data-test-id='phone'] [name='phone']").val("79001178141");
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid [class='input__sub']").shouldBe(ownText("Телефон указан неверно."));
    }

    @Test
    void shouldUncheckedCheckBox() {
        $("[data-test-id='city'] [placeholder='Город']").val("Тверь");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(generateDate(3));
        $("[data-test-id='name'] [name='name']").val("Иван Смирнов");
        $("[data-test-id='phone'] [name='phone']").val("+79001178141");
        $(".button").click();
        $(" [data-test-id='agreement'].input_invalid [class='checkbox__text']").shouldHave(ownText("Я соглашаюсь с условиями обработки"));
    }

    @Test
    void shouldEmptyCityField() {
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(generateDate(3));
        $("[data-test-id='name'] [name='name']").val("Иван Смирнов");
        $("[data-test-id='phone'] [name='phone']").val("+79001178141");
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
        $(".button").click();
        $("[data-test-id='city'].input_invalid [class='input__sub']").shouldHave(ownText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldEmptyDateField() {
        $("[data-test-id='city'] [placeholder='Город']").val("Тверь");
        $("[data-test-id='name'] [name='name']").val("Иван Смирнов");
        $("[data-test-id='phone'] [name='phone']").val("+79001178141");
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
        $(".button").click();
        $("[data-test-id='date'] .input_invalid [class='input__sub']").shouldHave(ownText("Неверно введена дата"));
    }

    @Test
    void shouldEmptyNameField() {
        $("[data-test-id='city'] [placeholder='Город']").val("Тверь");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(generateDate(3));
        $("[data-test-id='phone'] [name='phone']").val("+79001178141");
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid [class='input__sub']").shouldBe(ownText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldEmptyPhoneField() {
        $("[data-test-id='city'] [placeholder='Город']").val("Тверь");
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(generateDate(3));
        $("[data-test-id='name'] [name='name']").val("Иван Смирнов");
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid [class='input__sub']").shouldBe(ownText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldDropdownListOfCities() {
        $("[data-test-id='city'] [placeholder='Город']").val("Тве");
        ElementsCollection listOfCities = $$("[class='popup__container'] span");
        listOfCities.findBy(text("Тверь")).click();
        $("[data-test-id='date'] [placeholder='Дата встречи']").val(generateDate(3));
        $("[data-test-id='name'] [name='name']").val("Иван Смирнов");
        $("[data-test-id='phone'] [name='phone']").val("+79001178141");
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
        $(".button").click();
        $("[data-test-id='notification'] [class='notification__title']").shouldBe(text("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id='notification'] [class='notification__content']").shouldBe(text("Встреча успешно забронирована на " + generateDate(3)));
    }

    @Test
    void shouldDropDownCalendar() {
        $("[data-test-id='city'] [placeholder='Город']").val("Горно-Алтайск");
        $("[data-test-id='date'] [placeholder='Дата встречи']").click();
        ElementsCollection dates = $$("[class='popup__container'] [data-day]");
        int days = 13 + 7;
        int remains;
        int currentWeek = dates.size();
        if (currentWeek < days) {
            remains = days - currentWeek;
            $("[class='popup__container'] [data-step=\"1\"]").click();
            dates.get(remains).click();
        } else {
            dates.get(days).click();
        }
        $("[data-test-id='name'] [name='name']").val("Иван Смирнов");
        $("[data-test-id='phone'] [name='phone']").val("+79001178141");
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
        $(".button").click();
        $("[data-test-id='notification'] [class='notification__title']").shouldBe(text("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id='notification'] [class='notification__content']").shouldBe(text("Встреча успешно забронирована на " + generateDate(days + 3)));
    }

}
