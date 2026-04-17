package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import java.util.ArrayList;

public class EyouthTests extends BaseTest {

    @Test(description = "Test Case #1: Search with a valid keyword")
    public void testSearchKeyword() {
        HomePage homePage = new HomePage(driver);
        homePage.searchFor("كيف تنضم إلى البنك");
        Assert.assertTrue(driver.getTitle().contains("البنك إلى تنضم كيف"), "Title doesn't match");
    }

    @Test(description = "Test Case #2: Open course details")
    public void testOpenCourseDetails() {
        HomePage homePage = new HomePage(driver);
        homePage.clickAllCourses();
        CoursesPage coursesPage = new CoursesPage(driver);
        coursesPage.clickFirstCourse();
        Assert.assertTrue(coursesPage.isAboutSectionDisplayed(), "About section not displayed");
    }

    @Test(description = "Test Case #3: Open registration page")
    public void testOpenRegistrationPage() {
        HomePage homePage = new HomePage(driver);
        homePage.clickRegister();
        Assert.assertTrue(driver.getCurrentUrl().contains("/register") || driver.getCurrentUrl().contains("/signup"), "URL mismatch");
    }

    @Test(description = "Test Case #4: Register with an empty username field")
    public void testRegisterEmptyName() {
        HomePage homePage = new HomePage(driver);
        homePage.clickRegister();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register(null, "test@example.com", "0123456789", "Password123!");
        Assert.assertEquals(registerPage.getNameErrorMessage(), "مطلوب المستخدم اسم", "Error message mismatch");
    }

    @Test(description = "Test Case #5: Login with invalid credentials")
    public void testLoginInvalid() {
        HomePage homePage = new HomePage(driver);
        homePage.clickLogin();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("invalid@email.com", "wrongpass");
        // Note: Actual assertion might need adjustment based on site's error handling
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"), "Should stay on login page or show error");
    }

    @Test(description = "Test Case #8: Verify Facebook link")
    public void testFacebookLink() {
        HomePage homePage = new HomePage(driver);
        homePage.clickFacebook();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        Assert.assertTrue(driver.getCurrentUrl().contains("facebook.com"), "Facebook URL mismatch");
    }

    @Test(description = "Test Case #9: Verify LinkedIn link")
    public void testLinkedinLink() {
        HomePage homePage = new HomePage(driver);
        homePage.clickLinkedin();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        Assert.assertTrue(driver.getCurrentUrl().contains("linkedin.com"), "LinkedIn URL mismatch");
    }

    @Test(description = "Test Case #10: Verify YouTube link")
    public void testYoutubeLink() {
        HomePage homePage = new HomePage(driver);
        homePage.clickYoutube();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        Assert.assertTrue(driver.getCurrentUrl().contains("youtube.com"), "YouTube URL mismatch");
    }

    @Test(description = "Test Case #11: Verify course cards UI")
    public void testCourseCardsUI() {
        HomePage homePage = new HomePage(driver);
        homePage.clickAllCourses();
        CoursesPage coursesPage = new CoursesPage(driver);
        Assert.assertTrue(coursesPage.getCourseCards().size() > 0, "No course cards found");
        Assert.assertTrue(coursesPage.verifyCardUI(coursesPage.getCourseCards().get(0)), "Card UI elements missing");
    }
}
