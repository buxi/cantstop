<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<div id="langSelection">
	<a href="?playerId=${playerId}&language=de"><img height="15" alt="de" src="resources/images/DEIcon.png"></a>
	<a href="?playerId=${playerId}&language=en"><img height="15" alt="uk" src="resources/images/UKIcon.png"></a>
	<a href='<s:message code="HELP_LINK"/>'><s:message code="HELP"/></a>
</div>