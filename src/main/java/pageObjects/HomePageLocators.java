package pageObjects;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePageLocators {
    private WebDriver driver;

    private By topButton = By.xpath(".//div[contains(@class, 'Header_Nav')]/button[text()='Заказать']");
    private By bottomButton = By.xpath(".//div[contains(@class, 'Home_FinishButton')]/button[text()='Заказать']");
    private By homePage = By.xpath(".//img[@src='/assets/scooter.svg']");
    private By orderStatus = By.xpath(".//div[contains(@class, 'Header_Nav')]/button[text()='Статус заказа']");
    private By searchOrderField = By.xpath(".//input[contains(@class, 'Input')]");
    private By buttonGo = By.xpath(".//button[text()='Go!']");
    private By cookieButton = By.xpath(".//button[contains(@class, 'App_CookieButton')]");
    private By questionFields = By.xpath(".//div[contains(@class,'Home_FourPart')]/div[text()='Вопросы о важном']");

    public HomePageLocators(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnTopButton(){
        driver.findElement(topButton).click();
    }

    public void clickOnBottomButton(){
        driver.findElement(bottomButton).click();
    }

    public void waitHomePage(){
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[contains(@class, 'Home_Header') and starts-with(text(), 'Самокат')]")));
    }

    public void waitForLoadQuestions(){
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(questionFields));
    }

    public void scrolToQuestions(){
        WebElement element = driver.findElement((By.xpath("//div[contains(text(), 'Вопросы о важном')]")));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    public void selectQuestion(String text){
        driver.findElement(By.xpath(".//div[text()='"+ text +"']")).click();
    }

    public String getAnswer(int linkPostfix){
        return driver.findElement(By.id("accordion__panel-" + linkPostfix)).getText();
    }

    public void returnToHomePage(){
        driver.findElement(homePage).click();
        waitHomePage();
    }

    public void findMyOrder(String text){
        driver.findElement(orderStatus).click();
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(searchOrderField));
        Assert.assertTrue(driver.findElement(searchOrderField).isEnabled());
        driver.findElement(searchOrderField).clear();
        driver.findElement(searchOrderField).sendKeys(text);
        clickOnGoButton();
    }

    public void clickOnGoButton(){
        driver.findElement(buttonGo).click();
    }

    public void agreeWithCookie(){
        driver.findElement(cookieButton).click();
        waitHomePage();
    }

}
