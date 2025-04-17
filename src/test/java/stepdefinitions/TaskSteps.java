package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.DriverSetup;

import java.time.Duration;

public class TaskSteps {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setup() {
        System.out.println("Starting test execution...");
        driver = DriverSetup.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        System.out.println("WebDriver initialized successfully");
    }

    @Given("I open the Asana login page")
    public void i_open_the_asana_login_page() {
        try {
            System.out.println("Navigating to Asana login page...");
            driver.get("https://app.asana.com/-/login");
            System.out.println("Successfully navigated to Asana login page");
        } catch (Exception e) {
            System.out.println("Failed to navigate to Asana login page: " + e.getMessage());
            DriverSetup.takeScreenshot("navigation_error");
            throw e;
        }
    }

    @When("I log in with email {string} and password {string}")
    public void i_log_in_with_email_and_password(String email, String password) {
        try {
            System.out.println("Attempting to log in with provided credentials...");
            
            // Wait for and enter email
            System.out.println("Entering email...");
            WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@type='email' or @name='email']")));
            emailField.clear();
            emailField.sendKeys(email);
            System.out.println("Email entered successfully");

            // Press TAB key
            System.out.println("Pressing TAB key...");
            emailField.sendKeys(Keys.TAB);
            Thread.sleep(1000); // Wait 1 second

            // Press ENTER key
            System.out.println("Pressing ENTER key...");
            driver.switchTo().activeElement().sendKeys(Keys.ENTER);
            System.out.println("Continue button action completed");

            // Wait for password field to be visible
            System.out.println("Waiting for password field...");
            WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@type='password' or @name='password']")));
            
            // Pause briefly before entering password
            Thread.sleep(2000);
            
            System.out.println("Entering password...");
            passwordField.clear();
            passwordField.sendKeys(password);
            System.out.println("Password entered successfully");

            // Press TAB key first time
            System.out.println("Pressing first TAB key...");
            passwordField.sendKeys(Keys.TAB);
            Thread.sleep(1000); // Wait 1 second

            // Press TAB key second time
            System.out.println("Pressing second TAB key...");
            driver.switchTo().activeElement().sendKeys(Keys.TAB);
            Thread.sleep(1000); // Wait 1 second

            // Press TAB key third time
            System.out.println("Pressing third TAB key...");
            driver.switchTo().activeElement().sendKeys(Keys.TAB);
            Thread.sleep(1000); // Wait 1 second

            // Press ENTER key
            System.out.println("Pressing ENTER key...");
            driver.switchTo().activeElement().sendKeys(Keys.ENTER);
            System.out.println("Login button action completed");

        } catch (Exception e) {
            System.out.println("Error during login process: " + e.getMessage());
            DriverSetup.takeScreenshot("login_error");
            throw new RuntimeException("Login process failed: " + e.getMessage());
        }
    }

    @Then("I should see the Asana dashboard")
    public void i_should_see_the_asana_dashboard() {
        try {
            System.out.println("Waiting for dashboard to load...");
            WebElement dashboard = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(@class, 'Dashboard') or contains(@class, 'SidebarContent')]")));
            Assert.assertTrue("Dashboard should be visible", dashboard.isDisplayed());
            System.out.println("Successfully verified dashboard presence");
        } catch (Exception e) {
            System.out.println("Error verifying dashboard: " + e.getMessage());
            DriverSetup.takeScreenshot("dashboard_error");
            throw e;
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            System.out.println("Scenario failed! Taking screenshot...");
            DriverSetup.takeScreenshot(scenario.getName() + "_failure");
        }
        System.out.println("Cleaning up WebDriver...");
        DriverSetup.quitDriver();
        System.out.println("Test execution completed");
    }
} 