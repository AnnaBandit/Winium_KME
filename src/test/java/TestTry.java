import framework.BaseTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.ChoosePatientPage;
import pages.LoginPage;

import static framework.BasePage.initPage;
import static org.testng.Assert.assertTrue;

public class TestTry extends BaseTest {
    LoginPage loginPage;
    ChoosePatientPage choosePatientPage;

    @BeforeSuite
    public void initPages(){
        loginPage = initPage(LoginPage.class);
        choosePatientPage = initPage(ChoosePatientPage.class);
    }

    @Test
    public void testActivation(){
        loginPage.openActivationCodeDialog();
        loginPage.enterActivationCode("3422");

        assertTrue(loginPage.userLoginIsRight("3422"));
    }

    @Test
    public void testLogin(){

        loginPage.selectLanguage("Русский");
        loginPage.setLogin("test\\testoviy");
        loginPage.setPassword("1234qwer");
        loginPage.clickEnterButton();

        assertTrue(choosePatientPage.isOpened());
    }
}
