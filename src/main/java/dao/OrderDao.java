package dao;

import model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDao {

    public Order getDummyTrailingStopOrder() {
        TrailingStopOrder order = new TrailingStopOrder();

        order.setId(1);
        order.setDatetime(new Date());
        order.setNumShares(5);
        order.setPercentage(12.0);
        return order;
    }

    public Order getDummyMarketOrder() {
        MarketOrder order = new MarketOrder();

        order.setId(1);
        order.setDatetime(new Date());
        order.setNumShares(5);
        order.setBuySellType("buy");
        return order;
    }

    public Order getDummyMarketOnCloseOrder() {
        MarketOnCloseOrder order = new MarketOnCloseOrder();

        order.setId(1);
        order.setDatetime(new Date());
        order.setNumShares(5);
        order.setBuySellType("buy");
        return order;
    }

    public Order getDummyHiddenStopOrder() {
        HiddenStopOrder order = new HiddenStopOrder();

        order.setId(1);
        order.setDatetime(new Date());
        order.setNumShares(5);
        order.setPricePerShare(145.0);
        return order;
    }

    public List<Order> getDummyOrders() {
        List<Order> orders = new ArrayList<Order>();

        for (int i = 0; i < 3; i++) {
            orders.add(getDummyTrailingStopOrder());
        }

        for (int i = 0; i < 3; i++) {
            orders.add(getDummyMarketOrder());
        }

        for (int i = 0; i < 3; i++) {
            orders.add(getDummyMarketOnCloseOrder());
        }

        for (int i = 0; i < 3; i++) {
            orders.add(getDummyHiddenStopOrder());
        }

        return orders;
    }

    public String submitOrder(Order order, Customer customer, Employee employee, Stock stock) {

		/*
		 * Student code to place stock order
		 * Employee can be null, when the order is placed directly by Customer
         * */
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://107.155.113.86:3306/STOCKSYSTEM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "cse305", "CSE305XYZ");
            connection.setAutoCommit(false); // only one transaction
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            statement = connection.createStatement();
            // get the price of this stock
            String stockSymbol = stock.getSymbol();
            ResultSet rs = statement.executeQuery(
                    "SELECT PricePerShare, TotalShare FROM Stock S WHERE S.StockSymbol = " + "'" + stockSymbol + "'"
            );
            rs.next();
            double pricePerShare = rs.getDouble("PricePerShare");
            int numShares = order.getNumShares();
            double fee = numShares * pricePerShare * 0.05;
            // insert to order
            // check order type
            if(order instanceof MarketOrder || order instanceof MarketOnCloseOrder) {
                preparedStatement = connection.prepareStatement("INSERT INTO Orders(NumShares, PricePerShare, DateTime, PriceType, OrderType) VALUE (?,?,?,?,?)");
                preparedStatement.setInt(1, numShares);
                preparedStatement.setDouble(2, pricePerShare);
                // get time
                java.util.Date dt = new java.util.Date();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTime = sdf.format(dt);
                preparedStatement.setString(3, currentTime);
                String buySellType = ((MarketOrder)order).getBuySellType();
                // check type
                String priceType = "MarketOnClose";
                if(order instanceof MarketOrder) {
                    priceType = "Market";
                }
                preparedStatement.setString(4, priceType);
                preparedStatement.setString(5, buySellType);
                preparedStatement.executeUpdate();
                // insert to statement
                preparedStatement = connection.prepareStatement("INSERT INTO Transaction(Fee, DateTime, PricePerShare) VALUE (?,?,?)");
                preparedStatement.setDouble(1, fee);
                preparedStatement.setString(2, currentTime);
                preparedStatement.setDouble(3, pricePerShare);
                preparedStatement.executeUpdate();

                // get the id of trans and order
                rs = statement.executeQuery("SELECT * FROM Orders ORDER BY Id DESC LIMIT 1");
                rs.next();
                int orderId = rs.getInt("Id");
                rs = statement.executeQuery("SELECT * FROM Transaction ORDER BY Id DESC LIMIT 1");
                rs.next();
                int transId = rs.getInt("Id");
                preparedStatement = connection.prepareStatement("INSERT INTO Trade(AccountId, BrokerId, TransactionId, OrderId, StockId) VALUE (?,?,?,?,?)");
                preparedStatement.setInt(1, customer.getAccountNumber());
                if(employee != null) {
                    preparedStatement.setInt(2, Integer.parseInt(employee.getEmployeeID()));
                }
                else {
                    preparedStatement.setNull(2, java.sql.Types.INTEGER);
                }
                preparedStatement.setInt(3, transId);
                preparedStatement.setInt(4, orderId);
                preparedStatement.setString(5, stockSymbol);
                preparedStatement.executeUpdate();
                // add stock to customer's account
                // check if customer has this stock
                rs = statement.executeQuery(
                        "SELECT COUNT(*) AS NUM FROM HasStock H WHERE StockID = '" + stockSymbol + "' AND AccountID = " + customer.getAccountNumber()
                );
                rs.next();
                // check buy or sell
                int hasStock = rs.getInt("NUM");
                if(hasStock == 1) {
                    rs = statement.executeQuery(
                            "SELECT * FROM HasStock H WHERE StockID = '" + stockSymbol + "' AND AccountID = " + customer.getAccountNumber()
                    );
                    rs.next();
                    int stockHold = rs.getInt("NumberOfShare");
                    if(buySellType.equals("Sell")) {
                        // sell stock
                        if (stockHold > numShares) {
                            stockHold -= numShares;
                            preparedStatement = connection.prepareStatement("UPDATE HasStock H SET H.NumberOfShare = ? WHERE H.AccountID = " + customer.getAccountNumber());
                            preparedStatement.setInt(1, stockHold);
                            preparedStatement.executeUpdate();
                        }
                        else {
                            // Not enough stock holds
                            rs.close();
                            preparedStatement.close();
                            statement.close();
                            connection.close();
                            return "fail";
                        }
                    }
                    else {
                        // buy stock
                        stockHold += numShares;
                        preparedStatement = connection.prepareStatement("UPDATE HasStock H SET H.NumberOfShare = ? WHERE H.AccountID = " + customer.getAccountNumber());
                        preparedStatement.setInt(1, stockHold);
                        preparedStatement.executeUpdate();
                    }
                }
                else {
                    if(buySellType.equals("Sell")) {
                        // if customer doesn't have any this kind of stock, don't allow he to sell any stock
                        rs.close();
                        preparedStatement.close();
                        statement.close();
                        connection.close();
                        return "fail";
                    }
                    else {
                        preparedStatement = connection.prepareStatement("INSERT INTO HasStock(StockID, AccountID, NumberOfShare) VALUE(?, ?, ?)");
                        preparedStatement.setString(1, stockSymbol);
                        preparedStatement.setInt(2, customer.getAccountNumber());
                        preparedStatement.setInt(3, numShares);
                        preparedStatement.executeUpdate();
                    }
                }
                connection.commit();
                rs.close();
                preparedStatement.close();
                statement.close();
                connection.close();
            }
            else if(order instanceof HiddenStopOrder) {
                pricePerShare = ((HiddenStopOrder) order).getPricePerShare();
                String priceType = "HiddenStop";
                preparedStatement = connection.prepareStatement("INSERT INTO Orders(NumShares, PricePerShare, DateTime, PriceType, OrderType) VALUE (?,?,?,?,?)");
                preparedStatement.setInt(1, numShares);
                preparedStatement.setDouble(2, pricePerShare);
                // get time
                java.util.Date dt = new java.util.Date();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTime = sdf.format(dt);
                preparedStatement.setString(3, currentTime);
                preparedStatement.setString(4, priceType);
                preparedStatement.setString(5, "Sell");
                preparedStatement.executeUpdate();
                connection.commit();
                rs.close();
                preparedStatement.close();
                statement.close();
                connection.close();
            }
            else if(order instanceof TrailingStopOrder) {
                double percentage = ((TrailingStopOrder) order).getPercentage();
                String priceType = "TrailingStop";
                preparedStatement = connection.prepareStatement("INSERT INTO Orders(NumShares, DateTime, Percentage, PriceType, OrderType) VALUE (?,?,?,?,?)");
                preparedStatement.setInt(1, numShares);
                // get time
                java.util.Date dt = new java.util.Date();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTime = sdf.format(dt);
                preparedStatement.setString(2, currentTime);
                preparedStatement.setDouble(3, percentage);
                preparedStatement.setString(4, priceType);
                preparedStatement.setString(5, "Sell");
                preparedStatement.executeUpdate();
                connection.commit();
                rs.close();
                preparedStatement.close();
                statement.close();
                connection.close();
            }
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
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException se2) {
                System.out.println(se2.getMessage());
            }
        }
        return "fail";
    }

    public List<Order> getOrderByStockSymbol(String stockSymbol) {
        /*
		 * Student code to get orders by stock symbol
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
            String tempStock = null;
            //check valid stock or not
            ResultSet resultSet = statement.executeQuery(
                    "SELECT StockSymbol FROM Stock S  WHERE S.StockSymbol = '"+stockSymbol+"'");
            while (resultSet.next()){
                
            }

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
        return getDummyOrders();
    }

    public List<Order> getOrderByCustomerName(String customerName) {
         /*
		 * Student code to get orders by customer name
         */
        return getDummyOrders();
    }

    public List<Order> getOrderHistory(String customerId) {
        /*
		 * The students code to fetch data from the database will be written here
		 * Show orders for given customerId
		 */
        return getDummyOrders();
    }


    public List<OrderPriceEntry> getOrderPriceHistory(String orderId) {

        /*
		 * The students code to fetch data from the database will be written here
		 * Query to view price history of hidden stop order or trailing stop order
		 * Use setPrice to show hidden-stop price and trailing-stop price
		 */
        List<OrderPriceEntry> orderPriceHistory = new ArrayList<OrderPriceEntry>();

        for (int i = 0; i < 10; i++) {
            OrderPriceEntry entry = new OrderPriceEntry();
            entry.setOrderId(orderId);
            entry.setDate(new Date());
            entry.setStockSymbol("aapl");
            entry.setPricePerShare(150.0);
            entry.setPrice(100.0);
            orderPriceHistory.add(entry);
        }
        return orderPriceHistory;
    }
}
