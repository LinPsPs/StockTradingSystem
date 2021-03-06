package resources;

import dao.EmployeeDao;
import dao.LoginDao;
import model.Employee;
import model.Location;
import model.Login;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class AddCustomerController
 */
public class AddEmployeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddEmployeeController() {
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
//		doGet(request, response);

		String email = request.getParameter("employeeEmail");
		String password = request.getParameter("employeePassword");
		String firstName = request.getParameter("employeeFirstName");
		String lastName = request.getParameter("employeeLastName");
		String address = request.getParameter("employeeAddress");
		String city = request.getParameter("employeeCity");
		String state= request.getParameter("employeeState");
		int zipcode = Integer.parseInt(request.getParameter("employeeZipcode"));
		String telephone = request.getParameter("employeeTelephone");
		String ssn = request.getParameter("employeeSSN");
		String startDate = request.getParameter("employeeStartDate");
		float hourlyRate = Float.parseFloat(request.getParameter("employeeHourlyRate"));
		String level = request.getParameter("role");


        Location location = new Location();
        location.setCity(city);
        location.setState(state);
        location.setZipCode(zipcode);

        Employee employee = new Employee();
		employee.setEmail(email);
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setAddress(address);
		employee.setStartDate(startDate);
		employee.setTelephone(telephone);
		employee.setEmployeeID(ssn);
		employee.setSsn(ssn);
        employee.setLocation(location);
		employee.setHourlyRate(hourlyRate);
		employee.setLevel(level);
		
		EmployeeDao employeeDao = new EmployeeDao();
		String result = employeeDao.addEmployee(employee);
		
		if(result.equals("success")) {
			Login login = new Login();
			login.setUsername(email);
			login.setPassword(password);
			login.setRole("customerRepresentative");
			LoginDao loginDao = new LoginDao();
			String loginResult = loginDao.addUser(login);
			if(loginResult.equals("success")) {
				response.sendRedirect("managerHome.jsp?status=addEmployeeSuccess");
			}
			else {
				response.sendRedirect("viewAddEmployee.jsp?status=error");
			}
		}
		else {
			response.sendRedirect("viewAddEmployee.jsp?status=error");
		}

		
	}

}
