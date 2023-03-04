package homework_Registration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


//Test scenario - Registration with invalid e-mail
//1.Navigate to Login page
//2.Click on Register
//3.Validate that url has changed to "http://training.skillo-bg.com:4200/users/register"
//4.Validate that the Sign up header is shown
//5.Enter valid username
//6.Enter invalid email (without @,)
//7.Check presented string "Email invalid" on email field
//8.Enter valid password
//9.Confirm password
//10.Click on Sign in button
//11.Validate that appear popup message "Registration failed"
//12.Validate that the user is still on Register page , check the url "http://training.skillo-bg.com:4200/users/register"

public class RegistrationInvalidEmail {
    ChromeDriver driver;
    final String baseUrl = "http://training.skillo-bg.com:4200";
    final String registerUrl = baseUrl + "/users/register";
    final String loginUrl = baseUrl + "/users/login";

    @BeforeMethod
    public void setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(loginUrl);
    }

    @Test
    public void emailWithoutDomain() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        System.out.println("1.Navigate to Login page");
        clickElement(By.id("nav-link-login"), 5);

        System.out.println("2.Click on Register");
        clickElement(By.linkText("Register"), 5);

        System.out.println("3.Validate that url has changed to \"http://training.skillo-bg.com:4200/users/register\"");
        wait.until(ExpectedConditions.urlToBe(registerUrl));

        System.out.println("4.Validate that the Sign up header is shown");
        WebElement signUpHeader = driver.findElement(By.xpath("//app-register//h4[text()='Sign up']"));
        wait.until(ExpectedConditions.visibilityOf(signUpHeader));

        System.out.println("5.Enter valid username");
        WebElement usernameField = driver.findElement(By.xpath("//input[@name='username']"));
        enterTextField(usernameField, "sisi_4440");

        System.out.println("6.Enter invalid email (without domain)");
        WebElement emailField = driver.findElement(By.xpath("//input[@placeholder=\"email\"]"));
        enterInvalidEmail(emailField, "qa@");

        System.out.println("7.Check presented string \"Email invalid\" on email field");
        WebElement invalidEmailFeedBack = driver.findElement(By.xpath("//input/following-sibling::*"));
        wait.until(ExpectedConditions.visibilityOf(invalidEmailFeedBack));

        System.out.println("8.Enter valid password");
        WebElement passwordField = driver.findElement(By.cssSelector("input#defaultRegisterFormPassword"));
        enterTextField(passwordField, "1043a3e5");

        System.out.println("9.Confirm password");
        WebElement confirmPasswordField = driver.findElement(By.cssSelector("input#defaultRegisterPhonePassword"));
        enterTextField(confirmPasswordField, "1043a3e5");

        System.out.println("10.Click on Sign in button");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button#sign-in-button")));
        clickElement(By.id("sign-in-button"),3);

        System.out.println("11.Validate that appear popup message -  Registration failed");
        WebElement toastMessage=wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-message")));
        String toastMessageText= toastMessage.getText().trim();
        Assert.assertEquals(toastMessageText,"Registration failed!","Successful register!");

    }
    @Test
    public void emptyEmailField(){

    }
    @Test
    public void emailWithoutA(){

    }

    private WebElement clickElement(By locator, int timeoutSec) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
        return element;
    }

    private void enterInvalidEmail(WebElement element, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(text);
        Boolean isInvalid = element.getAttribute("class").contains("is-invalid");
        Assert.assertTrue(isInvalid, "Email invalid!");
    }

    private void enterTextField(WebElement element, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(text);
        Boolean isValid = element.getAttribute("class").contains("is-valid");
        Assert.assertTrue(isValid, "The field is invalid");


    }


    @AfterMethod
    public void teardown() {
        driver.close();


    }


}

