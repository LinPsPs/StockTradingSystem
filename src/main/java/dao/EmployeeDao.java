package dao;

import model.Employee;
import model.Location;
import model.Login;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EmployeeDao {
	/*
	 * This class handles all the database operations related to the employee table
	 */

    public Employee getDummyEmployee()
    {
        Employee employee = new Employee();

        Location location = new Location();
        location.setCity("Stony Brook");
        location.setState("NY");
        location.setZipCode(11790);

		/*Sample data begins*/
        employee.setEmail("shiyong@cs.sunysb.edu");
        employee.setFirstName("Shiyong");
        employee.setLastName("Lu");
        employee.setLocation(location);
        employee.setAddress("123 Success Street");
        employee.setStartDate("2006-10-17");
        employee.setTelephone("5166328959");
        employee.setEmployeeID("631-413-5555");
        employee.setHourlyRate(100);
		/*Sample data ends*/

        return employee;
    }

    public List<Employee> getDummyEmployees()
    {
       List<Employee> employees = new ArrayList<Employee>();

        for(int i = 0; i < 10; i++)
        {
            employees.add(getDummyEmployee());
        }

        return employees;
    }

	public String addEmployee(Employee employee) {

		/*
		 * All the values of the add employee form are encapsulated in the employee object.
		 * These can be accessed by getter methods (see Employee class in model package).
		 * e.g. firstName can be accessed by employee.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database insertion of the employee details and return "success" or "failure" based on result of the database insertion.
		 */
		/*Sample data begins*/
		Connection connection = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://107.155.113.86:3306/STOCKSYSTEM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"cse305", "CSE305XYZ");
			connection.setAutoCommit(false); // only one transaction
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			statement = connection.createStatement();
			Location location = employee.getLocation();
			//check employee exist or not
			int tempSSN = -1;
			ResultSet resultSet = statement.executeQuery(
					"SELECT E.SSN FROM Employee E WHERE E.SSN = "+Integer.parseInt(employee.getSsn()));
			while (resultSet.next()){
				tempSSN = resultSet.getInt("SSN");
			}
			//fail to insert
			if (tempSSN!=-1){
				connection.rollback();
				resultSet.close();
				statement.close();
				preparedStatement.close();
				connection.close();
				return "failure";
			}
			//check location exist or not
			int  tempZip = -1;
			resultSet = statement.executeQuery("SELECT ZipCode " + "FROM Location L" +
					" WHERE L.ZipCode = "+location.getZipCode()+" ");
			while (resultSet.next()){
				tempZip = resultSet.getInt("ZipCode");
			}
			// if does not exist the add location
			if (tempZip==-1){
				preparedStatement = connection.prepareStatement("INSERT INTO Location(ZipCode, City, State) VALUE (?,?,?)");
				preparedStatement.setInt(1,location.getZipCode());
				preparedStatement.setString(2,location.getCity());
				preparedStatement.setString(3,location.getState());
				preparedStatement.executeUpdate();
			}
			//insert person
			preparedStatement = connection.prepareStatement(
					"INSERT INTO Person(SSN, LastName, FirstName, Address, ZipCode, Telephone, Email) VALUE " +
							"(?,?,?,?,?,?,?)");
			preparedStatement.setInt(1,Integer.parseInt(employee.getSsn()));
			preparedStatement.setString(2,employee.getLastName());
			preparedStatement.setString(3,employee.getFirstName());
			preparedStatement.setString(4,employee.getAddress());
			preparedStatement.setInt(5,location.getZipCode());
			preparedStatement.setLong(6,Long.parseLong(employee.getTelephone()));
			preparedStatement.setString(7,employee.getEmail());
			preparedStatement.executeUpdate();

			//insert employee
			preparedStatement = connection.prepareStatement(
					"INSERT INTO Employee(Role, SSN, StartDate) VALUE (?,?,?)");
			preparedStatement.setString(1,employee.getLevel());
			preparedStatement.setInt(2,Integer.parseInt(employee.getSsn()));
			preparedStatement.setString(3,employee.getStartDate());
			preparedStatement.executeUpdate();

			//commit
			connection.commit();
			resultSet.close();
			statement.close();
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
				if (statement!=null)
					statement.close();
			}catch (SQLException se2){
				System.out.println(se2.getMessage());
			}
			try{
				if (preparedStatement!=null)
					preparedStatement.close();
			}catch (SQLException s2){
				System.out.println(s2.getMessage());
			}
			try{
				if (connection!=null)
					connection.close();
			}catch (SQLException se3){
				System.out.println(se3.getMessage());
			}
		}
		return "failure";
		/*Sample data ends*/

	}

	public String editEmployee(Employee employee) {
		/*
		 * All the values of the edit employee form are encapsulated in the employee object.
		 * These can be accessed by getter methods (see Employee class in model package).
		 * e.g. firstName can be accessed by employee.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database update and return "success" or "failure" based on result of the database update.
		 */
		
		/*Sample data begins*/
		Connection connection = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://107.155.113.86:3306/STOCKSYSTEM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"cse305", "CSE305XYZ");
			connection.setAutoCommit(false); // only one transaction
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			statement = connection.createStatement();
			int tempSSN = -1;
			//check employee table
			ResultSet resultSet = statement.executeQuery(
					"SELECT SSN FROM Employee E WHERE E.ID = "+employee.getEmployeeID());
			while (resultSet.next()){
				tempSSN = resultSet.getInt("SSN");
			}
			if (tempSSN == -1)
				return "failure";
			//update employee table
			preparedStatement = connection.prepareStatement(
					"UPDATE Employee E SET E.Role = ?, E.StartDate = ?, E.HourlyRate= ? " +
							"WHERE E.ID = "+Integer.parseInt(employee.getEmployeeID()));
			preparedStatement.setString(1,employee.getLevel());
			preparedStatement.setString(2,employee.getStartDate());
			preparedStatement.setFloat(3,employee.getHourlyRate());
			preparedStatement.executeUpdate();
			//update location table
			int tempZip = -1;
			resultSet = statement.executeQuery(
					"SELECT ZipCode FROM Location L WHERE L.ZipCode = "+employee.getLocation().getZipCode());
			while (resultSet.next()){
				tempZip = resultSet.getInt("ZipCode");
			}
			//New location then add
			if (tempZip==-1){
				preparedStatement = connection.prepareStatement(
						"INSERT INTO Location(ZipCode, City, State) VALUE (?,?,?)");
				preparedStatement.setInt(1,employee.getLocation().getZipCode());
				preparedStatement.setString(2,employee.getLocation().getCity());
				preparedStatement.setString(3,employee.getLocation().getState());
				preparedStatement.executeUpdate();
			}
			//update person table
			preparedStatement = connection.prepareStatement(
					"UPDATE Person P " +
							"SET P.FirstName =?,P.LastName =?,P.Address =?,P.ZipCode =?,P.Email = ?,P.Telephone =? " +
							"WHERE P.SSN = "+Integer.parseInt(employee.getSsn()));
			preparedStatement.setString(1,employee.getFirstName());
			preparedStatement.setString(2,employee.getLastName());
			preparedStatement.setString(3,employee.getAddress());
			preparedStatement.setInt(4,employee.getLocation().getZipCode());
			preparedStatement.setString(5,employee.getEmail());
			preparedStatement.setLong(6,Long.parseLong(employee.getTelephone()));
			preparedStatement.executeUpdate();
			//update login table
			preparedStatement = connection.prepareStatement("UPDATE Login L SET L.Role =? " +
					"WHERE L.Email = '"+employee.getEmail()+"'");
			preparedStatement.setString(1,employee.getLevel());
			preparedStatement.executeUpdate();

			connection.commit();
			resultSet.close();
			preparedStatement.close();
			statement.close();
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
				if (statement!=null)
					statement.close();
			}catch (SQLException se2){
				System.out.println(se2.getMessage());
			}
			try{
				if (preparedStatement!=null)
					preparedStatement.close();
			}catch (SQLException s2){
				System.out.println(s2.getMessage());
			}
			try{
				if (connection!=null)
					connection.close();
			}catch (SQLException se3){
				System.out.println(se3.getMessage());
			}
		}
		return "failure";
		/*Sample data ends*/

	}

	public String deleteEmployee(String employeeID) {
		/*
		 * employeeID, which is the Employee's ID which has to be deleted, is given as method parameter
		 * The sample code returns "success" by default.
		 * You need to handle the database deletion and return "success" or "failure" based on result of the database deletion.
		 */
		
		/*Sample data begins*/
		Connection connection = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://107.155.113.86:3306/STOCKSYSTEM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"cse305", "CSE305XYZ");
			connection.setAutoCommit(false); // only one transaction
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			statement = connection.createStatement();
			int tempSSN = -1;
			ResultSet resultSet = statement.executeQuery(
					"SELECT SSN FROM Employee E WHERE E.ID = "+Integer.parseInt(employeeID));
			while (resultSet.next()){
				tempSSN = resultSet.getInt("SSN");
			}
			if (tempSSN==-1)
				return "failure";
			//employee exsit
			preparedStatement = connection.prepareStatement("DELETE FROM Person WHERE Person.SSN = ?");
			preparedStatement.setInt(1,tempSSN);
			preparedStatement.executeUpdate();

			//clean
			connection.commit();
			resultSet.close();
			preparedStatement.close();
			statement.close();
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
				if (statement!=null)
					statement.close();
			}catch (SQLException se2){
				System.out.println(se2.getMessage());
			}
			try{
				if (preparedStatement!=null)
					preparedStatement.close();
			}catch (SQLException s2){
				System.out.println(s2.getMessage());
			}
			try{
				if (connection!=null)
					connection.close();
			}catch (SQLException se3){
				System.out.println(se3.getMessage());
			}
		}
		return "failure";
		/*Sample data ends*/

	}

	
	public List<Employee> getEmployees() {

		/*
		 * The students code to fetch data from the database will be written here
		 * Query to return details about all the employees must be implemented
		 * Each record is required to be encapsulated as a "Employee" class object and added to the "employees" List
		 */

		List<Employee> employees = new ArrayList<Employee>();


		//connections
		Connection connection = null;
		Statement statement = null;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://107.155.113.86:3306/STOCKSYSTEM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"cse305","CSE305XYZ");
			connection.setAutoCommit(false); // only one transaction
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT ID FROM Employee");
			while (resultSet.next()){
				//construct employee
				Employee employee = getEmployee(resultSet.getInt("ID")+"");
				if (employee!=null){
					employees.add(employee);
				}
			}
			//clean up
			connection.commit();
			resultSet.close();
			statement.close();
			connection.close();

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
		
		return employees;
	}

	public Employee getEmployee(String employeeID) {

		/*
		 * The students code to fetch data from the database based on "employeeID" will be written here
		 * employeeID, which is the Employee's ID who's details have to be fetched, is given as method parameter
		 * The record is required to be encapsulated as a "Employee" class object
		 */
		Connection connection = null;
		Statement statement = null;
		Employee employee = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://107.155.113.86:3306/STOCKSYSTEM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"cse305", "CSE305XYZ");
			connection.setAutoCommit(false); // only one transaction
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Employee E WHERE E.ID = "+Integer.parseInt(employeeID));
			while (resultSet.next()){
				employee = new Employee();
				employee.setEmployeeID(resultSet.getInt("ID")+"");
				employee.setSsn(resultSet.getInt("SSN")+"");
				employee.setHourlyRate(resultSet.getFloat("HourlyRate"));
				employee.setLevel(resultSet.getString("Role"));
				employee.setStartDate(resultSet.getString("StartDate"));
				//find person
				ResultSet findEmployee = connection.createStatement().executeQuery(
						"SELECT * FROM Person P WHERE P.SSN = "+Integer.parseInt(resultSet.getString("SSN")));
				while (findEmployee.next()){
					employee.setFirstName(findEmployee.getString("FirstName"));
					employee.setLastName(findEmployee.getString("LastName"));
					employee.setAddress(findEmployee.getString("Address"));
					employee.setEmail(findEmployee.getString("Email"));
					employee.setTelephone(findEmployee.getLong("Telephone")+"");
					//find location
					ResultSet findLocation = connection.createStatement().executeQuery("SELECT * FROM Location L WHERE L.ZipCode = "
							+findEmployee.getInt("ZipCode"));
					while (findLocation.next()){
						Location location = new Location();
						location.setCity(findLocation.getString("City"));
						location.setState(findLocation.getString("State"));
						location.setZipCode(findLocation.getInt("ZipCode"));
						employee.setLocation(location);
					}
					findLocation.close();
				}
				findEmployee.close();
			}
			//clean up
			connection.commit();

			resultSet.close();
			statement.close();
			connection.close();
			return employee;

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

		return null;
	}
	
	public Employee getHighestRevenueEmployee() {
		
		/*
		 * The students code to fetch employee data who generated the highest revenue will be written here
		 * The record is required to be encapsulated as a "Employee" class object
		 */
		return getDummyEmployee();
	}

	public String getEmployeeID(String username) {
		/*
		 * The students code to fetch data from the database based on "username" will be written here
		 * username, which is the Employee's email address who's Employee ID has to be fetched, is given as method parameter
		 * The Employee ID is required to be returned as a String
		 */
		Connection connection = null;
		Statement statement = null;
		String employeeID = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://107.155.113.86:3306/STOCKSYSTEM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"cse305", "CSE305XYZ");
			connection.setAutoCommit(false); //
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT P.SSN FROM Person P WHERE P.Email = '"+username+"'");
			while (resultSet.next()){
				//find employee
				ResultSet findEmployee = connection.createStatement().executeQuery(
						"SELECT E.ID FROM Employee E WHERE E.SSN = "+resultSet.getInt("SSN"));
				while (findEmployee.next()) {
					employeeID = findEmployee.getInt("ID") + "";
				}
				findEmployee.close();
			}
			//clean
			connection.commit();
			resultSet.close();
			statement.close();
			connection.close();
			return employeeID;

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
		return null;
	}

}
