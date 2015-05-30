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
		StringBuffer responseHTMLPage = new StringBuffer();
		// Set response content type
		response.setContentType("text/html");

		// retrieve POST Data
		String firstName = request.getParameter ("first_name");
		String lastName = request.getParameter ("last_name");
		int birthdayDay = Integer.parseInt(request.getParameter ("birthday_day"));
		int birthdayMonth = Integer.parseInt(request.getParameter ("birthday_month"));
		int birthdayYear = Integer.parseInt(request.getParameter ("birthday_year"));
		boolean sex = Integer.parseInt(request.getParameter("sex")) == 0?false:true;

		// Add Player to database
		myData.addPlayer(firstName, lastName, birthdayDay, birthdayMonth, birthdayYear, sex);
		
		// give Feedback to the user
		responseHTMLPage.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n");
		responseHTMLPage.append("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"\n");
		responseHTMLPage.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"de\" lang=\"de\">");
		responseHTMLPage.append("<head>");
		responseHTMLPage.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"/static/ranking.css\" />");
		responseHTMLPage.append("<title>Spieler zur USC Doppel-Rangliste hinzugefügt</title>");
		responseHTMLPage.append("</head><body>" + "<h1>" + firstName + " " + lastName + "</h1><br/>");
		responseHTMLPage.append("Geboren am " + birthdayDay + "." + birthdayMonth + "." + birthdayYear + " wurde der Datenbank hinzugefügt.<br/>");
		responseHTMLPage.append("Zurück zur <a href=\"./Rangliste.jsp\">Rangliste</a><br/>");
		responseHTMLPage.append("Neues <a href=\"./DoppelAufnehmen.jsp\">Doppel hinzufügen</a>");
		responseHTMLPage.append("</body>");
		
		PrintWriter out = response.getWriter();
		out.println(responseHTMLPage.toString());
	}

	public void destroy()
	{
		// do nothing.
	}	
}