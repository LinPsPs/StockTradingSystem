package resources;

import dao.StockDao;
import model.Stock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class AddCustomerController
 */
public class AddStocksController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddStocksController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String stockSymbol = request.getParameter("stockSymbol");
		String stockType = request.getParameter("stockType");
		String stockCompany = request.getParameter("stockCompany");
		String stockPrice =request.getParameter("stockPrice");
		String stockShare = request.getParameter("stockShare");
		StockDao dao = new StockDao();
		String result = dao.addStock(stockSymbol,stockType,stockCompany,Double.parseDouble(stockPrice),Integer.parseInt(stockShare));
		if (result!=null){
		    if (result.equals("success")){
                response.sendRedirect("managerHome.jsp?status=addStockSuccess");
            }else if (result.equals("exist")){
                response.sendRedirect("viewAddStock.jsp?status=exist");
            }else {
                response.sendRedirect("viewAddStock.jsp?status=error");
            }
        }else {
            response.sendRedirect("viewAddStock.jsp?status=error");
        }
	}

}
