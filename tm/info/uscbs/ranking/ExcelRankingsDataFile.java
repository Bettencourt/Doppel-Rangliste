package tm.info.uscbs.ranking;

import java.io.IOException;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.*;

public class ExcelRankingsDataFile implements DataInterface
{
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
		int newID = -1;
		int zeile = 0;
		
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
			
			// ToDo: add doubles match to rankings
			
			rankingsDataWritableWorkbook.write();
			deinitializeWritingFileConnection();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
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
	
	public boolean addPlayerValue (int playerID, int newPlayerValue)
	{
		return true;
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
}