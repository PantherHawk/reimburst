package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Employee;
import beans.Expense;
import data.ExpenseService;

public class DecisionDelegate {

	public void handleDecision(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("made into decision delegate");
		HttpSession session = req.getSession();
		Employee employee = (Employee) session.getAttribute("user");
//		make sure employee is a manager
		if (employee.getHasManager() > 0) {
			res.sendRedirect("html/Login.html");
		} 
		String decision = req.getParameter("decision");
		BufferedReader expense_id = req.getReader();
//		System.out.println("id for expense approval----------->    " + );
		String str = expense_id.readLine();
		int id = Integer.parseInt(str.replaceAll("[^0-9]+", ""));
		System.out.println("id to make an expense ---- " + id);
		System.out.println("decision argument   " + decision);
//		String id_string = expense_id.readLine().substring(7, expense_id.readLine().)1
//		Expense expense = new ObjectMapper().readValue(id, Expense.class);
		Expense expense = new Expense(id);
		
		boolean approveSuccess = ExpenseService.getInstance().handleExpense(employee, expense, decision);
		
		System.out.println("approve success in decision delegate -------------- " + approveSuccess);
		
		if (approveSuccess) {
			res.setContentType("application/json");
			PrintWriter out = res.getWriter();
			String json = "{\"success\": \"ok\"}";
			out.println(json);
			out.flush();
		}
	}
}
