<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Error</title>
</head>
<body>
<h2>Error occured</h2>
Error message: ${exception}<br>

${exception.message }<br>
<c:forEach items="${exception.stackTrace}" var="element">
	${element }
</c:forEach>

</body>
</html>