package delegate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Employee;
import beans.Expense;
import data.ExpenseService;

public class ExpensesDelegate {
	public void getExpenses(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute("user");
		System.out.println("Employee bean in expense delegate made from session-------->" + employee);
		if (employee == null) {
			System.out.println("no session found in expense delegate, sending back to login...");
			RequestDispatcher dispatcher = req.getRequestDispatcher("/html/Login.html");
			dispatcher.forward(req, res);
//			res.sendError(404, "No session started.");
			return;
		}
		
		List<Expense> expenses = null;
		System.out.println("when expense array is null---->" + expenses);
		if (employee.getHasManager() < 1) {
			System.out.println("employee is a manager, so grabbing all the expenses for her...");
//			get all expenses 
			expenses = ExpenseService.getInstance().getEveryExpense();
			System.out.println("filled expense array from the DB----->" + expenses);
			ObjectMapper mapper = new ObjectMapper();
			res.setHeader("Content-Type", "application/json");
			mapper.writeValue(res.getOutputStream(), expenses);
		} else {
//			get expenses for one employee
			expenses = ExpenseService.getInstance().getEmployeeSpecificExpenses(employee.getId());
			System.out.println("filled expense array from the DB----->" + expenses);
			ObjectMapper mapper = new ObjectMapper();
			res.setHeader("Content-Type", "application/json");
			mapper.writeValue(res.getOutputStream(), expenses);
		}
	}
	public void makeNewExpense(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		TODO: fire add expense from expense DAO
	}

	public void postExpenses(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("hit postexpenses in expenses delegate.");
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute("user");
		System.out.println("Employee object in post expense ------> " + employee);
//		if employee is a manager, the post is to approve.
		if (employee.getHasManager() < 1) {
//			handle approve expense
			if (req.getParameterValues("decision") != null) {
				String approveOrReject = req.getParameter("decision");
				ObjectMapper mapper = new ObjectMapper();
				Expense expense = mapper.readValue(req.getReader(), Expense.class);
				boolean approveSuccess = ExpenseService.getInstance().handleExpense(employee, expense, approveOrReject);
				if (approveSuccess) {
					Map<String, Boolean> jsonMap = new HashMap<String, Boolean>();
					jsonMap.put(approveOrReject.toUpperCase(), approveSuccess);
					res.setHeader("Content-Type", "text/json");
					mapper.writeValue(res.getOutputStream(), jsonMap);
				}
			}
		}
		
	}
}
