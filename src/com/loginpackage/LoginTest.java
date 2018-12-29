package com.loginpackage;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.support.ui.Select;

import com.palindromevalidation.HomePageElements;
import com.util.DriverDetailsClass;
import com.util.LoginUtil;


@Test(groups="loginTests")
public class LoginTest {

	WebDriver webDriver;
	DriverDetailsClass driverDetailsClass=new DriverDetailsClass();
	
	
	@BeforeMethod
	public void setUp() {
		//System.out.println("Before Method");
		String location=driverDetailsClass.getDriverLocation();
		System.out.println(location);
		System.setProperty("webdriver.chrome.driver", location);
		webDriver = new ChromeDriver();
		webDriver.manage().deleteAllCookies();
		webDriver.manage().window().maximize();
		webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		webDriver.get("http://qa-home-palindrome.s3-website.us-east-2.amazonaws.com/login.html");
		
	}
	

	
	@DataProvider
	public Object[][] getLoginData() {
		Object[][] loginData = LoginUtil.getLoginData("login");
		return loginData;
	}
	
	@Test(dataProvider="getLoginData")
	public void loginTest(String username,String password,String expected) throws Exception {
	
		webDriver.findElement(By.xpath("//*[@id=\"inputUsername\"]")).sendKeys(username);
		webDriver.findElement(By.xpath("//*[@id=\"inputPassword\"]")).sendKeys(password);
	
		WebElement signInButton= webDriver.findElement(By.xpath("//*[@id=\"form-signin\"]/button"));
		JavascriptExecutor js=(JavascriptExecutor)webDriver;
		js.executeScript("arguments[0].click();", signInButton);
		
		
		if(expected.equalsIgnoreCase("Pass")) {
			String morePlaindromes= webDriver.findElement(By.xpath("/html/body/div/button")).getText();
			
			Assert.assertEquals(morePlaindromes, "More Palindromes!");
			Thread.sleep(3000);
			
		}
		else {
			
			String alertMessage= webDriver.findElement(By.xpath("//*[@id=\"alert\"]")).getText();
			if(username.length()!=0 && password.length()!=0) {
				Assert.assertEquals(alertMessage, "Username or Password is incorrect.");
			}
			Thread.sleep(3000);
			
			
		}
		
	}

	
	@AfterMethod
	public void tearDown() {
		webDriver.quit();
	}
}
