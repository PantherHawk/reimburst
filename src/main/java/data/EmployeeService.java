package data;

import java.util.List;

import org.apache.log4j.Logger;

import beans.Employee;

public class EmployeeService {
	
	final static Logger logger = Logger.getLogger(EmployeeService.class);
	
//	set up singleton
	private static EmployeeService employeeService;
	private EmployeeService() {
		
	}
	public static EmployeeService getInstance() {
		if (employeeService == null) {
			employeeService = new EmployeeService();
		}
		return employeeService;
	}
	
	public Employee getEmployee(Employee employee) {
		return EmployeeOracle.getInstance().select(employee);
	}
	
	public Employee login(Employee employee) {
		return EmployeeOracle.getInstance().select(employee);
	}
	
	public boolean makeNewEmployee(Employee employee) {
		return EmployeeOracle.getInstance().addEmployee(employee);
	}
	
	public List<Employee> getAllEmployees() {
		return EmployeeOracle.getInstance().selectAll();
	}
	
	public Employee editEmployee(Employee employee) {
		return EmployeeOracle.getInstance().updateEmployee(employee);
	}
}
