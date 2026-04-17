package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CoursesPage extends BasePage {
    private By courseCards = By.cssSelector("a[href*='/courses/']"); // Adjust based on structure
    private By firstCourseCard = By.xpath("(//a[contains(@href, '/courses/')])[1]");
    private By aboutSection = By.xpath("//*[contains(text(), 'حول الدورة التدريبية')]");
    
    // UI Verification locators
    private By cardImage = By.cssSelector("img");
    private By cardTitle = By.cssSelector("h3");
    private By cardInstructor = By.cssSelector("h6");
    private By subscribeBtn = By.xpath("//button[contains(text(), 'اشترك الآن')]");

    public CoursesPage(WebDriver driver) {
        super(driver);
    }

    public void clickFirstCourse() {
        click(firstCourseCard);
    }

    public boolean isAboutSectionDisplayed() {
        return isDisplayed(aboutSection);
    }

    public List<WebElement> getCourseCards() {
        return driver.findElements(courseCards);
    }

    public boolean verifyCardUI(WebElement card) {
        return card.findElement(cardImage).isDisplayed() &&
               card.findElement(cardTitle).isDisplayed() &&
               card.findElement(cardInstructor).isDisplayed() &&
               card.findElement(subscribeBtn).isDisplayed();
    }
}
