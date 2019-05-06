package dao;

import com.sun.org.apache.bcel.internal.generic.INEG;
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
        ArrayList<Stock> activelyTrade = new ArrayList<>();
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
            ResultSet resultSet = statement.executeQuery("SELECT T.StockId,COUNT(*) AS TRADE_COUNT\n" +
                    "FROM  Trade T,Stock S\n" +
                    "WHERE  T.StockId = S.StockSymbol\n" +
                    "GROUP BY StockId\n" +
                    "ORDER BY TRADE_COUNT DESC");
            while (resultSet.next()){
                Stock stock = getStockBySymbol(resultSet.getString("StockId"));
                activelyTrade.add(stock);
            }
            connection.commit();
            resultSet.close();
            statement.close();
            connection.close();
            return activelyTrade;
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
        return activelyTrade;
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
                    "SELECT * FROM Stock S WHERE S.StockSymbol = " + "'" + stockSymbol + "'"
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
            // add this tuple to stock history
            preparedStatement = connection.prepareStatement("INSERT INTO StockHistory(StockSymbol, ChangeDate, PricePerShare) VALUE (?,?,?)");
            preparedStatement.setString(1, stockSymbol);
            // get time
            java.util.Date dt = new java.util.Date();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(dt);
            preparedStatement.setString(2, currentTime);
            preparedStatement.setDouble(3, stockPrice);
            preparedStatement.executeUpdate();
            connection.close();
            checkConditionOrder(stockSymbol, stockPrice);
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

        ArrayList<Stock> overAllBest = new ArrayList<>();
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
            ResultSet resultSet = statement.executeQuery("SELECT T.StockId,SUM(O.NumShares)AS SELL_COUNT\n" +
                    "FROM Trade T, Orders O\n" +
                    "WHERE T.OrderId = O.Id\n" +
                    "GROUP BY StockId\n" +
                    "ORDER BY SELL_COUNT DESC");
            while (resultSet.next()){
                Stock stock = getStockBySymbol(resultSet.getString("StockId"));
                overAllBest.add(stock);
            }
            connection.commit();
            resultSet.close();
            statement.close();
            connection.close();
            return overAllBest;
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
        return overAllBest;

	}

    public List<Stock> getCustomerBestsellers(String customerID) {

		/*
		 * The students code to fetch data from the database will be return getDummyStocks();written here.
		 * Get list of customer bestseller stocks
		 */
        ArrayList<Stock> customerBest = new ArrayList<>();
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
            preparedStatement = connection.prepareStatement(
                    "SELECT T.StockId, SUM(O.NumShares)AS TRADE_AMOUNT\n" +
                    "FROM  Trade T,Stock S,Orders O,Account A, Client C\n" +
                    "WHERE T.OrderId = O.Id AND T.StockId = S.StockSymbol AND T.AccountId = A.Id AND A.Client = C.Id AND C.Id =?\n" +
                    "GROUP BY StockId\n" +
                    "ORDER BY TRADE_AMOUNT DESC");
            preparedStatement.setInt(1,Integer.parseInt(customerID));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Stock stock = getStockBySymbol(resultSet.getString("StockId"));
                customerBest.add(stock);
            }
            connection.commit();
            resultSet.close();
            statement.close();
            preparedStatement.close();
            connection.close();
            return customerBest;
        }
        catch(SQLException ex){
            ex.printStackTrace();
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
        return customerBest;

    }

	public List getStocksByCustomer(String customerId) {

		/*
		 * The students code to fetch data from the database will be written here
		 * Get stockHoldings of customer with customerId
		 */
//		System.out.println("getStocksByCustomer start!");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Stock> stockList = new ArrayList<Stock>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://107.155.113.86:3306/STOCKSYSTEM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "cse305", "CSE305XYZ");
            connection.setAutoCommit(false); // only one transaction
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            //statement = connection.createStatement();
            preparedStatement = connection.prepareStatement(
                   "SELECT S.StockID,S2.PricePerShare,S2.CompanyName,S2.Type,S.NumberOfShare\n" +
                   "FROM HasStock S, Client C , Account A,Stock S2\n" +
                   "WHERE  S.AccountID = A.Id AND A.Client = C.Id AND C.Id = ? AND S.StockID = S2.StockSymbol");
            preparedStatement.setInt(1,Integer.parseInt(customerId));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String stockSymbol = rs.getString("StockID");
                double pricePerShare = rs.getDouble("PricePerShare");
                String companyName = rs.getString("CompanyName");
                String type = rs.getString("Type");
                int totalShare = rs.getInt("NumberOfShare");
                Stock stock  = new Stock(companyName, stockSymbol, type, pricePerShare, totalShare);
                stockList.add(stock);
            }
            connection.commit();
            rs.close();
            preparedStatement.close();
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
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException s2) {
                System.out.println(s2.getMessage());
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se3) {
                System.out.println(se3.getMessage());
            }
        }
        return stockList;
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
        ArrayList<Stock> suggestion = new ArrayList<>();
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
            preparedStatement = connection.prepareStatement(
                    "SELECT S.StockSymbol\n" +
                            "FROM\n" +
                            "     (SELECT S.Type, COUNT(*)AS TRADE_COUNT\n" +
                            "      FROM Trade T,Stock S\n" +
                            "      WHERE T.StockId = S.StockSymbol AND\n" +
                            "            T.AccountId = (SELECT A.Id FROM Account A  WHERE A.Client =?)\n" +
                            "      GROUP BY S.Type\n" +
                            "      ORDER BY TRADE_COUNT DESC\n" +
                            "      LIMIT 1)AS HISTORY, Stock S,HasStock HS, Account A,Client C\n" +
                            "WHERE S.Type = HISTORY.Type AND S.StockSymbol NOT IN (\n" +
                            "        SELECT S.StockID\n" +
                            "        FROM HasStock S, Client C , Account A\n" +
                            "        WHERE  S.AccountID = A.Id AND A.Client = C.Id AND C.Id = ?\n" +
                            "    )\n" +
                            "GROUP BY S.StockSymbol\n" +
                            "ORDER BY S.StockSymbol ASC;");
            preparedStatement.setInt(1,Integer.parseInt(customerID));
            preparedStatement.setInt(2,Integer.parseInt(customerID));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Stock stock = getStockBySymbol(resultSet.getString("StockSymbol"));
                suggestion.add(stock);
            }
            connection.commit();
            resultSet.close();
            statement.close();
            connection.close();
            preparedStatement.close();
            return suggestion;
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
        return suggestion;

    }

    public List<Stock> getStockPriceHistory(String stockSymbol) {

		/*
		 * The students code to fetch data from the database
		 * Return list of stock objects, showing price history
		 */
//        System.out.println("getStockPriceHistory start!");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Stock> stockList = new ArrayList<Stock>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://107.155.113.86:3306/STOCKSYSTEM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "cse305", "CSE305XYZ");
            connection.setAutoCommit(false); // only one transaction
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            //System.out.println(stockSymbol);
            preparedStatement = connection.prepareStatement(
                    "SELECT S.StockSymbol, S.Type, S.TotalShare,S.CompanyName,SH.PricePerShare,SH.ChangeDate\n" +
                    "FROM Stock S, StockHistory SH\n" +
                    "WHERE S.StockSymbol = SH.StockSymbol AND S.StockSymbol = ?\n" +
                    "GROUP BY ChangeDate DESC");
            preparedStatement.setString(1,stockSymbol);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String stocksymbol = resultSet.getString("StockSymbol");
                double pricePerShare = resultSet.getDouble("PricePerShare");
                String companyName = resultSet.getString("CompanyName");
                String type = resultSet.getString("Type");
                int totalShare = resultSet.getInt("TotalShare");
                Stock stock  = new Stock(companyName, stockSymbol, type, pricePerShare, totalShare);
                stockList.add(stock);
            }
            connection.commit();
            resultSet.close();
            preparedStatement.close();
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
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException se2) {
                System.out.println(se2.getMessage());
            }
        }
        return null;
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
    public void checkConditionOrder(String stockSymbol, double stockPrice) {
        /**
         *  This method will check the orderlist to find if the order will place
         */
        Connection connection = null;
        Statement statement = null;
        Statement checkStatement = null;
        PreparedStatement preparedStatement;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://107.155.113.86:3306/STOCKSYSTEM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "cse305", "CSE305XYZ");
            connection.setAutoCommit(false); // only one transaction
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            statement = connection.createStatement();
            checkStatement = connection.createStatement();
            // check hiddenStop
            int orderId;
            double numShares;
            ResultSet hiddenStop = statement.executeQuery("SELECT Id FROM Orders O WHERE O.PriceType = '" + "HiddenStop" + "'");
            while(hiddenStop.next()) {
                orderId = hiddenStop.getInt("Id");
                numShares = hiddenStop.getDouble("NumShares");
                ResultSet rs = checkStatement.executeQuery("SELECT * FROM HiddenStop H WHERE H.OrderId = " + orderId);
                rs.next();
                double targetPrice = rs.getDouble("OriginalPrice");
                // insert into current price into this table
                preparedStatement = connection.prepareStatement("INSERT INTO HiddenStop(OrderId, PricePerShare, OriginalPrice, OrderDate) VALUE (?,?,?,?)");
                preparedStatement.setInt(1, orderId);
                preparedStatement.setDouble(2, stockPrice);
                preparedStatement.setDouble(3, targetPrice);
                java.util.Date dt = new java.util.Date();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTime = sdf.format(dt);
                preparedStatement.setString(4, currentTime);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                rs.close();
                // check if it satisfies the target
                if(stockPrice < targetPrice) {
                    // check trade
                    rs = checkStatement.executeQuery("SELECT TransactionId FROM Trade T WHERE T.OrderId = " + orderId);
                    rs.next();
                    int transId = rs.getInt("TransactionId");
                    int accountId = rs.getInt("AccountId");
                    rs.close();
                    rs = checkStatement.executeQuery("SELECT NumberOfShare FROM HasStock H WHERE H.AccountID = " + accountId + ", H.StockID = '" + stockSymbol + "'");
                    if(rs.next()) {
                        int stockHold = rs.getInt("NumberOfShare");
                        if(stockHold > numShares) {
                            stockHold -= numShares;
                        }
                        else {
                            numShares = stockHold;
                        }
                        preparedStatement = connection.prepareStatement("UPDATE HasStock H SET H.NumberOfShare = ? WHERE H.AccountID = " + accountId + ", H.StockID = '" + stockSymbol + "'");
                        preparedStatement.setInt(1, stockHold);
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                    }
                    rs.close();
                    double fee = stockPrice * numShares * 0.05;
                    preparedStatement = connection.prepareStatement("UPDATE Transaction T SET T.fee = ?, T.PricePerShare = ? WHERE T.Id = " + transId);
                    preparedStatement.setDouble(1, fee);
                    preparedStatement.setDouble(2, stockPrice);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                }
            }
            hiddenStop.close();
            // check trailing stop
            ResultSet trailingStop = statement.executeQuery("SELECT Id FROM Orders O WHERE O.PriceType = '" + "TrailingStop" + "'");
            while(hiddenStop.next()) {
                orderId = trailingStop.getInt("Id");
                numShares = hiddenStop.getDouble("NumShares");
                ResultSet rs = checkStatement.executeQuery("SELECT * FROM TrailingStop H WHERE H.OrderId = " + orderId);
                rs.next();
                double targetPrice = rs.getDouble("OriginalPrice");
                // insert into current price into this table
                preparedStatement = connection.prepareStatement("INSERT INTO TrailingStop(OrderId, PricePerShare, OriginalPrice, OrderDate) VALUE (?,?,?,?)");
                preparedStatement.setInt(1, orderId);
                preparedStatement.setDouble(2, stockPrice);
                preparedStatement.setDouble(3, targetPrice);
                java.util.Date dt = new java.util.Date();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTime = sdf.format(dt);
                preparedStatement.setString(4, currentTime);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                rs.close();
                // check if it satisfies the target
                if(stockPrice < targetPrice) {
                    // check trade
                    rs = checkStatement.executeQuery("SELECT TransactionId FROM Trade T WHERE T.OrderId = " + orderId);
                    rs.next();
                    int transId = rs.getInt("TransactionId");
                    int accountId = rs.getInt("AccountId");
                    rs.close();
                    rs = checkStatement.executeQuery("SELECT NumberOfShare FROM HasStock H WHERE H.AccountID = " + accountId + ", H.StockID = '" + stockSymbol + "'");
                    if(rs.next()) {
                        int stockHold = rs.getInt("NumberOfShare");
                        if(stockHold > numShares) {
                            stockHold -= numShares;
                        }
                        else {
                            numShares = stockHold;
                        }
                        preparedStatement = connection.prepareStatement("UPDATE HasStock H SET H.NumberOfShare = ? WHERE H.AccountID = " + accountId + ", H.StockID = '" + stockSymbol + "'");
                        preparedStatement.setInt(1, stockHold);
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                    }
                    rs.close();
                    double fee = stockPrice * numShares * 0.05;
                    preparedStatement = connection.prepareStatement("UPDATE Transaction T SET T.fee = ?, T.PricePerShare = ? WHERE T.Id = " + transId);
                    preparedStatement.setDouble(1, fee);
                    preparedStatement.setDouble(2, stockPrice);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                }
            }
            statement.close();
            checkStatement.close();
            connection.close();
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
    }
}
