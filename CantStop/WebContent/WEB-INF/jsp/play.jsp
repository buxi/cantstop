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
Game state:${gameInfo.gameState}
New playerId:${newPlayerId}
</body>
</html>