# V2 Fixes & Improvements Over V1

## Missing Test Cases (Added in V2)

### TC#6 — Login with empty fields
- **V1 status:** Completely absent. Not implemented at all.
- **V2 fix:** Added `testLoginEmptyFields()`. Navigates to login page, clicks the Login button with no input, and asserts that at least one validation message is displayed using a multi-selector strategy.

### TC#7 — End-to-end subscribe to a course
- **V1 status:** Completely absent. Not implemented at all.
- **V2 fix:** Added `testEndToEndSubscribeCourse()`. Performs full E2E flow: login → navigate to all courses → click first subscribe button → assert redirect to course/dashboard/my-courses URL.

---

## Missing Page Class (Added in V2)

### `CourseDetailsPage.java`
- **V1 status:** TC#2 asserted the about section via `CoursesPage`, which is architecturally wrong — the course details page is a separate page.
- **V2 fix:** Added `CourseDetailsPage` with locators for course title, about section, and enroll button. TC#2 now correctly uses this class after navigating from `CoursesPage`.

---

## Missing Infrastructure (Added in V2)

### `ConfigReader.java` + `config.properties`
- **V1 status:** All test data (emails, passwords, URLs) were hardcoded in test methods. No central configuration.
- **V2 fix:** Created `utils/ConfigReader.java` and `src/test/resources/config.properties`. All test data is now read from config, following the project requirement: *"Data can be hardcoded or read from config"*.

### `testng.xml`
- **V1 status:** No TestNG suite XML file. Running `mvn test` relied on TestNG auto-discovery, which can lead to unpredictable ordering and no control over which tests run.
- **V2 fix:** Added `src/test/resources/testng.xml` with all 11 tests explicitly listed in order. `pom.xml` updated to reference it via `<suiteXmlFile>`.

---

## Broken Locators Fixed

### `HomePage.java` — `allCoursesBtn`
- **V1:** `By.linkText("عرض الدورات التدريبية")` — Incorrect link text. The navigation link on eyouthlearning.com uses different Arabic text.
- **V2:** Multi-fallback XPath: `//a[contains(text(),'الدورات') or contains(text(),'دوراتنا') or contains(text(),'جميع الدورات')]`

### `HomePage.java` — `registerBtn`
- **V1:** `By.linkText("حساب جديد")` — Incorrect. The project requirement states the button text is "انضم لنا".
- **V2:** XPath: `//a[contains(text(),'انضم لنا') or contains(text(),'سجل') or contains(text(),'إنشاء حساب')]`

### `HomePage.java` — `searchInput`
- **V1:** `By.cssSelector("input[placeholder*='بحث عن الدورات التدريبية']")` — Placeholder text may not exactly match.
- **V2:** `By.cssSelector("input[placeholder*='بحث'], input[type='search'], input[name='q']")`

### `HomePage.java` — `searchIcon`
- **V1:** `By.cssSelector("button svg")` — Matches any SVG button on the page, extremely fragile.
- **V2:** More specific: `By.cssSelector("button.search-btn, button[aria-label*='search'], header button svg")`

### `LoginPage.java` — `errorMsg`
- **V1:** `By.xpath("//*[contains(@class, 'error')]")` with comment `// Placeholder class` — admitted placeholder, won't reliably find the actual error.
- **V2:** Robust dual-strategy: matches on class AND text content (`خطأ`, `غير`, `بيانات`, `incorrect`). Also falls back to URL still containing `/login`.

### `LoginPage.java` — `emailInput` / `passwordInput`
- **V1:** Used `By.id("email")` and `By.id("password")` — IDs may differ on the live site.
- **V2:** Multi-attribute selectors: `input[type='email'], input[name='email'], input[id='email'], input[placeholder*='البريد']`

