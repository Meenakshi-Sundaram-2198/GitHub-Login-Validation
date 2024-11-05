package Project;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.time.Duration;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;

public class GitHubLoginValidation {

	public static void main(String[] args) {
		// Set path to your WebDriver (e.g., chromedriver)
		System.setProperty("webdriver.chrome.driver", "D:\\Selenium Workspace\\Chrome Driver\\chromedriver.exe");

		WebDriver driver = new ChromeDriver();
		try {
			// Open GitHub login page
			driver.get("https://github.com/login");

			// Define WebDriverWait
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			// Locate username, password fields, and sign in button
			WebElement usernameField = driver.findElement(By.id("login_field"));
			WebElement passwordField = driver.findElement(By.id("password"));
			WebElement loginButton = driver.findElement(By.name("commit"));

			// Enter credentials (use valid credentials for testing)
			String username = "Meenakshi-Sundaram-2198";
			String password = "Richard@582";

			usernameField.sendKeys(username);
			passwordField.sendKeys(password);

			// Take a screenshot before login attempt
			takeScreenshot(driver, "before_login.png");

			// Click the login button
			loginButton.click();

			// Check for successful login by verifying the presence of a specific element
			boolean loginSuccessful;
			try {
				// Wait for profile picture or expected element on successful login
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@class='avatar circle']")));
				loginSuccessful = true;
			} catch (Exception e) {
				loginSuccessful = false;
			}

			if (loginSuccessful) {
				System.out.println("Login successful.");
				takeScreenshot(driver, "after_successful_login.png");
			} else {
				System.out.println("Login failed. Checking for error message...");

				// Check for error message if login failed
				WebElement errorMessage = wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.cssSelector("#js-flash-container .flash-error")));
				System.out.println("Error message displayed: " + errorMessage.getText());

				// Take a screenshot for failed login
				takeScreenshot(driver, "after_failed_login.png");
			}
		} catch (Exception e) {
			System.out.println("An error occurred during login validation: " + e.getMessage());
		} finally {
			// Close the browser
			driver.quit();
		}
	}

	// Utility method to take a screenshot and save to a specified directory
	public static void takeScreenshot(WebDriver driver, String fileName) {
		// Define the directory where you want to save the screenshots
		String directory = "D:\\Selenium Workspace\\Selenium Screenshot\\"; // e.g., "C:/Screenshots/" or "./screenshots/"

		// Create directory if it doesn't exist
		File dir = new File(directory);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// Take screenshot and save it to the specified directory
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenshot, new File(directory + fileName));
			System.out.println("Screenshot saved: " + directory + fileName);
		} catch (Exception e) {
			System.out.println("Unable to save screenshot: " + e.getMessage());
		}
	}
}
