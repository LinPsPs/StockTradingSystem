<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Customer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page isELIgnored="false" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="header.jsp" %>
<!--
	This is the Edit Customer page
	This page displays fields to edit a Customer 
	The details are sent to the UpdateCustomerController class in resources package
-->


	<div class="container">
	
	<h1>Edit Customer:</h1>
	<c:if test="${empty editCustomer}">
		<h3> Customer details not found! <h3/> 
	</c:if>
	<c:if test="${not empty editCustomer}"> 	
	<form action="updateCustomer" method="POST">
	  <div class="form-group">
	    <label for="customerEmail">Email address</label>
	    <input type="email" class="form-control" id="customerEmail" name="customerEmail" placeholder="Enter email" value=${editCustomer.email} required>
	  </div>
  	  <div class="form-group">
	    <label for="customerFirstName">First Name</label>
	    <input type="text" class="form-control" id="customerFirstName" name="customerFirstName" placeholder="First Name" value="${editCustomer.firstName}" required>
	  </div>
  	  <div class="form-group">
	    <label for="customerLastName">Last Name</label>
	    <input type="text" class="form-control" id="customerLastName" name="customerLastName" placeholder="Last Name" value="${editCustomer.lastName}" required>
	  </div>
   	  <div class="form-group">
	    <label for="customerAddress">Address</label>
	    <input type="text" class="form-control" id="customerAddress" name="customerAddress" placeholder="Address" value="${editCustomer.address}" required>
	  </div>
   	  <div class="form-group">
	    <label for="customerCity">City</label>
	    <input type="text" class="form-control" id="customerCity" name="customerCity" placeholder="City" value="${editCustomer.location.city}" required>
	  </div>
   	  <div class="form-group">
	    <label for="customerState">State</label>
	    <input type="text" class="form-control" id="customerState" name="customerState" placeholder="State" value="${editCustomer.location.state}" required>
	  </div>
   	  <div class="form-group">
	    <label for="customerZipcode">Zipcode</label>
	    <input type="number" class="form-control" id="customerZipcode" name="customerZipcode" placeholder="Zipcode" value=${editCustomer.location.zipCode} required>
	  </div>
   	  <div class="form-group">
	    <label for="customerTelephone">Telephone</label>
	    <input type="tel" class="form-control" id="customerTelephone" name="customerTelephone" placeholder="Telephone number" value=${editCustomer.telephone} pattern="[0-9]{10}" required>
	  </div>
   	  <div class="form-group">
	    <label for="customerSSN">SSN (Customer ID)</label>
	    <input type="text" class="form-control" id="customerSSN" name="customerSSN" placeholder="XXX-XX-XXXX" value=${editCustomer.clientId} readonly>
	  </div>
   	  <div class="form-group">
	    <label for="customerCreditCard">Credit Card Number(No Dashes)</label>
	    <input type="text" class="form-control" id="customerCreditCard" name="customerCreditCard" placeholder="XXXX-XXXX-XXXX-XXXX" value=${editCustomer.creditCard} pattern="[0-9]{16}" required>
	  </div>
   	  <div class="form-group">
	    <label for="customerRating">Rating (0-9)</label>
	    <input type="text" class="form-control" id="customerRating" name="customerRating" placeholder="Hourly Rate" value=${editCustomer.rating} pattern="[0-9]{1}" required>
	  </div>
		<div class="form-group" style="text-align: center">
			<input type="submit" value="Update" class="btn btn-success"/>
			<input type="button" value="Cancel" class="btn btn-secondary" onclick="history.go(-1)"/>
		</div>

	</form>
	</c:if>
	</div>

<%@ include file="footer.jsp" %>