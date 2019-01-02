"# VotemTestProject" 

The Project Contains login and plaindrome validation for the Votem website.

LoginTest:

For the login module the data is read through the excel file and fed to the test method using the @DataProvider annotation provided by the testng. It validates for different cases based on the different credentails provided to it by the @DataProvider which in turn fetches it from the  Excel file.


GetAllHomePageElements:

This executes after the login is successful and fetches all the elements displayed on the homepage and adds them to an ArrayList.


CountNumberOfPalindromes:

This method depends on the getAllElementsFromHomePage.It is not executed(skipped) if the getAllElementsFromHomePage fails.  It checks whether the string in the ArrayList is a palindrome or not and increments a counter based on it. 


PrintingPalindrome:

It depends on the method countNumberOfPalindromes. It is skipped if the CountNumberOfPalindromes fails. It uses the PrintWriter to output the number of palindromes into a .txt file.


