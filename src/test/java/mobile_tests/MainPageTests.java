package mobile_tests;

import base.MobileDeviceDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class MainPageTests extends MobileDeviceDriver {

    @Test
    public void validate_title_test() {
        getDriver().navigate().to("https://demo-bank.ct.digital.ai/login");

        String title = getDriver().getTitle();
        String expectedTitle = "DemoWebsite";

        try {
            assertEquals(expectedTitle, title, "Website Title is not matching with expected value.");

            helper.addStepInReport("Website Title is matching as expected", "true");
            helper.addStepInReport("Expected Title: " + expectedTitle + ", Actual Title: " + title, "true");
        } catch (AssertionError e) {
            helper.addStepInReport(e.getMessage(), "false");
            helper.addStepInReport("Website Title is NOT matching as expected", "false");
            helper.addStepInReport("Expected Title: " + expectedTitle + ", Actual Title: " + title, "false");
            throw e;
        }
    }

}
