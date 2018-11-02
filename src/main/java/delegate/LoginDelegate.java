package delegate;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Employee;
import data.EmployeeService;

public class LoginDelegate {
	
	public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		BufferedReader payload = req.getReader();
		System.out.println("post body ----------> " + payload);
		
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
			req.getRequestDispatcher("static/login.html").forward(req,resp);
		} else {
			resp.sendRedirect("home");
		}
	}
	
	public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.getSession().invalidate();
		resp.sendRedirect("login");
	}


}
