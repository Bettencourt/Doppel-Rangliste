package tm.info.uscbs.ranking;

import java.io.IOException;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.*;

public class ExcelRankingsDataFile implements DataInterface
{
	private final static Logger LOGGER = Logger.getLogger(ExcelRankingsDataFile.class.getName());

	// Constants of column-numbers for players sheet
	public static final int COLUMN_ID = 0;
	public static final int COLUMN_FIRST_NAME = 1;
	public static final int COLUMN_LAST_NAME = 2;
	public static final int COLUMN_BIRTHDAY_DAY = 3;
	public static final int COLUMN_BIRTHDAY_MONTH = 4;
	public static final int COLUMN_BIRTHDAY_YEAR = 5;
	public static final int COLUMN_SEX = 6;	

	// Constants of column-numbers for doubles sheet
	public static final int COLUMN_PLAYER1_TEAM1 = 1;
	public static final int COLUMN_PLAYER2_TEAM1 = 2;
	public static final int COLUMN_PLAYER1_TEAM2 = 3;
	public static final int COLUMN_PLAYER2_TEAM2 = 4;
	public static final int COLUMN_POINTS_TEAM1_SET1 = 5;
	public static final int COLUMN_POINTS_TEAM2_SET1 = 6;
	public static final int COLUMN_POINTS_TEAM1_SET2 = 7;
	public static final int COLUMN_POINTS_TEAM2_SET2 = 8;
	public static final int COLUMN_POINTS_TEAM1_SET3 = 9;
	public static final int COLUMN_POINTS_TEAM2_SET3 = 10;
	
	// Constants of column-numbers for rankings sheet
	public static final int COLUMN_RANK = 0;
	public static final int COLUMN_RANK_VALUE = 1;
	public static final int COLUMN_RANKING_PLAYER_ID = 2;
	public static final int COLUMN_RANKING_PLAYER_POINTS_SUM = 3;
	public static final int COLUMN_RANKING_PLAYER_POINTS_GAME1 = 4;
	public static final int COLUMN_RANKING_PLAYER_POINTS_GAME2 = 5;
	public static final int COLUMN_RANKING_PLAYER_POINTS_GAME3 = 6;
	public static final int COLUMN_RANKING_PLAYER_POINTS_GAME4 = 7;
	public static final int COLUMN_RANKING_PLAYER_POINTS_GAME5 = 8;
	
	private WritableWorkbook rankingsDataWritableWorkbook;
	private WritableSheet playersWritableSheet;
	private WritableSheet doublesWritableSheet;
	private WritableSheet rankingsWritableSheet;
	
	private Workbook rankingsDataReadableWorkbook;
	private Sheet playersReadableSheet;
	private Sheet doublesReadableSheet;
	private Sheet rankingsReadableSheet;
	
	/**
	* Open the Workbook.
	*/
	public ExcelRankingsDataFile ()
	{
		LOGGER.setLevel(Level.INFO);
	}

