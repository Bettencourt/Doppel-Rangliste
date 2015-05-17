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
		
		// give Feedback to the user
		PrintWriter out = response.getWriter();
		out.println("Doppel zwischen " + myData.getPlayer(player1team1ID).getLastName() + "/" + myData.getPlayer(player2team1ID).getLastName() + " und " + myData.getPlayer(player1team2ID).getLastName() + "/" + myData.getPlayer(player2team2ID).getLastName() + " wurde der Datenbank hinzugefügt.");

		// Add Player to database
		myData.addDoublesMatch(player1team1ID, player2team1ID, player1team2ID, player2team2ID, pointsTeam1set1, pointsTeam1set1, pointsTeam1set2, pointsTeam2set2, pointsTeam1set3, pointsTeam2set3);
	}

	public void destroy()
	{
		// do nothing.
	}	
}