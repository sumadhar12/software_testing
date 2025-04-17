package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utilities.DriverSetup;

public class Hooks {
    
    @Before
    public void beforeScenario(Scenario scenario) {
        System.out.println("\n============================");
        System.out.println("Starting Scenario: " + scenario.getName());
        System.out.println("============================");
    }

    @After
    public void afterScenario(Scenario scenario) {
        System.out.println("\n============================");
        System.out.println("Scenario Status: " + (scenario.isFailed() ? "FAILED" : "PASSED"));
        System.out.println("============================\n");
        
        if (scenario.isFailed()) {
            DriverSetup.takeScreenshot(scenario.getName() + "_failure");
        }
        DriverSetup.quitDriver();
    }
} 