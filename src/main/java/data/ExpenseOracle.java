package data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import beans.Employee;
import beans.Expense;
import util.ConnectionUtil;

public class ExpenseOracle implements ExpenseDao {

	final static Logger logger = Logger.getLogger(EmployeeOracle.class);
	
	private static ExpenseOracle expenseOracle;
	
	public static ExpenseOracle getInstance() {
		if (expenseOracle == null) {
			expenseOracle = new ExpenseOracle();
		}
		return expenseOracle;
	}
	
	private ExpenseOracle() {
		
	}
	
	@Override
	public List<Expense> selectAllExpenses() {
		int statementIndex = 0;
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM EXPENSES";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet result = stmt.executeQuery();
			List<Expense> allExpenses = new ArrayList<Expense>();
			while(result.next()) {
				allExpenses.add(new Expense(
						 result.getInt("ID"),
						 result.getFloat("AMOUNT"),
						 result.getString("NAME"),
						 result.getString("DESCRIPTION"),
						 result.getString("STATUS"),
						 result.getTimestamp("DATE_SUBMITTED"),
						 result.getTimestamp("DATE_HANDLED"),
						 result.getInt("EMPLOYEE_ID"),
						 result.getInt("TEAM_ID")
						));
			}
			return allExpenses;
		} catch(SQLException | ClassNotFoundException e) {
			logger.warn(e);
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@Override
	public List<Expense> selectAllExpensesForOneEmployee(int employeeId) {
		int statementIndex = 0;
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM EXPENSES WHERE EMPLOYEE_ID = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(++statementIndex, employeeId);
			ResultSet result = stmt.executeQuery();
			List<Expense> employeeExpenses = new ArrayList<Expense>();
			while(result.next()) {
				employeeExpenses.add(new Expense(
						 result.getInt("ID"),
						 result.getFloat("AMOUNT"),
						 result.getString("NAME"),
						 result.getString("DESCRIPTION"),
						 result.getString("STATUS"),
						 result.getTimestamp("DATE_SUBMITTED"),
						 result.getTimestamp("DATE_HANDLED"),
						 result.getInt("EMPLOYEE_ID"),
						 result.getInt("TEAM_ID")
						));
			}
			return employeeExpenses;
		} catch(SQLException | ClassNotFoundException e) {
			logger.warn(e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean insertExpense(Expense expense) {
		int statementIndex = 0;
		try (Connection conn = ConnectionUtil.getConnection()) {
//			amount in number, e_id in number, t_id in number, description in varchar2, name in varchar2
			String sql = "CALL ADD_EXPENSE(?, ?, ?, ?, ?)";
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setDouble(++statementIndex, expense.getAmount());
			stmt.setInt(++statementIndex, expense.getEmployeeId());
			stmt.setInt(++statementIndex, expense.getTeamId());
			stmt.setString(++statementIndex, expense.getDescription());
			stmt.setString(++statementIndex, expense.getName());
			
			int i = stmt.executeUpdate();
			if (i == 0) {
				logger.info("Added expense to DB.");
				return true;
			}
		} catch(SQLException | ClassNotFoundException e) {
			logger.warn(e);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean approveOrRejectExpense(Employee manager, Expense expense, String approveOrReject) {
		if (manager.getHasManager() > 0 ) {
			System.out.println("manager status?");
//			not a manager, only managers can approve expenses
			return false;
		}
		
		int statementIndex = 0;
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "CALL APPROVE_EXPENSE(?, ?, ?, ?)";
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setString(++statementIndex, approveOrReject);
			stmt.setInt(++statementIndex, expense.getId());
			stmt.setInt(++statementIndex, manager.getId());
			stmt.setString(++statementIndex, manager.getUsername());
			
			int i = stmt.executeUpdate();
			logger.info("Record inserted into DB Expenses table!");
			System.out.println("result of approve_expense call ----->" + i);
			if (i == 0) {
				return true;
			}
		} catch(SQLException | ClassNotFoundException e) {
			logger.warn(e);
			e.printStackTrace();
		}
		return false;
	}

}
