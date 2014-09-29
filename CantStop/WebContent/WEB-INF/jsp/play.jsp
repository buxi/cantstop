<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<html>
<head>
<title><s:message code="PLAYROOM" text="Playroom" /></title>
</head>
<body>

<!-- BUILDING SCREEN -->
PlayerId:${playerId}<br>
Error message: ${errorMsg}<br>
Game messages: ${gameInfo.errorMessage}
<s:message code="GAME_STATUS"/>  <s:message code="STATE_${gameInfo.gameState}"/> <br>
<a href="http://localhost:8080/CantStop/play?playerId=0">P1</a>
<a href="http://localhost:8080/CantStop/play?playerId=1">P2</a>
<a href="http://localhost:8080/CantStop/play?playerId=2">P3</a>
<a href="http://localhost:8080/CantStop/play?playerId=3">P4</a>

<!--  display board -->
<jsp:include page="board.jsp"/>


<!-- display players  -->

	<s:message code="LAST.THROW"/>
	<c:forEach items="${gameInfo.lastThrow}" var="dice">
	 	<td> 
		 	<img width="25" src="resources/images/dice${dice.diceValue}.png"/>
	 	</td>
	</c:forEach>
	
	
	<c:set var="actualPlayer" value="${gameInfo.actualPlayerNumber}"/>
	<c:set var="lastUsedPairInfo" value="${gameInfo.lastUsedPairInfo }"/>
	<table border="">
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
					<table border=""><tr>
						<c:forEach items="${player.markers}" var="marker">
							<td bgcolor="${marker.color }">&nbsp;&nbsp;&nbsp;</td>
						</c:forEach>					
					</tr></table>
				</td>
				<!-- display climbers -->
				<td> 
					<table border=""><tr>
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

<c:if test="${gameInfo.gameState=='DICES_THROWN'}">
	<!-- displaying dices -->
	<table border="">
		<tr>
			<td>
				<s:message code="ACTUAL.THROW"/>
			</td>
			<c:forEach items="${gameInfo.dices}" var="dice">
			 	<td> 
				 	<img src="resources/images/dice${dice.diceValue}.png">
			 	</td>
			</c:forEach>
		</tr>
	</table>

	<!--  displaying pairs to choose -->
	<table border="">
		<tr>
			<td>
				<s:message code="CHOOSE_A_PAIR"/>
			</td>
			<c:forEach items="${gameInfo.choosablePairsWithId}" var="entry">
				<c:set var="pairId" value="${entry.key}"/>
				<c:set var="pair" value="${entry.value}"/>
				
			 	<td>
			 	<table border="1"><tr><td valign="middle" align="center">
			 	<img width="30" src="resources/images/dice${pair.firstPair.first.diceValue}.png"/>
			 	<img width="30" src="resources/images/dice${pair.firstPair.second.diceValue}.png"/>
			 	<img width="30" src="resources/images/dice${pair.secondPair.first.diceValue}.png"/>
			 	<img width="30" src="resources/images/dice${pair.secondPair.second.diceValue}.png"/>
			 	 
			 	<form action="do.executePair" method="post">
			 		<input type="submit" value="<s:message code='BUTTON.PAIRWAHL'/>" />
					<input type="hidden" name="chosenPairId" value="${pairId}" />
					<!-- TODO real enum should be used -->
					<c:choose>
						<c:when test="${pair.pairChoiceInfo == 'WITHWAYINFO'}">
							<select name="wayNumber">
					  			<option value="${pair.firstSum}">${pair.firstSum}</option>
  								<option value="${pair.secondSum}">${pair.secondSum}</option>
							</select>
						</c:when>
						<c:otherwise>
							<input type="hidden" name="wayNumber" value="-1" />
						</c:otherwise>
					</c:choose>
					
					<input type="hidden" name="playerId" value="${playerId}" />
				</form>
				</td></tr></table>
			 	</td>
			</c:forEach>
		</tr>
	</table>
	
</c:if>
<!-- TODO real enum should be used -->
<c:if test="${gameInfo.gameState=='GAME_WIN'}">
	<s:message code="STATE_GAME_WIN"/> ${gameInfo.actualPlayer.name }<br>
</c:if>


<table border="">
<tr>
<td><s:message code="ACTIONTITLE"/></td>
<td>
	<form action="do.finishgame" method="post">
	<input type="hidden" name="playerId" value="${playerId}" />
	<input type="submit" value="Finish game" />
	</form>
</td>
<!-- TODO real enum should be used -->
<c:choose>

<c:when test="${gameInfo.gameState=='INIT'}">
<td>
	<form action="do.gamestart" method="post">
	<input type="hidden" name="playerId" value="${playerId}" />
	<input type="submit" value="Start Game" />
	</form>
</td>
</c:when>
<c:when test="${gameInfo.gameState=='IN_ROUND'}">
<td>
	<form action="do.finishturn" method="post">
	<input type="hidden" name="playerId" value="${playerId}" />
	<input type="submit" value="Finish turn" />
	</form>
</td>
<td>
	<form action="do.throw" method="post">
	<input type="hidden" name="playerId" value="${playerId}" />
	<input type="submit" value="Throw" />
	</form>
</td>
</c:when>
</c:choose>
</tr>
</table>
</body>
</html>