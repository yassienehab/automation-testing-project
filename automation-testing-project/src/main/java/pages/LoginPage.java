package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private By emailInput = By.id("email");
    private By passwordInput = By.id("password");
    private By loginBtn = By.xpath("//button[contains(text(), 'تسجيل الدخول')]");
    private By errorMsg = By.xpath("//*[contains(@class, 'error')]"); // Placeholder class

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String email, String password) {
        type(emailInput, email);
        type(passwordInput, password);
        click(loginBtn);
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMsg);
    }
}
