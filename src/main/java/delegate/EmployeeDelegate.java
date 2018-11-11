package delegate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Employee;
import data.EmployeeService;
import util.WebUtils;

public class EmployeeDelegate {
	
	public void getAllEmployees(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("Hit employee delegate...");
//		HttpSession session = req.getSession();
		
		System.out.println("Jsession id ?   " + req.getParameter("JSESSIONID"));
		Map<String, String> headers = WebUtils.getHeadersInfo(req);
		System.out.println("Request headers    --------  " + headers);
		String username = headers.get("username");
		String password = headers.get("password");
		Employee employee = EmployeeService.getInstance().login(new Employee(username, password));
		
//		System.out.println("Trying to make employee bean out of session data...");
//		System.out.println("test for user session in employee delegate --------------    " + session.getAttribute("user"));
//		Employee employee = (Employee)session.getAttribute("user");
		
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
	
	public void editEmployeeInfo(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		System.out.println("Jsession id ?   " + req.getParameter("JSESSIONID"));
		Map<String, String> headers = WebUtils.getHeadersInfo(req);
		System.out.println("Request headers    --------  " + headers);
		String username = headers.get("username");
		String password = headers.get("password");
//		read the post body, make an employee bean
		Employee employee = new ObjectMapper().readValue(req.getReader(	), Employee.class);
//		update employee info
		ObjectMapper mapper = new ObjectMapper();
		Employee updateSuccess = EmployeeService.getInstance().editEmployee(employee);
		if (updateSuccess != null) {
			System.out.println("update succes    " + updateSuccess);
			res.setHeader("Content-Type", "application/json");
			setAccessControlHeaders(res);
			mapper.writeValue(res.getOutputStream(), updateSuccess);
		} else {
			System.out.println("update failed...");
			Map<String, String> json = new HashMap<String, String>();
			json.put("message", "FAILED TO UPDATE USER INFO");
			res.setContentType("applicaiton/json");
			mapper.writeValue(res.getOutputStream(), json);
		}
	}
	
	private void setAccessControlHeaders(HttpServletResponse resp) {
	      resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8082");
	      resp.setHeader("Access-Control-Allow-Methods", "POST");
	}
}
