<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

<title><s:message code="BYE.TITLE"/></title>
</head>
<body>
<c:set var="homeURL" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"/> 
<h2><s:message code="BYE.HEAD"/></h2>

<s:message code="BYE.MSG"/>
<br>
<a href="${homeURL}"><s:message code="START_NEW_GAME"/></a> 
</body>
</html>