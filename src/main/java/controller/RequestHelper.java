package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import delegate.EmployeeDelegate;
import delegate.ExpensesDelegate;
import delegate.LoginDelegate;

public class RequestHelper {
	private LoginDelegate ld = new LoginDelegate();
	private ExpensesDelegate exd = new ExpensesDelegate();
	private EmployeeDelegate ed = new EmployeeDelegate();
	private DecisionDelegate dd = new DecisionDelegate();
	
	public void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("In request helper ------>" + req.getMethod());
		String switchString = req.getRequestURI().substring(req.getContextPath().length()+1);
		System.out.println("switch string:     " + switchString);
		while(switchString.indexOf("/") > 0) {
			switchString = switchString.substring(switchString.indexOf("/")+1);
			System.out.println("switch string:    " + switchString);
		}
//		post login for manager's info for dashboard, first fify expenses: .../api/manager/  
//		get the expenses for a specific user: .../api/manager/employee/id?q=10
//		post expense approval: .../api/manager/expense body: {Expense, Manager, Approve/Reject}
		
//		get the employee's info for home, first fifty expenses: .../api/employee/
		switch(switchString) {
		case "decision": dd.handleDecision(req, res); break;
		case "expenses": if ("GET".equals(req.getMethod())) {
			exd.getExpenses(req, res);
		} else {
			exd.postExpenses(req, res);
		}
		case "employee": if ("GET".equals(req.getMethod())) {
			ed.getAllEmployees(req, res);
		}; break;
		case "manager": /*TODO: serve the Manager assets*/; break;
		case "login": if ("POST".equals(req.getMethod())) {
			ld.login(req, res); break;
			/*TODO: handle login*/
		} else {
			System.out.println("Sending to login delegate for redirect.");
			ld.getPage(req, res);
			/*TODO: serve the login page*/
		} break;
		case "logout": ld.logout(req, res); break;
		default: break;
		}
	}
}
