package ru.netology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class applicationСardTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver/chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");

        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }


    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }


    @Test
    void TheContentsInTheFieldAreNotCorrect() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Marina Igorevna");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79888999999");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void IncorrectPhoneNumberEntry() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Марина Игоревна");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+788800099988");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void CheckingTheConsentCheckbox() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Виктория Васильевна");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79625013494");
        driver.findElement(By.className("button")).click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestCardOrder() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Алексей Бестужев-Рюмин");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79625013494");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        assertEquals(expected, actual);


    }
}


