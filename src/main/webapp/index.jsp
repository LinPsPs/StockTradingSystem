<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="heading" value="Home"/>
<%  String email = (String)session.getAttribute("email");
    String role = (String)session.getAttribute("role");
    //redirect to home page if already logged in
    if(email != null) {%>
        <%@ include file="header.jsp" %>
    <%}else{%>
        <%@ include file="header_default.jsp" %>
    <%}
%>
<!--<%@ include file="header_default.jsp" %>-->

<div class="container">
	<div class="bd-example">
		<div id="carouselExampleCaptions" class="carousel slide" data-ride="carousel">
			<ol class="carousel-indicators">
				<li data-target="#carouselExampleCaptions" data-slide-to="0" class="active"></li>
				<li data-target="#carouselExampleCaptions" data-slide-to="1"></li>
				<li data-target="#carouselExampleCaptions" data-slide-to="2"></li>
			</ol>
			<div class="carousel-inner">
				<div class="carousel-item active">
					<img src="./img/one.jpg" class="d-block w-100" alt="...">
					<div class="carousel-caption d-none d-md-block">
						<h5>Fast</h5>
						<p>Nulla vitae elit libero, a pharetra augue mollis interdum.</p>
					</div>
				</div>
				<div class="carousel-item">
					<img src="./img/two.jpg" class="d-block w-100" alt="...">
					<div class="carousel-caption d-none d-md-block">
						<h5 style="color: black">Reliable</h5>
						<p style="color: black">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
					</div>
				</div>
				<div class="carousel-item">
					<img src="./img/three.jpg" class="d-block w-100" alt="...">
					<div class="carousel-caption d-none d-md-block">
						<h5>Smart</h5>
						<p>Praesent commodo cursus magna, vel scelerisque nisl consectetur.</p>
					</div>
				</div>
			</div>
			<a class="carousel-control-prev" href="#carouselExampleCaptions" role="button" data-slide="prev">
				<span class="carousel-control-prev-icon" aria-hidden="true"></span>
				<span class="sr-only">Previous</span>
			</a>
			<a class="carousel-control-next" href="#carouselExampleCaptions" role="button" data-slide="next">
				<span class="carousel-control-next-icon" aria-hidden="true"></span>
				<span class="sr-only">Next</span>
			</a>
		</div>
	</div>
	<div class="jumbotron mt-2" style="text-align: center;background: white">
		<h1 class="display-4">Welcome to XYZ Online Stocking</h1>
		<p class="lead">Our platform provides most simple, fast, and secure online stocking trading experience.</p>
		<h1><img src="./img/xwyLogo.png"></h1>
		<hr class="my-4">
		<!--<p>It uses utility classes for typography and spacing to space content out within the larger container.</p>-->
		<div class="card-deck">
			<div class="card text-center">
				<h1><img class="img-thumbnail rounded-circle mt-3" src="./img/fast_connection.png" height="128" width="128" alt="Card image cap"></h1>
				<div class="card-body">
					<h5 class="card-title align-content-center">Fast Connection</h5>
				</div>
			</div>
			<div class="card text-center">
				<h1><img class="img-thumbnail rounded-circle mt-3" src="./img/uptime.png" height="128" width="128" alt="Card image cap"></h1>
				<div class="card-body">
					<h5 class="card-title align-content-center"> Guarantee 100% Uptime</h5>
				</div>
			</div>
			<div class="card text-center">
				<h1><img class="img-thumbnail rounded-circle mt-3" src="./img/smart.png" height="128" width="128" alt="Card image cap"></h1>
				<div class="card-body">
					<h5 class="card-title align-content-center">Smart Suggestion</h5>
				</div>
			</div>
		</div>
		<a class="btn btn-primary btn-lg mt-5" href="sign_up.jsp" role="button">Join Now</a>
	</div>

</div>
<%@ include file="footer.jsp" %>