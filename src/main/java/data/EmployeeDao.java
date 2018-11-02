package data;

import java.util.List;

import beans.Employee;

public interface EmployeeDao {
	public Employee select(Employee employee);
	public boolean addEmployee(Employee employee);
	public List<Employee> selectAll();
	public Employee updateEmployee(Employee employee);
}
