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
    public void init() throws ServletException {
        super.init();
        controller = new GameEngine();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println("GamePage Servlet: doGet");    

        req.setAttribute("response", controller.setData());

        // Call JSP to generate the empty form
        req.getRequestDispatcher("/_view/gamePage.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        String response = null;
        
        // Get the "direction" parameter from the request
        String direction = req.getParameter("direction");
        
        // If the parameter is missing or empty, set a default message
        if (direction == null || direction.trim().isEmpty()) {
            direction = "None";
            response = "No direction entered.";
        } else {
            // Validate the direction input
            if (isValidDirection(direction)) {
                // Process the valid direction using your GameEngine
                response = controller.response(direction);
            } else {
                response = "Invalid input.";
            }
        }
        
        // If this is an AJAX request, return the response as plain text.
        if (req.getParameter("ajax") != null) {
            resp.setContentType("text/plain");
            resp.getWriter().write(response);
        } else {
            // Otherwise, forward back to the JSP.
            req.setAttribute("direction", direction);
            req.setAttribute("response", response);
            req.getRequestDispatcher("/_view/gamePage.jsp").forward(req, resp);
        }
    }
    
    // Utility method to validate the input direction.
    private boolean isValidDirection(String direction) {
        return direction.equalsIgnoreCase("north") ||
               direction.equalsIgnoreCase("east") ||
               direction.equalsIgnoreCase("south") ||
               direction.equalsIgnoreCase("west") ||
               direction.equalsIgnoreCase("up") ||
               direction.equalsIgnoreCase("down") ||
               direction.equalsIgnoreCase("pick up") ||
               direction.equalsIgnoreCase("search") ||
               direction.equalsIgnoreCase("check inventory");
    }
}
