package tm.info.uscbs.ranking;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddPlayer extends HttpServlet
{
	/**
	* Initialization of the servlet. Loads Database connection or data from files.
	*/
	public void init() throws ServletException
	{
		// load the players and games history (from whatever data storage system is apropriate)
	}	
  
	public void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException
	{
		// Set response content type
		response.setContentType("text/html");

		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		out.println("<h1>" + request.getParameter(first_name) + " " + request.getParameter(last_name) + "</h1>");		
	}

	public void destroy()
	{
		// do nothing.
	}	
}