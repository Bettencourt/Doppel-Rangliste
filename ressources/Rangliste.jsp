<%@ page import="tm.info.uscbs.ranking.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Vector" %>

<%! DataInterface myData = new ExcelRankingsDataFile(); %>
<%! Vector<RankingEntry> rankingsVector; %>
<%! Iterator<RankingEntry> rankingsIterator; %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">

<head>
    <link rel="stylesheet" type="text/css" href="/static/ranking.css" />
    <title>USC Doppel-Rangliste</title>
</head>

<body>
    <div id="sidebar">
        
        <table border="0" cellpadding="5" width="100%">
          <colgroup><col width="1*" span="4"></colgroup>
          <tr>
            <td align="left">&nbsp; <!--<a href="/accounts/login/?next=/ranking/">Anmelden</a> --></td>
            <td align="center"><a href="/DoppelAufnehmen.jsp">Doppel aufnehmen</a></td>
            <td align="center"><a href="/SpielerHinzufuegen.html">Spieler hinzufügen</a></td>
            <td align="right">&nbsp; <!--<a href="/accounts/logout/">Abmelden</a>--></td>
          </tr>
          <tr>
            <td>&nbsp; <!--<a href="/ranking/">Übersicht</a>--></td>
            <td align="center">&nbsp; <!--<a href="/ranking/lastMatches/">Die letzten 30 Spiele</a>--></td>
            <td align="center">&nbsp; <!--<a href="/ranking/players/">Alphabetische Spielerliste</a> --> </td>
            <td align="right">&nbsp; <!--<a href="/ranking/pdf/">Rangliste als PDF</a>--></td>
          </tr>
        </table>
        
    </div>

    <div id="content" align="center">
    
        <h1>USC Rangliste</h1>
        
<!-- <p align="center">Stand: May 15, 2015</p>-->

<br>

<!-- <a href="/ranking/">zur normalen Ansicht</a> -->
<br>
<br>
<table border="1" cellpadding="10">
  <tr>
    <th>Platz</th>
    <th>Name</th>
    <th>Wert</th>
    <th>Summe</th>
    <th>Letzte Punkte<br>(alt, ..., neu)</th>
    <!-- <th>Quote</th>-->
    <!-- <th>Anzahl<br>Spiele</th>-->
  </tr>

	<%
	rankingsVector = myData.getRankingEntries();
	rankingsIterator = rankingsVector.iterator();
	
	while (rankingsIterator.hasNext())
	{
		RankingEntry nextEntry = rankingsIterator.next();
		%>

		<tr>
		  <td align="right"><%= nextEntry.getRankingPosition()%>.</td>
		  <td><!-- <a href="/ranking/players/2">--> <%= myData.getPlayer(nextEntry.getRankingPlayerID()).getFullName()%></a></td>
		  <td align="center"><%= nextEntry.getRankingValue()%></td>
		  <td align="center"><%= nextEntry.getRankingSum()%></td>
		  <td align="center"><%= nextEntry.getRankingGame1()%>, <%= nextEntry.getRankingGame2()%>, <%= nextEntry.getRankingGame3()%>, <%= nextEntry.getRankingGame4()%>, <%= nextEntry.getRankingGame5()%></td>
		  <!-- <td align="center"><span style="color:green">84%</span></td> -->
		  <!-- <td align="center">306</td> -->
		</tr>
    <%}%>  
</table>

    
    </div>
</body>
</html>