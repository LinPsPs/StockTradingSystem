package dao;

import model.SignUp;

public class SignUpDao {
	/*
	 * This class handles all the database operations related to login functionality
	 */
	
	
	public SignUp signUp(String initUsername,String initPassowrd,String initFirstName,
						 String initLastName ,String initAddress,
						 String initCity,String initState,String initZip,
						 String initPhone,String initSSN,String initRole) {
		/*
		 * Return a Login object with role as "manager", "customerRepresentative" or "customer" if successful login
		 * Else, return null
		 * The role depends on the type of the user, which has to be handled in the database
		 * username, which is the email address of the user, is given as method parameter
		 * password, which is the password of the user, is given as method parameter
		 * Query to verify the username and password and fetch the role of the user, must be implemented
		 */
		
		/*Sample data begins*/
		SignUp signUp = new SignUp();
		return signUp;
		/*Sample data ends*/
		
	}
	

}
