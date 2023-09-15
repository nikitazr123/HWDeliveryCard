package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryNegativeTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    // Отправка формы с пустым полем "Выберите ваш город";
    @Test
    public void shouldReturnErrorMessageIfCityEmpty() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);

        $("[data-test-id='city'] [placeholder='Город']").setValue("");
        $("[data-test-id='date'] [class='input__box'] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] [class='input__box'] [placeholder='Дата встречи']").setValue(dateText);
        $("[data-test-id='name'] [type=text]").setValue("Никита Зырянов");
        $("[data-test-id='phone'] [type=tel]").setValue("+79872498117");
        $("[data-test-id=agreement]").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible).
                shouldHave(exactText("Поле обязательно для заполнения"));
    }

    //  Отправка фомы с городом не из административных центров субъектов РФ;
    @Test
    public void shouldReturnErrorMessageIfCityInvalid() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);

        $("[data-test-id='city'] [placeholder='Город']").setValue("Лондон");
        $("[data-test-id='date'] [class='input__box'] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] [class='input__box'] [placeholder='Дата встречи']").setValue(dateText);
        $("[data-test-id='name'] [type=text]").setValue("Никита Зырянов");
        $("[data-test-id='phone'] [type=tel]").setValue("+79872498117");
        $("[data-test-id=agreement]").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible).
                shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    // Отправка формы с пустым полем "Дата встречи";
    @Test
    public void shouldReturnErrorMessageIfDateIsEmpty() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);

        $("[data-test-id='city'] [placeholder='Город']").setValue("Уфа");
        $("[data-test-id='date'] [class='input__box'] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] [class='input__box'] [placeholder='Дата встречи']").setValue("");
        $("[data-test-id='name'] [type=text]").setValue("Никита Зырянов");
        $("[data-test-id='phone'] [type=tel]").setValue("+79872498117");
        $("[data-test-id=agreement]").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='date'] .input__sub").shouldBe(visible).
                shouldHave(exactText("Неверно введена дата"));
    }

    // Отправка формы с пустым полем "Фамилия и Имя";
    @Test
    public void shouldReturnErrorMessageIfSurnameAndNameIsEmpty() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);

        $("[data-test-id='city'] [placeholder='Город']").setValue("Уфа");
        $("[data-test-id='date'] [class='input__box'] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] [class='input__box'] [placeholder='Дата встречи']").setValue(dateText);
        $("[data-test-id='name'] [type=text]").setValue("");
        $("[data-test-id='phone'] [type=tel]").setValue("+7987298117");
        $("[data-test-id=agreement]").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    //  Отправка формы с полем "Фамилия и Имя", состоящий из цифр;
    @Test
    public void shouldReturnErrorMessageIfSurnameAndNameConsistsOfNumbers() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);

        $("[data-test-id='city'] [placeholder='Город']").setValue("Уфа");
        $("[data-test-id='date'] [class='input__box'] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] [class='input__box'] [placeholder='Дата встречи']").setValue(dateText);
        $("[data-test-id='name'] [type=text]").setValue("12341234");
        $("[data-test-id='phone'] [type=tel]").setValue("+79872498117");
        $("[data-test-id=agreement]").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    //  Отправка формы с полем "Фамилия и Имя", состоящий из спецсимволов;
    @Test
    public void shouldReturnErrorMessageIfSurnameAndNameConsistsOfSpecialCharacters() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);

        $("[data-test-id='city'] [placeholder='Город']").setValue("Уфа");
        $("[data-test-id='date'] [class='input__box'] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] [class='input__box'] [placeholder='Дата встречи']").setValue(dateText);
        $("[data-test-id='name'] [type=text]").setValue("!;№№?%%;");
        $("[data-test-id='phone'] [type=tel]").setValue("+79872498117");
        $("[data-test-id=agreement]").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    // Отправка формы с полем "Фамилия и Имя" на английском языке;
    @Test
    public void shouldReturnErrorMessageIfInvalidSurnameAndName() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);

        $("[data-test-id='city'] [placeholder='Город']").setValue("Уфа");
        $("[data-test-id='date'] [class='input__box'] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] [class='input__box'] [placeholder='Дата встречи']").setValue(dateText);
        $("[data-test-id='name'] [type=text]").setValue("Nikita Zyryanov");
        $("[data-test-id='phone'] [type=tel]").setValue("+79872498117");
        $("[data-test-id=agreement]").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    // Отправка формы с пустым полем "Мобильный телефон";
    @Test
    public void shouldReturnSuccessIfFieldsAreFilledInCorrectly() {

        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);

        $("[data-test-id=city] [placeholder='Город']").setValue("Уфа");
        $("[data-test-id=date] [class='input__box'] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [class='input__box'] [placeholder='Дата встречи']").setValue(dateText);
        $("[data-test-id=name] [type=text]").setValue("Никита Зырянов");
        $("[data-test-id=phone] [type=tel]").setValue("");
        $("[data-test-id=agreement]").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    // Отправка формы с введенным в поле "Мобильный телефон" букв;
    @Test
    public void shouldReturnErrorMessageIfPhoneWithLetters() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);

        $("[data-test-id=city] [placeholder='Город']").setValue("Уфа");
        $("[data-test-id=date] [class='input__box'] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [class='input__box'] [placeholder='Дата встречи']").setValue(dateText);
        $("[data-test-id=name] [type=text]").setValue("Никита Зырянов");
        $("[data-test-id=phone] [type=tel]").setValue("НЕТОЛОГИЯ");
        $("[data-test-id=agreement]").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    // Отправка формы с введенным в поле "Мобильный телефон" спецсимволов;
    @Test
    public void shouldReturnErrorMessageIfPhoneHasSpecialCharacters() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);

        $("[data-test-id=city] [placeholder='Город']").setValue("Уфа");
        $("[data-test-id=date] [class='input__box'] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [class='input__box'] [placeholder='Дата встречи']").setValue(dateText);
        $("[data-test-id=name] [type=text]").setValue("Никита Зырянов");
        $("[data-test-id=phone] [type=tel]").setValue("??:%;№**:%");
        $("[data-test-id=agreement]").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    // Отправка формы с неверно заполненным полем "Мобильный телефон", без "+";
    @Test
    public void shouldReturnErrorMessageIfPhoneIsWrong() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);

        $("[data-test-id=city] [placeholder='Город']").setValue("Уфа");
        $("[data-test-id=date] [class='input__box'] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [class='input__box'] [placeholder='Дата встречи']").setValue(dateText);
        $("[data-test-id=name] [type=text]").setValue("Никита Зырянов");
        $("[data-test-id=phone] [type=tel]").setValue("79872498117");
        $("[data-test-id=agreement]").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    // Отправка формы с введенным в поле "Мобильный телефон" неверного количества цифр;
    @Test
    public void shouldReturnErrorMessageIfPhoneWrong() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);

        $("[data-test-id=city] [placeholder='Город']").setValue("Уфа");
        $("[data-test-id=date] [class='input__box'] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [class='input__box'] [placeholder='Дата встречи']").setValue(dateText);
        $("[data-test-id=name] [type=text]").setValue("Никита Зырянов");
        $("[data-test-id=phone] [type=tel]").setValue("+7987249811");
        $("[data-test-id=agreement]").click();
        $("[role=button] .button__content").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    // Отправка формы без согласия с условиями обработки персональных данных;
    @Test
    public void shouldReturnErrorMessageIfDoNotTick() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);

        $("[data-test-id=city] [placeholder='Город']").setValue("Уфа");
        $("[data-test-id=date] [class='input__box'] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] [class='input__box'] [placeholder='Дата встречи']").setValue(dateText);
        $("[data-test-id=name] [type=text]").setValue("Никита Зырянов");
        $("[data-test-id=phone] [type=tel]").setValue("+79872498117");
        $("[role=button] .button__content").click();
        $("[data-test-id='agreement'].input_invalid .checkbox__text").shouldBe(visible)
                .shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
