package com.ofos.testcases;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class OrderFromHomePage {

	public static void main(String[] args) {

		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));

		driver.get("http://rmgtestingserver/domain/Online_Food_Ordering_System/");
		Assert.assertEquals(driver.getTitle(), "Home");

		driver.findElement(By.xpath("//a[text()='Login']")).click();
		wait.until(ExpectedConditions.titleContains("Login"));
		Assert.assertEquals(driver.getTitle(), "Login");
		driver.findElement(By.name("username")).sendKeys("Kishore");
		driver.findElement(By.name("password")).sendKeys("kishore");
		driver.findElement(By.id("buttn")).click();

		wait.until(ExpectedConditions.titleContains("Home"));
		Assert.assertEquals(driver.getTitle(), "Home");
		driver.findElement(By.xpath("//a[contains(@href,'id=4') and text()='Order Now']")).click();
		Assert.assertEquals(driver.getTitle(), "Dishes");
		// div[@class='food-item']//a[text()='Chicken Madeira']
		String dish="Chicken Madeira";
		String qty = "//div[@class='food-item']//a[text()='"+dish+"']//ancestor::div[@class='col-xs-12 col-sm-12 col-lg-8']/following-sibling::div//input[@name='quantity']";
		String addToCart = "//div[@class='food-item']//a[text()='"+dish+"']//ancestor::div[@class='col-xs-12 col-sm-12 col-lg-8']/following-sibling::div//*[@value='Add To Cart']";
		driver.findElement(By.xpath(qty)).clear();
		driver.findElement(By.xpath(qty)).sendKeys("2");
		driver.findElement(By.xpath(addToCart)).click();
		driver.findElement(By.xpath("//*[text()='Checkout']")).click();

		wait.until(ExpectedConditions.titleContains("Checkout"));
		Assert.assertEquals(driver.getTitle(), "Checkout");
		driver.findElement(By.name("submit")).click();
		driver.switchTo().alert().accept();
		driver.switchTo().alert().accept();

		wait.until(ExpectedConditions.titleContains("My Orders"));
		Assert.assertEquals(driver.getTitle(), "My Orders");
		String time = driver.findElement(By.xpath("(//td[@data-column='Date'])[last()]")).getText().trim();
		// System.out.println(time);
		// driver.findElement(By.xpath("//a[text()='Logout']")).click();
		// wait.until(ExpectedConditions.titleContains("Login"));
		// Assert.assertEquals(driver.getTitle(), "Login");

		driver.get("http://rmgtestingserver/domain/Online_Food_Ordering_System/admin/");
		Assert.assertEquals(driver.getTitle(), "Admin Login");

		driver.findElement(By.name("username")).sendKeys("admin");
		driver.findElement(By.name("password")).sendKeys("codeastro");
		driver.findElement(By.name("submit")).click();

		wait.until(ExpectedConditions.titleContains("Admin Panel"));
		Assert.assertEquals(driver.getTitle(), "Admin Panel");
	//	driver.findElement(By.xpath("//span[text()='Orders']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Orders']"))).click();
		wait.until(ExpectedConditions.titleContains("All Orders"));
		Assert.assertEquals(driver.getTitle(), "All Orders");
		String orderId = "//td[text()='" + time + "']";
		if (driver.findElement(By.xpath(orderId)).getText() == null) {
			Assert.assertTrue(false, "OrderId does not exist");
		}
		// td[text()='2023-02-02
		// 04:13:30']//following-sibling::td//a[contains(@class,'btn-sm')]
		driver.findElement(By.xpath(orderId + "//following-sibling::td//a[contains(@class,'btn-sm')]")).click();

		wait.until(ExpectedConditions.titleContains("View Order"));
		Assert.assertEquals(driver.getTitle(), "View Order");
		String parentWindow = driver.getWindowHandle();
		driver.findElement(By.xpath("//button[text()='Update Order Status']")).click();

		Set<String> windows = driver.getWindowHandles();

		for (String window : windows) {
			driver.switchTo().window(window);
			// wait.until(ExpectedConditions.titleContains(orderId));
			if (driver.getTitle().contains("Order Update")) {
				Assert.assertEquals(driver.getTitle(), "Order Update");
				WebElement status = driver.findElement(By.name("status"));
				Select s4 = new Select(status);
				s4.selectByVisibleText("On the way");
				driver.findElement(By.name("remark")).sendKeys("on the way");
				driver.findElement(By.name("update")).click();
				driver.switchTo().alert().accept();
				driver.findElement(By.name("Submit2")).click();
				break;
			}
		}

		driver.switchTo().window(parentWindow);
		wait.until(ExpectedConditions.titleContains("View Order"));
		Assert.assertEquals(driver.getTitle(), "View Order");

		driver.findElement(By.xpath("//span[text()='Orders']")).click();
		wait.until(ExpectedConditions.titleContains("All Orders"));
		Assert.assertEquals(driver.getTitle(), "All Orders");
		String currentstatus = driver.findElement(By.xpath("//tr[last()]//preceding-sibling::td//button")).getText();
		// System.out.println(currentstatus);
		if (!currentstatus.contains("On The Way!")) {
			Assert.assertTrue(false, "status not updated");
		}
		driver.findElement(By.xpath("//img[@alt='user']")).click();
		driver.findElement(By.xpath("//a[text()=' Logout']")).click();
		Assert.assertEquals(driver.getTitle(), "Admin Login");

	}

}
