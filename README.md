

### 🆕 New Files in V2

| File | Why Added |
|------|-----------|
| `CourseDetailsPage.java` | V1 had no page class for the course details page; TC#2 was asserting sections from the wrong page object |
| `utils/ConfigReader.java` | V1 hardcoded all test data directly in test methods; config is now centralized |
| `src/test/resources/config.properties` | Holds base URL, credentials, and registration data |
| `src/test/resources/testng.xml` | V1 had no suite file; tests ran by auto-discovery with no ordering control |
| `FIXES_AND_IMPROVEMENTS.md` | Full audit log of every V1 bug and how V2 fixed it |

---

### 🐛 Bugs Fixed

| Bug | V1 | V2 |
|-----|----|----|
| **TC#6 missing** | Not implemented | Added `testLoginEmptyFields()` |
| **TC#7 missing** | Not implemented | Added `testEndToEndSubscribeCourse()` |
| **Wrong register link text** | `"حساب جديد"` | `contains 'انضم لنا'` (matches spec) |
| **Wrong courses link text** | `"عرض الدورات التدريبية"` | Multi-fallback XPath |
| **Username error Arabic word order** | `"مطلوب المستخدم اسم"` (reversed) | `"اسم المستخدم مطلوب"` (correct) |
| **`nameInput` wrong field ID** | `id="name"` | `name="username"` + placeholder fallback |
| **TC#5 assertion wrong** | Checked URL instead of error message | Checks actual error element |
| **Social icons not scrolled to** | No scroll before footer clicks | `scrollToBottom()` + `jsClick()` |
| **Tab switch without wait** | Immediate `getWindowHandles()` — race condition | Waits for `handles.size() > 1` |
| **`verifyCardUI()` XPath escapes card context** | `//button[...]` matches page-wide | `.//button[...]` anchored to card element |
| **Login errorMsg is placeholder** | `contains(@class, 'error')` comment says "Placeholder" | Matches on class AND Arabic error text |

---

### ▶️ How to Run

```bash
cd automation-testing-project
# Edit src/test/resources/config.properties with real credentials for TC#7
mvn clean test

# Allure report
mvn allure:serve
```
