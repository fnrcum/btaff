package com.bl.qa.selenium;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserActionImpl implements BrowserAction {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Inject
    private WebDriver webDriver;

    @Inject
    private WebDriverWait wait;

    @Inject
    private JavascriptExecutor javascriptExecutor;

    @Override
    public void waitForPageLoad() {
        // Sleep 1 second for stability
        sleep(1000);

        // Wait for Javascript to load
        wait.until(webDriver -> javascriptExecutor.executeScript("return document.readyState").equals("complete"));

        // Wait for JQuery to load
        wait.until(webDriver ->
                javascriptExecutor.executeScript("return (window.jQuery != null) && (jQuery.active === 0)").equals(true));

        // Wait for Selenium to find body element in DOM
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body")));
    }

    @Override
    public void waitForAngularRequestFinish() {
        wait.until(angularDetected -> (Boolean) javascriptExecutor.executeScript("return !!(window.angular||window.ng)"));
        Object isAngularTwoPlus = javascriptExecutor.executeScript("return window.getAllAngularTestabilities");
        if (isAngularTwoPlus != null) {
            waitForAngularTwoPlusRequestFinish();
        } else {
            waitForAngularOneRequestFinish();
        }
    }

    private void waitForAngularOneRequestFinish() {
        WebElement element = webDriver.findElement(By.xpath("//*[@ng-app]"));
        Boolean angularReady = (Boolean) javascriptExecutor.executeScript("return angular.element(arguments[0]).injector().get" +
                "('$http')" + ".pendingRequests.length == 0;", element);
        wait.until(angularOneRequestsFinished -> angularReady);
    }

    private void waitForAngularTwoPlusRequestFinish() {
        wait.until(angularTwoPlusRequestsFinished -> {
            Object stable = javascriptExecutor.executeScript("return window.getAllAngularTestabilities()[0]" + "._isZoneStable;");
            return stable != null && (boolean) stable;
        });
    }

    private void sleep(int duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
