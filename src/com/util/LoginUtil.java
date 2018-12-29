package com.util;


import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LoginUtil {
	
	
	public static String filename = null;
	public static FileInputStream fis = null;
	public static FileOutputStream fileOut = null;
	private static XSSFWorkbook workbook = null;
	private static XSSFSheet sheet = null;
	private static XSSFRow row = null;

	
	
	public static Object[][] getLoginData(String sheetName) {
		Object[][] loginData =null;
		
		String workingDir = System.getProperty("user.dir");
		filename=workingDir+"//UserLoginDetails.xlsx";
		
		try {
			// File file =    new File("");
			 fis = new FileInputStream(filename);
			 workbook =  new XSSFWorkbook(fis);
			
			 sheet = workbook.getSheet(sheetName);
			 loginData= new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			 for(int i=0;i<sheet.getLastRowNum();i++) {
				 for(int k=0; k<sheet.getRow(0).getLastCellNum();k++) {
					 loginData[i][k]= sheet.getRow(i+1).getCell(k).getStringCellValue();
					 System.out.println(loginData[i][k]);
				
				 }
			
			 }
		}catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
		}
		return loginData;
	}
	
	public static void main(String[] args) {
		getLoginData("login");
	}

}
