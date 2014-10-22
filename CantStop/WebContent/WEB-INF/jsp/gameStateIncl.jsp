<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="ajaxLoading">
  <img height="15" alt="uk" src="resources/images/ajax-loader.gif">
</div>

<div id="gameState">
  <s:message code="GAME_STATUS"/> <s:message code="STATE_${gameInfo.gameState}"/> <br>
</div>

<div id="messages">
<c:if test="${not empty errorMsg}"><div style="color: red"><s:message code="${errorMsg}"/></div><br/></c:if>
<div id="game_errorMessage" style="color: red"></div><br/>
<div id="json_status" style="color: green;"></div>
<div id="json_errorMessage" style="color: green;"></div>
</div>
