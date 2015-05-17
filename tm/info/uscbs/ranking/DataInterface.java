package tm.info.uscbs.ranking;

import java.util.Vector;

public interface DataInterface
{
	/**
	* Returns a specified player from the database
	*
	* @param playerID The unique identifier of the player to be returned
	* @returns The player object representation of the player with the given ID
	*/
	public Player getPlayer (int playerID);
	
	/**
	* Adds a new player to the database
	*
	* @param firstName the first name of the player to be added to the database
	* @param lastName the last name of the player to be added to the database
	* @param birthdayDay the day of month of the birthday of the player to be added to the database
	* @param birthdayMonth the month of birth of the player to be added to the database
	* @param birthdayYear the year of birth of the player to be added to the database
	* @param sex true if the player is male, false otherwise
	* @retruns The new Player object representing the new given player
	*/
	public Player addPlayer (String firstName, String lastName, int birthdayDay, int birthdayMonth, int birthdayYear, boolean sex);
	
	public DoublesMatch addDoublesMatch (int player1team1ID, int player2team1ID, int player1team2ID, int player2team2ID, int pointsTeam1Set1, int pointsTeam2Set1, int pointsTeam1Set2, int pointsTeam2Set2, int pointsTeam1Set3, int pointsTeam2Set3);
	
	public boolean addPlayerValue (int playerID, int newPlayerValue);
	
	public Vector<Player> getAllPlayers();
	public Vector<Match> getAllMaches();
}