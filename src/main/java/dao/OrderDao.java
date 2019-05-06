package dao;

import model.*;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
                // insert to statement
                preparedStatement = connection.prepareStatement("INSERT INTO Transaction(Fee, DateTime, PricePerShare) VALUE (?,?,?)");
                preparedStatement.setDouble(1, -1);
                preparedStatement.setString(2, currentTime);
                preparedStatement.setDouble(3, -1);
                preparedStatement.executeUpdate();
                // get the id of trans and order
                rs = statement.executeQuery("SELECT * FROM Orders ORDER BY Id DESC LIMIT 1");
                rs.next();
                int orderId = rs.getInt("Id");
                rs = statement.executeQuery("SELECT * FROM Transaction ORDER BY Id DESC LIMIT 1");
                rs.next();
                int transId = rs.getInt("Id");
                // insert to trade
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

                //add to history
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO HiddenStop(OrderId, PricePerShare, OriginalPrice, OrderDate) VALUE (?,?,?,?)");
                preparedStatement.setInt(1,orderId);
                preparedStatement.setDouble(2,stock.getPrice());
                preparedStatement.setDouble(3,((HiddenStopOrder) order).getPricePerShare());
                preparedStatement.setString(4,currentTime);
                preparedStatement.executeUpdate();
                //clean
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
                // insert to statement
                preparedStatement = connection.prepareStatement("INSERT INTO Transaction(Fee, DateTime, PricePerShare) VALUE (?,?,?)");
                preparedStatement.setDouble(1, -1);
                preparedStatement.setString(2, currentTime);
                preparedStatement.setDouble(3, -1);
                preparedStatement.executeUpdate();
                // get the id of trans and order
                rs = statement.executeQuery("SELECT * FROM Orders ORDER BY Id DESC LIMIT 1");
                rs.next();
                int orderId = rs.getInt("Id");
                rs = statement.executeQuery("SELECT * FROM Transaction ORDER BY Id DESC LIMIT 1");
                rs.next();
                int transId = rs.getInt("Id");
                // insert to trade
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

                //add to history
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO TrailingStop(OrderId, PricePerShare, OriginalPrice, OrderDate) VALUE (?,?,?,?)");
                preparedStatement.setInt(1,orderId);
                preparedStatement.setDouble(2,stock.getPrice());
                preparedStatement.setDouble(3,stock.getPrice()*(1+((TrailingStopOrder) order).getPercentage()));
                preparedStatement.setString(4,currentTime);
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
        ArrayList<Order> orders = new ArrayList<Order>();
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
                tempStock =  resultSet.getString("StockSymbol");
            }
            if (tempStock==null){
                connection.commit();
                resultSet.close();
                statement.close();
                //preparedStatement.close();
                connection.close();
                return orders;
            }
            //is valid order
            resultSet = statement.executeQuery(
                    "SELECT O.Id AS ORDER_ID,O.NumShares,O.DateTime,O.OrderType,O.PriceType,O.Percentage,O.PricePerShare\n" +
                            "FROM Trade T, Orders O\n" +
                            "WHERE T.OrderId = O.Id AND T.StockId = '"+stockSymbol+"'\n" +
                            "ORDER BY  ORDER_ID ASC");
            while (resultSet.next()){
                String orderType= resultSet.getString("PriceType");
                switch (orderType){
                    case "Market":
                        MarketOrder order = new MarketOrder();
                        Date orderDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .parse(resultSet.getString("DateTime"));
                        order.setDatetime(orderDate);
                        order.setId(resultSet.getInt("ORDER_ID"));
                        order.setBuySellType(resultSet.getString("OrderType"));
                        order.setNumShares(resultSet.getInt("NumShares"));
                        orders.add(order);break;
                    case "MarketOnClose":
                        MarketOnCloseOrder order1 = new MarketOnCloseOrder();
                        Date orderDate1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .parse(resultSet.getString("DateTime"));
                        order1.setDatetime(orderDate1);
                        order1.setId(resultSet.getInt("ORDER_ID"));
                        order1.setBuySellType(resultSet.getString("OrderType"));
                        order1.setNumShares(resultSet.getInt("NumShares"));
                        orders.add(order1);break;
                    case "TrailingStop":
                        TrailingStopOrder order2 = new TrailingStopOrder();
                        Date orderDate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .parse(resultSet.getString("DateTime"));
                        order2.setDatetime(orderDate2);
                        order2.setId(resultSet.getInt("ORDER_ID"));
                        order2.setNumShares(resultSet.getInt("NumShares"));
                        order2.setPercentage(resultSet.getDouble("Percentage"));
                        orders.add(order2);break;
                    case "HiddenStop":
                        HiddenStopOrder order3 = new HiddenStopOrder();
                        Date orderDate3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .parse(resultSet.getString("DateTime"));
                        order3.setDatetime(orderDate3);
                        order3.setId(resultSet.getInt("ORDER_ID"));
                        order3.setNumShares(resultSet.getInt("NumShares"));
                        order3.setPricePerShare(resultSet.getDouble("PricePerShare"));
                        orders.add(order3);break;
                }
            }
            //clean up
            connection.commit();
            resultSet.close();
            //preparedStatement.close();
            statement.close();
            connection.close();
            return orders;
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
       return orders;
    }

    public List<Order> getOrderByCustomerName(String customerName) {
         /*
		 * Student code to get orders by customer name
         */
        ArrayList<Order> orders = new ArrayList<Order>();
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
            //valid cus
            preparedStatement = connection.prepareStatement(
                    "SELECT O.Id AS ORDER_ID,O.NumShares,O.DateTime,O.OrderType,O.PriceType,O.Percentage,O.PricePerShare\n" +
                    "FROM Orders O,Trade T,Client C,Account A, Person P\n" +
                    "WHERE O.Id = T.OrderId AND T.AccountId = A.Id AND C.Id = A.Client AND P.SSN = C.Id AND P.FirstName=? AND P.LastName = ?\n" +
                    "GROUP BY ORDER_ID\n" +
                    "ORDER BY ORDER_ID ASC");
            preparedStatement.setString(1,customerName.substring(0,customerName.indexOf(" ")));
            preparedStatement.setString(2,customerName.substring(customerName.indexOf(" ")+1));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String orderType= resultSet.getString("PriceType");
                switch (orderType){
                    case "Market":
                        MarketOrder order = new MarketOrder();
                        Date orderDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .parse(resultSet.getString("DateTime"));
                        order.setDatetime(orderDate);
                        order.setId(resultSet.getInt("ORDER_ID"));
                        order.setBuySellType(resultSet.getString("OrderType"));
                        order.setNumShares(resultSet.getInt("NumShares"));
                        orders.add(order);break;
                    case "MarketOnClose":
                        MarketOnCloseOrder order1 = new MarketOnCloseOrder();
                        Date orderDate1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .parse(resultSet.getString("DateTime"));
                        order1.setDatetime(orderDate1);
                        order1.setId(resultSet.getInt("ORDER_ID"));
                        order1.setBuySellType(resultSet.getString("OrderType"));
                        order1.setNumShares(resultSet.getInt("NumShares"));
                        orders.add(order1);break;
                    case "TrailingStop":
                        TrailingStopOrder order2 = new TrailingStopOrder();
                        Date orderDate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .parse(resultSet.getString("DateTime"));
                        order2.setDatetime(orderDate2);
                        order2.setId(resultSet.getInt("ORDER_ID"));
                        order2.setNumShares(resultSet.getInt("NumShares"));
                        order2.setPercentage(resultSet.getDouble("Percentage"));
                        orders.add(order2);break;
                    case "HiddenStop":
                        HiddenStopOrder order3 = new HiddenStopOrder();
                        Date orderDate3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .parse(resultSet.getString("DateTime"));
                        order3.setDatetime(orderDate3);
                        order3.setId(resultSet.getInt("ORDER_ID"));
                        order3.setNumShares(resultSet.getInt("NumShares"));
                        order3.setPricePerShare(resultSet.getDouble("PricePerShare"));
                        orders.add(order3);break;
                }
            }
            //clean up
            connection.commit();
            resultSet.close();
            //preparedStatement.close();
            statement.close();
            connection.close();
            return orders;

        } catch(SQLException ex){
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
        return orders;
    }

    public List<Order> getOrderHistory(String customerId) {
        /*
		 * The students code to fetch data from the database will be written here
		 * Show orders for given customerId
		 */
        Connection connection = null;
        Statement statement = null;
        Statement orderStatement = null;
        List<Order> orderList = new ArrayList<Order>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://107.155.113.86:3306/STOCKSYSTEM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "cse305", "CSE305XYZ");
            connection.setAutoCommit(false); // only one transaction
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            statement = connection.createStatement();
            orderStatement = connection.createStatement();
            ResultSet accountRs = statement.executeQuery("SELECT Id FROM Account A WHERE A.Client = " + customerId);
            accountRs.next();
            int accountId = accountRs.getInt("Id");
            accountRs.close();
            ResultSet tradeRs = statement.executeQuery("SELECT OrderId FROM Trade T WHERE T.AccountId = " + accountId);
            while(tradeRs.next()) {
                int orderId = tradeRs.getInt("OrderId");
                ResultSet orderRs = orderStatement.executeQuery("SELECT * FROM Orders O WHERE O.Id = " + orderId);
                orderRs.next();
                String priceType = orderRs.getString("PriceType");
                switch (priceType) {
                    case "Market":
                        MarketOrder order = new MarketOrder();
                        Date orderDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .parse(orderRs.getString("DateTime"));
                        order.setDatetime(orderDate);
                        order.setId(orderRs.getInt("Id"));
                        order.setBuySellType(orderRs.getString("OrderType"));
                        order.setNumShares(orderRs.getInt("NumShares"));
                        orderList.add(order);
                        break;
                    case "MarketOnClose":
                        MarketOnCloseOrder order1 = new MarketOnCloseOrder();
                        Date orderDate1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .parse(orderRs.getString("DateTime"));
                        order1.setDatetime(orderDate1);
                        order1.setId(orderRs.getInt("Id"));
                        order1.setBuySellType(orderRs.getString("OrderType"));
                        order1.setNumShares(orderRs.getInt("NumShares"));
                        orderList.add(order1);
                        break;
                    case "TrailingStop":
                        TrailingStopOrder order2 = new TrailingStopOrder();
                        Date orderDate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .parse(orderRs.getString("DateTime"));
                        order2.setDatetime(orderDate2);
                        order2.setId(orderRs.getInt("Id"));
                        order2.setNumShares(orderRs.getInt("NumShares"));
                        order2.setPercentage(orderRs.getDouble("Percentage"));
                        orderList.add(order2);
                        break;
                    case "HiddenStop":
                        HiddenStopOrder order3 = new HiddenStopOrder();
                        Date orderDate3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .parse(orderRs.getString("DateTime"));
                        order3.setDatetime(orderDate3);
                        order3.setId(orderRs.getInt("Id"));
                        order3.setNumShares(orderRs.getInt("NumShares"));
                        order3.setPricePerShare(orderRs.getDouble("PricePerShare"));
                        orderList.add(order3);
                        break;
                }
                orderRs.close();
            }
            //clean up
            connection.commit();
            tradeRs.close();
            statement.close();
            orderStatement.close();
            connection.close();
            return orderList;
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


    public List<OrderPriceEntry> getOrderPriceHistory(String orderId) {

        /*
		 * The students code to fetch data from the database will be written here
		 * Query to view price history of hidden stop order or trailing stop order
		 * Use setPrice to show hidden-stop price and trailing-stop price
		 */
        List<OrderPriceEntry> orderPriceHistory = new ArrayList<OrderPriceEntry>();

        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement =null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://107.155.113.86:3306/STOCKSYSTEM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "cse305", "CSE305XYZ");
            connection.setAutoCommit(false); // only one transaction
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            statement = connection.createStatement();
            //check order type
            String tempOrderType = null;
            ResultSet resultSet = statement.executeQuery("SELECT PriceType FROM Orders O  WHERE O.Id = "+ orderId);
            while (resultSet.next()){
                tempOrderType = resultSet.getString("PriceType");
            }
            if (tempOrderType==null){
                connection.commit();
                resultSet.close();
                statement.close();
                connection.close();
                return orderPriceHistory;
            }
            //valid order
            if (tempOrderType.equals("TrailingStop")){
                preparedStatement = connection.prepareStatement(
                        "SELECT T1.OrderId,T1.OrderDate,T1.PricePerShare,T1.OriginalPrice ,T.StockId\n" +
                        "FROM TrailingStop T1, Trade T\n" +
                        "WHERE T1.OrderId=? AND T.OrderId =T1.OrderId");
                preparedStatement.setInt(1,Integer.parseInt(orderId));
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    OrderPriceEntry ope = new OrderPriceEntry();
                    Date orderDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .parse(resultSet.getString("OrderDate"));
                    ope.setDate(orderDate);
                    ope.setOrderId(orderId);
                    ope.setStockSymbol(resultSet.getString("StockId"));
                    ope.setPricePerShare(resultSet.getDouble("PricePerShare"));
                    ope.setPrice(resultSet.getDouble("T1.OriginalPrice"));
                    orderPriceHistory.add(ope);
                }
            }else {
                preparedStatement = connection.prepareStatement(
                        "SELECT H.OrderId,H.OrderDate, H.PricePerShare,H.OriginalPrice,T.StockId\n" +
                                "FROM HiddenStop H, Trade T\n" +
                                "WHERE H.OrderId=? AND T.OrderId =H.OrderId;");
                preparedStatement.setInt(1,Integer.parseInt(orderId));
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    OrderPriceEntry ope = new OrderPriceEntry();
                    Date orderDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .parse(resultSet.getString("OrderDate"));
                    ope.setDate(orderDate);
                    ope.setOrderId(orderId);
                    ope.setStockSymbol(resultSet.getString("StockId"));
                    ope.setPricePerShare(resultSet.getDouble("PricePerShare"));
                    ope.setPrice(resultSet.getDouble("OriginalPrice"));
                    orderPriceHistory.add(ope);
                }
            }
            //clean
            connection.commit();
            resultSet.close();
            preparedStatement.close();
            statement.close();
            connection.close();
            return orderPriceHistory;
        }catch(SQLException ex){
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
        return orderPriceHistory;
    }
}
