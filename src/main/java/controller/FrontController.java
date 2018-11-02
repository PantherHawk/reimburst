package controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrontController extends HttpServlet {
	
//	private static FrontController frontController;
//	public static FrontController getInstance() {
//		if (frontController == null) {
//			frontController = new FrontController();
//		}
//		return frontController;
//	}
//	private FrontController() {
//		
//	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RequestHelper rh = new RequestHelper();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("GET intercepted by front controller");
		BufferedReader payload = req.getReader();
		System.out.println("request URI--------->" + req.getRequestURI());
		if (req.getRequestURI().substring(req.getContextPath().length()).startsWith("/static/")) {
//			super.doGet(req, res);
			RequestDispatcher view = req.getRequestDispatcher("html/splash.htm");
			view.forward(req, res);
		} else {
			rh.process(req, res);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
