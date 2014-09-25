<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title>Welcome</title>
</head>
<body>
<h2>Welcome to Can't Stop</h2>
<img alt="" width="200" src="resources/images/cantstop.jpg">
<form method="post">
Enter your name:
<input type="text" name="playerName" value="${playerName}" />
<input type="submit" value="Start Game" />
</form>

</body>
</html>