package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {

    // eyouthlearning.com signup form fields
    // The site uses "username" not "name" as the field identifier
    private By usernameInput        = By.cssSelector(
        "input[name='username'], input[id='username'], input[placeholder*='اسم المستخدم'], input[placeholder*='username']"
    );
    private By emailInput           = By.cssSelector(
        "input[name='email'], input[id='email'], input[type='email'], input[placeholder*='البريد'], input[placeholder*='email']"
    );
    private By phoneInput           = By.cssSelector(
        "input[name='phone'], input[id='phone'], input[type='tel'], input[placeholder*='الهاتف'], input[placeholder*='phone']"
    );
    private By passwordInput        = By.cssSelector(
        "input[name='password'], input[id='password'], input[type='password']"
    );
    private By confirmPasswordInput = By.cssSelector(
        "input[name='password_confirmation'], input[name='confirm_password'], input[id='password_confirmation'], input[id='confirm_password']"
    );
    private By termsCheckbox        = By.cssSelector(
        "input[type='checkbox'], input[name='terms'], input[id='terms']"
    );

    // Submit button — the task says "انشاء"
    private By submitBtn = By.xpath(
        "//button[contains(text(),'إنشاء') or contains(text(),'انشاء') or contains(text(),'سجل') or contains(text(),'Register') or contains(text(),'تسجيل')]"
        + " | //input[@type='submit']"
    );

    // Username validation error — "اسم المستخدم مطلوب"
    private By usernameError = By.xpath(
        "//*[contains(text(),'اسم المستخدم مطلوب') or contains(text(),'مطلوب') and contains(text(),'المستخدم')]"
        + " | //input[@name='username' or @id='username']/following-sibling::*[contains(@class,'error') or contains(@class,'invalid') or contains(@class,'danger')]"
        + " | //*[contains(@class,'error') or contains(@class,'invalid-feedback')][contains(text(),'اسم') or contains(text(),'مستخدم')]"
    );

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Fills out the registration form.
     * Pass null for username to leave it empty (TC#4).
     */
    public void register(String username, String email, String phone, String password) {
        if (username != null && !username.isEmpty()) {
            type(usernameInput, username);
        }
        type(emailInput, email);
        if (phone != null) {
            type(phoneInput, phone);
        }
        type(passwordInput, password);
        try {
            type(confirmPasswordInput, password);
        } catch (Exception ignored) {
            // confirm password field may not exist on all versions of the form
        }
        try {
            jsClick(termsCheckbox);
        } catch (Exception ignored) {
            // terms checkbox may not be present
        }
        click(submitBtn);
    }

    public boolean isUsernameErrorDisplayed() {
        return isDisplayed(usernameError);
    }

    public String getUsernameErrorMessage() {
        try {
            return getText(usernameError);
        } catch (Exception e) {
            return "";
        }
    }
}
