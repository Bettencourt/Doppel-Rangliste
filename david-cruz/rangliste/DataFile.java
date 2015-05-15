class DataFile implements DataInterface
{
	public int getPlayerValue (String playerName)
	{
		return 500;
	}
	
	public int getPlayerPosition (String playerName)
	{
		return 1;
	}
	
	public boolean addPlayerValue (int newPlayerValue)
	{
		return true;
	}
	
	public String getHTMLRankingRepresentation()
	{
		return "<head><title>Doppel-Rangliste</title></head><body><H1>Doppel-Rangliste</H1></body>";
	}
}