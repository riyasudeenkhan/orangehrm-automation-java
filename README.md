# OrangeHRM Automation Framework -JAVA Stack

This project contains automation scripts developed using:

- **Java**
- **Selenium WebDriver**
- **TestNG**
- **Page Object Model (POM)**
- **Maven**
- **Allure Reporting**
- **VSCode IDE**

## 🔧 Setup

1. Clone this repository:
   ```bash
   git clone https://github.com/your-username/orangehrm-automation.git
   ```

2. Import as a Maven project in your IDE.

3. Install dependencies:
   ```bash
   mvn clean install
   ```

## 🚀 Execution

To run all tests:
```bash
mvn test
```

## 📊 Reporting

Allure reports are generated in `target/allure-results`.

To view the report:
```bash
allure serve target/allure-results
```

## 🧪 Test Structure

- `com.orangehrm.tests` → TestNG test classes  
- `com.orangehrm.pages` → Page Object classes  
- `com.orangehrm.utils` → Reusable utilities (e.g., WaitHelper)

## 🔐 Credentials

Login credentials used for testing:

- **URL:** [https://opensource-demo.orangehrmlive.com/](https://opensource-demo.orangehrmlive.com/)
- **Username:** `Admin`
- **Password:** `admin123`

> This is a public demo site intended for testing purposes only.
