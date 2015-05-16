package tm.info.uscbs.ranking;

class ExcelRankingsDataFile implements DataInterface
{
	private WritableWorkbook rankingsDataWorkbook;
	private WritableSheet playersSheet;
	
	/**
	* Open the Workbook.
	*/
	public ExcelFile ()
	{
		rankingsDataWorkbook = Workbook.createWorkbook(new File("DoppelRangliste.xls")); 
		
		// the playersSheet must already exist, so set the variable the the existing players sheet
		playersSheet = workbook.createSheet("Spieler", 0); 
	}
	
	/**
	* Returns a specified player from the database
	*
	* @param playerID The unique identifier of the player to be returned
	* @returns The player object representation of the player with the given ID
	*/
	public Player getPlayer (int playerID)
	{
		return new Player();
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
		// Go to the Players Tab
		// Find the last line
		// Find out the new ID
		int newID = -1;
		
		// Write Player Data into the columns
		Label label = new Label(0, 2, "A label record");
		sheet.addCell(label);

		Number number = new Number(3, 4, 3.1459);
		sheet.addCell(number); 
		
		rankingsDataWorkbook.write();
		rankingsDataWorkbook.close();
		
		// Create and return new Player object
		return new Player (newID, firstName, lastName, birthdayDay, birthdayMonth, birthdayYear, sex);
	}	
	
	public boolean addPlayerValue (int newPlayerValue)
	{
		return true;
	}
	
	public Vector getAllPlayers()
	{
		return new Vector();
	}
	
	public Vector getAllGames()
	{
		return new Vector();
	}
}