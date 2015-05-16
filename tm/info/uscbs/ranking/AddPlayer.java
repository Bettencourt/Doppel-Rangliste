package tm.info.uscbs.ranking;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddPlayer extends HttpServlet
{
	DataInterface myData = new ExcelRankingsDataFile();

	/**
	* Initialization of the servlet. Loads Database connection or data from files.
	*/
	public void init() throws ServletException
	{
		// load the players and games history (from whatever data storage system is apropriate)
	}	
  
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Set response content type
		response.setContentType("text/html");

		// retrieve POST Data
		String firstName = request.getParameter ("first_name");
		String lastName = request.getParameter ("last_name");
		int birthdayDay = Integer.parseInt(request.getParameter ("birthday_day"));
		int birthdayMonth = Integer.parseInt(request.getParameter ("birthday_month"));
		int birthdayYear = Integer.parseInt(request.getParameter ("birthday_year"));
		boolean sex = Integer.parseInt(request.getParameter("sex")) == 0?false:true;
		
		// give Feedback to the user
		PrintWriter out = response.getWriter();
		out.println("<h1>" + firstName + " " + lastName + "</h1>");
		out.println("Geboren am " + birthdayDay + "." + birthdayMonth + "." + birthdayYear + " wurde der Datenbank hinzugefügt.");

		// Add Player to database
		myData.addPlayer(firstName, lastName, birthdayDay, birthdayMonth, birthdayYear, sex);
	}

	public void destroy()
	{
		// do nothing.
	}	
}