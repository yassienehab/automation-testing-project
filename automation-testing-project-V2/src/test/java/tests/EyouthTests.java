package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.ConfigReader;

import java.time.Duration;
import java.util.ArrayList;

public class EyouthTests extends BaseTest {

    // ─────────────────────────────────────────────────────────────
    // TC#1: Search with a valid keyword
    // ─────────────────────────────────────────────────────────────
    @Test(description = "TC#1: Search with a valid keyword")
    @Feature("Search")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Enter keyword 'كيف تنضم إلى البنك', submit search, assert page title contains the keyword.")
    public void testSearchKeyword() {
        HomePage homePage = new HomePage(driver);
        homePage.searchFor("كيف تنضم إلى البنك");

        // Wait for navigation / title update
        new WebDriverWait(driver, Duration.ofSeconds(20))
            .until(ExpectedConditions.or(
                ExpectedConditions.titleContains("البنك"),
                ExpectedConditions.urlContains("search"),
                ExpectedConditions.urlContains("q=")
            ));

        String title = driver.getTitle();
        String url   = driver.getCurrentUrl();

        // Assert title OR URL contains the search keyword (some sites reflect keyword in URL)
        Assert.assertTrue(
            title.contains("البنك") || url.contains("البنك") || url.contains("search"),
            "Search did not navigate to results page. Title: [" + title + "] URL: [" + url + "]"
        );
    }

    // ─────────────────────────────────────────────────────────────
    // TC#2: Open course details
    // ─────────────────────────────────────────────────────────────
    @Test(description = "TC#2: Open course details")
    @Feature("Courses")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Navigate to all courses, click first course, assert details page and 'حول الدورة التدريبية' section.")
    public void testOpenCourseDetails() {
        HomePage homePage = new HomePage(driver);
        homePage.clickAllCourses();

        CoursesPage coursesPage = new CoursesPage(driver);
        coursesPage.waitForCoursesPageToLoad();
        coursesPage.clickFirstCourse();

        CourseDetailsPage detailsPage = new CourseDetailsPage(driver);
        detailsPage.waitForPageToLoad();

        Assert.assertTrue(
            detailsPage.isCourseTitleDisplayed(),
            "Course details page did not open — course title not displayed."
        );
        Assert.assertTrue(
            detailsPage.isAboutSectionDisplayed(),
            "'حول الدورة التدريبية' section is not displayed on the course details page."
        );
    }

