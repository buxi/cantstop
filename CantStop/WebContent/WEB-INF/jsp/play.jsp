<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title>Playroom</title>
</head>
<body>
Actual players:
<table border="1">
<tr>
<th>Id</th>
<th>Name</th>
<th>Color</th>
</tr>
<c:forEach items="${gameInfo.playerList}" var="player">
<tr>
<td>${player.order}</td>
<td>${player.name}</td>
<td>${player.color}</td>
</tr>
</c:forEach>
</table>
Game state:${gameInfo.gameState}<br>
PlayerId:${playerId}<br>
Error message ${errorMsg}<br>
<div>
	<form action="do.finishgame" method="post">
	<input type="hidden" name="playerId" value="${playerId}" />
	<input type="submit" value="Finish game" />
	</form>
<c:choose>
<c:when test="${gameInfo.gameState=='INIT'}">
	<form action="do.gamestart" method="post">
	<input type="hidden" name="playerId" value="${playerId}" />
	<input type="submit" value="Start Game" />
	</form>
</c:when>
<c:when test="${gameInfo.gameState=='IN_ROUND'}">
	<form action="do.finishturn" method="post">
	<input type="hidden" name="playerId" value="${playerId}" />
	<input type="submit" value="Finish turn" />
	</form>

	<form action="do.throw" method="post">
	<input type="hidden" name="playerId" value="${playerId}" />
	<input type="submit" value="Throw" />
	</form>
</c:when>
</c:choose>
</div>
</body>
</html>