package web_tests;

import base.DesktopBrowserDriver;
import org.testng.annotations.Test;

public class DummyTest extends DesktopBrowserDriver {

    @Test
    public void navigation_test() {
        getDriver().navigate().to("https://digital.ai");
    }

}
