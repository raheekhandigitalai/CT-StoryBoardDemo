package mobile_tests;

import base.MobileDeviceDriver;
import org.testng.annotations.Test;

public class DebugTests extends MobileDeviceDriver {

    @Test
    public void navigation_test() {
        getDriver().navigate().to("https://demo-bank.ct.digital.ai/login");

        System.out.println(getDriver().getTitle());
        System.out.println(getDriver().getCurrentUrl());
    }

}
