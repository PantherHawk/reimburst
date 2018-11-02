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
import util.ConnectionUtil;

public class EmployeeOracle implements EmployeeDao {
	
	final static Logger logger = Logger.getLogger(EmployeeOracle.class);	

	private static EmployeeOracle employeeOracle;
	
//	set up the singleton
	public static EmployeeDao getInstance() {
		if (employeeOracle == null) {
			employeeOracle = new EmployeeOracle();
		}
		return employeeOracle;
	}
	
	private EmployeeOracle() {
		
	}
	
	@Override
	public Employee select(Employee employee) {
		int statementIndex = 0;
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM EMPLOYEES WHERE USERNAME=? AND PASS=?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(++statementIndex, employee.getUsername());
			stmt.setInt(++statementIndex, employee.hashCode());
			
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				return new Employee(
						result.getInt("ID"),
						result.getString("FIRSTNAME"),
						result.getString("LASTNAME"),
						result.getString("USERNAME"),
						result.getInt("HAS_MANAGER"),
						result.getString("EMAIL"),
						result.getInt("TEAM_ID"),
						result.getString("INFO")
						);
			}
		} catch(SQLException | ClassNotFoundException e) {
			logger.warn(e);
			e.printStackTrace();
		}
		return new Employee();
	}

	@Override
	public boolean addEmployee(Employee employee) {
		int statementIndex = 0;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String procedure = "CALL ADD_EMPLOYEE(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			CallableStatement stmt = conn.prepareCall(procedure);
			stmt.setString(++statementIndex, employee.getFirstName());
			stmt.setString(++statementIndex, employee.getLastName());
			stmt.setString(++statementIndex, employee.getUsername());
			stmt.setString(++statementIndex, String.valueOf(employee.hashCode()));
			stmt.setInt(++statementIndex, employee.getHasManager());
			stmt.setString(++statementIndex, null);
			stmt.setInt(++statementIndex, 4);
			stmt.setString(++statementIndex, employee.getEmail());
			stmt.setString(++statementIndex, employee.getInfo());
			
			int i = stmt.executeUpdate();
			if (i == 0) {
				logger.info("Added new EMPLOYEE");
				return true;
			}
		} catch (SQLException | ClassNotFoundException e) {
			logger.warn(e);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Employee> selectAll() {
		int statemenIndex = 0;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM EMPLOYEES";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet result = stmt.executeQuery();
			
			List<Employee> employees = new ArrayList<Employee>();
			
			while(result.next()) {
				employees.add(new Employee(
						result.getInt("ID"),
						result.getString("FIRSTNAME"),
						result.getString("LASTNAME"),
						result.getString("USERNAME"),
						result.getInt("HAS_MANAGER"),
						result.getString("EMAIL"),
						result.getInt("TEAM_ID"),
						result.getString("INFO")
						));
			}
			System.out.println("all employees --------->" + employees);
			return employees;
		} catch (SQLException | ClassNotFoundException e) {
			logger.warn(e);
			e.printStackTrace();
		}
		
		return new ArrayList<>();
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		int statementIndex = 0;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE EMPLOYEES SET FIRSTNAME = ?, LASTNAME = ?, USERNAME = ?, PASS = ?, HAS_MANAGER = ?, EMAIL = ?, INFO = ? WHERE ID = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(++statementIndex, employee.getFirstName());
			stmt.setString(++statementIndex, employee.getLastName());
			stmt.setString(++statementIndex, employee.getUsername());
			stmt.setString(++statementIndex, employee.getPassword());
			stmt.setString(++statementIndex, employee.getEmail());
			stmt.setString(++statementIndex, employee.getInfo());
			stmt.setInt(++statementIndex, employee.getId());
			int i = stmt.executeUpdate();
			if (i == 0) {
				logger.info("Updated employee");
				return employee;
			}
 			
		} catch (SQLException | ClassNotFoundException e) {
			logger.warn(e);
			e.printStackTrace();
		}
		return null; 
	}

}