### `RegisterPage.java` — `nameInput`
- **V1:** `By.id("name")` — The field is actually a **username** field ("اسم المستخدم"), not a name field. ID is likely `username`.
- **V2:** `By.cssSelector("input[name='username'], input[id='username'], input[placeholder*='اسم المستخدم']")`

### `RegisterPage.java` — `termsCheckbox`
- **V1:** `By.id("terms")` — Guessed ID that may not exist.
- **V2:** `By.cssSelector("input[type='checkbox'], input[name='terms'], input[id='terms']")` with `jsClick()` fallback.

### `RegisterPage.java` — `submitBtn`
- **V1:** `By.xpath("//button[contains(text(), 'إنشاء حساب جديد')]")` — Text does not match requirement ("انشاء").
- **V2:** `//button[contains(text(),'إنشاء') or contains(text(),'انشاء') or contains(text(),'سجل')]`

### `RegisterPage.java` — `nameError`
- **V1:** `By.xpath("//*[contains(text(), 'مطلوب المستخدم اسم')]")` — Arabic word order is wrong. Correct is "اسم المستخدم مطلوب".
- **V2:** `contains(text(),'اسم المستخدم مطلوب') or (contains(text(),'مطلوب') and contains(text(),'المستخدم'))` with CSS class fallbacks.

### `CoursesPage.java` — `verifyCardUI()`
- **V1:** Used relative selectors (`h3`, `h6`, `img`, `//button[...]`) directly on an `<a>` anchor element that wraps the card. The button XPath would escape the card context and match any button on the page.
- **V2:** Checks for presence (not just visibility) using `findElements().isEmpty()`, and uses relative XPath `.//button[...]` anchored to the card. Falls back gracefully if button is not inside the anchor.

### `CoursesPage.java` — `firstCourseCard`
- **V1:** `By.xpath("(//a[contains(@href, '/courses/')])[1]")` — Could match navigation links, not just course cards.
- **V2:** Waits for courses page to load first via `waitForCoursesPageToLoad()`, then clicks using the same XPath but only after a stability check.

---

## Test Logic Bugs Fixed

### TC#5 — Incorrect assertion
- **V1:** `Assert.assertTrue(driver.getCurrentUrl().contains("/login"), ...)` — This checks the URL, not the error message. A failed login that stays on the page would pass even without showing an error.
- **V2:** `loginPage.isInvalidCredentialsErrorDisplayed()` — checks for the actual error element, with URL as a secondary fallback.

### TC#8 / TC#9 / TC#10 — Social links clicked without scrolling
- **V1:** Clicked footer social icons without scrolling to them. On a 1920×1080 viewport this causes `ElementClickInterceptedException` because the footer is below the fold.
- **V2:** `scrollToBottom()` called before each social icon click. `jsClick()` used as the click mechanism to bypass overlay interception.

### TC#8 / TC#9 / TC#10 — Tab switching without wait
- **V1:** `new ArrayList<>(driver.getWindowHandles())` called immediately, before the new tab has time to open.
- **V2:** Shared `switchToNewTab()` helper that waits for `windowHandles.size() > 1` before switching, then waits for the URL to be non-blank.

---

## Reliability Improvements

| Area | V1 | V2 |
|------|----|----|
| Wait timeout | 15 seconds | 20 seconds (BasePage) |
| Chrome options | `--headless` (old flag) | `--headless=new` (Chromium 112+) |
| Click strategy | `wait.click()` only | `scrollIntoView()` + click; `jsClick()` fallback |
| Social icon scroll | None | `scrollToBottom()` before every footer interaction |
| Tab switch wait | None | Explicit wait for `windowHandles.size() > 1` |
| URL wait | None | `ExpectedConditions.urlContains()` before assertions |
| Empty field handling | `type()` called with null, causing NPE | null-checked before calling `type()` |
| Confirm password | Always filled, could cause issues | Wrapped in try/catch — field may not always exist |
| Allure annotations | None | `@Feature`, `@Severity`, `@Description` on all tests |
