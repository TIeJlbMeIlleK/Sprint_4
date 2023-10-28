import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import pageObjects.HomePageLocators;
import pageObjects.OrderPageLocators;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MakeAnOrder {
    WebDriver driver;

    @Test
    public void makeAnOrder() {
        HomePageLocators homePageLocators = new HomePageLocators(driver);
        OrderPageLocators orderPageLocators = new OrderPageLocators(driver);
        homePageLocators.agreeWithCookie(); //соглашаемся с куки

        //Делаем заказ по кнопке сверху. Данные передал в метод. Данные уникальны. Это первый набор данных.
        homePageLocators.clickOnTopButton(); // кликаем на верхнюю кнопку "Заказать"
        orderPageLocators.waitForOrderPage();
        orderPageLocators.doFullStepsToOrder("Владислав", "Евграфов", "Ул.Ленина д.13", "Бульвар Рокоссовского", "85554443423", "14.11.2023", "Серый, обязательно серый!", "grey");
        String actualSecondOrder = driver.findElement(By.xpath(".//div[contains(@class, 'Order_ModalHeader')]")).getText();
        Assert.assertTrue("Что-то пошло не так!", actualSecondOrder.startsWith("Заказ оформлен"));

        orderPageLocators.clickOnOrderStatus();
        homePageLocators.returnToHomePage();

        //Делаем заказ по кнопке снизу. Данные передал в метод. Данные уникальны. Это второй набор данных.
        homePageLocators.clickOnBottomButton(); // кликаем на нижнюю кнопку "Заказать"
        orderPageLocators.waitForOrderPage(); //ожидаем загрузки страницы с заказом
        orderPageLocators.doFullStepsToOrder("Анатолий", "Кульбит", "Ул.Пушкина д.17", "Курская", "80154443001", "30.10.2023", "Черный, обязательно черный!", "black");
        String actualFirstOrder = driver.findElement(By.xpath(".//div[contains(@class, 'Order_ModalHeader')]")).getText();
        Assert.assertTrue("Что-то пошло не так!", actualFirstOrder.startsWith("Заказ оформлен"));
    }

    @Before
    public void beforeStart() {
        System.setProperty("webdriver.gecko.driver", "C:\\geckodriver\\geckodriver.exe");
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new FirefoxDriver(options);
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @After
    public void quit() {
        driver.quit();
    }
}
