<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>

<!--
This is the Add Customer page
This page displays fields to add a Customer
The details are sent to the AddCustomerController class in resources package
-->

<div class="container">
	<h1>Add a new Customer:</h1>
    <%
        String status = request.getParameter("status");
        if(status != null) {
            if(status.equals("error")) {%>
    <div class="alert alert-danger" role="alert" style="text-align: center">
        Update Failed! Please Check Input Again!
    </div>
    <%}
    else {%>
    <div class="alert alert-danger" role="alert" style="text-align: center">
        Some Error Occurred! Please Try Again!
    </div>
    <%}
    }%>
	<form action="addCustomer" method="POST">
		<div class="form-row">
            <div class="form-group col">
                <label for="customerEmail">Email address</label>
                <input type="email" class="form-control" id="customerEmail" name="customerEmail" placeholder="Enter email" required>
            </div>
            <div class="form-group col">
                <label for="customerPassword">Password</label>
                <input type="password" class="form-control" id="customerPassword" name="customerPassword" placeholder="Password" required>
            </div>
        </div>
		<div class="form-row">
            <div class="form-group col">
                <label for="customerFirstName">First Name</label>
                <input type="text" class="form-control" id="customerFirstName" name="customerFirstName" placeholder="First Name" required>
            </div>
            <div class="form-group col">
                <label for="customerLastName">Last Name</label>
                <input type="text" class="form-control" id="customerLastName" name="customerLastName" placeholder="Last Name" required>
            </div>
        </div>
		<div class="form-group">
			<label for="customerAddress">Address</label>
			<input type="text" class="form-control" id="customerAddress" name="customerAddress" placeholder="Address" required>
		</div>
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="customerCity">City</label>
                <input type="text" class="form-control" id="customerCity" name="customerCity" placeholder="City" required>
            </div>
            <div class="form-group col-md-4">
                <label for="customerState">State</label>
                <input type="text" class="form-control" id="customerState" name="customerState" placeholder="State" required>
            </div>
            <div class="form-group col-md-2">
                <label for="customerZipcode">Zipcode</label>
                <input type="number" class="form-control" id="customerZipcode" name="customerZipcode" placeholder="Zipcode" required>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col">
                <label for="customerTelephone">Telephone</label>
                <input type="tel" class="form-control" id="customerTelephone" name="customerTelephone" placeholder="Telephone number" pattern="[0-9]{10}" required>
            </div>
            <div class="form-group col">
                <label for="customerSSN">SSN (No Dashes)</label>
                <input type="text" class="form-control" id="customerSSN" name="customerSSN" placeholder="XXX-XX-XXXX"  pattern="[0-9]{9}" required>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col">
                <label for="customerCreditCard">Credit Card Number (No Dashes)</label>
                <input type="text" class="form-control" id="customerCreditCard" name="customerCreditCard" placeholder="XXXX-XXXX-XXXX-XXXX"  pattern="[0-9]{16}" required>
            </div>
            <div class="form-group col">
                <label for="customerRating">Rating (0-9)</label>
                <input type="text" class="form-control" id="customerRating" name="customerRating" placeholder="Rating" pattern="[0-9]{1}"required>
            </div>
        </div>

		<div class="form-group" style="text-align: center">
			<button type="submit" class="btn btn-success">Add</button>
		</div>
	</form>
</div>

<%@ include file="footer.jsp" %>