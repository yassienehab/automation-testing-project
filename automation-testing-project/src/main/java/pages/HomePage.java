package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private By searchInput = By.cssSelector("input[placeholder*='بحث عن الدورات التدريبية']");
    private By searchIcon = By.cssSelector("button svg"); // Adjust if needed
    private By allCoursesBtn = By.linkText("عرض الدورات التدريبية");
    private By loginBtn = By.linkText("تسجيل الدخول");
    private By registerBtn = By.linkText("حساب جديد");
    private By facebookIcon = By.cssSelector("a[href*='facebook.com']");
    private By linkedinIcon = By.cssSelector("a[href*='linkedin.com']");
    private By youtubeIcon = By.cssSelector("a[href*='youtube.com']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void searchFor(String keyword) {
        type(searchInput, keyword);
        driver.findElement(searchInput).submit();
    }

    public void clickAllCourses() {
        click(allCoursesBtn);
    }

    public void clickLogin() {
        click(loginBtn);
    }

    public void clickRegister() {
        click(registerBtn);
    }

    public void clickFacebook() {
        click(facebookIcon);
    }

    public void clickLinkedin() {
        click(linkedinIcon);
    }

    public void clickYoutube() {
        click(youtubeIcon);
    }
}
