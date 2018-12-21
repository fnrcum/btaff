package com.bl.qa.guice;

import com.bl.qa.selenium.BrowserAction;
import com.bl.qa.selenium.BrowserActionImpl;
import com.bl.qa.selenium.WebDriverProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public final class ProjectModule extends AbstractModule {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Override
    public void configure() {
        bind(BrowserAction.class).to(BrowserActionImpl.class);
        bind(WebDriver.class).toProvider(WebDriverProvider.class).in(Singleton.class);
        loadApplicationProperties();
    }

    @Provides
    public Actions getActions(WebDriver driver) {
        return new Actions(driver);
    }

    @Provides
    public JavascriptExecutor getExecutor(WebDriver driver) {
        return (JavascriptExecutor) (driver);
    }

    @Provides
    public WebDriverWait getWebDriverWait(WebDriver driver) {
        int timeoutInSeconds = Integer.valueOf(Optional.ofNullable(System.getProperty("selenium.timeout-in-seconds")).orElse("15"));
        return new WebDriverWait(driver, timeoutInSeconds);
    }

    private void loadApplicationProperties() {
        ClassLoader classLoader = getClass().getClassLoader();
        loadProperties(classLoader.getResource("application-common.properties").getPath());
        loadProperties(classLoader.getResource("application-" + System.getProperty("test.env".toLowerCase()) + ".properties").getPath());
    }

    private void loadProperties(String filePath) {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(filePath));
            Names.bindProperties(binder(), properties);
        } catch (FileNotFoundException | NullPointerException e) {
            LOGGER.error("The properties file " + filePath + " cannot be found");
        } catch (IOException e) {
            LOGGER.error("I/O exception during loading of properties file: " + filePath);
        }
    }
}