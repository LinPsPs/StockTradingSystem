package dao;

import model.Login;

import java.sql.*;

public class LoginDao {
	/*
	 * This class handles all the database operations related to login functionality
	 */
	
	
	public Login login(String username, String password, String role) {
		/*
		 * Return a Login object with role as "manager", "customerRepresentative" or "customer" if successful login
		 * Else, return null
		 * The role depends on the type of the user, which has to be handled in the database
		 * username, which is the email address of the user, is given as method parameter
		 * password, which is the password of the user, is given as method parameter
		 * Query to verify the username and password and fetch the role of the user, must be implemented
		 */
		
		/*Sample data begins*/
        Connection connection = null;
        Statement statement = null;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://107.155.113.86:3306/STOCKSYSTEM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "cse305","CSE305XYZ");
            connection.setAutoCommit(true); // only one transaction
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            statement = connection.createStatement();
            ResultSet resultSet =statement.executeQuery
                    ("SELECT Role FROM Login L WHERE L.Email= '"+username+"' AND L.Password= '"+password+"'");
            String tempRole = null;
            while (resultSet.next()){
                tempRole = resultSet.getString("Role");
            }
            //System.out.println(role);
            //System.out.println(tempRole);
            // clean up environment
            resultSet.close();
            statement.close();
            connection.close();

            if (tempRole.equals(role)){
                Login login = new Login();
                login.setRole(role);
                login.setUsername(username);
                return login;
            }
            return null;

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
            try{
		        if (connection!=null)
		            connection.close();
            }catch (SQLException se3){
                System.out.println(se3.getMessage());
            }
        }

		/*Sample data ends*/
		return null;
	}
	
	public String addUser(Login login) {
		/*
		 * Query to insert a new record for user login must be implemented
		 * login, which is the "Login" Class object containing username and password for the new user, is given as method parameter
		 * The username and password from login can get accessed using getter methods in the "Login" model
		 * e.g. getUsername() method will return the username encapsulated in login object
		 * Return "success" on successful insertion of a new user
		 * Return "failure" for an unsuccessful database operation
		 */
		
		/*Sample data begins*/
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://107.155.113.86:3306/STOCKSYSTEM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"cse305", "CSE305XYZ");
			connection.setAutoCommit(false); // only one transaction
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			preparedStatement = connection.prepareStatement("INSERT INTO Login(EMAIL, PASSWORD, ROLE) VALUE (?,?,?)");
			preparedStatement.setString(1,login.getUsername());
			preparedStatement.setString(2,login.getPassword());
			preparedStatement.setString(3,login.getRole());
			preparedStatement.executeUpdate();
			//clean
			connection.commit();
			preparedStatement.close();
			connection.close();
			return "success";
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
				if (preparedStatement!=null)
					preparedStatement.close();
			}catch (SQLException se2){
				System.out.println(se2.getMessage());
			}
			try{
				if (connection!=null)
					connection.close();
			}catch (SQLException se3){
				System.out.println(se3.getMessage());
			}
		}
		return "fail";
		/*Sample data ends*/
	}

}
