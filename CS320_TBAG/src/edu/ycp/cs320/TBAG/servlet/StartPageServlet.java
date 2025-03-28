package edu.ycp.cs320.TBAG.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StartPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		   // Get the action parameter from the form submission
	       String action = req.getParameter("action");
	       if ("game".equals(action)) {
	           // Forward to AddNumbersServlet
	           req.getRequestDispatcher("/gamePage").forward(req, resp);
	       } else if ("multiply".equals(action)) {
	           // Forward to MultiplyNumbersServlet
	           req.getRequestDispatcher("/multiplyNumbers").forward(req, resp);
	       } else if ("guess".equals(action)) {
	           // Forward to GuessingGameServlet
	           req.getRequestDispatcher("/guessingGame").forward(req, resp);
	       } else {
	    	   
	    	   System.out.println("startPage Servlet: doGet");
	           // Default: forward to the index.jsp
	           req.getRequestDispatcher("/_view/startPage.jsp").forward(req, resp);
	       }
	}
}
