package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CoursesPage extends BasePage {

    // Course card container — the eyouthlearning course grid cards
    private By courseCardContainers = By.cssSelector(
        ".course-card, .card, article.course, div[class*='course-item'], div[class*='CourseCard'], div[class*='course_card']"
    );

    // First clickable course link
    private By firstCourseLink = By.xpath(
        "(//a[contains(@href,'/courses/') or contains(@href,'/course/')])[1]"
    );

    // About section on course details page
    private By aboutSection = By.xpath(
        "//*[contains(text(),'حول الدورة التدريبية') or contains(text(),'عن الدورة') or contains(text(),'نبذة')]"
    );

    // Card UI sub-elements (searched within each card container)
    private By cardImage      = By.cssSelector("img");
    private By cardTitle      = By.cssSelector("h3, h4, h2, [class*='title'], [class*='Title']");
    private By cardInstructor = By.cssSelector("h6, p, span, [class*='instructor'], [class*='author'], [class*='Instructor']");
    private By subscribeBtn   = By.xpath(
        ".//button[contains(text(),'اشترك') or contains(text(),'سجل') or contains(text(),'ابدأ') or contains(text(),'الاشتراك')]"
        + " | .//a[contains(text(),'اشترك') or contains(text(),'سجل') or contains(text(),'ابدأ')]"
    );

    // My courses / enrolled card locator (used in TC#7 E2E)
    private By myCoursesSection = By.xpath(
        "//*[contains(text(),'دوراتي') or contains(text(),'المسجلة') or contains(text(),'المشتركة')]"
    );

    public CoursesPage(WebDriver driver) {
        super(driver);
    }

    public void waitForCoursesPageToLoad() {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstCourseLink));
    }

    public void clickFirstCourse() {
        waitForCoursesPageToLoad();
        click(firstCourseLink);
    }

    public boolean isAboutSectionDisplayed() {
        return isDisplayed(aboutSection);
    }

    /**
     * Returns the list of course card container elements.
     * Falls back to generic card selectors if specific ones aren't found.
     */
    public List<WebElement> getCourseCards() {
        List<WebElement> cards = driver.findElements(courseCardContainers);
        if (cards.isEmpty()) {
            // Fallback: any anchor that links to a course
            cards = driver.findElements(By.cssSelector("a[href*='/course']"));
        }
        return cards;
    }

    /**
     * Verifies the UI elements within a single course card.
     * Robust: checks for any of the expected sub-elements.
     */
    public boolean verifyCardUI(WebElement card) {
        boolean hasImage      = !card.findElements(cardImage).isEmpty()
                                && card.findElements(cardImage).get(0).isDisplayed();
        boolean hasTitle      = !card.findElements(cardTitle).isEmpty()
                                && card.findElements(cardTitle).get(0).isDisplayed();
        boolean hasInstructor = !card.findElements(cardInstructor).isEmpty()
                                && card.findElements(cardInstructor).get(0).isDisplayed();
        boolean hasSubscribe  = !card.findElements(subscribeBtn).isEmpty();

        return hasImage && hasTitle && hasInstructor && hasSubscribe;
    }

    /**
     * Subscribes to the first available course on the courses list page.
     * Used in TC#7 E2E test.
     */
    public void subscribeToFirstCourse() {
        waitForCoursesPageToLoad();
        List<WebElement> cards = getCourseCards();
        if (cards.isEmpty()) {
            throw new RuntimeException("No course cards found on courses page");
        }
        // Click the first subscribe button found
        WebElement firstSubscribeBtn = cards.get(0).findElements(subscribeBtn).stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No subscribe button found on first card"));
        scrollIntoView(firstSubscribeBtn);
        firstSubscribeBtn.click();
    }

    public boolean isMyCoursesSectionDisplayed() {
        return isDisplayed(myCoursesSection);
    }
}
