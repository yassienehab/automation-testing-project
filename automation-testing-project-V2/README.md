# EYouth Automation Testing Project — Version 2

Automated test suite for [eyouthlearning.com](https://eyouthlearning.com/ar) built with **Selenium 4 + TestNG + Page Object Model (POM)**.

---

## Project Structure

```
automation-testing-project/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/
    │   │   ├── pages/
    │   │   │   ├── BasePage.java          # Shared WebDriver helpers & explicit waits
    │   │   │   ├── HomePage.java          # Home page actions (search, nav, social links)
    │   │   │   ├── CoursesPage.java       # Courses listing page actions & card UI
    │   │   │   ├── CourseDetailsPage.java # Course details page assertions  ← NEW in V2
    │   │   │   ├── LoginPage.java         # Login page actions & error assertions
    │   │   │   └── RegisterPage.java      # Registration page actions & validation
    │   │   └── utils/
    │   │       └── ConfigReader.java      # Reads config.properties               ← NEW in V2
    │   └── resources/
    │       └── config.properties          # Test data & base URL
    └── test/
        ├── java/tests/
        │   ├── BaseTest.java              # ChromeDriver setup & teardown
        │   └── EyouthTests.java          # All 11 test cases
        └── resources/
            ├── testng.xml                 # TestNG suite definition                ← NEW in V2
            └── config.properties          # Test-scope copy of config
```

---

## Test Cases

| # | Test Method | Description |
|---|-------------|-------------|
| TC#1  | `testSearchKeyword`            | Search keyword "كيف تنضم إلى البنك", assert title/URL |
| TC#2  | `testOpenCourseDetails`        | Navigate to courses → first course → assert details page & about section |
| TC#3  | `testOpenRegistrationPage`     | Click register link, assert URL `/signup` or `/register` |
| TC#4  | `testRegisterEmptyUsername`    | Submit form without username, assert validation error |
| TC#5  | `testLoginInvalidCredentials`  | Login with wrong creds, assert error message |
| TC#6  | `testLoginEmptyFields`         | Click login with empty fields, assert required validation |
| TC#7  | `testEndToEndSubscribeCourse`  | Full E2E: login → courses → subscribe → assert redirect |
| TC#8  | `testFacebookLink`             | Footer Facebook icon opens facebook.com in new tab |
| TC#9  | `testLinkedinLink`             | Footer LinkedIn icon opens linkedin.com in new tab |
| TC#10 | `testYoutubeLink`              | Footer YouTube icon opens youtube.com in new tab |
| TC#11 | `testCourseCardUI`             | First course card has image, title, instructor, subscribe button |

---

## Prerequisites

- Java 11+
- Maven 3.6+
- Google Chrome (latest)
- Internet connection (tests run against live site)

---

## Configuration

Edit `src/test/resources/config.properties` before running:

```properties
base.url=https://eyouthlearning.com/ar

# Required for TC#7 — use a real registered account
valid.email=your_real_email@example.com
valid.password=YourRealPassword
```

> **Note:** TC#7 (E2E subscribe) requires valid login credentials. All other tests use non-authenticated flows or invalid credentials intentionally.

---

## Running Tests

### Run all tests
```bash
cd automation-testing-project
mvn clean test
```

### Run a specific test
```bash
mvn clean test -Dtest=EyouthTests#testSearchKeyword
```

### Run via TestNG XML
```bash
mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml
```

---

## Allure Report (Bonus)

Tests are annotated with `@Feature`, `@Severity`, and `@Description` for rich Allure reporting.

### Generate & open report
```bash
# Run tests first
mvn clean test

# Generate report
mvn allure:report

# Open report in browser
mvn allure:serve
```

The report will be generated at `target/site/allure-maven-plugin/`.

---

## V2 Changes from V1

See [FIXES_AND_IMPROVEMENTS.md](./FIXES_AND_IMPROVEMENTS.md) for the complete fix log.

### Key improvements
- **TC#6 added** — was completely missing in V1
- **TC#7 added** — was completely missing in V1  
- **`CourseDetailsPage`** — new page class for course detail assertions
- **`ConfigReader`** — centralized test data management via `config.properties`
- **`testng.xml`** — explicit suite file for ordered, controlled execution
- **Corrected locators** — all placeholder/guessed selectors replaced with multi-strategy CSS/XPath
- **Scroll before social icon clicks** — footer elements now scrolled into view before clicking
- **`switchToNewTab()` helper** — shared, wait-safe tab switching replaces inline duplicate code
- **`jsClick()`** — JavaScript fallback for elements that intercept pointer events
- **Allure annotations** — `@Feature`, `@Severity`, `@Description` on every test method
