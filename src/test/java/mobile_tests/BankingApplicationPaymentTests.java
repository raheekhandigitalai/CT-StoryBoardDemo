package mobile_tests;

import base.MobileDeviceDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotEquals;

public class BankingApplicationPaymentTests extends MobileDeviceDriver {

    @Test
    @Parameters({"mobile.os"})
    public void verify_user_making_payment(String mobileOS) throws InterruptedException {
        getDriver().navigate().to("https://demo-bank.ct.digital.ai/login");

        WebElement loginButton = getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='dx-button-content' and contains(text(), 'Login')]")));

        getDriver().findElement(By.xpath("(//input[@class='dx-texteditor-input'])[1]")).sendKeys("company");
        getDriver().findElement(By.xpath("(//input[@class='dx-texteditor-input'])[2]")).sendKeys("company");
        
        loginButton.click();

        Thread.sleep(2000);

        try {
            if (mobileOS.equalsIgnoreCase("android")) {
                loginButton.click();
            }
        } catch (Exception e) {
            // Nothing
        }

        WebElement welcomeHeader = getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='welcome-header']")));
        
        String actualText = welcomeHeader.getText();
        String expectedText = "Welcome to your account, Tester";

        try {
            if (actualText.equals(expectedText)) {
                addStepInReport("Actual Text matches with Expected Value", "true");
                addStepInReport("Actual Text: " + actualText + ", Expected Value :" + expectedText, "true");
            }
        } catch (Exception e) {
            addStepInReport("Actual Text does not match with Expected Value", "false");
            addStepInReport("Actual Text: " + actualText + ", Expected Value : " + expectedText, "false");
        }

        Thread.sleep(3000);

        WebElement currentAmountElement = getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='total-balance-amount']")));
        String amountTextBeforePayment = currentAmountElement.getText().replaceAll("[^\\d.]", "");;
        int balanceBeforePayment = Integer.parseInt(amountTextBeforePayment);

        WebElement transferFundsButtonMainPage = getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='dx-button-text' and contains(text(), 'Transfer Funds')]")));
        transferFundsButtonMainPage.click();

        // //*[@class='dx-popup-content']//*[@class='dx-button-content' and contains(text(), 'Transfer')]
        WebElement transferFundsButtonPopupPage = getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-auto='transfer-button']//*[@class='dx-button-content']")));

        getDriver().findElement(By.name("NAME")).sendKeys("Derek Holt");
        getDriver().findElement(By.name("PHONE")).sendKeys("3470000000");
        getDriver().findElement(By.name("AMOUNT")).sendKeys("100");

        Thread.sleep(3000);

        transferFundsButtonPopupPage.click();

        try {
            if (mobileOS.equalsIgnoreCase("android")) {
                transferFundsButtonPopupPage.click();
            }
        } catch (Exception e) {
            // Nothing
        }

        Thread.sleep(3000);

        WebElement amountUpdatedElement = getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='total-balance-amount']")));
        String amountTextAfterPayment = amountUpdatedElement.getText().replaceAll("[^\\d.]", "");
        int balanceAfterPayment = Integer.parseInt(amountTextAfterPayment);

        try {
            assertNotEquals(balanceBeforePayment, balanceAfterPayment, "Amount has not been deducted.");

            addStepInReport("Amount has been deducted", "true");
            addStepInReport("Previous Balance: " + balanceBeforePayment + ", Current Balance: " + balanceAfterPayment, "true");
        } catch (AssertionError e) {
            addStepInReport(e.getMessage(), "false");
            addStepInReport("Amount has not been deducted", "false");
            addStepInReport("Previous Balance: " + balanceBeforePayment + ", Current Balance: " + balanceAfterPayment, "false");
            throw e;
        }

    }

}
