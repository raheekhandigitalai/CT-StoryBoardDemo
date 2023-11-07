package helpers;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.rmi.Remote;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonDriverHelper {

    protected RemoteWebDriver driver;

    public CommonDriverHelper(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void addStepInReport(String message, String status) {
        driver.executeScript("seetest:client.report", message, status);
    }

    public void setReportStatus(String status, String message) {
        driver.executeScript("seetest:client.setReportStatus", status, message);
    }

    public void setReportStatus(String status, String message, String stackTrace) {
        driver.executeScript("seetest:client.setReportStatus", status, message, stackTrace);
    }

    public void addPropertyForReporting(String property, String value) {
        driver.executeScript("seetest:client.addTestProperty", property, value);
    }

    public void startGroupingOfSteps(String testName) {
        driver.executeScript("seetest:client.startStepsGroup", testName);
    }

    public void endGroupingOfSteps() {
        driver.executeScript("seetest:client.stopStepsGroup()");
    }

    public String getCurrentDateAndTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        String currentDateAndTime = now.format(formatter);
        return currentDateAndTime;
    }

}
