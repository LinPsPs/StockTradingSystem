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
	This is the Edit Employee page
	This page displays fields to edit an Employee 
	The details are sent to the UpdateEmployeeController class in resources package
-->

	<div class="container">
	
	<h1>Edit Employee:</h1>
	<c:if test="${empty editEmployee}">
		<h3> Employee details not found! <h3/> 
	</c:if>
	<c:if test="${not empty editEmployee}">
	<form action="updateEmployee" method="POST">
        <div class="form-group">
			<label for="employeeID">Employee ID</label>
			<input type="text" class="form-control" id="employeeID" name="employeeID" placeholder="XXX-XX-XXXX" value=${editEmployee.employeeID} readonly>
		</div>
        <div class="form-group">
            <label for="employeeSSN">Employee SSN</label>
            <input type="text" class="form-control" id="employeeSSN" name="employeeSSN" placeholder="XXX-XX-XXXX" value=${editEmployee.ssn} readonly>
        </div>
	  <div class="form-group">
	    <label for="employeeEmail">Email address</label>
	    <input type="email" class="form-control" id="employeeEmail" name="employeeEmail" placeholder="Enter email" value=${editEmployee.email} required>
	  </div>
  	  <div class="form-group">
	    <label for="employeeFirstName">First Name</label>
	    <input type="text" class="form-control" id="employeeFirstName" name="employeeFirstName" placeholder="First Name" value="${editEmployee.firstName}" required>
	  </div>
  	  <div class="form-group">
	    <label for="employeeLastName">Last Name</label>
	    <input type="text" class="form-control" id="employeeLastName" name="employeeLastName" placeholder="Last Name" value="${editEmployee.lastName}" required>
	  </div>
   	  <div class="form-group">
	    <label for="employeeAddress">Address </label>
	    <input type="text" class="form-control" id="employeeAddress" name="employeeAddress" placeholder="Address" value="${editEmployee.address}" required>
	  </div>
   	  <div class="form-group">
	    <label for="employeeCity">City</label>
	    <input type="text" class="form-control" id="employeeCity" name="employeeCity" placeholder="City" value="${editEmployee.location.city}" required>
	  </div>
   	  <div class="form-group">
	    <label for="employeeState">State</label>
	    <input type="text" class="form-control" id="employeeState" name="employeeState" placeholder="State" value="${editEmployee.location.state}" required>
	  </div>
   	  <div class="form-group">
	    <label for="employeeZipcode">Zipcode</label>
	    <input type="number" class="form-control" id="employeeZipcode" name="employeeZipcode" placeholder="Zipcode" value=${editEmployee.location.zipCode} required>
	  </div>
   	  <div class="form-group">
	    <label for="employeeTelephone">Telephone</label>
	    <input type="tel" class="form-control" id="employeeTelephone" name="employeeTelephone" placeholder="Telephone number" pattern="[0-9]{10}" value=${editEmployee.telephone} required>
	  </div>
   	  <div class="form-group">
	    <label for="employeeStartDate">Start Date</label>
	    <input type="text" class="form-control" id="employeeStartDate" name="employeeStartDate" placeholder="YYYY-MM-DD" pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}"  value=${editEmployee.startDate} required>
	  </div>
   	  <div class="form-group">
	    <label for="employeeHourlyRate">Hourly Rate</label>
	    <input type="number" class="form-control" id="employeeHourlyRate" name="employeeHourlyRate" placeholder="Hourly Rate" value=${editEmployee.hourlyRate} required>
	  </div>
        <div class="form-group">
            <label for="employeeRole" >Role</label>
            <select class="form-control" name="role" id= "employeeRole" >
                <option value="manager">Manager</option>
                <option value="customerRepresentative">Customer Representative</option>
            </select>
        </div>
        <script>
            setSelectedIndex(document.getElementById("employeeRole"), "${editEmployee.level}" );
            function setSelectedIndex(s, valsearch)
            {
                // Loop through all the items in drop down list
                for (i = 0; i< s.options.length; i++)
                {
                    if (s.options[i].value == valsearch)
                    {
                        // Item is found. Set its property and exit
                        s.options[i].selected = true;

                        break;
                    }
                }
                return;
            }
        </script>
        <div class="form-group" style="text-align: center">
            <input type="submit" value="Update" class="btn btn-success"/>
            <input type="button" value="Cancel" class="btn btn-secondary" onclick="history.go(-1)"/>
        </div>
	</form>
	</c:if>
	</div>

<%@ include file="footer.jsp" %>