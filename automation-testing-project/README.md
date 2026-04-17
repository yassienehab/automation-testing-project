# EYOUTH Automation Project

This project automates key functionalities of the [EYOUTH website](https://eyouthlearning.com/ar) using Selenium WebDriver, TestNG, and the Page Object Model (POM) design pattern.

## Prerequisites
- Java JDK 11 or higher
- Maven 3.6 or higher
- Chrome Browser

## Project Structure
- `src/main/java/pages`: Contains Page Object classes for each website page.
- `src/test/java/tests`: Contains test classes and test cases.
- `pom.xml`: Project configuration and dependencies.

## How to Run Tests
1.  **Clone the repository**:
    ```bash
    git clone <repository_url>
    cd automation-testing-project
    ```
2.  **Run all tests**:
    ```bash
    mvn clean test
    ```
3.  **Generate Allure Report**:
    ```bash
    mvn allure:report
    mvn allure:serve
    ```

## Test Cases Covered
1.  Search with a valid keyword
2.  Open course details
3.  Open registration page
4.  Register with an empty username field
5.  Login with invalid credentials
6.  Login with empty fields
7.  End-to-end subscription flow
8.  Verify Facebook link
9.  Verify LinkedIn link
10. Verify YouTube link
11. Verify course cards UI

## Framework Details
- **POM Design Pattern**: Enhances maintainability and readability.
- **Explicit Waits**: Used for reliable element interaction.
- **TestNG**: Manages test execution and assertions.
- **WebDriverManager**: Automatically handles browser driver binaries.
- **Allure Report**: Provides detailed visual test results.
