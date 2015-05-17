package tm.info.uscbs.ranking;

public class Player
{
	private int m_ID;
	private String m_firstName;
	private String m_lastName;
	private int m_birthdayDay;
	private int m_birthdayMonth;
	private int m_birthdayYear;
	private boolean m_sex;
	
	/**
	* Constructor for a new player
	*/
	public Player (int newID, String firstName, String lastName, int birthdayDay, int birthdayMonth, int birthdayYear, boolean sex)
	{
		m_ID = newID;
		m_firstName = firstName;
		m_lastName = lastName;
		m_birthdayDay = birthdayDay;
		m_birthdayMonth = birthdayMonth;
		m_birthdayYear = birthdayYear;
		m_sex = sex;
	}
	
	public String getFirstName()
	{
		return m_firstName;
	}
	
	public String getLastName()
	{
		return m_lastName;
	}
	
	public String getFullName()
	{
		return m_firstName + " " + m_lastName;
	}
	
	public int getID()
	{
		return m_ID;
	}
}