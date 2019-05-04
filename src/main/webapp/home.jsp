<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="heading" value="Home"/>
<%@ include file="header.jsp" %>

<div class="container">
    <h2>Customer Options:</h2>
    <hr>
    <%
        String email = (String)session.getAttribute("email");
        String role = (String)session.getAttribute("role");

        //redirect to appropriate home page if already logged in
        if(email != null) {
            if(role.equals("manager")) {
                response.sendRedirect("managerHome.jsp");
            }
            else if(role.equals("customerRepresentative")) {
                response.sendRedirect("customerRepresentativeHome.jsp");
            }
        }
        else {
            // redirect to log in if not alreaddy logged in
            response.sendRedirect("index.jsp");
        }
    %>
    <div class="row mt-2">
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <div class="container">
                        <div class="media" >
                            <img src="./img/orders.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                            <h5 class="mt-3">Orders</h5>
                        </div>
                        <hr>
                        <div class="container">
                            <div class="row">
                                <div class="media">
                                    <img src="./img/place_order_as_customer.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                    <form action="viewAddOrder" class="mt-3">
                                        <input type="submit" value="Place Order" class="btn btn-success"/>
                                    </form>
                                </div>
                            </div>
                            <div class="row mt-1">
                                <div class="media">
                                    <img src="./img/order_history.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                    <form action="getOrdersByCustomer" class="mt-3">
                                        <input type="submit" value="Order History" class="btn btn-success"/>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row mt-2">
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <div class="container">
                        <div class="media">
                            <img src="./img/stocks.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                            <h5 class="mt-3">Stocks</h5>
                        </div>
                        <hr>
                        <div class="container">
                            <div class="row">
                                <div class="col">
                                    <div class="row">
                                        <div class="media">
                                            <img src="./img/current_stock_hold.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                            <form action="getStocksByCustomer" class="mt-3">
                                                <input type="submit" value="Current stock holdings" class="btn btn-success"/>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="row mt-1">
                                        <div class="media">
                                            <img src="./img/stock_price_history.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                            <form action="viewGetStockPriceHistory" class="mt-3">
                                                <input type="submit" value="Stock price history" class="btn btn-success"/>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="row mt-1">
                                        <div class="media">
                                            <img src="./img/search_stocks.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                            <form action="viewSearchStocks" class="mt-3">
                                                <input type="submit" value="Search stocks" class="btn btn-success"/>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="row mt-1">
                                        <div class="media">
                                            <img src="./img/view_best_sell_stock.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                            <form action="getCustomerBestsellers" class="mt-3">
                                                <input type="submit" value="View bestseller stocks" class="btn btn-success"/>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="row mt-1">
                                        <div class="media">
                                            <img src="./img/customer_suggestion.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                            <form action="getStockSuggestions" class="mt-3">
                                                <input type="submit" value="View suggested stocks" class="btn btn-success"/>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <div class="col">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>