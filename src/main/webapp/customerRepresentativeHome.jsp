<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="heading" value="Home"/>
<%@ include file="header.jsp" %>
<!--
	This is the Home page for Customer Representative
	This page contains various buttons to navigate the online auction house
	This page contains customer representative specific accessible buttons
-->
<div class="container">
    <h2>Customer Representative Options:</h2>
    <hr>
    <%
        String email = (String)session.getAttribute("email");
        String role = (String)session.getAttribute("role");

        //redirect to appropriate home page if already logged in
        if(email != null) {
            if(role.equals("manager")) {
                response.sendRedirect("managerHome.jsp");
            }
            else if(!role.equals("customerRepresentative")) {
                response.sendRedirect("home.jsp");
            }
        }
        else {
            // redirect to log in if not alreaddy logged in
            response.sendRedirect("index.jsp");
        }
    %>
        <%
        String status = request.getParameter("status");
        if(status != null) {
            if(status.equals("editCustomerSuccess")) {%>
                <div class="alert alert-success" role="alert" style="text-align: center">
                    Update Employee Success!
                </div>
        <%} else if (status.equals("deleteSuccess")){%>
                <div class="alert alert-success" role="alert" style="text-align: center">
                    Delete Employee Success!
                </div>
        <%} else if (status.equals("addCustomerSuccess")){%>
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
                            <img src="./img/record_order.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                            <h5 class="mt-3">Record Order</h5>
                        </div>
                        <hr>
                        <div class="container">
                            <div class="row">
                                <div class="media">
                                    <img src="./img/add_order.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                    <form action="viewAddCustomerOrder" class="mt-3">
                                    <input type="submit" value="Record order" class="btn btn-success"/>
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
                            <img src="./img/manage_customer.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                            <h5 class="mt-3">Manage Customer</h5>
                        </div>
                        <hr>
                        <div class="container">
                            <div class="row">
                                <div class="col">
                                    <div class="row">
                                        <div class="media">
                                            <img src="./img/add_customer.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                            <form action="viewAddCustomer.jsp" class="mt-3">
                                                <input type="submit" value="Add Customer" class="btn btn-primary"/>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="row mt-1">
                                        <div class="media">
                                            <img src="./img/view_customers.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                            <form action="getCustomers" class="mt-3">
                                                <input type="submit" value="View / Edit / Delete Customer" class="btn btn-primary"/>
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

    <div class="row mt-2">
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <div class="container">
                        <div class="media">
                            <img src="./img/other.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                            <h5 class="mt-3">Other</h5>
                        </div>
                        <hr>
                        <div class="container">
                            <div class="row">
                                <div class="col">
                                    <div class="row">
                                        <div class="media">
                                            <img src="./img/customer_mail.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                            <form action="getCustomerMailingList" class="mt-3">
                                                <input type="submit" value="Customer Mailing List" class="btn btn-primary"/>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="row">
                                        <div class="media">
                                            <img src="./img/suggestion.png" width="64" height="64" class="mr-3 img-thumbnail" alt="...">
                                            <form action="viewCustomerStockSuggestions" class="mt-3">
                                                <input type="submit" value="View Suggestions" class="btn btn-success"/>
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