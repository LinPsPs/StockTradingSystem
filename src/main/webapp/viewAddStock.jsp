<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="Set Stock Price" value="Home"/>
<%@ include file="header.jsp" %>

<div class="container">
    <h2>Set Stock Price:</h2>
        <%
        String status = request.getParameter("status");
        if(status != null) {
            if(status.equals("exist")) {%>
                <div class="alert alert-danger" role="alert" style="text-align: center">
                    Error! Stock Already Exist!
                </div>
        <%}else if (status.equals("error")){%>
                <div class="alert alert-danger" role="alert" style="text-align: center">
                    Some Error Occured! Please Try Again!
                </div>
    <%} else if (status.equals("notEnough")){%>
                <div class="alert alert-danger" role="alert" style="text-align: center">
                    Failed To Record Order! Not Enough Stcok To Place the Order!
                </div>
    <%}
    }%>
    <form action="addStock" method="POST">
        <div class="form-row">
            <div class="form-group col">
                <label for="stockSymbol">Stock Symbol:</label>
                <input class="form-control" type="text" id="stockSymbol" name="stockSymbol" placeholder="AAPL" required>
            </div>
            <div class="form-group col">
                <label for="stockType">Stock Type:</label>
                <input class="form-control" type="text" id="stockType" name="stockType" placeholder="Technology" required>
            </div>
        </div>
        <div class="form-group">
           <div class="form-group">
               <label for="stockCompany">Stock Company Name:</label>
               <input class="form-control" type="text" id="stockCompany" name="stockCompany" placeholder="Apple Inc." required>
           </div>
        </div>
        <div class="form-row">
            <div class="form-group col">
                <label for="stockPrice">Price</label>
                <input type="number" class="form-control" id="stockPrice" name="stockPrice" placeholder="New Price" required>
            </div>
            <div class="form-group">
                <label for="stockShare">Total Share</label>
                <input type="number" class="form-control" id="stockShare" name="stockShare" placeholder="10000" required>
            </div>
        </div>
        <div class="form-group text-center">
            <button type="submit" class="btn btn-primary">Add Stock</button>
        </div>

    </form>
</div>
<%@ include file="footer.jsp" %>