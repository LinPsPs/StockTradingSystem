<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="heading" value="Home"/>
<%@ include file="header_default.jsp" %>
<style type="text/css">
	body{
		background-image: url("./img/login.jpg");
	}
</style>
<div class="container">
	<div class="row">
		<div class="col"></div>
	</div>
	<div class="row mt-lg-5">
		<div class="col"></div>
		<div class="col"></div>
		<div class="col ml-auto mt-lg-5">
			<div class="card">
				<div class=" mr-1 ml-1 ">
					<h2 style="text-align: center">Login</h2>
					<%
						String email = (String)session.getAttribute("email");
						String role = (String)session.getAttribute("role");

						//redirect to home page if already logged in
						if(email != null) {
							if(role.equals("manager")) {
								response.sendRedirect("managerHome.jsp");
							}
							else if(role.equals("customerRepresentative")) {
								response.sendRedirect("customerRepresentativeHome.jsp");
							}
							else {
								response.sendRedirect("home.jsp");
							}
						}

						String status = request.getParameter("status");
						if(status != null) {
							if(status.equals("false")) {%>
					<div class="alert alert-danger" role="alert" style="text-align: center">
						Invalid Username or Password or Role!
					</div>
					<%}
					else {%>
					<div class="alert alert-danger" role="alert" style="text-align: center">
						Some Error Occurred! Please Try Again!
					</div>
					<%}
					}
					%>
					<form action="login">
						<div class="form-group">
							<input type="email" class="form-control" name="username" placeholder="Username">
						</div>
						<div class="form-group">
							<input type="password" class="form-control" name="password" placeholder="Password">
						</div>
						<div class="form-group">
							<select class="form-control" name="role">
								<option value="customer">Customer</option>
								<option value="manager">Manager</option>
								<option value="customerRepresentative">Customer Representative</option>
							</select>
						</div>
						<div class="form-group" style="text-align: center;">
							<input type="submit" value="Login" class="btn btn-success"/>
						</div>
					</form>
				</div>

			</div>

		</div>


	</div>
	</div>


<%@ include file="footer.jsp" %>