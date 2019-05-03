package resources;

import dao.EmployeeDao;
import model.Employee;
import model.Location;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class UpdateEmployeeController
 */
public class UpdateEmployeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateEmployeeController() {
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
		
		String email = request.getParameter("employeeEmail");
		String firstName = request.getParameter("employeeFirstName");
		String lastName = request.getParameter("employeeLastName");
		String address = request.getParameter("employeeAddress");
		String city = request.getParameter("employeeCity");
		String state= request.getParameter("employeeState");
		int zipcode = Integer.parseInt(request.getParameter("employeeZipcode"));
		String telephone = request.getParameter("employeeTelephone");
		String ssn = request.getParameter("employeeSSN");
		String employeeID = request.getParameter("employeeID");
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
		employee.setHourlyRate(hourlyRate);
        employee.setLocation(location);
        employee.setLevel(level);
        employee.setEmployeeID(employeeID);

		EmployeeDao employeeDao = new EmployeeDao();
		String result = employeeDao.editEmployee(employee);
		
		if(result.equals("success")) {
			response.sendRedirect("getEmployees?status=editEmployeeSuccess");
		}
		else {
			response.sendRedirect("editEmployee.jsp?status=error");
		}

	}

}
