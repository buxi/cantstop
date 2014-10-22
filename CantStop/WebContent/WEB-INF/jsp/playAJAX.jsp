<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><s:message code="PLAYROOM" text="Playroom" /></title>
<!-- AJAX scripts START -->
<script src="resources/js/jquery.js"></script>
<script src="resources/js/gamescripts.js"></script>
<script type="text/javascript">
function poll(){
	$('#ajaxLoading').show();   
	   setTimeout(function(){
	      $.ajax({ type: "POST", url: "pollingAJAX", 
	    	error: function(e){  
	    	  $('#info').html('Error: ' + e.responseText);
		    }  , 
		    success: function(response){
			    $('#json_status').html("polling called");
			    $('#json_errorcode').html("");
		        $('#gameState').html(response.gameState);
		        $('#game_errorMessage').html(response.errorMessage);
		        /*$('#joinedPlayersList').html(response.joinedPlayersListAJAX);
				if (response.gameState == 'ENOUGH_PLAYER') {
					$('#startGame').show();
				}
				*/
				if (response.gameState == 'GAME_FINISHED' ) {
					window.location = "do.showGameOver?playerId=" + $('#playerId').val();
				}
				refreshPage($('#globalPlayerId').val(), response);
				
		        //Setup the next poll recursively
		        poll();
	      }, dataType: "json"});
	  }, 3000);
	   $('#ajaxLoading').hide(); 
	}
	
(poll());
function doAjaxPost(command, playerIdForm, pairId ) {  
	  var wayNumberForm = "-1";
	  if ($('#chosenPairWayNumber'+pairId) != null) {
		  wayNumberForm =  $('#chosenPairWayNumber'+pairId).val();	  
	  }
	  var formData = {playerId:playerIdForm, chosenPairId:pairId, wayNumber:wayNumberForm};
	  $.ajax({  
	    type: "POST",  
	    url: command,  
	    dataType: 'json',
	    data: formData,   
	    success: function(response){
	    	// we have the response 
	    	if(response.status == "SUCCESS"){
	    		$('#json_status').html(response.status);
	      	  	$('#json_errorMessage').html(response.errorMessage);
	      	  	$('#game_errorMessage').html(response.gto.errorMessage);
	      	   // $('#playerId').val(response.methodResult);
	      	    /*$('#addPlayer').hide();
	      	  	$('#waitingForOthers').show();
	      	  	*/
		    }else{
			  	$('#json_status').html(response.status);
	      	  	$('#json_errorMessage').html(response.errorMessage);
	      	  	$('#game_errorMessage').html(response.gto.errorMessage)
		    }
	    	refreshPage(playerIdForm, response.gto);
	    },  
	    error: function(e){  
	    	$('#info').html('Error: ' + e);
	    }  
	  });  
	}  
	
// gto = GameTransferObject
function refreshPage(playerId, gto) {
	$('#PAIRSTOCHOOSE').hide();
	$('#DICES_THROWN').hide();
	$('#commandsInTurn').hide();
	$('#lastThrow').hide();
	//refresh buttons
	if (playerId == gto.actualPlayerId && gto.gameState=='IN_ROUND') {
		$('#commandsInTurn').show(); 	
	}
	
	refreshBoard(gto.board);
	//displaying actual throw
	if (gto.gameState == 'DICES_THROWN') {
		$('#actualThrowRow').html(refreshDices(gto.dices));
		$('#DICES_THROWN').show();
		
		//  displaying pairs to choose 
		if (playerId == gto.actualPlayerId)	{ 
			$('#PAIRSTOCHOOSE').show();
			refreshPairsToChoose($('#PAIRSTOCHOOSEINNERHTML'), gto.choosablePairsWithId, playerId);
		}
	}
	
	//displaying last throw
	if (gto.lastThrow != null) {
		$('#lastThrowRow').html(refreshDices(gto.lastThrow));
		$('#lastThrow').show();
	}
	
	//displaying players
	refreshPlayersList(gto.playerList, gto.actualPlayerId, gto.lastUsedPairInfo);
}
</script>
<!-- AJAX scripts END -->

</head>

<body>

<!-- BUILDING SCREEN -->
<jsp:include page="langSelectionIncl.jsp"/>

<!-- TODO should be removed in Production -->
<jsp:include page="playerSelection.jsp"/>

<!-- game state and error messages selection -->
<jsp:include page="gameStateIncl.jsp"/>

<div id="debugInfo" style="color: blue;"></div>

<!--  display board -->
<jsp:include page="boardAJAX.jsp"/>


<!-- display lastThrow  -->
<div id="lastThrow" style="display: none;">
	<s:message code="LAST.THROW"/><table><tr id="lastThrowRow"/></table>
</div>	 
	<c:set var="lastUsedPairInfo" value="${gameInfo.lastUsedPairInfo }"/>
	<table border="0">
		<c:forEach items="${gameInfo.playerList}" var="player">
			<tr>
				<td>
					<div id="inturn_${player.order}" style="display: none;">
						<s:message code="IN_TURN"/>
					</div>
				</td>
				<td bgcolor="${player.color}">${player.name} (${player.order})</td>
				<!-- display markers -->
				<td> 
					<table border="0"><tr id="playersMarkers_${player.order}">
					</tr></table>
				</td>
				<!-- display climbers -->
				<td> 
					<table border="0"><tr><td id="playersClimbers_${player.order}">
					</td></tr></table>
				</td>
				<td>
					<table id="lastUsedPair_${playerId }" border="1"><tr id="lastUsedPairRow_${playerId }">
					</tr></table>
				</td>
			</tr>
		</c:forEach>
	</table>

<!-- display dices  -->
<div id="DICES_THROWN" style="display: none;">
	<s:message code="ACTUAL.THROW"/><table><tr id="actualThrowRow"/></table>

	<!--  displaying pairs to choose -->
	<div id="PAIRSTOCHOOSE">
	<table border=0>
		<tr>
			<td>
				<s:message code="CHOOSE_A_PAIR"/>
			</td>
			 	<td>
				 	<div id="PAIRSTOCHOOSEINNERHTML">
					</div>
			 	</td>
		</tr>
	</table>
	</div>
</div>

<!-- TODO real enum should be used -->
<c:if test="${gameInfo.gameState=='GAME_WIN'}">
	<s:message code="STATE_GAME_WIN"/> ${gameInfo.actualPlayer.name }<br>
</c:if>
<input type="hidden" id="globalPlayerId" value="${playerId}" />

<table border="0">
<tr>
<td><s:message code="ACTIONTITLE"/></td>
<td>
	<c:if test="${not empty playerId}">
		<form action="do.finishgame" method="post">
		<input id="playerId" type="hidden" name="playerId" value="${playerId}" />
		<input type="submit" value="<s:message code="ACTION.FINISHGAME"/>" />
		</form>
	</c:if>

	<input type="hidden" name="playerId" value="${playerId}" />
	<div id="commandsInTurn" style="display: none;">
		<input type="button" value="<s:message code='ACTION.FINISHTURN'/>" onclick="doAjaxPost('do.finishturnAJAX', '${playerId}', null)">
		<input type="button" value="<s:message code='ACTION.THROW'/>" onclick="doAjaxPost('do.throwAJAX', '${playerId}', null)">
	</div>
</td>
</tr>
</table>
</body>
</html>