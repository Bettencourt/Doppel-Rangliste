interface DataInterface
{
	/**
	* @param playerName A String representation of the Player whos value should be returned
	* @returns The Value (in Points) of the given Player
	*/
	public int getPlayerValue (String playerName);
	
	/**
	* @param playerName A String representation of the Player whos value should be returned
	* @returns The position in the ranking the given player is currently at
	*/
	public int getPlayerPosition (String playerName);
	
	public boolean addPlayerValue (int newPlayerValue);
	public String getHTMLRankingRepresentation();
}