package tm.info.uscbs.ranking;

import java.io.*;
import java.util.Vector;
import javax.servlet.*;
import javax.servlet.http.*;

public class Ranking extends HttpServlet
{
	// The players of the ranking
	private Vector players;
	
	/**
	* Initialization of the servlet. Loads Database connection or data from files.
	*/
	public void init() throws ServletException
	{
		// load the players and games history (from whatever data storage system is apropriate)
	}	
  
	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException
	{
		// Set response content type
		response.setContentType("text/html");

		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		out.println("<h1>USC Braunschweig Doppelrangliste</h1>");		
	}

	public void destroy()
	{
		// do nothing.
	}	

	/**
	* Updates the player database with a new game
	*
	* @param player1Team1ID The ID of the first player of team 1
	* @param player2Team1ID The ID of the second player of team 1
	* @param player1Team2ID The ID of the first player of team 2
	* @param player2Team2ID The ID of the second player of team 2
	* @param winnerteam true if team 1 won; false if team 2 won
	*/
	/*public synchronized void addGame (int player1Team1ID, int player2Team1ID, int player1Team2ID, int player2Team2ID, boolean winnerteam)
	{
		Player player1team1 = players.getPlayer(player1Team1ID);
		Player player2team1 = players.getPlayer(player2Team1ID);
		Player player1team2 = players.getPlayer(player1Team2ID);
		Player player2team2 = players.getPlayer(player2Team2ID);		
		
		int team1Value = getPlayerValue (player1Team1ID) + getPlayerValue (player2Team1ID);
		int team2Value = getPlayerValue (player1Team2ID) + getPlayerValue (player2Team2ID);
		int winnerValue = team1Value >= team2Value?team1Value:team2Value;
		int looserValue	= team1Value <= team2Value?team1Value:team2Value;
		
		if (winnerteam)
		{
			// team 1 won: calculate new Values for team members
			addPlayerValue (player1Team1ID, winnerValue*getPlayerValue (player1Team1ID)/team1Value);
			addPlayerValue (player2Team1ID, winnerValue*getPlayerValue (player2Team1ID)/team1Value);
		}
		else
		{
		}
		
		// add game to game database
	}*/
}