package com.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DriverDetailsClass {

	public String getDriverLocation() {
		Properties prop = new Properties();
		InputStream input = null;
		String location=null;
		try {

			input = new FileInputStream("driver-details.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			location=prop.getProperty("webdriver.chrome.driver");
		
			
	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		}
		
		return location;
	}
	
	public static void main(String[] args) {
		System.out.println(new DriverDetailsClass().getDriverLocation());
	}
}
