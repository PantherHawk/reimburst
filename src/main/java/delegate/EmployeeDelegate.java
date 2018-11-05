package delegate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Employee;
import data.EmployeeService;

public class EmployeeDelegate {
	
	public void getAllEmployees(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("Hit employee delegate...");
		HttpSession session = req.getSession();
		System.out.println("Trying to make employee bean out of session data...");
		Employee employee = (Employee)session.getAttribute("user");
		
		if (employee == null) {
			System.out.println("No session found at employee delegate.");
			res.sendError(404, "You have not yet logged in.");
			return;
		}
//		get the employees from the DB.
		List<Employee> allEmployees = new ArrayList<Employee>();
		allEmployees = EmployeeService.getInstance().getAllEmployees();
		System.out.println("employee list from db: " + allEmployees);
		
//		Unmarshall/ turn byte stream into JSON and send the employees to client
		ObjectMapper unmarshalling = new ObjectMapper();
		res.setHeader("Content-Type", "application/json");
		unmarshalling.writeValue(res.getOutputStream(), allEmployees);
	}
}