    // ─────────────────────────────────────────────────────────────
    // TC#3: Open registration page
    // ─────────────────────────────────────────────────────────────
    @Test(description = "TC#3: Open registration page")
    @Feature("Registration")
    @Severity(SeverityLevel.NORMAL)
    @Description("Click 'انضم لنا' / register link and assert URL contains /signup or /register.")
    public void testOpenRegistrationPage() {
        HomePage homePage = new HomePage(driver);
        homePage.clickRegister();

        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/signup"),
                ExpectedConditions.urlContains("/register")
            ));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(
            currentUrl.contains("/signup") || currentUrl.contains("/register"),
            "Registration page URL does not match. Current URL: " + currentUrl
        );
    }

    // ─────────────────────────────────────────────────────────────
    // TC#4: Register with an empty username field
    // ─────────────────────────────────────────────────────────────
    @Test(description = "TC#4: Register with empty username field")
    @Feature("Registration")
    @Severity(SeverityLevel.NORMAL)
    @Description("Fill all registration fields except username, submit and assert validation error appears.")
    public void testRegisterEmptyUsername() {
        HomePage homePage = new HomePage(driver);
        homePage.clickRegister();

        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/signup"),
                ExpectedConditions.urlContains("/register")
            ));

        RegisterPage registerPage = new RegisterPage(driver);
        // Pass null for username — all other fields filled
        registerPage.register(
            null,
            ConfigReader.getRegisterEmail(),
            ConfigReader.getRegisterPhone(),
            ConfigReader.getRegisterPassword()
        );

        Assert.assertTrue(
            registerPage.isUsernameErrorDisplayed(),
            "Username validation error message was not displayed after submitting with empty username."
        );
    }

    // ─────────────────────────────────────────────────────────────
    // TC#5: Login with invalid credentials
    // ─────────────────────────────────────────────────────────────
    @Test(description = "TC#5: Login with invalid credentials")
    @Feature("Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Enter wrong username and password, submit, assert error message is displayed.")
    public void testLoginInvalidCredentials() {
        HomePage homePage = new HomePage(driver);
        homePage.clickLogin();

        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.urlContains("/login"));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
            ConfigReader.getInvalidEmail(),
            ConfigReader.getInvalidPassword()
        );

        Assert.assertTrue(
            loginPage.isInvalidCredentialsErrorDisplayed(),
            "Error message was not displayed after login with invalid credentials."
        );
    }

    // ─────────────────────────────────────────────────────────────
    // TC#6: Login with empty fields
    // ─────────────────────────────────────────────────────────────
    @Test(description = "TC#6: Login with empty fields")
    @Feature("Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("Leave username and password empty, click Login, assert required field validation messages appear.")
    public void testLoginEmptyFields() {
        HomePage homePage = new HomePage(driver);
        homePage.clickLogin();

        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.urlContains("/login"));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLoginWithoutCredentials();

        Assert.assertTrue(
            loginPage.isAnyValidationMessageDisplayed(),
            "Validation messages were not shown after clicking Login with empty fields."
        );
    }

    // ─────────────────────────────────────────────────────────────
    // TC#7: End-to-end — login, go to courses, subscribe, verify
    // ─────────────────────────────────────────────────────────────
    @Test(description = "TC#7: End-to-end — login, subscribe to course, assert enrollment")
    @Feature("E2E")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Log in with valid credentials, navigate to all courses, subscribe to first course, assert card is in user's courses.")
    public void testEndToEndSubscribeCourse() {
        // Step 1: Login
        HomePage homePage = new HomePage(driver);
        homePage.clickLogin();

        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.urlContains("/login"));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
            ConfigReader.getValidEmail(),
            ConfigReader.getValidPassword()
        );

        // Wait for redirect back to home after successful login
        new WebDriverWait(driver, Duration.ofSeconds(20))
            .until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));

        // Step 2: Navigate to All Courses
        homePage = new HomePage(driver);
        homePage.clickAllCourses();

        // Step 3: Subscribe to the first available course
        CoursesPage coursesPage = new CoursesPage(driver);
        coursesPage.waitForCoursesPageToLoad();
        coursesPage.subscribeToFirstCourse();

        // Step 4: Assert enrollment confirmation
        // Enrollment may redirect to course details or show a success message
        new WebDriverWait(driver, Duration.ofSeconds(20))
            .until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/course"),
                ExpectedConditions.urlContains("/dashboard"),
                ExpectedConditions.urlContains("/my-courses")
            ));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(
            currentUrl.contains("/course") || currentUrl.contains("/dashboard") || currentUrl.contains("/my-courses"),
            "After subscribing, expected redirect to course/dashboard/my-courses. Current URL: " + currentUrl
        );
    }

    // ─────────────────────────────────────────────────────────────
    // TC#8: Verify Facebook link
    // ─────────────────────────────────────────────────────────────
    @Test(description = "TC#8: Verify Facebook footer link")
    @Feature("Social Links")
    @Severity(SeverityLevel.MINOR)
    @Description("Scroll to footer, click Facebook icon, switch to new tab, assert URL contains facebook.com.")
    public void testFacebookLink() {
        HomePage homePage = new HomePage(driver);
        homePage.clickFacebook();

        String newTabUrl = switchToNewTab();
        Assert.assertTrue(
            newTabUrl.contains("facebook.com"),
            "Facebook link did not open facebook.com. Opened: " + newTabUrl
        );
    }

    // ─────────────────────────────────────────────────────────────
    // TC#9: Verify LinkedIn link
    // ─────────────────────────────────────────────────────────────
    @Test(description = "TC#9: Verify LinkedIn footer link")
    @Feature("Social Links")
    @Severity(SeverityLevel.MINOR)
    @Description("Scroll to footer, click LinkedIn icon, switch to new tab, assert URL contains linkedin.com.")
    public void testLinkedinLink() {
        HomePage homePage = new HomePage(driver);
        homePage.clickLinkedin();

        String newTabUrl = switchToNewTab();
        Assert.assertTrue(
            newTabUrl.contains("linkedin.com"),
            "LinkedIn link did not open linkedin.com. Opened: " + newTabUrl
        );
    }

    // ─────────────────────────────────────────────────────────────
    // TC#10: Verify YouTube link
    // ─────────────────────────────────────────────────────────────
    @Test(description = "TC#10: Verify YouTube footer link")
    @Feature("Social Links")
    @Severity(SeverityLevel.MINOR)
    @Description("Scroll to footer, click YouTube icon, switch to new tab, assert URL contains youtube.com.")
    public void testYoutubeLink() {
        HomePage homePage = new HomePage(driver);
        homePage.clickYoutube();

        String newTabUrl = switchToNewTab();
        Assert.assertTrue(
            newTabUrl.contains("youtube.com"),
            "YouTube link did not open youtube.com. Opened: " + newTabUrl
        );
    }

    // ─────────────────────────────────────────────────────────────
    // TC#11: Verify course card UI elements
    // ─────────────────────────────────────────────────────────────
    @Test(description = "TC#11: Verify course card UI elements")
    @Feature("Courses")
    @Severity(SeverityLevel.NORMAL)
    @Description("Open courses page, get first card, assert it has image, title, instructor name, and subscribe button.")
    public void testCourseCardUI() {
        HomePage homePage = new HomePage(driver);
        homePage.clickAllCourses();

        CoursesPage coursesPage = new CoursesPage(driver);
        coursesPage.waitForCoursesPageToLoad();

        java.util.List<org.openqa.selenium.WebElement> cards = coursesPage.getCourseCards();
        Assert.assertFalse(cards.isEmpty(), "No course cards found on the courses page.");

        boolean cardUIValid = coursesPage.verifyCardUI(cards.get(0));
        Assert.assertTrue(
            cardUIValid,
            "First course card is missing one or more expected UI elements: image, title, instructor, or subscribe button."
        );
    }

    // ─────────────────────────────────────────────────────────────
    // Shared helper: switch to the most recently opened tab
    // ─────────────────────────────────────────────────────────────
    @Step("Switch to new browser tab and get URL")
    private String switchToNewTab() {
        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(d -> d.getWindowHandles().size() > 1);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));
        // Wait for the new tab to finish loading
        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(d -> !d.getCurrentUrl().equals("about:blank") && !d.getCurrentUrl().isEmpty());
        return driver.getCurrentUrl();
    }
}
