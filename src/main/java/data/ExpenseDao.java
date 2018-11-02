package data;

import java.util.List;

import beans.Employee;
import beans.Expense;

public interface ExpenseDao {
	public List<Expense> selectAllExpenses();
	public List<Expense> selectAllExpensesForOneEmployee(int employeeId);
	public boolean insertExpense(Expense expense);
	public boolean approveOrRejectExpense(Employee manager, Expense expense, String approveOrReject);
}
