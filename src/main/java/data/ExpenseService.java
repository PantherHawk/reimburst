package data;

import java.util.List;

import beans.Employee;
import beans.Expense;

public class ExpenseService {
	
	private static ExpenseService expenseService;
	public static ExpenseService getInstance() {
		if (expenseService == null) {
			expenseService = new ExpenseService();
		}
		return expenseService;
	}
	private ExpenseService() {
		
	}
	
	public List<Expense> getEveryExpense() {
		return ExpenseOracle.getInstance().selectAllExpenses();
	}
	
	public List<Expense> getEmployeeSpecificExpenses(int employeeId){
		return ExpenseOracle.getInstance().selectAllExpensesForOneEmployee(employeeId);
	}
	
	public boolean addExpense(Expense expense) {
		return ExpenseOracle.getInstance().insertExpense(expense);
	}
	
	public boolean handleExpense(Employee manager, Expense expense, String approveOrReject) {
		return ExpenseOracle.getInstance().approveOrRejectExpense(manager, expense, approveOrReject);
	}
}
