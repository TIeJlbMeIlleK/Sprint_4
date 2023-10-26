import org.junit.After;
import org.junit.Assert;
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
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    private final FirefoxOptions options = new FirefoxOptions();
    WebDriver driver;


    @Test
    public void makeAnOrder() {
        System.setProperty("webdriver.gecko.driver", "C:\\geckodriver\\geckodriver.exe");
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new FirefoxDriver(options);
        HomePageLocators homePageLocators = new HomePageLocators(driver);
        OrderPageLocators orderPageLocators = new OrderPageLocators(driver);
        Date date = new Date(System.currentTimeMillis());
        String nowDate = formatter.format(date);

        // переход на страницу тестового приложения
        driver.get("https://qa-scooter.praktikum-services.ru/");
        //Делаем заказ по кнопке снизу
        homePageLocators.agreeWithCookie(); //соглашаемся с куки
        homePageLocators.clickOnBottomButton();
        orderPageLocators.waitForOrderPage(); //ожидаем загрузки страницы с заказом
        orderPageLocators.doFullStepsToOrder("Владислав", "Евграфов", "Ул.Ленина д.13", "Бульвар Рокоссовского", "85554443423", nowDate, "Комментарий", "black");
        String actualFirstOrder = driver.findElement(By.xpath(".//div[contains(@class, 'Order_ModalHeader')]")).getText();
        Assert.assertTrue("Что-то пошло не так!", actualFirstOrder.startsWith("Заказ оформлен"));

        orderPageLocators.clickOnOrderStatus();
        homePageLocators.returnToHomePage();
        //Делаем заказ по кнопке сверху
        homePageLocators.clickOnTopButton();
        orderPageLocators.waitForOrderPage();
        orderPageLocators.doFullStepsToOrder("Владислав", "Евграфов", "Ул.Ленина д.13", "Бульвар Рокоссовского", "85554443423", nowDate, "Комментарий", "black");
        String actualSecondOrder = driver.findElement(By.xpath(".//div[contains(@class, 'Order_ModalHeader')]")).getText();
        Assert.assertTrue("Что-то пошло не так!", actualSecondOrder.startsWith("Заказ оформлен"));
    }

    @After
    public void quit() {
        driver.quit();
    }
}
