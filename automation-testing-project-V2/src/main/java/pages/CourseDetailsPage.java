package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CourseDetailsPage extends BasePage {

    // "حول الدورة التدريبية" section heading
    private By aboutSection = By.xpath(
        "//*[contains(text(),'حول الدورة التدريبية') or contains(text(),'عن الدورة') or contains(text(),'نبذة عن الدورة')]"
    );

    // Course title on the details page
    private By courseTitle = By.cssSelector(
        "h1, h2, [class*='course-title'], [class*='CourseTitle']"
    );

    // Subscribe / enroll button on the details page
    private By enrollBtn = By.xpath(
        "//button[contains(text(),'اشترك') or contains(text(),'الاشتراك') or contains(text(),'سجل')]"
        + " | //a[contains(text(),'اشترك') or contains(text(),'الاشتراك')]"
    );

    public CourseDetailsPage(WebDriver driver) {
        super(driver);
    }

    public void waitForPageToLoad() {
        wait.until(ExpectedConditions.presenceOfElementLocated(courseTitle));
    }

    public boolean isAboutSectionDisplayed() {
        return isDisplayed(aboutSection);
    }

    public boolean isCourseTitleDisplayed() {
        return isDisplayed(courseTitle);
    }

    public void clickEnroll() {
        click(enrollBtn);
    }

    public boolean isEnrollButtonDisplayed() {
        return isDisplayed(enrollBtn);
    }
}
