package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    // Header / Nav locators
    private By searchIcon       = By.cssSelector("button.search-btn, button[aria-label*='search'], header button svg, .navbar button[type='button']");
    private By searchInput      = By.cssSelector("input[placeholder*='بحث'], input[type='search'], input[name='q']");
    private By searchSubmitBtn  = By.cssSelector("button[type='submit'], form button[type='submit']");

    // Navigation links — eyouthlearning.com uses Arabic nav links
    private By allCoursesLink   = By.xpath("//a[contains(text(),'الدورات') or contains(text(),'دوراتنا') or contains(text(),'جميع الدورات') or contains(text(),'كل الكورسات')]");
    private By loginLink        = By.xpath("//a[contains(text(),'تسجيل الدخول') or contains(text(),'دخول')]");
    private By registerLink     = By.xpath("//a[contains(text(),'انضم لنا') or contains(text(),'سجل') or contains(text(),'حساب جديد') or contains(text(),'إنشاء حساب')]");

    // Footer social icons
    private By facebookIcon     = By.cssSelector("footer a[href*='facebook.com'], a[href*='facebook.com']");
    private By linkedinIcon     = By.cssSelector("footer a[href*='linkedin.com'], a[href*='linkedin.com']");
    private By youtubeIcon      = By.cssSelector("footer a[href*='youtube.com'], a[href*='youtube.com']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Perform a header search.
     * Tries to open the search field first (some sites hide it behind an icon),
     * then types the keyword and submits.
     */
    public void searchFor(String keyword) {
        // Try clicking search icon to reveal input if hidden
        try {
            if (!driver.findElements(searchInput).stream().anyMatch(WebElement::isDisplayed)) {
                jsClick(searchIcon);
            }
        } catch (Exception ignored) {}

        type(searchInput, keyword);

        // Submit via the submit button if available, otherwise use form submit
        try {
            click(searchSubmitBtn);
        } catch (Exception e) {
            driver.findElements(searchInput).stream()
                  .filter(WebElement::isDisplayed)
                  .findFirst()
                  .ifPresent(WebElement::submit);
        }
    }

    public void clickAllCourses() {
        click(allCoursesLink);
    }

    public void clickLogin() {
        click(loginLink);
    }

    public void clickRegister() {
        click(registerLink);
    }

    /**
     * Scrolls to the footer social icons before clicking.
     */
    public void clickFacebook() {
        scrollToBottom();
        jsClick(facebookIcon);
    }

    public void clickLinkedin() {
        scrollToBottom();
        jsClick(linkedinIcon);
    }

    public void clickYoutube() {
        scrollToBottom();
        jsClick(youtubeIcon);
    }

    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(text(),'الدورات') or contains(text(),'دوراتنا')]")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
