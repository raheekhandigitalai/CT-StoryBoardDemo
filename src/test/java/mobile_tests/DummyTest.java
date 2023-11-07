package mobile_tests;

import base.MobileDeviceDriver;
import org.testng.annotations.Test;

public class DummyTest extends MobileDeviceDriver {

    @Test
    public void navigation_test() {
        getDriver().navigate().to("https://digital.ai");
    }

}
