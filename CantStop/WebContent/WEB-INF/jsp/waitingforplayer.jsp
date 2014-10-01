<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<html>
<head>
<meta http-equiv="refresh" content="1; URL=do.waitingforplayer?playerId=${playerId}">
<title><s:message code="PLAYROOM" text="Playroom" /></title>
<s:message code="WAITING.OTHERPLAYER"/>
</head>
<body>

<!-- BUILDING SCREEN -->
<c:if test="${not empty errorMsg}"><div style="color: red"><s:message code="${errorMsg}"/></div><br/></c:if>
<c:if test="${not empty gameInfo.errorMessage}"><div style="color: red"><s:message code="${gameInfo.errorMessage}"/></div><br/></c:if>
<s:message code="GAME_STATUS"/>  <s:message code="STATE_${gameInfo.gameState}"/> <br>

<jsp:include page="langSelection.jsp"/>

<jsp:include page="playerSelection.jsp"/>


	<c:set var="actualPlayer" value="${gameInfo.actualPlayerNumber}"/>
<br/>
<s:message code="PLAYERSINGAME"/>
	<table border="0">
		<c:forEach items="${gameInfo.playerList}" var="player">
			<tr>
				<td>
					<c:if test="${player.order ==  actualPlayer}">
						<s:message code="IN_TURN"/>
					</c:if> 
				</td>
				<td bgcolor="${player.color}">${player.name} (${player.order})</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>