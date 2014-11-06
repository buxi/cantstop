<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="ajaxLoading">
  <img height="15" alt="uk" src="resources/images/ajax-loader.gif">
</div>

<div id="gameState" style="float:left; display:none">
  <s:message code="GAME_STATUS"/> <s:message code="STATE_${gameInfo.gameState}"/> <br>
</div>

<div id="game_messages" style="color: blue"></div><br/>

<div id="messages" style="float:right">
	<c:if test="${not empty errorMsg}"><div style="color: red"><s:message code="${errorMsg}"/></div><br/></c:if>
	
	<div id="json_status" style="color: green; display:none"></div>
	<div id="json_errorMessage" style="color: yellow;  display:none"></div>
</div>
