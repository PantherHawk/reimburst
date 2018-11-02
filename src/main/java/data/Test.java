package data;

import java.sql.Timestamp;
import java.util.List;

import beans.Employee;
import beans.Expense;

public class Test {
	public static void main(String[] args) {
//		make a new employee ---- SUCCESS!
//		Employee martianManhunter = new Employee("J'onn", "J'onzz", "martian_manhunter", "password", 0, "jonzz@mars.com", 4, "Helping earthlings, waiting for rescue.");
//		EmployeeService.getInstance().makeNewEmployee(martianManhunter);
//		select a employee ------- SUCCESS!
//		Employee selected = EmployeeService.getInstance().getEmployee(new Employee("martian_manhunter", "password"));
//		System.out.println("selected: ------> " + selected);
//		fail to get the employee when password wrong ---- SUCCESS!

//		login ---- SUCCESS!
//			select employee, returns employee
//			if employee.id > 0, success
//		Employee selected = EmployeeService.getInstance().getEmployee(new Employee("wonderwoman", "password"));
//		if (selected.getId() > 0) {
//			System.out.println("login success");
//		} else {
//			System.out.println("login failed");
//		}
//		update a employee ---- FAILING : (
		Employee martianManhunter = EmployeeService.getInstance().getEmployee(new Employee("martian_manhunter", "password"));
//		selected.setInfo("Fled Earth, looking for answers on Thanagar.");
//		System.out.println("selected: -------->" + selected);
//		EmployeeService.getInstance().editEmployee(selected);
		
//		get all employees ------ SUCCESS!
//		List<Employee> allEmployees = EmployeeService.getInstance().getAllEmployees();
//		System.out.println("All your employees =============> " + allEmployees);
		
//		-------------------EXPENSES TESTS--------------------------
//		add new expense --------- SUCCESS!
		Expense newOne = new Expense(2, 100, "Pizza", "JLA Bowling Night", "PENDING", null,
				null, 10, 4);
//		ExpenseService.getInstance().addExpense(newOne);newOne
		
//		get all expenses -------- SUCCESS!
//		List<Expense> allExpenses = ExpenseService.getInstance().getEveryExpense();
//		System.out.println("All the expenses--------------->" + allExpenses);
//		get all expenses for an employee ------- SUCCESS!
//		List<Expense> batmansExpenses = ExpenseService.getInstance().getEmployeeSpecificExpenses(10);
//		System.out.println("Batman's expenses --------> " + batmansExpenses);
//		approve expense
		boolean success = ExpenseService.getInstance().handleExpense(martianManhunter, newOne, "APPROVED");
		if (success) {
			System.out.println("success! batman's expense approved.");
		} else {
			System.out.println("nope.");
		}
	}
}
