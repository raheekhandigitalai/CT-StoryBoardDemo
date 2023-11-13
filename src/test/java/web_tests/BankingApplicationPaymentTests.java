package web_tests;

import base.DesktopBrowserDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotEquals;

public class BankingApplicationPaymentTests extends DesktopBrowserDriver {

    @Test
    public void verify_user_making_payment() {
        getDriver().navigate().to("https://demo-bank.ct.digital.ai/login");

        WebElement loginButton = getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='dx-button-content' and contains(text(), 'Login')]")));

        getDriver().findElement(By.xpath("(//input[@class='dx-texteditor-input'])[1]")).sendKeys("company");
        getDriver().findElement(By.xpath("(//input[@class='dx-texteditor-input'])[2]")).sendKeys("company");

        loginButton.click();

        WebElement welcomeHeader = getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='welcome-header']")));

        String actualText = welcomeHeader.getText();
        String expectedText = "Welcome to your account, Tester";

        try {
            if (actualText.equals(expectedText)) {
                helper.addStepInReport("Actual Text matches with Expected Value", "true");
                helper.addStepInReport("Actual Text: " + actualText + ", Expected Value :" + expectedText, "true");
            }
        } catch (Exception e) {
            helper.addStepInReport("Actual Text does not match with Expected Value", "false");
            helper.addStepInReport("Actual Text: " + actualText + ", Expected Value : " + expectedText, "false");
        }

        WebElement currentAmountElement = getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='amount']")));
        String amountTextBeforePayment = currentAmountElement.getText().replaceAll("[^\\d.]", "");;
        int balanceBeforePayment = Integer.parseInt(amountTextBeforePayment);

        WebElement transferFundsButtonMainPage = getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='dx-button-text' and contains(text(), 'Transfer Funds')]")));
        transferFundsButtonMainPage.click();

        WebElement transferFundsButtonPopupPage = getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='dx-popup-content']//*[@class='dx-button-content' and contains(text(), 'Transfer Funds')]")));

        getDriver().findElement(By.name("NAME")).sendKeys("Derek Holt");
        getDriver().findElement(By.name("PHONE")).sendKeys("3470000000");
        getDriver().findElement(By.name("AMOUNT")).sendKeys("100");
        getDriver().findElement(By.id("country-arrow")).click();

        WebElement countrySelection = getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='USA']")));
        countrySelection.click();

        transferFundsButtonPopupPage.click();

        WebElement amountUpdatedElement = getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='amount']")));
        String amountTextAfterPayment = amountUpdatedElement.getText().replaceAll("[^\\d.]", "");
        int balanceAfterPayment = Integer.parseInt(amountTextAfterPayment);

        try {
            assertNotEquals(balanceBeforePayment, balanceAfterPayment, "Amount has not been deducted.");

            helper.addStepInReport("Amount has been deducted", "true");
            helper.addStepInReport("Previous Balance: " + balanceBeforePayment + ", Current Balance: " + balanceAfterPayment, "true");
        } catch (AssertionError e) {
            helper.addStepInReport(e.getMessage(), "false");
            helper.addStepInReport("Amount has not been deducted", "false");
            helper.addStepInReport("Previous Balance: " + balanceBeforePayment + ", Current Balance: " + balanceAfterPayment, "false");
            throw e;
        }

    }

}
