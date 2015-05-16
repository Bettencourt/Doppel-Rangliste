package tm.info.uscbs.ranking;

import java.io.IOException;
import java.io.File;
import java.util.Vector;

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

	private WritableWorkbook rankingsDataWorkbook;
	private WritableSheet playersSheet;
	
	/**
	* Open the Workbook.
	*/
	public ExcelRankingsDataFile ()
	{
		initializeFileConnection();
	}

	private void initializeFileConnection()
	{
		// if the file connection is closed, open it
		try
		{
			// read Workbook File
			Workbook workbook = Workbook.getWorkbook(new File("DoppelRangliste.xls"));
			
			// Create writable copy
			rankingsDataWorkbook = Workbook.createWorkbook(new File("DoppelRangliste.xls"), workbook); 
			
			// the playersSheet must already exist, so set the variable the the existing players sheet
			playersSheet = rankingsDataWorkbook.getSheet("Spieler"); 
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
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
			initializeFileConnection();
			
			// Check weather the given player already exists
			// Find the last line
			zeile = playersSheet.getRows();
			
			// Find out the new ID
			newID = playersSheet.getRows();
			
			// Initialy write Headers into the columns
			/*
			Label labelID = new Label(COLUMN_ID, zeile, "ID");
			playersSheet.addCell(labelID);

			Label labelFirstName = new Label(COLUMN_FIRST_NAME, zeile, "Vorname");
			playersSheet.addCell(labelFirstName);

			Label labelLastName = new Label(COLUMN_LAST_NAME, zeile, "Nachname");
			playersSheet.addCell(labelLastName);

			Label labelBirthdayDay = new Label(COLUMN_BIRTHDAY_DAY, zeile, "Tag");
			playersSheet.addCell(labelBirthdayDay);

			Label labelBirthdayMonth = new Label(COLUMN_BIRTHDAY_MONTH, zeile, "Monat");
			playersSheet.addCell(labelBirthdayMonth);

			Label labelBirthdayYear = new Label(COLUMN_BIRTHDAY_YEAR, zeile, "Jahr");
			playersSheet.addCell(labelBirthdayYear);

			Label labelSex = new Label(COLUMN_SEX, zeile, "Geschlecht");
			playersSheet.addCell(labelSex);
			*/
			
			// Write Player Data into the columns
			jxl.write.Number numberPlayerID = new jxl.write.Number(COLUMN_ID, zeile, newID);
			playersSheet.addCell(numberPlayerID);
			
			Label labelPlayerFirstName = new Label(COLUMN_FIRST_NAME, zeile, firstName);
			playersSheet.addCell(labelPlayerFirstName);

			Label labelPlayerLastName = new Label(COLUMN_LAST_NAME, zeile, lastName);
			playersSheet.addCell(labelPlayerLastName);

			jxl.write.Number numberBirthdayDay = new jxl.write.Number(COLUMN_BIRTHDAY_DAY, zeile, birthdayDay);
			playersSheet.addCell(numberBirthdayDay);
			
			jxl.write.Number numberBirthdayMonth = new jxl.write.Number(COLUMN_BIRTHDAY_MONTH, zeile, birthdayMonth);
			playersSheet.addCell(numberBirthdayMonth);
			
			jxl.write.Number numberBirthdayYear = new jxl.write.Number(COLUMN_BIRTHDAY_YEAR, zeile, birthdayYear);
			playersSheet.addCell(numberBirthdayYear);
			
			jxl.write.Number numberSex = new jxl.write.Number(COLUMN_SEX, zeile, sex?1:0);
			playersSheet.addCell(numberSex);			
			
			rankingsDataWorkbook.write();
			rankingsDataWorkbook.close();
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
		return new Vector<Player>();
	}
	
	public Vector<Match> getAllMaches()
	{
		return new Vector<Match>();
	}
	
	public void finalize() throws Throwable
	{
		try
		{
			rankingsDataWorkbook.close();
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