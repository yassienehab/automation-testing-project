package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    private By emailInput    = By.cssSelector("input[type='email'], input[name='email'], input[id='email'], input[placeholder*='البريد'], input[placeholder*='email']");
    private By passwordInput = By.cssSelector("input[type='password'], input[name='password'], input[id='password']");
    private By loginBtn      = By.xpath(
        "//button[contains(text(),'تسجيل الدخول') or contains(text(),'دخول') or contains(text(),'Login')]"
        + " | //input[@type='submit']"
    );

    // Error messages — invalid credentials or server-side error
    private By invalidCredentialsError = By.xpath(
        "//*[contains(@class,'error') or contains(@class,'alert') or contains(@class,'invalid') or contains(@class,'danger')]"
        + "[contains(text(),'خطأ') or contains(text(),'غير') or contains(text(),'invalid') or contains(text(),'بيانات') or contains(text(),'incorrect')]"
    );

    // Required field validation messages (HTML5 or custom)
    private By emailRequiredMsg    = By.xpath(
        "//*[contains(text(),'مطلوب') or contains(text(),'required') or contains(text(),'إجباري')]"
        + "[preceding-sibling::*[contains(@name,'email')] or following-sibling::*[contains(@name,'email')]]"
        + " | //input[@name='email' or @type='email']/following-sibling::*[contains(@class,'error') or contains(@class,'invalid')]"
    );
    private By passwordRequiredMsg = By.xpath(
        "//*[contains(text(),'مطلوب') or contains(text(),'required') or contains(text(),'إجباري')]"
        + "[preceding-sibling::*[contains(@type,'password')] or following-sibling::*[contains(@type,'password')]]"
        + " | //input[@type='password']/following-sibling::*[contains(@class,'error') or contains(@class,'invalid')]"
    );
    // Catch-all for any visible validation message on the page
    private By anyValidationMsg = By.cssSelector(
        ".error, .invalid-feedback, [class*='error'], [class*='Error'], [role='alert']"
    );

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String email, String password) {
        type(emailInput, email);
        type(passwordInput, password);
        click(loginBtn);
    }

    /**
     * Clicks the login button without filling any fields.
     * Used in TC#6 (empty fields validation).
     */
    public void clickLoginWithoutCredentials() {
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
    }

    public boolean isInvalidCredentialsErrorDisplayed() {
        // First wait a moment for the server response
        try {
            wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(invalidCredentialsError),
                ExpectedConditions.urlContains("/login")
            ));
        } catch (Exception ignored) {}
        return isDisplayed(invalidCredentialsError)
               || driver.getCurrentUrl().contains("/login");
    }

    public boolean isAnyValidationMessageDisplayed() {
        return isDisplayed(anyValidationMsg)
               || isDisplayed(emailRequiredMsg)
               || isDisplayed(passwordRequiredMsg);
    }
}
