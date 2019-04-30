package resources;

import dao.SignUpDao;
import model.SignUp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class LoginController
 */
public class SignUpController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * This method is called by the login button
		 * It receives the username and password values and sends them to LoginDao's login method for processing
		 * On Success (receiving "true" from login method), it redirects to the Home page
		 */


		String username = request.getParameter("username");
		String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName  = request.getParameter("lastName");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("zipcode");
        String phone = request.getParameter("phone");
        String ssn = request.getParameter("ssn");
		String role = request.getParameter("role");
        SignUpDao signUpDao = new SignUpDao();
		SignUp  signUp  = signUpDao.signUp(username,password,firstName,lastName,address,city,state,zip,phone,ssn,role);

		if(signUp != null) {
			response.sendRedirect("sign_up.jsp?status=true");
		}
		else {
			response.sendRedirect("sign_up.jsp?status=false");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
