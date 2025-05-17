package firstautomation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class Loginpage {

    WebDriver driver;

    @BeforeMethod
    public void setup() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        Thread.sleep(3000);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @DataProvider(name = "loginData")
    public Object[][] loginDataProvider() {
        return new Object[][]{
            {"Admin", "admin123", "Valid Login"},
            {"WrongUser", "admin123", "Invalid Username"},
            {"Admin", "WrongPass", "Invalid Password"},
            {"", "", "Empty Username and Password"},
            {"", "admin123", "Empty Username"},
            {"Admin", "", "Empty Password"},
            {" Admin ", "admin123", "Username with Whitespace"},
            {"admin", "admin123", "Case Sensitivity - lowercase username"},
            {"!@#User", "admin123", "Special Characters in Username"},
            {"Admin", "!@#123", "Special Characters in Password"},
        };
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String usernameInput, String passwordInput, String testName) throws InterruptedException {
        System.out.println("▶️ Running Test: " + testName);

        WebElement username = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));

        username.sendKeys(usernameInput);
        password.sendKeys(passwordInput);
        loginButton.click();

        Thread.sleep(3000); // Replace with WebDriverWait in production

        String currentUrl = driver.getCurrentUrl();

        if (usernameInput.trim().equals("Admin") && passwordInput.equals("admin123")) {
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
    }
} 
//TestNG
