package com.palindromevalidation;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class PalindromeCounter {

	public PalindromeCounter() {
		// TODO Auto-generated constructor stub
	}
	

	
	public boolean isPalindrome(String original) {
		String  reverse = "";
	     
	      int length = original.length();
	     
	      for (int i = length - 1; i >= 0; i--)
	         reverse = reverse + original.charAt(i);
	      
	      if(original.equalsIgnoreCase(reverse))
	    	  return true;
	      else
	    	  return false;
		
	}

}
