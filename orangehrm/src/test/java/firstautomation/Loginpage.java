package firstautomation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Loginpage {

    public static void main(String[] args) throws InterruptedException {
        runTest("Admin", "admin123", "Valid Login");
        runTest("WrongUser", "admin123", "Invalid Username");
        runTest("Admin", "WrongPass", "Invalid Password");
        runTest("", "", "Empty Username and Password");
        runTest("", "admin123", "Empty Username");
        runTest("Admin", "", "Empty Password");
        runTest(" Admin ", "admin123", "Username with Whitespace");
        runTest("admin", "admin123", "Case Sensitivity - lowercase username");
        runTest("!@#User", "admin123", "Special Characters in Username");
        runTest("Admin", "!@#123", "Special Characters in Password");
    }

    public static void runTest(String usernameInput, String passwordInput, String testName) throws InterruptedException {
        System.out.println("▶️ Running Test: " + testName);

        WebDriver driver = new ChromeDriver();
        try {
            driver.manage().window().maximize();
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
            Thread.sleep(3000); // Ideally use WebDriverWait instead

            WebElement username = driver.findElement(By.name("username"));
            WebElement password = driver.findElement(By.name("password"));
            WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));

            username.sendKeys(usernameInput);
            password.sendKeys(passwordInput);
            loginButton.click();

            Thread.sleep(3000); // Wait for page to load (replace with explicit wait in real use)

            String currentUrl = driver.getCurrentUrl();

            if (usernameInput.equals("Admin") && passwordInput.equals("admin123")) {
                if (currentUrl.contains("dashboard")) {
                    System.out.println("✅ " + testName + " - Passed (Logged in successfully)");
                } else {
                    System.out.println("❌ " + testName + " - Failed (Dashboard not reached)");
                }
            } else {
                boolean errorMessage = driver.getPageSource().contains("Invalid credentials");
                if (errorMessage || currentUrl.contains("auth/login")) {
                    System.out.println("✅ " + testName + " - Passed (Proper validation message shown)");
                } else {
                    System.out.println("❌ " + testName + " - Failed (Unexpected behavior)");
                }
            }
        } finally {
            driver.quit();
        }
    }
    //my first test case
}
