package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.Keys.ARROW_DOWN;
import static org.openqa.selenium.Keys.ENTER;

public class OrderPageLocators {
    private WebDriver driver;


    private By orderPage = By.xpath(".//div[text()='Для кого самокат']");
    private By aboutRent = By.xpath(".//div[text()='Про аренду']");
    private By bottonNext = By.xpath(".//div[contains(@class, 'Order_NextButton')]/button[text()='Далее']");
    private By nameInputField = By.xpath(".//input[@placeholder =\"* Имя\"]");
    private By secondNameInputField = By.xpath(".//input[@placeholder =\"* Фамилия\"]");
    private By addressInputField = By.xpath(".//input[@placeholder =\"* Адрес: куда привезти заказ\"]");
    private By subwayField = By.xpath(".//input[@placeholder =\"* Станция метро\"]");
    private By telephoneInputField = By.xpath(".//input[@placeholder =\"* Телефон: на него позвонит курьер\"]");
    private By orderOnDateField = By.xpath(".//input[@placeholder =\"* Когда привезти самокат\"]");
    private By rentalTimeField = By.className("Dropdown-control");
    private By colorBlackCheckbox = By.id("black");
    private By colorGreyCheckbox = By.id("grey");
    private By commentField = By.xpath(".//input[@placeholder =\"Комментарий для курьера\"]");
    private By confirmOrder = By.xpath(".//div[contains(@class, 'Order_Buttons')]/button[text()='Заказать']");
    private By seeOrderStatus = By.xpath(".//button[text()='Посмотреть статус']");
    private By buttonYes = By.xpath(".//button[text()='Да']");
    private By cancelOrder = By.xpath(".//button[text()='Отменить заказ']");


    public OrderPageLocators(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForOrderPage(){
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(orderPage));
    }

    public void makeOrder(String name, String secondName, String address, String subway, String telephone){
        setTextOnFieldAfterClear(nameInputField,name);
        setTextOnFieldAfterClear(secondNameInputField,secondName);
        setTextOnFieldAfterClear(addressInputField,address);
        driver.findElement(subwayField).sendKeys(subway + ARROW_DOWN + ENTER);
        setTextOnFieldAfterClear(telephoneInputField,telephone);
        driver.findElement(bottonNext).click();
    }

    public void aboutRent(String date, String color, String comment){
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(aboutRent));
        driver.findElement(orderOnDateField).clear();
        driver.findElement(orderOnDateField).sendKeys(date + ENTER);
        driver.findElement(rentalTimeField).click();
        driver.findElement(By.xpath(".//div[text()='двое суток']")).click();

        if(color.equals("black")){driver.findElement(colorBlackCheckbox).click();
        }else if(color.equals("grey"))driver.findElement(colorGreyCheckbox).click();

        setTextOnFieldAfterClear(commentField, comment);
        driver.findElement(confirmOrder).click();
        driver.findElement(buttonYes).click();
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(seeOrderStatus));
    }

    public void setTextOnFieldAfterClear(By field, String text){
        driver.findElement(field).click();
        driver.findElement(field).clear();
        driver.findElement(field).sendKeys(text);
    }

    public void doFullStepsToOrder(String name, String secondName, String address, String subway, String telephone, String date, String comment, String color){
        makeOrder(name, secondName, address, subway,telephone);
        aboutRent(date, color, comment);
    }

    public void clickOnOrderStatus(){
        driver.findElement(seeOrderStatus).click();
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(cancelOrder));
    }

    public void cancelOrder(){
        driver.findElement(cancelOrder).click();
    }
}
