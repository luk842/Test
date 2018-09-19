package test;

import data.TestUser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Random;

import static org.testng.Assert.assertTrue;

public class MyTest {

    private Random random;
    private WebDriver driver;

    @BeforeTest
    public void prepareTests() {
        System.setProperty(
                "webdriver.chrome.driver",
                "webdriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        random = new Random();
    }

    @Test
    public void registrationTest() {

        //GIVEN
        String baseURL = "https://qa-preproduction.qiagen.com/userregistration/form/personal";
        TestUser testUser = getSampleUser();
        WebDriverWait myWait = new WebDriverWait(driver, 10);

        //WHEN
        driver.get(baseURL);
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys(testUser.getEmail());
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys(testUser.getPassword());
        driver.findElement(By.xpath("//input[@formcontrolname='telephoneNumber']")).sendKeys(testUser.getPhoneNumber());
        driver.findElement(By.xpath("//input[@formcontrolname='firstName']")).sendKeys(testUser.getFirstName());
        driver.findElement(By.xpath("//input[@formcontrolname='lastName']")).sendKeys(testUser.getLastName());
        Select select1 = new Select(driver.findElement(By.xpath("//div/label/select[@name='title']")));
        select1.selectByIndex(1);
        Select select2 = new Select(driver.findElement(By.xpath("//div[@class='row']//div[2]//urs-field-with-errors[1]//div[1]//label[1]//select[@name='title']")));
        select2.selectByIndex(2);

        WebElement submitButton = myWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div/button[@type='submit']")));
        submitButton.findElement(By.xpath("//div/button[@type='submit']")).click();

        System.out.println("1 step passed");

        WebElement link = myWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='header']")));
        link.findElement(By.xpath("//a[@class='header']")).click();

        WebElement button = myWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn btn--primary btn--wide']")));
        button.findElement(By.xpath("//button[@class='btn btn--primary btn--wide']")).click();

        System.out.println("2 step passed");

        WebElement header = myWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(text(),'You are registered for My QIAGEN')]")));

        //THEN
        assertTrue(driver.getCurrentUrl().contains(testUser.getEmail()));

        System.out.println("3 step passed");

        System.out.println("Tests END");
    }

    @AfterTest
    public void closeResources() {
        driver.close();
    }

    private String generateRandomEmail() {
        return "username" + random.nextInt(1000) + "@gmail.com";
    }

    private TestUser getSampleUser() {
        return new TestUser("Karol", "Kowalski", generateRandomEmail(), "Test123!@", "123456789");
    }

}
