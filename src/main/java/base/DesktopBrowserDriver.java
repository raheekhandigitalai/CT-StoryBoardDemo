package base;

import helpers.CommonDriverHelper;
import helpers.PropertiesReader;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DesktopBrowserDriver {

    protected ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
    protected ThreadLocal<DesiredCapabilities> desiredCapabilities = new ThreadLocal<>();
    protected ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();

    protected CommonDriverHelper helper;

    protected RemoteWebDriver getDriver() {
        return driver.get();
    }

    protected WebDriverWait getWait() {
        return wait.get();
    }

    @BeforeMethod
    @Parameters({"browser.name"})
    public void setUp(@Optional ITestContext context, @Optional Method method, @Optional String browserName) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("experitest:testName", method.getName());
        caps.setCapability("experitest:accessKey", new PropertiesReader().getProperty("ct.accesskey"));

        desiredCapabilities.set(setWebDriver(browserName, caps));
        driver.set(new RemoteWebDriver(new URL(new PropertiesReader().getProperty("ct.cloudUrl")), caps));
        wait.set(new WebDriverWait(getDriver(), Duration.ofSeconds(10)));
        helper = new CommonDriverHelper(getDriver());

        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        getDriver().manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        try {
            if (result.isSuccess()) {
                helper.setReportStatus("Passed", "Test Passed");
            } else {
                helper.setReportStatus("Failed", "Test Failed", result.getThrowable().getCause().toString());
            }
        } catch (Exception e) {
            // Nothing
        }

        getDriver().quit();
        driver.remove();
        wait.remove();
    }

    protected DesiredCapabilities setWebDriver(String browserName, DesiredCapabilities caps) {
        caps.setCapability("browserName", browserName);
        return caps;
    }

}
