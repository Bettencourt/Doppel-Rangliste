<%@ page import="tm.info.uscbs.ranking.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Vector" %>

<%! DataInterface myData = new ExcelRankingsDataFile(); %>
<%! Vector<Player> playerVector; %>
<%! Iterator<Player> playerIterator; %>

<html>
<head>
<title>Doppel aufnehmen</title>
</head>

<body>
	<form action="AddDoubles" method="POST">
		<HR2>Team 1:</hr2><br/>
		Player 1 <select name="player1team1">
		<%
		playerVector = myData.getAllPlayers();
		playerIterator = playerVector.iterator();
		
		while (playerIterator.hasNext())
		{
			Player nextPlayer = playerIterator.next();
			%>
			<option value="<%= nextPlayer.getID() %>"><%= nextPlayer.getFullName()%></option>
			<%
		}
		%>
		</select>
		<br />
		Player 2 <select name="player2team1">
		<%
		playerIterator = playerVector.iterator();
		
		while (playerIterator.hasNext())
		{
			Player nextPlayer = playerIterator.next();
			%>
			<option value="<%= nextPlayer.getID() %>"><%= nextPlayer.getFullName()%></option>
			<%
		}
		%>
		</select>
		<br />
		<HR2>Team 2:</hr2><br/>
		Player 1 <select name="player1team2">
		<%
		playerIterator = playerVector.iterator();
		
		while (playerIterator.hasNext())
		{
			Player nextPlayer = playerIterator.next();
			%>
			<option value="<%= nextPlayer.getID() %>"><%= nextPlayer.getFullName()%></option>
			<%
		}
		%>
		</select>
		<br />
		Player 2 <select name="player1team2">
		<%
		playerIterator = playerVector.iterator();
		
		while (playerIterator.hasNext())
		{
			Player nextPlayer = playerIterator.next();
			%>
			<option value="<%= nextPlayer.getID() %>"><%= nextPlayer.getFullName()%></option>
			<%
		}
		%>
		</select>
		<br />		
		<input type="submit" value="Submit" />
	</form>
</body>
</html>