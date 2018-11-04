package delegate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Employee;
import beans.Expense;
import data.EmployeeService;
import data.ExpenseService;

public class LoginDelegate {
	
	public void login(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
String page = "";
		
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
//				Make a new session
				HttpSession session = req.getSession();
//				Throw the user object into the session
				session.setAttribute("user", login);
				System.out.println("session:   " + session);
//				If she's not a manager
				if (login.getHasManager() > 0) {
//					Fetch all their expenses
					List<Expense> allTheirExpenses = new ArrayList<Expense>();
					allTheirExpenses = ExpenseService.
										getInstance().
										getEmployeeSpecificExpenses(login.getId());
//					Send them the employee homepage
						ObjectMapper mapper = new ObjectMapper();
						res.setHeader("Content-Type", "application/json");
						mapper.writeValue(res.getOutputStream(), login);
//						RequestDispatcher dispatch = req.getRequestDispatcher("/html/Home.html");
//						dispatch.include(req, res);

//						mapper.writeValue(res.getOutputStream(), allTheirExpenses);

				} else {
					
					if (login.getHasManager() == 0) {
//					TODO: Get all the expenses for all the employees
					}
			   }

			}
		} catch(JsonParseException | JsonMappingException e) {
			e.printStackTrace();
			RequestDispatcher dispatch = req.getRequestDispatcher("/html/Error.html");
			dispatch.include(req, res);
		} catch(IOException e) {
			e.printStackTrace();
			RequestDispatcher dispatch = req.getRequestDispatcher("/html/ServerError.html");
			dispatch.include(req, res);
		}
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