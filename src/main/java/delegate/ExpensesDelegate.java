package delegate;

import java.io.IOException;
import java.util.List;

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
		List<Expense> expenses = null;
		if (employee.getHasManager() > 0) {
//			get all expenses 
			expenses = ExpenseService.getInstance().getEveryExpense();
			ObjectMapper mapper = new ObjectMapper();
			res.setHeader("Content-Type", "application/json");
			mapper.writeValue(res.getOutputStream(), expenses);
		} else {
//			get expenses for one employee
			expenses = ExpenseService.getInstance().getEmployeeSpecificExpenses(employee.getId());
			ObjectMapper mapper = new ObjectMapper();
			res.setHeader("Content-Type", "application/json");
			mapper.writeValue(res.getOutputStream(), expenses);
		}
	}
}
