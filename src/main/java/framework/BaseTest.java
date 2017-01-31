package framework;

import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.Thread.sleep;

public class BaseTest {
    private static Settings settings = new Settings();

    @BeforeSuite(alwaysRun = true)
    public void setUp(){
        DesktopOptions options = new DesktopOptions();
        options.setApplicationPath(settings.getApplicationPath());
        BasePage.settings = settings;

        try {
            BasePage.driver = new WiniumDriver(new URL("http://localhost:9999"), options);
            sleep(10000);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
/*
    @AfterSuite(alwaysRun = true)
    public void shutDown(){
        if (BasePage.driver != null) {
            BasePage.driver.close();
        }
    }*/


}
