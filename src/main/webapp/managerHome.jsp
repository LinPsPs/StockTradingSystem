<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="heading" value="Home"/>
<%@ include file="header.jsp" %>
<div class="container">
    <h2>Manager Options:</h2>
    <hr>
    <%
        String email = (String) session.getAttribute("email");
        String role = (String) session.getAttribute("role");

        //redirect to appropriate home page if already logged in
        if (email != null) {
            if (role.equals("customerRepresentative")) {
                response.sendRedirect("customerRepresentativeHome.jsp");
            } else if (!role.equals("manager")) {
                response.sendRedirect("home.jsp");
            }
        } else {
            // redirect to log in if not alreaddy logged in
            response.sendRedirect("index.jsp");
        }

    %>
    <%
        String status = request.getParameter("status");
        if(status != null) {
            if(status.equals("editEmployeeSuccess")) {%>
                <div class="alert alert-success" role="alert" style="text-align: center">
                    Update Employee Success!
                </div>
            <%} else if (status.equals("deleteSuccess")){%>
                <div class="alert alert-success" role="alert" style="text-align: center">
                    Delete Employee Success!
                </div>
            <%} else if (status.equals("addEmployeeSuccess")){%>
                <div class="alert alert-success" role="alert" style="text-align: center">
                    Add Employee Success!
                </div>
            <%}else if (status.equals("deleteFailure")){%>
                <div class="alert alert-danger" role="alert" style="text-align: center">
                    Delete Employee Fail!
                </div>
            <%}
    }%>
    <div class="row mt-2">
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <div class="container">
                        <div class="media" >
                            <img src="./img/employee_option.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                            <h5 class="mt-3">Manage Employee</h5>
                        </div>
                        <hr>
                        <div class="container">
                            <div class="row">
                                <div class="media">
                                    <img src="./img/add_Employee.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                    <form action="viewAddEmployee.jsp" class="mt-3">
                                        <input type="submit" value="Add Employee" class="btn btn-primary"/>
                                    </form>
                                </div>
                            </div>
                            <div class="row mt-1">
                                <div class="media">
                                    <img src="./img/remove_Employee.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                    <form action="getEmployees" class=" mt-3">
                                        <input type="submit" value="View / Edit / Delete Employee" class="btn btn-primary"/>
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
                            <img src="./img/sals_report.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                            <h5 class="mt-3">Sales and Orders</h5>
                        </div>
                        <hr>
                        <div class="container">
                            <div class="row">
                                <div class="col">
                                    <div class="row">
                                        <div class="media">
                                            <img src="./img/view_sales.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                            <form action="viewSalesReport.jsp" class="mt-3">
                                                <input type="submit" value="View sales report" class="btn btn-primary"/>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="row mt-1">
                                        <div class="media">
                                            <img src="./img/revenue.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                            <form action="viewSummaryListing.jsp" class="mt-3">
                                                <input type="submit" value="View Revenue Summary" class="btn btn-primary"/>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="row">
                                        <div class="media">
                                            <img src="./img/best_employee.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                            <form action="getHighestRevenueEmployee" class="mt-3">
                                                <input type="submit" value="Highest Revenue Customer Representative" class="btn btn-success"/>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="row  mt-1">
                                        <div class="media">
                                            <img src="./img/best_customer.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                            <form action="getHighestRevenueCustomer" class="mt-3">
                                                <input type="submit" value="Highest Revenue Customer" class="btn btn-success"/>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="row  mt-1">
                                        <div class="media">
                                            <img src="./img/search_order.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                            <form action="viewSearchOrders" class="mt-3">
                                                <input type="submit" value="Search orders" class="btn btn-success"/>
                                            </form>
                                        </div>
                                    </div>
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
                                            <img src="./img/set_stock_price.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                            <form action="viewSetStockPrice" class="mt-3">
                                                <input type="submit" value="Set stock price" class="btn btn-primary"/>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="row mt-1">
                                        <div class="media">
                                            <img src="./img/view_allstock.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                            <form action="getStocks" class="mt-3">
                                                <input type="submit" value="View all Stocks" class="btn btn-primary"/>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="row mt-1">
                                        <div class="media">
                                            <img src="./img/best_sell_stock.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                            <form action="getBestsellers" class="mt-3">
                                                <input type="submit" value="View Bestsellers" class="btn btn-primary"/>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="row">
                                        <div class="media">
                                            <img src="./img/actively_trade_stock.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                            <form action="getActivelyTradedStocks" class="mt-3">
                                                <input type="submit" value="View actively traded stocks" class="btn btn-success"/>
                                            </form>
                                        </div>
                                    </div>
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