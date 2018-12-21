package com.bl.qa.selenium;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BrowserActionImplTest {

    @Mock
    private WebDriver webDriver;

    @Mock
    private FluentWait<WebDriver> fluentWait;

    @Mock
    private WebDriverWait wait;

    @Mock
    private JavascriptExecutor javascriptExecutor;

    @Mock
    private WebElement webElement;

    @InjectMocks
    private BrowserAction browserAction = new BrowserActionImpl();

    @Before
    public void setUp() {
        when(wait.until(any())).thenReturn(fluentWait);
        when(webDriver.findElement(any(By.class))).thenReturn(webElement);
    }

    @Test
    public void testWaitForAngularRequestFinishForAngularOne() {
        when(javascriptExecutor.executeScript(anyString())).thenReturn(new Object());

        browserAction.waitForAngularRequestFinish();
        Mockito.verify(javascriptExecutor, times(1)).executeScript(anyString());
        Mockito.verify(wait, times(2)).until(any());
    }

    @Test
    public void testWaitForAngularRequestFinishForAngularTwoPlus() {
        when(javascriptExecutor.executeScript(any())).thenReturn(null);

        browserAction.waitForAngularRequestFinish();
        Mockito.verify(javascriptExecutor, times(1)).executeScript(anyString());
        Mockito.verify(wait, times(2)).until(any());
    }

    @Test
    public void testWaitUntilPageLoadCallsWebDriverWait() {
        browserAction.waitForPageLoad();
        Mockito.verify(wait, times(3)).until(any());
    }
}