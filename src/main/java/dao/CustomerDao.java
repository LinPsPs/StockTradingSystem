package dao;

import model.Customer;
import model.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
	/*
	 * This class handles all the database operations related to the customer table
	 */

    public Customer getDummyCustomer() {
        Location location = new Location();
        location.setZipCode(11790);
        location.setCity("Stony Brook");
        location.setState("NY");

        Customer customer = new Customer();
        customer.setId("111-11-1111");
        customer.setAddress("123 Success Street");
        customer.setLastName("Lu");
        customer.setFirstName("Shiyong");
        customer.setEmail("shiyong@cs.sunysb.edu");
        customer.setLocation(location);
        customer.setTelephone("5166328959");
        customer.setCreditCard("1234567812345678");
        customer.setRating(1);

        return customer;
    }
    public List<Customer> getDummyCustomerList() {
        /*Sample data begins*/
        List<Customer> customers = new ArrayList<Customer>();

        for (int i = 0; i < 10; i++) {
            customers.add(getDummyCustomer());
        }
		/*Sample data ends*/

        return customers;
    }

    /**
	 * @param String searchKeyword
	 * @return ArrayList<Customer> object
	 */
	public List<Customer> getCustomers(String searchKeyword) {
		/*
		 * This method fetches one or more customers based on the searchKeyword and returns it as an ArrayList
		 */
		

		/*
		 * The students code to fetch data from the database based on searchKeyword will be written here
		 * Each record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */
		
		return getDummyCustomerList();
	}


	public Customer getHighestRevenueCustomer() {
		/*
		 * This method fetches the customer who generated the highest total revenue and returns it
		 * The students code to fetch data from the database will be written here
		 * The customer record is required to be encapsulated as a "Customer" class object
		 */

		return getDummyCustomer();
	}

	public Customer getCustomer(String customerID) {

		/*
		 * This method fetches the customer details and returns it
		 * customerID, which is the Customer's ID who's details have to be fetched, is given as method parameter
		 * The students code to fetch data from the database will be written here
		 * The customer record is required to be encapsulated as a "Customer" class object
		 */
		
		return getDummyCustomer();
	}
	
	public String deleteCustomer(String customerID) {

		/*
		 * This method deletes a customer returns "success" string on success, else returns "failure"
		 * The students code to delete the data from the database will be written here
		 * customerID, which is the Customer's ID who's details have to be deleted, is given as method parameter
		 */

		/*Sample data begins*/
		return "success";
		/*Sample data ends*/
		
	}


	public String getCustomerID(String email) {
		/*
		 * This method returns the Customer's ID based on the provided email address
		 * The students code to fetch data from the database will be written here
		 * username, which is the email address of the customer, who's ID has to be returned, is given as method parameter
		 * The Customer's ID is required to be returned as a String
		 */

		return "111-11-1111";
	}


	public String addCustomer(Customer customer) {

		/*
		 * All the values of the add customer form are encapsulated in the customer object.
		 * These can be accessed by getter methods (see Customer class in model package).
		 * e.g. firstName can be accessed by customer.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database insertion of the customer details and return "success" or "failure" based on result of the database insertion.
		 */

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
			Location location = customer.getLocation();
			//check customer exist or not
			int tempSSN = -1;
			ResultSet resultSet = statement.executeQuery(
					"SELECT C.ID FROM Client C WHERE C.ID = "+Integer.parseInt(customer.getSsn()));
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
			//先插入一个person
			preparedStatement = connection.prepareStatement(
					"INSERT INTO Person(SSN, LastName, FirstName, Address, ZipCode, Telephone, Email) VALUE " +
							"(?,?,?,?,?,?,?)");
			preparedStatement.setInt(1,Integer.parseInt(customer.getSsn()));
			preparedStatement.setString(2,customer.getLastName());
			preparedStatement.setString(3,customer.getFirstName());
			preparedStatement.setString(4,customer.getAddress());
			preparedStatement.setInt(5,location.getZipCode());
			preparedStatement.setLong(6,Long.parseLong(customer.getTelephone()));
			preparedStatement.setString(7,customer.getEmail());
			preparedStatement.executeUpdate();

			//insert customer
			preparedStatement = connection.prepareStatement(
					"INSERT INTO Client(Email, Rating, CreditCardNumber, Id) VALUE (?,?,?,?)");
			preparedStatement.setString(1,customer.getEmail());
			preparedStatement.setInt(2,customer.getRating());
			preparedStatement.setString(3,customer.getCreditCard());
			preparedStatement.setString(4,customer.getId());
			preparedStatement.executeUpdate();

			//commit
			connection.commit();
			resultSet.close();
			statement.close();
			preparedStatement.close();
			connection.close();
			return "success";
		}catch(SQLException se){
			se.printStackTrace();
			return "fail";
		}catch(Exception e) {
			//Handle errors for Class.forName
			e.printStackTrace();
			return "fail";
		}finally{
			try{
				if(statement!=null)
					connection.close();
			}catch(SQLException se){
				//我tm怎么知道要干嘛
			}
			try{
				if(connection!=null)
					connection.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
			return "success";
		}

		/*Sample data begins*/
//		return "success";
		/*Sample data ends*/

	}

	public String editCustomer(Customer customer) {
		/*
		 * All the values of the edit customer form are encapsulated in the customer object.
		 * These can be accessed by getter methods (see Customer class in model package).
		 * e.g. firstName can be accessed by customer.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database update and return "success" or "failure" based on result of the database update.
		 */
		
		/*Sample data begins*/
		return "success";
		/*Sample data ends*/

	}

    public List<Customer> getCustomerMailingList() {

		/*
		 * This method fetches the all customer mailing details and returns it
		 * The students code to fetch data from the database will be written here
		 */

        return getDummyCustomerList();
    }

    public List<Customer> getAllCustomers() {
        /*
		 * This method fetches returns all customers
		 */
        return getDummyCustomerList();
    }
}
