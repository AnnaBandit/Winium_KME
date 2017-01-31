package pages;

import framework.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class ChoosePatientPage extends BasePage {

    private String exitButtonName = "Выход";


    public boolean exitButtonIsDisplayed(){
        WebElement kme = driver.findElement(By.className("Window")).findElement(By.name("KME-dev"));
        return (kme.findElements(By.name(exitButtonName)).size() == 1) && (kme.findElement(By.name(exitButtonName)).getAttribute("IsOffscreen").equals("False"));
    }

    public boolean isOpened(){
        return exitButtonIsDisplayed();
    }

}
