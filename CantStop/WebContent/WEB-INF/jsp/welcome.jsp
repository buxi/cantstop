<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<html>
<head>
<title><s:message code="WELCOME.TITLE" text="Welcome" /></title>
</head>
<body>
<a href="?language=de"><img height="15" alt="de" src="resources/images/DEIcon.png"></a>
<a href="?language=en"><img height="15" alt="uk" src="resources/images/UKIcon.png"></a>
<h2><s:message code="WELCOME.HEADER" text="Welcome to Can't Stop" /></h2>
<img alt="" width="200" src="resources/images/cantstop.jpg">
<form method="post">
<s:message code="FORM.ENTERYOURNAME" text="Please enter your name:" />
<input type="text" name="playerName" value="${playerName}" /><br>
<input type="submit" value="<s:message code='ACTION.STARTGAME' text='Welcome' />" />
</form>

</body>
</html>