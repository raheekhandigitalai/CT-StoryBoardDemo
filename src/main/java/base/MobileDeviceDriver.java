package base;

import helpers.CommonDriverHelper;
import helpers.PropertiesReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Platform;
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

public class MobileDeviceDriver {

    protected ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    protected ThreadLocal<DesiredCapabilities> desiredCapabilities = new ThreadLocal<>();
    protected ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();

    protected RemoteWebDriver getDriver() {
        return driver.get();
    }

    protected WebDriverWait getWait() {
        return wait.get();
    }

    protected CommonDriverHelper helper;

    @BeforeMethod
    @Parameters({"mobile.os"})
    public void setUp(@Optional ITestContext context, @Optional Method method, String mobileOS) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("testName", method.getName());
        caps.setCapability("accessKey", new PropertiesReader().getProperty("ct.accesskey"));

        if (mobileOS.equalsIgnoreCase("ios")) {
                desiredCapabilities.set(setiOSDriver(caps));
                driver.set(new IOSDriver(new URL(new PropertiesReader().getProperty("ct.cloudUrl")), caps));
        } else if (mobileOS.equalsIgnoreCase("android")) {
                desiredCapabilities.set(setAndroidDriver(caps));
                driver.set(new AndroidDriver(new URL(new PropertiesReader().getProperty("ct.cloudUrl")), caps));
        }

        wait.set(new WebDriverWait(getDriver(), Duration.ofSeconds(10)));
        helper = new CommonDriverHelper(getDriver());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        if (result.isSuccess()) {
            helper.setReportStatus("Passed", "Test Passed");
        } else {
            helper.setReportStatus("Failed", "Test Failed");
        }

        getDriver().quit();
        driver.remove();
        wait.remove();
    }

    protected DesiredCapabilities setAndroidDriver(DesiredCapabilities caps) {
        caps.setCapability("deviceQuery", "@os='android' and @category='PHONE' and contains(@region, 'US')");
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
        caps.setBrowserName(MobileBrowserType.CHROME);
        caps.setCapability("appiumVersion", "1.22.3");
        caps.setCapability("automationName", "UIAutomator2");
        return caps;
    }

    protected DesiredCapabilities setiOSDriver(DesiredCapabilities caps) {
        caps.setCapability("deviceQuery", "@os='ios' and @category='PHONE' and contains(@region, 'US')");
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.IOS);
        caps.setBrowserName(MobileBrowserType.SAFARI);
        caps.setCapability("appiumVersion", "2.1.3");
        caps.setCapability("automationName", "XCUITest");
        return caps;
    }

}
