package delegate;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Employee;
import data.EmployeeService;
import redis.clients.jedis.Jedis;
import util.WebUtils;

public class LoginDelegate {
	
	public void login(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		
		String page = "";
		
		Employee authEmployee = null;
		
//		res.setHeader("Access-Control-Allow-Origin", "*");
//        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//        res.setHeader("Access-Control-Max-Age", "3600");
//        res.setHeader("Access-Control-Allow-Headers", "X-PINGOTHER,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
//        res.addHeader("Access-Control-Expose-Headers", "xsrf-token");
        
		Jedis jedis = null;
		try {
//			jedis = WebUtils.pool.getResource();
			
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
				session.setMaxInactiveInterval(5*60*60);
				Cookie message = new Cookie("message", "Welcome");
				res.addCookie(message);
//				Throw the user object into the session
				session.setAttribute("user", login);
//				Redis session
//				jedis.getSet("user", authEmployee);
				System.out.println("session:   " + session);
//				If she's not a manager
				if (login.getHasManager() > 0) {
					System.out.println("Loggin in an employee...");
//					Fetch all their expenses
//					List<Expense> allTheirExpenses = new ArrayList<Expense>();
//					allTheirExpenses = ExpenseService.
//										getInstance().
//										getEmployeeSpecificExpenses(login.getId());
//					Send them the employee homepage
//					Throw the user object into the session
					session.setAttribute("user", login);
					System.out.println("session:   " + session.getAttribute("user"));
						ObjectMapper mapper = new ObjectMapper();
						res.setHeader("Content-Type", "application/json");
						res.setHeader("mode", "no-cors");
						mapper.writeValue(res.getOutputStream(), login);
//						RequestDispatcher dispatch = req.getRequestDispatcher("/html/Home.html");
//						dispatch.include(req, res);

//						mapper.writeValue(res.getOutputStream(), allTheirExpenses);

				} else {
					
					if (login.getHasManager() < 1) {
						System.out.println("logging in a manager...");
//					TODO: Get all the expenses for all the employees
						ObjectMapper mapper = new ObjectMapper();
						res.setHeader("Content-Type", "application/json");
						setAccessControlHeaders(res);
						mapper.writeValue(res.getOutputStream(), login);
					}
			   }

			}
		} catch(JsonParseException e) {
			e.printStackTrace();
			RequestDispatcher dispatch = req.getRequestDispatcher("/html/Error.html");
			dispatch.include(req, res);
		} catch(JsonMappingException e) {
			e.printStackTrace();
			RequestDispatcher dispatch = req.getRequestDispatcher("/html/Error.html");
			dispatch.include(req, res);
		} catch(IOException e) {
			e.printStackTrace();
			RequestDispatcher dispatch = req.getRequestDispatcher("/html/ServerError.html");
			dispatch.include(req, res);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
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
			Employee employee = (Employee) session.getAttribute("user");
			if (employee.getHasManager() < 1) {
//				send back manager object
				ObjectMapper mapper = new ObjectMapper();
				resp.setHeader("Content-Type", "application/json");
				mapper.writeValue(resp.getOutputStream(), employee);
			} else {
//				resp.sendRedirect("/EmployeeReimbursement/html/Home.html");
				ObjectMapper mapper = new ObjectMapper();
				resp.setHeader("Content-Type", "application/json");
				mapper.writeValue(resp.getOutputStream(), employee);
			}
		}
	}
	
	public void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();
		resp.sendRedirect("/");
//		resp.setContentType("application/json");
//		PrintWriter out = resp.getWriter();
//		String json = "{\"loggedIn\": \"false\", \"location\": \"\"\"}";
//		out.println(json);
//		out.flush();
	}
	
	private void setAccessControlHeaders(HttpServletResponse resp) {
	      resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8082");
	      resp.setHeader("Access-Control-Allow-Methods", "POST");
	  }
//	 protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
//         System.out.println("Adding CORS Headers ........................");        
//         res.setHeader("Access-Control-Allow-Origin", "*");
//         res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//         res.setHeader("Access-Control-Max-Age", "3600");
//         res.setHeader("Access-Control-Allow-Headers", "X-PINGOTHER,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
//         res.addHeader("Access-Control-Expose-Headers", "xsrf-token");
//         if ("OPTIONS".equals(req.getMethod())) {
//          res.setStatus(HttpServletResponse.SC_OK);
//         } else { 
//          chain.doFilter(req, res);
//         }        
//     }


}
