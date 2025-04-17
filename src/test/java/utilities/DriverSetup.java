package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

public class DriverSetup {
    private static WebDriver driver;
    private static WebDriverWait wait;

    public static WebDriver getDriver() {
        if (driver == null) {
            try {
                System.out.println("Initializing ChromeDriver...");
                WebDriverManager.chromedriver().setup();
                System.out.println("ChromeDriver setup complete");

                System.out.println("Configuring Chrome options...");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--remote-allow-origins=*");
                options.addArguments("--disable-notifications");
                options.addArguments("--disable-popup-blocking");
                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                System.out.println("Chrome options configured");

                System.out.println("Creating new ChromeDriver instance...");
                driver = new ChromeDriver(options);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
                driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
                driver.manage().window().maximize();
                wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                System.out.println("ChromeDriver instance created successfully");
                
            } catch (Exception e) {
                System.out.println("Error initializing WebDriver: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }
        return driver;
    }

    public static WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
        }
        return wait;
    }

    public static void takeScreenshot(String fileName) {
        try {
            if (driver != null) {
                System.out.println("Taking screenshot...");
                File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File targetFile = new File("target/screenshots/" + fileName + ".png");
                targetFile.getParentFile().mkdirs();
                Files.copy(screenshotFile.toPath(), targetFile.toPath());
                System.out.println("Screenshot saved: " + targetFile.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("Error taking screenshot: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void quitDriver() {
        if (driver != null) {
            try {
                System.out.println("Quitting WebDriver...");
                driver.quit();
                driver = null;
                wait = null;
                System.out.println("WebDriver quit successfully");
            } catch (Exception e) {
                System.out.println("Error quitting WebDriver: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
} 