	private void initializeWritingFileConnection()
	{
		// if the file connection is closed, open it
		if (rankingsDataWritableWorkbook == null)
		{
			try
			{
				// read Workbook File
				rankingsDataReadableWorkbook = Workbook.getWorkbook(new File("DoppelRangliste.xls"));
				
				// get the players sheet
				playersReadableSheet = rankingsDataReadableWorkbook.getSheet("Spieler");
				doublesReadableSheet = rankingsDataReadableWorkbook.getSheet("Doppel-Begegnungen");
				
				// Create writable copy
				rankingsDataWritableWorkbook = Workbook.createWorkbook(new File("DoppelRangliste.xls"), rankingsDataReadableWorkbook); 
				
				// the playersWritableSheet must already exist, so set the variable the the existing players sheet
				playersWritableSheet = rankingsDataWritableWorkbook.getSheet("Spieler");
				doublesWritableSheet = rankingsDataWritableWorkbook.getSheet("Doppel-Begegnungen");
				rankingsWritableSheet = rankingsDataWritableWorkbook.getSheet("Rangliste");
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}

	private void initializeReadingFileConnection()
	{
		// if the file connection is closed, open it
		if (rankingsDataReadableWorkbook == null)
		{
			try
			{
				// read Workbook File
				rankingsDataReadableWorkbook = Workbook.getWorkbook(new File("DoppelRangliste.xls"));
				
				// get the players sheet
				playersReadableSheet = rankingsDataReadableWorkbook.getSheet("Spieler");
				doublesReadableSheet = rankingsDataReadableWorkbook.getSheet("Doppel-Begegnungen");
				rankingsReadableSheet = rankingsDataReadableWorkbook.getSheet("Rangliste");
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	private void deinitializeWritingFileConnection()
	{
		if (rankingsDataWritableWorkbook != null)
		{
			try
			{
				rankingsDataWritableWorkbook.close();
				rankingsDataReadableWorkbook.close();
				rankingsDataWritableWorkbook = null;
				rankingsDataReadableWorkbook = null;
				playersWritableSheet = null;
				playersReadableSheet = null;
				doublesWritableSheet = null;
				doublesReadableSheet = null;
				rankingsWritableSheet = null;
				rankingsReadableSheet = null;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	private void deinitializeReadingFileConnection()
	{
		if (rankingsDataReadableWorkbook != null)
		{
			try
			{
				if (rankingsDataWritableWorkbook != null)
				{
					deinitializeWritingFileConnection();
				}
				else
				{
					rankingsDataReadableWorkbook.close();
					rankingsDataReadableWorkbook = null;
					playersReadableSheet = null;
					doublesReadableSheet = null;
					rankingsReadableSheet = null;
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	/**
	* Returns a specified player from the database
	*
	* @param playerID The unique identifier of the player to be returned
	* @returns The player object representation of the player with the given ID
	*/
	public Player getPlayer (int playerID)
	{
		Iterator<Player> allPlayers = getAllPlayers().iterator();
		Player nextPlayer = null;
		
		while (allPlayers.hasNext())
		{
			nextPlayer = allPlayers.next();
			
			if (nextPlayer.getID() == playerID)
				return nextPlayer;
		}
		
		return null;
	}
	
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
	public Player addPlayer (String firstName, String lastName, int birthdayDay, int birthdayMonth, int birthdayYear, boolean sex)
	{
		int newID = -1;
		int zeile = 0;
		
		try
		{
			initializeWritingFileConnection();
			
			// ToDo: Check weather the given player already exists
			// Find the last line
			zeile = playersWritableSheet.getRows();
			
			// Find out the new ID
			newID = playersWritableSheet.getRows();
			
			// Initialy write Headers into the columns
			/*
			Label labelID = new Label(COLUMN_ID, zeile, "ID");
			playersWritableSheet.addCell(labelID);

			Label labelFirstName = new Label(COLUMN_FIRST_NAME, zeile, "Vorname");
			playersWritableSheet.addCell(labelFirstName);

			Label labelLastName = new Label(COLUMN_LAST_NAME, zeile, "Nachname");
			playersWritableSheet.addCell(labelLastName);

			Label labelBirthdayDay = new Label(COLUMN_BIRTHDAY_DAY, zeile, "Tag");
			playersWritableSheet.addCell(labelBirthdayDay);

			Label labelBirthdayMonth = new Label(COLUMN_BIRTHDAY_MONTH, zeile, "Monat");
			playersWritableSheet.addCell(labelBirthdayMonth);

			Label labelBirthdayYear = new Label(COLUMN_BIRTHDAY_YEAR, zeile, "Jahr");
			playersWritableSheet.addCell(labelBirthdayYear);

			Label labelSex = new Label(COLUMN_SEX, zeile, "Geschlecht");
			playersWritableSheet.addCell(labelSex);
			*/
			
			// Write Player Data into the columns
			jxl.write.Number numberPlayerID = new jxl.write.Number(COLUMN_ID, zeile, newID);
			playersWritableSheet.addCell(numberPlayerID);
			
			Label labelPlayerFirstName = new Label(COLUMN_FIRST_NAME, zeile, firstName);
			playersWritableSheet.addCell(labelPlayerFirstName);

			Label labelPlayerLastName = new Label(COLUMN_LAST_NAME, zeile, lastName);
			playersWritableSheet.addCell(labelPlayerLastName);

			jxl.write.Number numberBirthdayDay = new jxl.write.Number(COLUMN_BIRTHDAY_DAY, zeile, birthdayDay);
			playersWritableSheet.addCell(numberBirthdayDay);
			
			jxl.write.Number numberBirthdayMonth = new jxl.write.Number(COLUMN_BIRTHDAY_MONTH, zeile, birthdayMonth);
			playersWritableSheet.addCell(numberBirthdayMonth);
			
			jxl.write.Number numberBirthdayYear = new jxl.write.Number(COLUMN_BIRTHDAY_YEAR, zeile, birthdayYear);
			playersWritableSheet.addCell(numberBirthdayYear);
			
			jxl.write.Number numberSex = new jxl.write.Number(COLUMN_SEX, zeile, sex?1:0);
			playersWritableSheet.addCell(numberSex);			
			
			// ToDo: Add player to rankings
			addPlayerToRankings (newID);
			
			rankingsDataWritableWorkbook.write();
			deinitializeWritingFileConnection();
		}		
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		// Create and return new Player object
		return new Player (newID, firstName, lastName, birthdayDay, birthdayMonth, birthdayYear, sex);
	}	
	
	public DoublesMatch addDoublesMatch (int player1team1ID, int player2team1ID, int player1team2ID, int player2team2ID, int pointsTeam1Set1, int pointsTeam2Set1, int pointsTeam1Set2, int pointsTeam2Set2, int pointsTeam1Set3, int pointsTeam2Set3)
	{
		LOGGER.fine ("Adding DoublesMatch for Players player1team1 (" + getPlayer(player1team1ID).getFullName() + "), player2team1 (" + getPlayer(player2team1ID).getFullName() + "), player1team2 (" + getPlayer(player1team2ID).getFullName() + "), player2team2 (" + getPlayer(player2team2ID).getFullName() + ")");
		
		int newID = -1;
		int zeile = 0;
		DoublesMatch returnDoublesMatch;
		
		try
		{
			initializeWritingFileConnection();

			// Initialy write Headers into the columns
			/*
			Label labelID = new Label(COLUMN_ID, 0, "ID");
			doublesWritableSheet.addCell(labelID);

			Label labelPlayer1Team1 = new Label(COLUMN_PLAYER1_TEAM1, 0, "Spieler 1, Team 1");
			doublesWritableSheet.addCell(labelPlayer1Team1);

			Label labelPlayer2Team1 = new Label(COLUMN_PLAYER2_TEAM1, 0, "Spieler 2, Team 2");
			doublesWritableSheet.addCell(labelPlayer2Team1);

			Label labelPlayer1Team2 = new Label(COLUMN_PLAYER1_TEAM2, 0, "Spieler 1, Team 2");
			doublesWritableSheet.addCell(labelPlayer1Team2);

			Label labelPlayer2Team2 = new Label(COLUMN_PLAYER2_TEAM2, 0, "Spieler 2, Team 2");
			doublesWritableSheet.addCell(labelPlayer2Team2);

			Label labelPointsTeam1Set1 = new Label(COLUMN_POINTS_TEAM1_SET1, 0, "Punkte Team 1, Satz 1");
			doublesWritableSheet.addCell(labelPointsTeam1Set1);

			Label labelPointsTeam2Set1 = new Label(COLUMN_POINTS_TEAM2_SET1, 0, "Punkte Team 2, Satz 1");
			doublesWritableSheet.addCell(labelPointsTeam2Set1);

			Label labelPointsTeam1Set2 = new Label(COLUMN_POINTS_TEAM1_SET2, 0, "Punkte Team 1, Satz 2");
			doublesWritableSheet.addCell(labelPointsTeam1Set2);

			Label labelPointsTeam2Set2 = new Label(COLUMN_POINTS_TEAM2_SET2, 0, "Punkte Team 2, Satz 2");
			doublesWritableSheet.addCell(labelPointsTeam2Set2);

			Label labelPointsTeam1Set3 = new Label(COLUMN_POINTS_TEAM1_SET3, 0, "Punkte Team 1, Satz 3");
			doublesWritableSheet.addCell(labelPointsTeam1Set3);

			Label labelPointsTeam2Set3 = new Label(COLUMN_POINTS_TEAM2_SET3, 0, "Punkte Team 2, Satz 3");
			doublesWritableSheet.addCell(labelPointsTeam2Set3);
			*/

			// ToDo: Check weather the given match already exists
			// Find the last line
			zeile = doublesWritableSheet.getRows();
			
			// Find out the new ID
			newID = doublesWritableSheet.getRows();
			
			// Write Match Data into the columns
			jxl.write.Number numberMatchID = new jxl.write.Number(COLUMN_ID, zeile, newID);
			doublesWritableSheet.addCell(numberMatchID);
			
			jxl.write.Number numberPlayer1team1 = new jxl.write.Number(COLUMN_PLAYER1_TEAM1, zeile, player1team1ID);
			doublesWritableSheet.addCell(numberPlayer1team1);

			jxl.write.Number numberPlayer2team1 = new jxl.write.Number(COLUMN_PLAYER2_TEAM1, zeile, player2team1ID);
			doublesWritableSheet.addCell(numberPlayer2team1);

			jxl.write.Number numberPlayer1team2 = new jxl.write.Number(COLUMN_PLAYER1_TEAM2, zeile, player1team2ID);
			doublesWritableSheet.addCell(numberPlayer1team2);

			jxl.write.Number numberPlayer2team2 = new jxl.write.Number(COLUMN_PLAYER2_TEAM2, zeile, player2team2ID);
			doublesWritableSheet.addCell(numberPlayer2team2);

			jxl.write.Number numberPointsTeam1Set1 = new jxl.write.Number(COLUMN_POINTS_TEAM1_SET1, zeile, pointsTeam1Set1);
			doublesWritableSheet.addCell(numberPointsTeam1Set1);

			jxl.write.Number numberPointsTeam2Set1 = new jxl.write.Number(COLUMN_POINTS_TEAM2_SET1, zeile, pointsTeam2Set1);
			doublesWritableSheet.addCell(numberPointsTeam2Set1);

			jxl.write.Number numberPointsTeam1Set2 = new jxl.write.Number(COLUMN_POINTS_TEAM1_SET2, zeile, pointsTeam1Set2);
			doublesWritableSheet.addCell(numberPointsTeam1Set2);

			jxl.write.Number numberPointsTeam2Set2 = new jxl.write.Number(COLUMN_POINTS_TEAM2_SET2, zeile, pointsTeam2Set2);
			doublesWritableSheet.addCell(numberPointsTeam2Set2);

			jxl.write.Number numberPointsTeam1Set3 = new jxl.write.Number(COLUMN_POINTS_TEAM1_SET3, zeile, pointsTeam1Set3);
			doublesWritableSheet.addCell(numberPointsTeam1Set3);

			jxl.write.Number numberPointsTeam2Set3 = new jxl.write.Number(COLUMN_POINTS_TEAM2_SET3, zeile, pointsTeam2Set3);
			doublesWritableSheet.addCell(numberPointsTeam2Set3);
						
			rankingsDataWritableWorkbook.write();
			deinitializeWritingFileConnection();						
			
			LOGGER.finer("Written new match into Excel-File.");			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		// add doubles match to rankings
		int player1team1value = getPlayerValue (player1team1ID);
		int player2team1value = getPlayerValue (player2team1ID);
		int player1team2value = getPlayerValue (player1team2ID);
		int player2team2value = getPlayerValue (player2team2ID);
		
		int team1Value = player1team1value + player2team1value;
		int team2Value = player1team2value + player2team2value;
		
		LOGGER.finer("Team1Value is: " + team1Value);
		LOGGER.finer("Team2Value is: " + team2Value);
		
		int winnerValue = team1Value <= team2Value?team1Value:team2Value;
		int looserValue = team1Value > team2Value?team1Value:team2Value;
		
		boolean team1isWinner = false;
		
		if (pointsTeam1Set1 > pointsTeam2Set1)
			if (pointsTeam1Set2 > pointsTeam2Set2)
				team1isWinner = true;
			else
				if (pointsTeam1Set3 > pointsTeam2Set3)
					team1isWinner = true;
					
		if (team1isWinner)
		{
			LOGGER.finer("Team1 is winner and should therefore get the winnerValue of " + winnerValue);
			LOGGER.finer("Team2 is looser and should therefore get the looserValue of " + looserValue);
			LOGGER.finer("Giving player 1 of team 1 the new value of " + winnerValue * player1team1value / team1Value);
			LOGGER.finer("Giving player 2 of team 1 the new value of " + winnerValue * player2team1value / team1Value);
			LOGGER.finer("Giving player 1 of team 2 the new value of " + looserValue * player1team2value / team2Value);
			LOGGER.finer("Giving player 2 of team 2 the new value of " + looserValue * player2team2value / team2Value);
			addPlayerValue(player1team1ID, winnerValue * player1team1value / team1Value);
			addPlayerValue(player2team1ID, winnerValue * player2team1value / team1Value);				
			addPlayerValue(player1team2ID, looserValue * player1team2value / team2Value);
			addPlayerValue(player2team2ID, looserValue * player2team2value / team2Value);			
		}
		else
		{
			LOGGER.finer("Team2 is winner and should therefore get the winnerValue of " + winnerValue);
			LOGGER.finer("Team1 is looser and should therefore get the looserValue of " + looserValue);
			LOGGER.finer("Giving player 1 of team 1 the new value of " + looserValue * player1team1value / team1Value);
			LOGGER.finer("Giving player 2 of team 1 the new value of " + looserValue * player2team1value / team1Value);
			LOGGER.finer("Giving player 1 of team 2 the new value of " + winnerValue * player1team2value / team2Value);
			LOGGER.finer("Giving player 2 of team 2 the new value of " + winnerValue * player2team2value / team2Value);
			addPlayerValue(player1team1ID, looserValue * player1team1value / team1Value);
			addPlayerValue(player2team1ID, looserValue * player2team1value / team1Value);				
			addPlayerValue(player1team2ID, winnerValue * player1team2value / team2Value);
			addPlayerValue(player2team2ID, winnerValue * player2team2value / team2Value);			
		}
		
		reorderRanking();
		
		// ToDo: return new DoublesMatch (newID, player1team1ID, player2team1ID, player1team2ID, player2team2ID, pointsTeam1Set1, pointsTeam2Set1, pointsTeam1Set2, pointsTeam2Set2, pointsTeam1Set3, pointsTeam2Set3);
		return new DoublesMatch();
	}
	
	private void addPlayerToRankings (int playerID) throws Exception
	{
		int row = 0;
		
		// Find the last line
		row = rankingsWritableSheet.getRows();
		
		// Write Match Data into the columns
		jxl.write.Number numberRank = new jxl.write.Number(COLUMN_RANK, row, row);
		rankingsWritableSheet.addCell(numberRank);

		jxl.write.Number numberRankValue = new jxl.write.Number(COLUMN_RANK_VALUE, row, row*10);
		rankingsWritableSheet.addCell(numberRankValue);

		jxl.write.Number numberRankingPlayerID = new jxl.write.Number(COLUMN_RANKING_PLAYER_ID, row, playerID);
		rankingsWritableSheet.addCell(numberRankingPlayerID);

		jxl.write.Number numberRankingPlayerPointsSum = new jxl.write.Number(COLUMN_RANKING_PLAYER_POINTS_SUM, row, row*50);
		rankingsWritableSheet.addCell(numberRankingPlayerPointsSum);

		jxl.write.Number numberRankingPlayerPointsGame1 = new jxl.write.Number(COLUMN_RANKING_PLAYER_POINTS_GAME1, row, row*10);
		rankingsWritableSheet.addCell(numberRankingPlayerPointsGame1);

		jxl.write.Number numberRankingPlayerPointsGame2 = new jxl.write.Number(COLUMN_RANKING_PLAYER_POINTS_GAME2, row, row*10);
		rankingsWritableSheet.addCell(numberRankingPlayerPointsGame2);

		jxl.write.Number numberRankingPlayerPointsGame3 = new jxl.write.Number(COLUMN_RANKING_PLAYER_POINTS_GAME3, row, row*10);
		rankingsWritableSheet.addCell(numberRankingPlayerPointsGame3);

		jxl.write.Number numberRankingPlayerPointsGame4 = new jxl.write.Number(COLUMN_RANKING_PLAYER_POINTS_GAME4, row, row*10);
		rankingsWritableSheet.addCell(numberRankingPlayerPointsGame4);

		jxl.write.Number numberRankingPlayerPointsGame5 = new jxl.write.Number(COLUMN_RANKING_PLAYER_POINTS_GAME5, row, row*10);
		rankingsWritableSheet.addCell(numberRankingPlayerPointsGame5);
	}
	
	private boolean addPlayerValue (int playerID, int newPlayerValue)
	{
		LOGGER.finer ("Trying to add new value " + newPlayerValue + " to player with playerID " + playerID);
		
		try
		{
			initializeWritingFileConnection();

			int lastRow = rankingsWritableSheet.getRows();
		
			for (int row = 1; row < lastRow; row++)
			{
				if (Integer.parseInt(rankingsWritableSheet.getCell (COLUMN_RANKING_PLAYER_ID, row).getContents()) == playerID)
				{
					LOGGER.finest ("Found player with ID " + playerID + " in row " + row);
					
					int secondRankingsValue = Integer.parseInt(rankingsWritableSheet.getCell (COLUMN_RANKING_PLAYER_POINTS_GAME2, row).getContents());
					int thirdRankingsValue = Integer.parseInt(rankingsWritableSheet.getCell (COLUMN_RANKING_PLAYER_POINTS_GAME3, row).getContents());
					int fourthRankingsValue = Integer.parseInt(rankingsWritableSheet.getCell (COLUMN_RANKING_PLAYER_POINTS_GAME4, row).getContents());
					int fifthRankingsValue = Integer.parseInt(rankingsWritableSheet.getCell (COLUMN_RANKING_PLAYER_POINTS_GAME5, row).getContents());
					
					LOGGER.finest ("New player values for player with ID " + playerID + " are: " + secondRankingsValue + ", " + thirdRankingsValue + ", " + fourthRankingsValue + ", " + fifthRankingsValue + ", " + newPlayerValue);
					
					jxl.write.Number newFirstValueNumber = (jxl.write.Number) rankingsWritableSheet.getWritableCell(COLUMN_RANKING_PLAYER_POINTS_GAME1, row);
					jxl.write.Number newSecondValueNumber = (jxl.write.Number) rankingsWritableSheet.getWritableCell(COLUMN_RANKING_PLAYER_POINTS_GAME2, row);
					jxl.write.Number newThirdValueNumber = (jxl.write.Number) rankingsWritableSheet.getWritableCell(COLUMN_RANKING_PLAYER_POINTS_GAME3, row);
					jxl.write.Number newFourthValueNumber = (jxl.write.Number) rankingsWritableSheet.getWritableCell(COLUMN_RANKING_PLAYER_POINTS_GAME4, row);
					jxl.write.Number newFifthValueNumber = (jxl.write.Number) rankingsWritableSheet.getWritableCell(COLUMN_RANKING_PLAYER_POINTS_GAME5, row);

					newFirstValueNumber.setValue (secondRankingsValue);
					newSecondValueNumber.setValue (thirdRankingsValue);
					newThirdValueNumber.setValue (fourthRankingsValue);
					newFourthValueNumber.setValue (fifthRankingsValue);
					newFifthValueNumber.setValue (newPlayerValue);
					
					jxl.write.Number newSumValueNumber = (jxl.write.Number) rankingsWritableSheet.getWritableCell(COLUMN_RANKING_PLAYER_POINTS_SUM, row);
					
					newSumValueNumber.setValue (newPlayerValue + secondRankingsValue + thirdRankingsValue + fourthRankingsValue + fifthRankingsValue);
				}
			}
		
			rankingsDataWritableWorkbook.write();
			deinitializeWritingFileConnection();						
			
			LOGGER.finer("Added new Player Value for Player with ID: " + playerID);			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}		
		
		return true;
	}
	
	private int getPlayerValue (int playerID)
	{
		try
		{
			initializeReadingFileConnection();

			int lastRow = rankingsReadableSheet.getRows();

			for (int row = 1; row < lastRow; row++)
			{
				if (Integer.parseInt(rankingsReadableSheet.getCell (COLUMN_RANKING_PLAYER_ID, row).getContents()) == playerID)
				{
					int returnValue = Integer.parseInt (rankingsReadableSheet.getCell (COLUMN_RANK_VALUE, row).getContents());

					deinitializeReadingFileConnection();
					
					LOGGER.fine("Returning " + returnValue + " player value for Player with ID: " + playerID);			
					
					return returnValue;
				}
			}
			
			deinitializeReadingFileConnection();
			
			LOGGER.warning("Did not find Player with ID: " + playerID + ". Therefore no player value can be returned");			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}		
		
		return -1;
	}
	
	private void reorderRanking()
	{
		try
		{
			initializeWritingFileConnection();

			// trivial bubble sort
			// TODO: implement faster algorithm
			int lastRow = rankingsWritableSheet.getRows();
		
			for (int row = 1; row < lastRow-1; row++)
			{
				// get the row's player's value
				int currentPlayersValue = Integer.parseInt(rankingsWritableSheet.getCell (COLUMN_RANKING_PLAYER_POINTS_SUM, row).getContents());
				int nextPlayersValue = Integer.parseInt(rankingsWritableSheet.getCell (COLUMN_RANKING_PLAYER_POINTS_SUM, row + 1).getContents());
				
				if (currentPlayersValue > nextPlayersValue)
				{
					LOGGER.finest ("Value of player in row " + row + " (" + currentPlayersValue + ") is bigger than the value of the player in the next row (" + nextPlayersValue + "). Therefore exchanging places.");
					
					// exchange places
					int currentPlayerID = Integer.parseInt(rankingsWritableSheet.getCell (COLUMN_RANKING_PLAYER_ID, row).getContents());
					int currentPlayerSumValue = Integer.parseInt(rankingsWritableSheet.getCell (COLUMN_RANKING_PLAYER_POINTS_SUM, row).getContents());
					int currentPlayerGame1Value = Integer.parseInt(rankingsWritableSheet.getCell (COLUMN_RANKING_PLAYER_POINTS_GAME1, row).getContents());
					int currentPlayerGame2Value = Integer.parseInt(rankingsWritableSheet.getCell (COLUMN_RANKING_PLAYER_POINTS_GAME2, row).getContents());
					int currentPlayerGame3Value = Integer.parseInt(rankingsWritableSheet.getCell (COLUMN_RANKING_PLAYER_POINTS_GAME3, row).getContents());
					int currentPlayerGame4Value = Integer.parseInt(rankingsWritableSheet.getCell (COLUMN_RANKING_PLAYER_POINTS_GAME4, row).getContents());
					int currentPlayerGame5Value = Integer.parseInt(rankingsWritableSheet.getCell (COLUMN_RANKING_PLAYER_POINTS_GAME5, row).getContents());

					int nextPlayerID = Integer.parseInt(rankingsWritableSheet.getCell (COLUMN_RANKING_PLAYER_ID, row+1).getContents());
					int nextPlayerSumValue = Integer.parseInt(rankingsWritableSheet.getCell (COLUMN_RANKING_PLAYER_POINTS_SUM, row+1).getContents());
					int nextPlayerGame1Value = Integer.parseInt(rankingsWritableSheet.getCell (COLUMN_RANKING_PLAYER_POINTS_GAME1, row+1).getContents());
					int nextPlayerGame2Value = Integer.parseInt(rankingsWritableSheet.getCell (COLUMN_RANKING_PLAYER_POINTS_GAME2, row+1).getContents());
					int nextPlayerGame3Value = Integer.parseInt(rankingsWritableSheet.getCell (COLUMN_RANKING_PLAYER_POINTS_GAME3, row+1).getContents());
					int nextPlayerGame4Value = Integer.parseInt(rankingsWritableSheet.getCell (COLUMN_RANKING_PLAYER_POINTS_GAME4, row+1).getContents());
					int nextPlayerGame5Value = Integer.parseInt(rankingsWritableSheet.getCell (COLUMN_RANKING_PLAYER_POINTS_GAME5, row+1).getContents());
					
					((jxl.write.Number) rankingsWritableSheet.getWritableCell(COLUMN_RANKING_PLAYER_ID, row)).setValue(nextPlayerID);
					((jxl.write.Number) rankingsWritableSheet.getWritableCell(COLUMN_RANKING_PLAYER_POINTS_SUM, row)).setValue(nextPlayerSumValue);
					((jxl.write.Number) rankingsWritableSheet.getWritableCell(COLUMN_RANKING_PLAYER_POINTS_GAME1, row)).setValue(nextPlayerGame1Value);
					((jxl.write.Number) rankingsWritableSheet.getWritableCell(COLUMN_RANKING_PLAYER_POINTS_GAME2, row)).setValue(nextPlayerGame2Value);
					((jxl.write.Number) rankingsWritableSheet.getWritableCell(COLUMN_RANKING_PLAYER_POINTS_GAME3, row)).setValue(nextPlayerGame3Value);
					((jxl.write.Number) rankingsWritableSheet.getWritableCell(COLUMN_RANKING_PLAYER_POINTS_GAME4, row)).setValue(nextPlayerGame4Value);
					((jxl.write.Number) rankingsWritableSheet.getWritableCell(COLUMN_RANKING_PLAYER_POINTS_GAME5, row)).setValue(nextPlayerGame5Value);
					
					((jxl.write.Number) rankingsWritableSheet.getWritableCell(COLUMN_RANKING_PLAYER_ID, row+1)).setValue(currentPlayerID);
					((jxl.write.Number) rankingsWritableSheet.getWritableCell(COLUMN_RANKING_PLAYER_POINTS_SUM, row+1)).setValue(currentPlayerSumValue);
					((jxl.write.Number) rankingsWritableSheet.getWritableCell(COLUMN_RANKING_PLAYER_POINTS_GAME1, row+1)).setValue(currentPlayerGame1Value);
					((jxl.write.Number) rankingsWritableSheet.getWritableCell(COLUMN_RANKING_PLAYER_POINTS_GAME2, row+1)).setValue(currentPlayerGame2Value);
					((jxl.write.Number) rankingsWritableSheet.getWritableCell(COLUMN_RANKING_PLAYER_POINTS_GAME3, row+1)).setValue(currentPlayerGame3Value);
					((jxl.write.Number) rankingsWritableSheet.getWritableCell(COLUMN_RANKING_PLAYER_POINTS_GAME4, row+1)).setValue(currentPlayerGame4Value);
					((jxl.write.Number) rankingsWritableSheet.getWritableCell(COLUMN_RANKING_PLAYER_POINTS_GAME5, row+1)).setValue(currentPlayerGame5Value);
					
					if (row == 1)
						row--;
					else
						row -= 2;
				}
			}			
			
			rankingsDataWritableWorkbook.write();					
			deinitializeWritingFileConnection();						
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}			
	}
	
	public Vector<Player> getAllPlayers()
	{
		Vector<Player> allPlayerVector = new Vector<Player>();
		
		int numberOfPlayers = 0;
		
		try
		{
			initializeReadingFileConnection();
			
			numberOfPlayers = playersReadableSheet.getRows() - 1;
			
			for (int currentPlayer=1; currentPlayer <= numberOfPlayers;currentPlayer++)
			{
				Cell a1 = playersReadableSheet.getCell(COLUMN_ID, currentPlayer);
				Cell a2 = playersReadableSheet.getCell(COLUMN_FIRST_NAME, currentPlayer);
				Cell a3 = playersReadableSheet.getCell(COLUMN_LAST_NAME, currentPlayer);
				Cell a4 = playersReadableSheet.getCell(COLUMN_BIRTHDAY_DAY, currentPlayer);
				Cell a5 = playersReadableSheet.getCell(COLUMN_BIRTHDAY_MONTH, currentPlayer);
				Cell a6 = playersReadableSheet.getCell(COLUMN_BIRTHDAY_YEAR, currentPlayer);
				Cell a7 = playersReadableSheet.getCell(COLUMN_SEX, currentPlayer);
			
				int playerID = Integer.parseInt(a1.getContents());
				String playerFirstName = a2.getContents();
				String playerLastName = a3.getContents();
				int playerBirthdayDay = Integer.parseInt(a4.getContents());
				int playerBirthdayMonth = Integer.parseInt(a5.getContents());
				int playerBirthdayYear = Integer.parseInt(a6.getContents());
				boolean playerSex = Integer.parseInt(a7.getContents())!= 0?true:false;
				
				allPlayerVector.add (new Player(playerID, playerFirstName, playerLastName, playerBirthdayDay, playerBirthdayMonth, playerBirthdayYear, playerSex));
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			deinitializeReadingFileConnection();
		}
		
		return allPlayerVector;
	}
	
	public Vector<Match> getAllMaches()
	{
		return new Vector<Match>();
	}	

	public Vector<RankingEntry> getRankingEntries()
	{
		Vector<RankingEntry> allRankingEntriesVector = new Vector<RankingEntry>();
		
		int numberOfRankingEntries = 0;
		
		try
		{
			initializeReadingFileConnection();
			
			numberOfRankingEntries = rankingsReadableSheet.getRows() - 1;
			
			for (int currentRankingEntry=1; currentRankingEntry <= numberOfRankingEntries;currentRankingEntry++)
			{
				Cell a1 = rankingsReadableSheet.getCell(COLUMN_RANK, currentRankingEntry);
				Cell a2 = rankingsReadableSheet.getCell(COLUMN_RANK_VALUE, currentRankingEntry);
				Cell a3 = rankingsReadableSheet.getCell(COLUMN_RANKING_PLAYER_ID, currentRankingEntry);
				Cell a4 = rankingsReadableSheet.getCell(COLUMN_RANKING_PLAYER_POINTS_SUM, currentRankingEntry);
				Cell a5 = rankingsReadableSheet.getCell(COLUMN_RANKING_PLAYER_POINTS_GAME1, currentRankingEntry);
				Cell a6 = rankingsReadableSheet.getCell(COLUMN_RANKING_PLAYER_POINTS_GAME2, currentRankingEntry);
				Cell a7 = rankingsReadableSheet.getCell(COLUMN_RANKING_PLAYER_POINTS_GAME3, currentRankingEntry);
				Cell a8 = rankingsReadableSheet.getCell(COLUMN_RANKING_PLAYER_POINTS_GAME4, currentRankingEntry);
				Cell a9 = rankingsReadableSheet.getCell(COLUMN_RANKING_PLAYER_POINTS_GAME5, currentRankingEntry);
			
				int rankingPosition = Integer.parseInt(a1.getContents());
				int rankingValue = Integer.parseInt(a2.getContents());
				int rankingPlayerID = Integer.parseInt(a3.getContents());
				int rankingSum = Integer.parseInt(a4.getContents());
				int rankingGame1 = Integer.parseInt(a5.getContents());
				int rankingGame2 = Integer.parseInt(a6.getContents());
				int rankingGame3 = Integer.parseInt(a7.getContents());
				int rankingGame4 = Integer.parseInt(a8.getContents());
				int rankingGame5 = Integer.parseInt(a9.getContents());
				
				allRankingEntriesVector.add (new RankingEntry(rankingPosition, rankingValue, rankingPlayerID, rankingSum, rankingGame1, rankingGame2, rankingGame3, rankingGame4, rankingGame5));
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			deinitializeReadingFileConnection();
		}
		
		return allRankingEntriesVector;	
	}
}