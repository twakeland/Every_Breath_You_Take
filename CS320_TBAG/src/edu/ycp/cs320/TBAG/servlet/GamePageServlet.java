package edu.ycp.cs320.TBAG.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.TBAG.controller.GameEngine;

public class GamePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GameEngine controller;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("GamePage Servlet: doGet");	
		
		controller = new GameEngine();
		req.setAttribute("response", controller.setData());
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/gamePage.jsp").forward(req, resp);
	}
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
		String response = null;
		
        // Get the "direction" parameter from the request
        String direction = req.getParameter("direction");
        
        // If the parameter is missing, you can set a default value (optional)
        if(direction == null) {
            direction = "None";
        }
        else {
        	response = controller.response(direction);
        }
        
        // Set the direction as a request attribute so that it can be accessed in the JSP
        req.setAttribute("direction", direction);
        req.setAttribute("response", response);
        
        // Forward the request to the addNumbers JSP to display the result or do further processing
        req.getRequestDispatcher("/_view/gamePage.jsp").forward(req, resp);
    }

}
