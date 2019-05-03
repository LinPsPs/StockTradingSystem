package dao;

import com.sun.org.apache.bcel.internal.generic.Select;
import model.Stock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDao {

    public Stock getDummyStock() {
        Stock stock = new Stock();
        stock.setName("Apple");
        stock.setSymbol("AAPL");
        stock.setPrice(150.0);
        stock.setNumShares(1200);
        stock.setType("Technology");

        return stock;
    }

    public List<Stock> getDummyStocks() {
        List<Stock> stocks = new ArrayList<Stock>();

		/*Sample data begins*/
        for (int i = 0; i < 10; i++) {
            stocks.add(getDummyStock());
        }
		/*Sample data ends*/

        return stocks;
    }

    public List<Stock> getActivelyTradedStocks() {

		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch details of all the stocks has to be implemented
		 * Return list of actively traded stocks
		 */

		// NOT FINISH YET DO NOT USE IT!!!


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
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
            try{
                if (connection!=null)
                    connection.rollback();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
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
        // Error case
        return null;
    }

	public List<Stock> getAllStocks() {

		/*
		 * The students code to fetch data from the database will be written here
		 * Return list of stocks
		 */
        Connection connection = null;
        Statement statement = null;
        List<Stock> stocks = new ArrayList<Stock>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://107.155.113.86:3306/STOCKSYSTEM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "cse305", "CSE305XYZ");
            connection.setAutoCommit(false); // only one transaction
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM Stock"
            );
            while (rs.next()) {
                double pricePerShare = rs.getDouble("PricePerShare");
                int totalShare = rs.getInt("TotalShare");
                String companyName = rs.getString("CompanyName");
                String stockSymbol = rs.getString("StockSymbol");
                String type = rs.getString("Type");
                Stock stock  = new Stock(companyName, stockSymbol, type, pricePerShare, totalShare);
                stocks.add(stock);
            }
            //clean up
            connection.commit();
            rs.close();
            statement.close();
            connection.close();
            return stocks;
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
            try{
                if (connection!=null)
                    connection.rollback();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException se2) {
                System.out.println(se2.getMessage());
            }
        }
		return null;

	}

    public Stock getStockBySymbol(String stockSymbol)
    {
        /*
		 * The students code to fetch data from the database will be written here
		 * Return stock matching symbol
		 */
        Connection connection = null;
        Statement statement = null;
        Stock stock = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://107.155.113.86:3306/STOCKSYSTEM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "cse305", "CSE305XYZ");
            connection.setAutoCommit(false); // only one transaction
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM Stock WHERE StockSymbol = " + stockSymbol
            );
            while (rs.next()) {
                String companyName = rs.getString("CompanyName");
                double pricePerShare = rs.getDouble("PricePerShare");
                String type = rs.getString("Type");
                int totalShare = rs.getInt("TotalShare");
                stock  = new Stock(companyName, stockSymbol, type, pricePerShare, totalShare);
            }
            connection.commit();
            rs.close();
            statement.close();
            connection.close();
            return stock;
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
            try{
                if (connection!=null)
                    connection.rollback();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException se2) {
                System.out.println(se2.getMessage());
            }
        }
        return null;
    }

    public String setStockPrice(String stockSymbol, double stockPrice) {
        /*
         * The students code to fetch data from the database will be written here
         * Perform price update of the stock symbol
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
            //check stock existence
            String tempStock = null;
            ResultSet resultSet = statement.executeQuery("SELECT S.StockSymbol FROM Stock S WHERE S.StockSymbol = '"+stockSymbol+"'");
            while (resultSet.next()){
                tempStock = resultSet.getString("StockSymbol");
            }
            //is stock does not exist
            if (tempStock==null){
                connection.rollback();
                resultSet.close();
                statement.close();
                connection.close();
                return "failure";
            }
            //exist
            preparedStatement = connection.prepareStatement("UPDATE Stock S" +
                    " SET S.PricePerShare = ? WHERE S.StockSymbol = ?");
            preparedStatement.setDouble(1,stockPrice);
            //System.out.println(stockSymbol);
            preparedStatement.setString(2,stockSymbol);
            preparedStatement.executeUpdate();
            //clean
            connection.commit();
            resultSet.close();
            preparedStatement.close();
            statement.close();
            connection.close();
            return "success";
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
            try{
                if (connection!=null)
                    connection.rollback();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
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
    }

	public List<Stock> getOverallBestsellers() {

		/*
		 * The students code to fetch data from the database will be written here
		 * Get list of bestseller stocks
		 */

		return getDummyStocks();

	}

    public List<Stock> getCustomerBestsellers(String customerID) {

		/*
		 * The students code to fetch data from the database will be written here.
		 * Get list of customer bestseller stocks
		 */

        return getDummyStocks();

    }

	public List getStocksByCustomer(String customerId) {

		/*
		 * The students code to fetch data from the database will be written here
		 * Get stockHoldings of customer with customerId
		 */
		return getDummyStocks();
	}

    public List<Stock> getStocksByName(String name) {

		/*
		 * The students code to fetch data from the database will be written here
		 * Return list of stocks matching "name"
		 */
        Connection connection = null;
        Statement statement = null;
        List<Stock> stockList = new ArrayList<Stock>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://107.155.113.86:3306/STOCKSYSTEM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "cse305", "CSE305XYZ");
            connection.setAutoCommit(false); // only one transaction
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM Stock WHERE CompanyName LIKE '%" + name + "%'"
            );
            while (rs.next()) {
                String stockSymbol = rs.getString("StockSymbol");
                double pricePerShare = rs.getDouble("PricePerShare");
                String companyName = rs.getString("CompanyName");
                String type = rs.getString("Type");
                int totalShare = rs.getInt("TotalShare");
                Stock stock  = new Stock(companyName, stockSymbol, type, pricePerShare, totalShare);
                stockList.add(stock);
            }
            connection.commit();
            rs.close();
            statement.close();
            connection.close();
            return stockList;
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
            try{
                if (connection!=null)
                    connection.rollback();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException se2) {
                System.out.println(se2.getMessage());
            }
        }
        return null;
    }

    public List<Stock> getStockSuggestions(String customerID) {

		/*
		 * The students code to fetch data from the database will be written here
		 * Return stock suggestions for given "customerId"
		 */

        return null;

    }

    public List<Stock> getStockPriceHistory(String stockSymbol) {

		/*
		 * The students code to fetch data from the database
		 * Return list of stock objects, showing price history
		 */

        return getDummyStocks();
    }

    public List<String> getStockTypes() {

		/*
		 * The students code to fetch data from the database will be written here.
		 * Populate types with stock types
		 */
        Connection connection = null;
        Statement statement = null;
        List<String> types = new ArrayList<String>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://107.155.113.86:3306/STOCKSYSTEM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "cse305", "CSE305XYZ");
            connection.setAutoCommit(false); // only one transaction
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT S.Type From Stock S"
            );
            while(rs.next()) {
                String type = rs.getString("Type");
                if(!types.contains(type)) {
                    types.add(type);
                }
            }
            return types;
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
            try{
                if (connection!=null)
                    connection.rollback();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException se2) {
                System.out.println(se2.getMessage());
            }
        }
        return null;
    }

    public List<Stock> getStockByType(String stockType) {

		/*
		 * The students code to fetch data from the database will be written here
		 * Return list of stocks of type "stockType"
		 */
        Connection connection = null;
        Statement statement = null;
        List<Stock> stockList = new ArrayList<Stock>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://107.155.113.86:3306/STOCKSYSTEM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "cse305", "CSE305XYZ");
            connection.setAutoCommit(false); // only one transaction
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM Stock S WHERE S.Type = '" + stockType + "'"
            );
            while (rs.next()) {
                String stockSymbol = rs.getString("StockSymbol");
                double pricePerShare = rs.getDouble("PricePerShare");
                String companyName = rs.getString("CompanyName");
                String type = rs.getString("Type");
                int totalShare = rs.getInt("TotalShare");
                Stock stock  = new Stock(companyName, stockSymbol, type, pricePerShare, totalShare);
                stockList.add(stock);
            }
            connection.commit();
            rs.close();
            statement.close();
            connection.close();
            return stockList;
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
            try{
                if (connection!=null)
                    connection.rollback();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException se2) {
                System.out.println(se2.getMessage());
            }
        }
        return null;
    }
}
