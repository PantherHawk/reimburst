package delegate;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Employee;
import data.EmployeeService;

public class LoginDelegate {
	
	public void login(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		Employee authEmployee = null;
		try {
			authEmployee = new ObjectMapper().readValue(req.getReader(	), Employee.class);
			System.out.println("employee object: " + authEmployee);

			Employee login = EmployeeService.getInstance().login(authEmployee);
			System.out.println("login object ------- " + login);
			if(login.getId() < 1) {
				System.out.println("login failed, redirecting back to login.");
				res.sendRedirect("login");
			} else {
				System.out.println("login success! redirecting to home page.");
				HttpSession session = req.getSession();
				session.setAttribute("user", login);
				System.out.println("session:   " + session);
				if (login.getHasManager() > 0) {
//					send them to employee page
					res.sendRedirect("employee");
				}
				res.sendRedirect("home");
			}
			
		} catch(JsonParseException | JsonMappingException e) {
			e.printStackTrace();
			res.sendError(400, "You did not send JSON formatted payload.");
		} catch(IOException e) {
			res.sendError(500, "Something broke on our end, sorry!");
		} 
		
//		Employee login = EmployeeService.getInstance().login(new Employee(username, password));
//		if(login == null) {
//			resp.sendRedirect("login");
//		} else {
//			HttpSession session = req.getSession();
//			session.setAttribute("user", login);
//			resp.sendRedirect("home");
//		}
	}
	
	public void getPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if(session.getAttribute("user")==null) {
			System.out.println("Sending client to Login page.");
//			req.getRequestDispatcher("html/Login.html").forward(req,resp);
			resp.setStatus(300);
			resp.sendRedirect("/EmployeeReimbursement/html/Login.html");
		} else {
			resp.sendRedirect("home");
		}
	}
	
	public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.getSession().invalidate();
		resp.sendRedirect("login");
	}


}
