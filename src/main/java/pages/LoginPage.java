package pages;

import framework.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import serverside.ERIDataBase;

import java.util.List;

public class LoginPage extends BasePage {

    private String loginTextboxID = "txtLogin";
    private String passwordTextboxID = "txtPassword";
    private String enterButtonName = "вход";
    private String languageDropdownName = "ComboBox";
    private String newUserLinkName = "Новый пользователь";
    private String activationCodeInputID = "txbToken";

    public void setLogin(String login){
        WebElement kme = driver.findElement(By.className("Window")).findElement(By.name("KME-dev"));
        kme.findElement(By.id(loginTextboxID)).clear();
        kme.findElement(By.id(loginTextboxID)).sendKeys(login);
    }

    public void setPassword(String password){
        WebElement kme = driver.findElement(By.className("Window")).findElement(By.name("KME-dev"));
        kme.findElement(By.id(passwordTextboxID)).clear();
        kme.findElement(By.id(passwordTextboxID)).sendKeys(password);
    }

    public void selectLanguage(String language){
        WebElement kme = driver.findElement(By.className("Window")).findElement(By.name("KME-dev"));
        kme.findElement(By.className(languageDropdownName)).click();
        List<WebElement> languages = kme.findElements(By.name("Eri.DAL.Domain.Languages.Language"));
                for (WebElement el: languages){
            if (el.findElement(By.className("TextBlock")).getAttribute("Name").equals(language)){
                el.click();
                break;
            }
        }
    }

    public void clickEnterButton(){
        WebElement kme = driver.findElement(By.className("Window")).findElement(By.name("KME-dev"));
        kme.findElement(By.name(enterButtonName)).click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.name("Выход")));
    }

    public LoginPage(){
        eriDataBase = new ERIDataBase(settings.getAzureServerName(),settings.getAzureServerDBName(),settings.getAzureServerUserLogin(),settings.getAzureServerUserPassword());
    }

    public void openActivationCodeDialog(){

        WebElement kme = driver.findElement(By.className("Window")).findElement(By.name("KME-dev"));
        kme.findElement(By.name(newUserLinkName)).click();
    }

    public void enterActivationCode(String userid) {
        eriDataBase.resetActivationCodeData(Integer.parseInt(userid));
        WebElement kme = driver.findElement(By.className("Window")).findElement(By.name("KME-dev"));
        kme.findElement(By.id(activationCodeInputID)).click();
        kme.findElement(By.id(activationCodeInputID)).sendKeys(eriDataBase.getUserActivationCode(Integer.parseInt(userid)));
        kme.sendKeys(Keys.ENTER);
    }

    public boolean userLoginIsRight(String userid){
        WebElement kme = driver.findElement(By.className("Window")).findElement(By.name("KME-dev"));
        new WebDriverWait(driver, 30).until(ExpectedConditions.textToBePresentInElement(kme.findElement(By.id(loginTextboxID)), eriDataBase.getUserLoginName(Integer.parseInt(userid))));
        return kme.findElement(By.id(loginTextboxID)).getText().contains(eriDataBase.getUserLoginName(Integer.parseInt(userid)));
    }
}
