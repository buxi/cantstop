<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<html>
<head>
<meta http-equiv="refresh" content="1; URL=do.waitingforplayer?playerId=${playerId}">
<title><s:message code="PLAYROOM" text="Playroom" /></title>
<s:message code="WAITING.OTHERPLAYER"/><br/>
</head>
<body>

<!-- BUILDING SCREEN -->
<c:if test="${not empty errorMsg}"><div style="color: red"><s:message code="${errorMsg}"/></div><br/></c:if>
<c:if test="${not empty gameInfo.errorMessage}"><div style="color: red"><s:message code="${gameInfo.errorMessage}"/></div><br/></c:if>
<s:message code="GAME_STATUS"/>  <s:message code="STATE_${gameInfo.gameState}"/> <br>
<a href="play?playerId=${playerId}&language=de"><img height="15" alt="de" src="resources/images/DEIcon.png"></a>
<a href="play?playerId=${playerId}&language=en"><img height="15" alt="uk" src="resources/images/UKIcon.png"></a>
<a href="play?playerId=0">P1</a>
<a href="play?playerId=1">P2</a>
<a href="play?playerId=2">P3</a>
<a href="play?playerId=3">P4</a>

	<c:set var="actualPlayer" value="${gameInfo.actualPlayerNumber}"/>

	<table border="0">
		<c:forEach items="${gameInfo.playerList}" var="player">
			<tr>
				<td>
					<c:if test="${player.order ==  actualPlayer}">
						<s:message code="IN_TURN"/>
					</c:if> 
				</td>
				<td bgcolor="${player.color}">${player.name} (${player.order})</td>
				<!-- display markers -->
				<td> 
					<table border="0"><tr>
						<c:forEach items="${player.markers}" var="marker">
							<td bgcolor="${marker.color }">&nbsp;&nbsp;&nbsp;</td>
						</c:forEach>					
					</tr></table>
				</td>
				<!-- display climbers -->
				<td> 
					<table border="0"><tr>
						<c:forEach items="${player.climbers}" var="climber">
							<td><img height=15 src="resources/images/climber.png"></td>
						</c:forEach>					
					</tr></table>
				</td>
				<td>
					<c:if test="${lastUsedPairInfo.player.order ==  player.order}">
						<table border="1"><tr><td valign="middle" align="center" bgcolor="<c:if test="${lastUsedPairInfo.chosenWayNumber == lastUsedPairInfo.chosenPair.firstSum}">grey</c:if>">
							
						 	<img width="30" src="resources/images/dice${lastUsedPairInfo.chosenPair.firstPair.first.diceValue}.png"/>
						 	<img width="30" src="resources/images/dice${lastUsedPairInfo.chosenPair.firstPair.second.diceValue}.png"/>
						 	</td>
						 	<td bgcolor="<c:if test="${lastUsedPairInfo.chosenWayNumber == lastUsedPairInfo.chosenPair.secondSum}">grey</c:if>">
						 	
						 	<img width="30" src="resources/images/dice${lastUsedPairInfo.chosenPair.secondPair.first.diceValue}.png"/>
						 	<img width="30" src="resources/images/dice${lastUsedPairInfo.chosenPair.secondPair.second.diceValue}.png"/>
						</td></tr></table>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>