package com.ofos.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class AdminFlow {

	public static void main(String[] args) {

		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));

		driver.get("http://rmgtestingserver/domain/Online_Food_Ordering_System/admin/");
		Assert.assertEquals(driver.getTitle(), "Admin Login");

		driver.findElement(By.name("username")).sendKeys("admin");
		driver.findElement(By.name("password")).sendKeys("codeastro");
		driver.findElement(By.name("submit")).click();

		wait.until(ExpectedConditions.titleContains("Admin Panel"));
		Assert.assertEquals(driver.getTitle(), "Admin Panel");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Restaurant']"))).click();
		// driver.findElement(By.xpath("//span[text()='Restaurant']")).click();
		driver.findElement(By.linkText("Add Category")).click();
		Assert.assertEquals(driver.getTitle(), "Add Category");

		driver.findElement(By.name("c_name")).sendKeys("South Indian");
		driver.findElement(By.name("submit")).click();
		String message = driver.findElement(By.xpath("//div[contains(@class,'alert-success')]")).getText();
		// System.out.println(message);
		if (!message.contains("New Category Added Successfully.")) {
			Assert.assertTrue(false, "Failed to add new category");
		}

		driver.findElement(By.linkText("Add Restaurant")).click();
		Assert.assertEquals(driver.getTitle(), "Add Restaurant");

		driver.findElement(By.name("res_name")).sendKeys("FishLand*");
		driver.findElement(By.name("email")).sendKeys("fishland@gmail.com");
		driver.findElement(By.name("phone")).sendKeys("9945102345");
		driver.findElement(By.name("url")).sendKeys("www.fishland.com");
		WebElement openHours = driver.findElement(By.name("o_hr"));
		WebElement closeHours = driver.findElement(By.name("c_hr"));
		WebElement openDays = driver.findElement(By.name("o_days"));
		WebElement category = driver.findElement(By.name("c_name"));
		Select s = new Select(openHours);
		s.selectByVisibleText("12pm");
		Select s1 = new Select(closeHours);
		s1.selectByVisibleText("11pm");
		Select s2 = new Select(openDays);
		s2.selectByValue("Mon-Sat");
		driver.findElement(By.id("lastName")).sendKeys("F:\\Project team 5\\Customer interface\\Home page1.jpg");
		Select s3 = new Select(category);
		s3.selectByVisibleText("South Indian");
		driver.findElement(By.name("address")).sendKeys("fishland, mount joy road, bangalore");
		driver.findElement(By.name("submit")).click();
		String message1 = driver.findElement(By.xpath("//div[contains(@class,'alert-success')]")).getText();
		if (!message1.contains("New Restaurant Added Successfully.")) {
			Assert.assertTrue(false, "Failed to add new Restaurant");
		}

		driver.findElement(By.xpath("//span[text()='Menu']")).click();
		driver.findElement(By.linkText("Add Menu")).click();
		Assert.assertEquals(driver.getTitle(), "Add Menu");
		driver.findElement(By.name("d_name")).sendKeys("fish curry");
		driver.findElement(By.name("about")).sendKeys("authentic mangalore fish curry");
		driver.findElement(By.name("price")).sendKeys("180");
		driver.findElement(By.id("lastName")).sendKeys("F:\\Project team 5\\Customer interface\\Home page1.jpg");
		WebElement restaurant = driver.findElement(By.name("res_name"));
		Select s4 = new Select(restaurant);
		s4.selectByVisibleText("FishLand*");
		driver.findElement(By.name("submit")).click();
		String message2 = driver.findElement(By.xpath("//div[contains(@class,'alert-success')]")).getText();
		// System.out.println(message);
		if (!message2.contains("New Dish Added Successfully.")) {
			Assert.assertTrue(false, "Failed to add new Dish");
		}

		driver.findElement(By.xpath("//img[@alt='user']")).click();
		driver.findElement(By.xpath("//a[text()=' Logout']")).click();
		Assert.assertEquals(driver.getTitle(), "Admin Login");
		//modified by vishwa
	}

}
