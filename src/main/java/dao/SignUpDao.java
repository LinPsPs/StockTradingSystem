package dao;

import model.Login;
import model.SignUp;

import java.sql.*;

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
		if (initPhone.length()!=10){
			return null;
		}
		if (initSSN.length()!=9){
			return null;
		}
		if (!(initRole.equals("manager")||initRole.equals("customerRepresentative"))){
			return null;
		}
		if(initPassowrd.equals("")||initPassowrd==null){
			return null;
		}
		/*Sample data begins*/
		Connection connection = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://107.155.113.86:3306/STOCKSYSTEM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"cse305","CSE305XYZ");
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			statement = connection.createStatement();
			ResultSet resultSet =statement.executeQuery
					("SELECT Email FROM Login L WHERE L.Email= '"+initUsername+"'");
			String tempEmail = null;
			while (resultSet.next()){
				tempEmail = resultSet.getString("Email");
			}
			System.out.println(tempEmail);
			if (tempEmail!=null){
				//fail sign up
				resultSet.close();
				statement.close();
				connection.close();
				return null;
			}
			// if user does not exist
			//check location existence
			//System.out.println(Integer.parseInt(initZip));
			int  tempZip = -1;
			resultSet = statement.executeQuery("SELECT ZipCode " + "FROM Location L" +
					" WHERE L.ZipCode = "+Integer.parseInt(initZip)+" ");
			while (resultSet.next()){
				tempZip = resultSet.getInt("ZipCode");
			}
			//if location does not exist
			//System.out.println(tempZip);
			if (tempZip==-1){
				preparedStatement = connection.prepareStatement("INSERT INTO Location(ZipCode, City, State) VALUE (?,?,?)");
				preparedStatement.setInt(1,Integer.parseInt(initZip));
				preparedStatement.setString(2,initCity);
				preparedStatement.setString(3,initState);
				preparedStatement.executeUpdate();
			}
			//insert person
			preparedStatement = connection.prepareStatement(
					"INSERT INTO Person(SSN, LastName, FirstName, Address, ZipCode, Telephone, Email) VALUE " +
					"(?,?,?,?,?,?,?)");
			preparedStatement.setInt(1,Integer.parseInt(initSSN));
			preparedStatement.setString(2,initLastName);
			preparedStatement.setString(3,initFirstName);
			preparedStatement.setString(4,initAddress);
			preparedStatement.setInt(5,Integer.parseInt(initZip));
			preparedStatement.setLong(6,Long.parseLong(initPhone));
			preparedStatement.setString(7,initUsername);
			preparedStatement.executeUpdate();
			// insert employee or customer
			//start date as register date
			java.util.Date dt = new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			String currentTime = sdf.format(dt);
			if (initRole.equals("manager")||initRole.equals("customerRepresentative")){
				//manager
				preparedStatement = connection.prepareStatement(
						"INSERT INTO Employee(Role, SSN, StartDate) VALUE (?,?,?)");
				preparedStatement.setString(1,initRole);
				preparedStatement.setInt(2,Integer.parseInt(initSSN));
				preparedStatement.setString(3,currentTime);
				preparedStatement.executeUpdate();
				//System.out.println("added E");
				preparedStatement = connection.prepareStatement("INSERT INTO Login(EMAIL, PASSWORD, ROLE) VALUE (?,?,?)");
				preparedStatement.setString(1,initUsername);
				preparedStatement.setString(2,initPassowrd);
				preparedStatement.setString(3,initRole);
				preparedStatement.executeUpdate();
				//System.out.println("added L");
				connection.commit();
			}else {
				resultSet.close();
				statement.close();
				preparedStatement.close();
				connection.close();
				return null;
			}
			resultSet.close();
			statement.close();
			preparedStatement.close();
			connection.close();
			SignUp signUp = new SignUp(initUsername,initPassowrd, initFirstName,
					initLastName,initAddress,initCity,initState,initZip,initPhone,initSSN,initRole);
			return signUp;

		}catch(SQLException ex){
			System.out.println(ex.getMessage());
			try{
				if (connection!=null)
					connection.rollback();
			}catch (Exception e){
				System.out.println(e.getMessage());
			}
		} catch (Exception e){
			System.out.println(e.getMessage());
		}finally {
			try{
				if (statement!=null)
					statement.close();
			}catch (SQLException se2){
				System.out.println(se2.getMessage());
			}
			try {
				if (preparedStatement!=null)
					preparedStatement.close();
			}catch (SQLException se3){
				System.out.println(se3.getMessage());
			}
			try{
				if (connection!=null)
					connection.close();
			}catch (SQLException se4){
				System.out.println(se4.getMessage());
			}
		}

		/*Sample data ends*/
		return null;
	}
	

}
