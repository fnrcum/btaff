package com.bl.qa.selenium;

import com.google.inject.Provider;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.Optional;

public class WebDriverProvider implements Provider<WebDriver> {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Override
    public WebDriver get() {
        String browser = Optional.ofNullable(System.getProperty("selenium.browser")).orElse("ie").toLowerCase();

        String chromeDriverVersion = Optional.ofNullable(System.getProperty("selenium.chrome-driver.version")).orElse("71.0.3578.33");
        String firefoxDriverVersion = Optional.ofNullable(System.getProperty("selenium.firefox-driver.version")).orElse("0.23.0");
        String ieDriverVersion = Optional.ofNullable(System.getProperty("selenium.ie-driver.version")).orElse("3.14");

        WebDriver webDriver;

        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().version(chromeDriverVersion).setup();
                webDriver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().version(firefoxDriverVersion).setup();
                webDriver = new FirefoxDriver();
                break;
            default:
                WebDriverManager.iedriver().arch32().version(ieDriverVersion).setup();
                webDriver = new InternetExplorerDriver();
                break;
        }

        webDriver.switchTo().window(webDriver.getWindowHandle());
        webDriver.manage().window().maximize();

        LOGGER.info("***** Driver initialized as a " + browser + " WebDriver. *****");

        return webDriver;
    }

}
