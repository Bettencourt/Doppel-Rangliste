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
		<table border="0" cellpadding="5" cellspacing="5" align="center" valign="center">
			<tr>
			<td></td><td align="center"><H2>Team 1:</h2></td><td align="center"><H2>Team2:</H2></td>
			</tr>
			<tr>
			<td></td>
			<td>
				Player 1: <select name="player1team1">
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
				Player 2: <select name="player2team1">
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
			</td>
			<td>
				Player 1: <select name="player1team2">
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
				Player 2: <select name="player1team2">
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
			</td>
			</tr>
			<tr>
			<td>1. Satz</td><td align="center"><input type="text" name="points_team1_set1" /></td><td align="center"><input type="text" name="points_team2_set1" /></td>
			</tr>
			<tr>
			<td>2. Satz</td><td align="center"><input type="text" name="points_team1_set2" /></td><td align="center"><input type="text" name="points_team2_set2" /></td>
			</tr>
			<tr>
			<td>3. Satz</td><td align="center"><input type="text" name="points_team1_set3" /></td><td align="center"><input type="text" name="points_team2_set3" /></td>
			</tr>
			</table>
		<center><input type="submit" value="Submit" /></center>
	</form>
</body>
</html>