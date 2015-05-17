package tm.info.uscbs.ranking;

import java.io.IOException;
import java.io.File;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.*;

public class ExcelRankingsDataFile implements DataInterface
{
	public static final int COLUMN_ID = 0;
	public static final int COLUMN_FIRST_NAME = 1;
	public static final int COLUMN_LAST_NAME = 2;
	public static final int COLUMN_BIRTHDAY_DAY = 3;
	public static final int COLUMN_BIRTHDAY_MONTH = 4;
	public static final int COLUMN_BIRTHDAY_YEAR = 5;
	public static final int COLUMN_SEX = 6;	

	private WritableWorkbook rankingsDataWritableWorkbook;
	private WritableSheet playersWritableSheet;
	
	private Workbook rankingsDataReadableWorkbook;
	private Sheet playersReadableSheet;
	
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
				
				// Create writable copy
				rankingsDataWritableWorkbook = Workbook.createWorkbook(new File("DoppelRangliste.xls"), rankingsDataReadableWorkbook); 
				
				// the playersWritableSheet must already exist, so set the variable the the existing players sheet
				playersWritableSheet = rankingsDataWritableWorkbook.getSheet("Spieler"); 
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
		return new Player(-1, "Max", "Mustermann", -1, -1, -1, false);
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
			
			// Check weather the given player already exists
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
			
			System.out.println ("There are " + numberOfPlayers + " players in the players list");
			
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
				
				System.out.println ("Added Player Nr. " + currentPlayer + " (" + playerFirstName + " " + playerLastName + ") to player vector");
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
	
	public void finalize() throws Throwable
	{
		try
		{
			rankingsDataWritableWorkbook.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			super.finalize();
		}	
	}
}