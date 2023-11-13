package web_tests;

import base.DesktopBrowserDriver;
import org.testng.annotations.Test;

public class DummyTest extends DesktopBrowserDriver {

    @Test
    public void navigation_test() {
        getDriver().navigate().to("https://demo-bank.ct.digital.ai/login");

        System.out.println(getDriver().getTitle());
        System.out.println(getDriver().getCurrentUrl());
    }

}
