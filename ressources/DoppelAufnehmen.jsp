<%@ page import="tm.info.uscbs.ranking.*" %>
<%@ page import="java.util.Iterator" %>
<%! DataInterface myData = new ExcelRankingsDataFile(); %>
 
<html>
<head>
<title>Doppel aufnehmen</title>
</head>

<body>
	<form action="AddDoubles" method="POST">
		<HR2>Team 1</hr2>
		Player 1 <select name="player1team1">
		<%Iterator playerIterator = myData.getAllPlayers().iterator();%>
		<%while (playerIterator.hasNext())%>
		<%{%>
			<%Player nextPlayer = (Player)playerIterator.next();%>
			<option value="<%=nextPlayer.getID()%>"><%=nextPlayer.getFullName()%></option>
		<%}%>
		</select>
		<br />
		Player 2 <input type="text" name="player2team1">
		<br />
		<HR2>Team 2</hr2>
		Player 1 <input type="text" name="player1team2">
		<br />
		Player 2 <input type="text" name="player2team2">
		<br />		
		<input type="submit" value="Submit" />
	</form>
</body>
</html>