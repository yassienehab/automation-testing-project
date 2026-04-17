package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {
    private By nameInput = By.id("name");
    private By emailInput = By.id("email");
    private By phoneInput = By.id("phone");
    private By passwordInput = By.id("password");
    private By confirmPasswordInput = By.id("confirm_password");
    private By termsCheckbox = By.id("terms");
    private By submitBtn = By.xpath("//button[contains(text(), 'إنشاء حساب جديد')]");
    private By nameError = By.xpath("//*[contains(text(), 'مطلوب المستخدم اسم')]");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public void register(String name, String email, String phone, String password) {
        if (name != null) type(nameInput, name);
        type(emailInput, email);
        type(phoneInput, phone);
        type(passwordInput, password);
        type(confirmPasswordInput, password);
        click(termsCheckbox);
        click(submitBtn);
    }

    public String getNameErrorMessage() {
        return getText(nameError);
    }
}
