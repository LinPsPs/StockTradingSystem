<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="heading" value="Home"/>
<%@ include file="header.jsp" %>
<div class="container">
    <h2>Summary Listing</h2>
    <h3>Search Stock Symbol, Stock Type or Customer Name:</h3>
    <form action="getSummaryListing" class="mt-2">
        <input type="text" name="searchKeyword" placeholder="Search Stock Symbol or Stock Type or Customer Name"
               class="form-control"/>
        <div class="form-group text-center mt-3">
            <input type="submit" value="Search" class="btn btn-success"/>
        </div>
    </form>
</div>
<%@ include file="footer.jsp" %>
