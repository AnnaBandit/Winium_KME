package framework;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.winium.WiniumDriver;
import serverside.ERIDataBase;

public class BasePage {
    public static WiniumDriver driver;
    protected ERIDataBase eriDataBase;
    public static Settings settings;

    public static <T extends BasePage> T initPage(Class<T> pageClass) {
        return PageFactory.initElements(driver, pageClass);
    }

}
