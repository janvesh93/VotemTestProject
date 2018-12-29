package com.palindromevalidation;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.util.DriverDetailsClass;

public class HomePageElements {
	WebDriver webDriver;
	DriverDetailsClass driverDetailsClass=new DriverDetailsClass();
	ArrayList<String> combinedList=new ArrayList<String>();
	int countOfPalindromes=0;
	
	@BeforeClass
	public void setUp() {
		System.out.println("Before Method");
		String location=driverDetailsClass.getDriverLocation();
		System.setProperty("webdriver.chrome.driver", location);
		webDriver = new ChromeDriver();
		webDriver.manage().deleteAllCookies();
		webDriver.manage().window().maximize();
		webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		webDriver.get("http://qa-home-palindrome.s3-website.us-east-2.amazonaws.com/login.html");
		webDriver.findElement(By.xpath("//*[@id=\"inputUsername\"]")).sendKeys("test");
		webDriver.findElement(By.xpath("//*[@id=\"inputPassword\"]")).sendKeys("password");
		WebElement signInButton= webDriver.findElement(By.xpath("//*[@id=\"form-signin\"]/button"));
		JavascriptExecutor js=(JavascriptExecutor)webDriver;
		js.executeScript("arguments[0].click();", signInButton);
		
	}
	
	@Test(dependsOnGroups="loginTests")
	public void getAllElementsOnHomePage() throws Exception {
		
		//radioButtons
		List<WebElement>radioList= webDriver.findElements(By.xpath("/html/body/div/div[1]"));
			
		//System.out.println(str);
		String []radioArray=radioList.get(0).getText().split("\\r?\\n");
		combinedList.addAll(Arrays.asList(radioArray));
		
		System.out.println("Radio " +combinedList.size());
		//textBoxes
		List<WebElement>textList= webDriver.findElements(By.xpath("/html/body/div/div[2]"));
		
		System.out.println(textList.get(0).getText());
		String []textArray=textList.get(0).getText().split("\\r?\\n");
		combinedList.addAll(Arrays.asList(textArray));
		System.out.println("Text " +combinedList.size());
		
		//checkboxes
		List<WebElement>checkList= webDriver.findElements(By.xpath("/html/body/div/div[3]"));
		System.out.println(checkList.get(0).getText());
		String []checkArray=checkList.get(0).getText().split("\\r?\\n");
		combinedList.addAll(Arrays.asList(checkArray));
		System.out.println("Check "+combinedList.size());
		
		//DropDown
		WebElement mySelectElement = webDriver.findElement(By.id("select"));
		Select dropdown= new Select(mySelectElement);
		
		List<WebElement>dl=dropdown.getOptions();
		for(WebElement we:dl) {
			combinedList.add(we.getText());
		}
		
		System.out.println("DropDown " +combinedList.size());
		
		
	    //Table
	    WebElement table_element = webDriver.findElement(By.id("table-body"));
        List<WebElement> tr_collection=table_element.findElements(By.xpath("//*[@id=\"table-body\"]/tr"));

        System.out.println("NUMBER OF ROWS IN THIS TABLE = "+tr_collection.size());
        int row_num,col_num;
        row_num=1;
        for(WebElement trElement : tr_collection)
        {
            List<WebElement> td_collection=trElement.findElements(By.xpath("td"));
            System.out.println("NUMBER OF COLUMNS="+td_collection.size());
            col_num=1;
            for(WebElement tdElement : td_collection)
            {
                combinedList.add(tdElement.getText());
                col_num++;
            }
            row_num++;
        } 
		
		WebElement morePlaindromes= webDriver.findElement(By.xpath("/html/body/div/button"));
		JavascriptExecutor js=(JavascriptExecutor)webDriver;
		js.executeScript("arguments[0].click();", morePlaindromes);
		
		Thread.sleep(2000);
		 System.out.println(webDriver.switchTo().alert().getText());
		 
			String [] morePalindomesArray=webDriver.switchTo().alert().getText().split(" ");
			combinedList.addAll(Arrays.asList(morePalindomesArray));
			
			
			
			System.out.println(combinedList.size());
		
	}
	
	
	@Test(dependsOnMethods="getAllElementsOnHomePage")
	public void countNumberofPlaindromes() {
		
		for(String pal:combinedList) {
			if(new PalindromeCounter().isPalindrome(pal))
				countOfPalindromes++;
		}
		
	}
	
	@Test(dependsOnMethods="countNumberofPlaindromes")
	public void printingPalindromes()  throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("PalindromeCount.txt", "UTF-8");
		writer.println("The Total Number of Plaindromes Present in the file are :"+countOfPalindromes);	
		writer.close();
	}
	
	@AfterClass
	public void tearDowm()
	{
		webDriver.quit();
	}
}
