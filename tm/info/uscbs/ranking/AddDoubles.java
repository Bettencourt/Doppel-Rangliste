package tm.info.uscbs.ranking;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddDoubles extends HttpServlet
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
		int player1team1ID = Integer.parseInt(request.getParameter ("player1team1"));
		int player2team1ID = Integer.parseInt(request.getParameter ("player2team1"));
		int player1team2ID = Integer.parseInt(request.getParameter ("player1team2"));
		int player2team2ID = Integer.parseInt(request.getParameter ("player2team2"));

		int pointsTeam1set1 = Integer.parseInt(request.getParameter ("points_team1_set1"));
		int pointsTeam2set1 = Integer.parseInt(request.getParameter ("points_team2_set1"));
		int pointsTeam1set2 = Integer.parseInt(request.getParameter ("points_team1_set2"));
		int pointsTeam2set2 = Integer.parseInt(request.getParameter ("points_team2_set2"));
		int pointsTeam1set3 = Integer.parseInt(request.getParameter ("points_team1_set3"));
		int pointsTeam2set3 = Integer.parseInt(request.getParameter ("points_team2_set3"));

		// ToDo: Check points in sets for plausibility
		
		// Add match to database
		myData.addDoublesMatch(player1team1ID, player2team1ID, player1team2ID, player2team2ID, pointsTeam1set1, pointsTeam2set1, pointsTeam1set2, pointsTeam2set2, pointsTeam1set3, pointsTeam2set3);

		// give Feedback to the user
		responseHTMLPage.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n");
		responseHTMLPage.append("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"\n");
		responseHTMLPage.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"de\" lang=\"de\">");
		responseHTMLPage.append("<head>");
		responseHTMLPage.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"/static/ranking.css\" />");
		responseHTMLPage.append("<title>Doppel zur USC Doppel-Rangliste hinzugefügt</title>");
		responseHTMLPage.append("</head><body>" + "<h1>Doppel hinzugefügt</h1><br/>");
		responseHTMLPage.append("Doppel zwischen " + myData.getPlayer(player1team1ID).getLastName() + "/" + myData.getPlayer(player2team1ID).getLastName() + " und " + myData.getPlayer(player1team2ID).getLastName() + "/" + myData.getPlayer(player2team2ID).getLastName() + " wurde der Datenbank hinzugefügt.<br/>");
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