<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>

<div class="container">
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
    <h1>Add a new Employee:</h1>

    <form action="addEmployee" method="POST">
        <div class="form-row">
            <div class="form-group col">
                <label for="employeeEmail">Email address</label>
                <input type="email" class="form-control" id="employeeEmail" name="employeeEmail" placeholder="Enter email"
                       required>
            </div>
            <div class="form-group col">
                <label for="employeePassword">Password</label>
                <input type="password" class="form-control" id="employeePassword" name="employeePassword"
                       placeholder="Password" required>
            </div>
        </div>
       <div class="form-row">
           <div class="form-group col">
               <label for="employeeFirstName">First Name</label>
               <input type="text" class="form-control" id="employeeFirstName" name="employeeFirstName"
                      placeholder="First Name" required>
           </div>
           <div class="form-group col">
               <label for="employeeLastName">Last Name</label>
               <input type="text" class="form-control" id="employeeLastName" name="employeeLastName"
                      placeholder="Last Name" required>
           </div>
       </div>
        <div class="form-group">
            <label for="employeeAddress">Address</label>
            <input type="text" class="form-control" id="employeeAddress" name="employeeAddress" placeholder="Address"
                   required>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="employeeCity">City</label>
                <input type="text" class="form-control" id="employeeCity" name="employeeCity" placeholder="City" required>
            </div>
            <div class="form-group col-md-4">
                <label for="employeeState">State</label>
                <input type="text" class="form-control" id="employeeState" name="employeeState" placeholder="State"
                       required>
            </div>
            <div class="form-group col-md-2">
                <label for="employeeZipcode">Zipcode</label>
                <input type="number" class="form-control" id="employeeZipcode" name="employeeZipcode" placeholder="Zipcode"
                       required>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-4">
                <label for="employeeTelephone">Telephone</label>
                <input type="tel" class="form-control" id="employeeTelephone" name="employeeTelephone"
                       placeholder="Telephone number" pattern="[0-9]{10}" required>
            </div>
            <div class="form-group col-md-4">
                <label for="employeeSSN">SSN (No Dashes)</label>
                <input type="tel" class="form-control" id="employeeSSN" name="employeeSSN" placeholder="XXX-XX-XXXX"
                       pattern="[0-9]{9}" required>
            </div>
            <div class="form-group col-md-2">
                <label for="employeeStartDate">Start Date</label>
                <input type="tel" class="form-control" id="employeeStartDate" name="employeeStartDate"
                       placeholder="YYYY-MM-DD" pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}" required>
            </div>
            <div class="form-group col-md-2">
                <label for="employeeHourlyRate">Hourly Rate</label>
                <input type="number" class="form-control" id="employeeHourlyRate" name="employeeHourlyRate"
                       placeholder="Hourly Rate" required>
            </div>
        </div>
        <div class="form-group">
            <label >Role</label>
            <select class="form-control" name="role">
                <option value="manager">Manager</option>
                <option value="customerRepresentative">Customer Representative</option>
            </select>
        </div>
        <div class="form-group" style="text-align: center">
            <button type="submit" class="btn btn-success">Add</button>
        </div>
    </form>
</div>
<%@ include file="footer.jsp" %>
