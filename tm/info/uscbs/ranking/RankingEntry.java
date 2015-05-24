package tm.info.uscbs.ranking;

public class RankingEntry
{
	private int m_rankingPosition;
	private int m_rankingValue;
	private int m_rankingPlayerID;
	private int m_rankingSum;
	private int m_rankingGame1;
	private int m_rankingGame2;
	private int m_rankingGame3;
	private int m_rankingGame4;
	private int m_rankingGame5;
	
	/**
	* Constructor for a new RankingEntry
	*/
	public RankingEntry (int rankingPosition, int rankingValue, int rankingPlayerID, int rankingSum, int rankingGame1, int rankingGame2, int rankingGame3, int rankingGame4, int rankingGame5)
	{
		m_rankingPosition = rankingPosition;
		m_rankingValue = rankingValue;
		m_rankingPlayerID = rankingPlayerID;
		m_rankingSum = rankingSum;
		m_rankingGame1 = rankingGame1;
		m_rankingGame2 = rankingGame2;
		m_rankingGame3 = rankingGame3;
		m_rankingGame4 = rankingGame4;
		m_rankingGame5 = rankingGame5;
	}
	
	public int getRankingPosition()
	{
		return m_rankingPosition;
	}	
	
	public int getRankingValue()
	{
		return m_rankingValue;
	}	
	
	public int getRankingPlayerID()
	{
		return m_rankingPlayerID;
	}	
	
	public int getRankingSum()
	{
		return m_rankingSum;
	}	
	
	public int getRankingGame1()
	{
		return m_rankingGame1;
	}	
	
	public int getRankingGame2()
	{
		return m_rankingGame2;
	}	
	
	public int getRankingGame3()
	{
		return m_rankingGame3;
	}	
	
	public int getRankingGame4()
	{
		return m_rankingGame4;
	}	
	
	public int getRankingGame5()
	{
		return m_rankingGame5;
	}	
}