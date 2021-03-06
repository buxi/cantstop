<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="resources/css/game.css" rel="stylesheet" type="text/css"/>

<title><s:message code="PLAYROOM" text="Playroom" /></title>
<!-- AJAX scripts START -->
<script type="text/javascript">
	var buttonLabel = "<s:message code="BUTTON.CHOOSE"/>"
</script>
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
		        $('#game_messages').html(refreshMessages(response.messages));
				if (response.gameState == 'INIT' ) {
					window.location = "do.showGameOver?playerId=" + $('#globalPlayerId').val();
				}
				refreshPage($('#globalPlayerId').val(), response);
				
		        //Setup the next poll recursively
		        poll();
	      }, dataType: "json"});
	  }, 5000);
	   	$('#ajaxLoading').hide(); 
	}
(poll());

function doAjaxPost(command, playerIdForm, pairId ) {  
	doAjaxPostWithWayNumber(command, playerIdForm, pairId, "-1");
}

function doAjaxPostWithWayNumber(command, playerIdForm, pairId, wayNumberForm ) {  
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
	      	  	$('#game_messages').html(refreshMessages(response.gto.messages));
		    }else{
			  	$('#json_status').html(response.status);
	      	  	$('#json_errorMessage').html(response.errorMessage);
	      	  	$('#game_messages').html(refreshMessages(response.gto.messages))
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
		$('#actualThrowRow').html(refreshDices(gto.dices, 40));
		$('#DICES_THROWN').show();
		
		//  displaying pairs to choose 
		if (playerId == gto.actualPlayerId)	{ 
			$('#PAIRSTOCHOOSE').show();
			refreshPairsToChoose($('#PAIRSTOCHOOSEINNERHTML'), gto.choosablePairsWithId, playerId);
		}
	}
	
	//displaying last throw
	if (gto.lastThrow != null && gto.lastThrow.length>0) {
		$('#lastThrowRow').html(refreshDices(gto.lastThrow, 25));
		$('#lastThrow').show();
	}
	
	//displaying players
	refreshPlayersList(gto.playerList, gto.actualPlayerId, gto.lastUsedPairInfo);
}
</script>
<!-- AJAX scripts END -->

</head>

<body>
<div style="width:675px; height:750px; border: 2px solid black;background-color: grey;">
<!-- BUILDING SCREEN -->
<jsp:include page="langSelectionIncl.jsp"/>

<!-- game state and error messages selection -->
<div style="width:500px;height:50px; overflow: hidden; white-space: nowrap; border: 0px solid black;  ">
	<jsp:include page="gameStateIncl.jsp"/>
</div>
<!--  display board -->
<div id="board" style="border: 2px solid black;  float:left; height:500px;">
	<jsp:include page="boardAJAX.jsp"/>
</div>

 
<!-- display playerList -->
<div id="playerList" style="float:right; border: 2px solid black;   width: 200px; height:500px">
	<div style="align:center; border:0px solid black; height:25px; border-bottom: 1px solid black;"><s:message code="ACTIVE.PLAYERS"/></div>
	<c:forEach items="${gameInfo.playerList}" var="player">
	<div id="inturnplayer_${player.order}" style=" height:125px">
		<div id="inturn_${player.order}" style="display: none; ">
			<s:message code="IN_TURN"/>
		</div>
		<div style="align:center;">${player.name}</div>
		
		<!-- display markers -->
		<div id="playersMarkers_${player.order}" ></div>
		
		<!-- display climbers -->
		<div id="playersClimbers_${player.order}" ></div>
		<div id="lastUsedPair_${player.order}">
			<div id="lastUsedPairRow_${player.order}" ></div>
		</div>
		<br>
	</div>
	</c:forEach>
</div>

<!-- display lastThrow  -->
<div id="lastThrow" style=" display: none; overflow: hidden; white-space: nowrap; border: 0px solid black;  width: 300px;">
	<div style="float:left"><s:message code="LAST.THROW"/></div><div id="lastThrowRow" style="float:right" /></div>
</div>	

<!-- display dices  -->
<div id="DICES_THROWN" style="display: none; overflow: hidden; white-space: nowrap; border: 0px solid black;  width: 450px;">
	<div style="float:left"><s:message code="ACTUAL.THROW"/><div id="actualThrowRow"></div></div>
	
	<!--  displaying pairs to choose -->
	<div id="PAIRSTOCHOOSE" style="float:right"><s:message code="CHOOSE_A_PAIR"/><div id="PAIRSTOCHOOSEINNERHTML"></div></div>
</div>

<!-- TODO real enum should be used -->
<c:if test="${gameInfo.gameState=='GAME_WIN'}">
	<s:message code="STATE_GAME_WIN"/> ${gameInfo.actualPlayer.name }<br>
</c:if>
<input type="hidden" id="globalPlayerId" value="${playerId}" />

<div id="actionButtonPart" style="overflow: hidden; white-space: nowrap; border: 0px solid black;  width: 450px;">
	<div style="float:left;"><s:message code="ACTIONTITLE"/></div>
	<c:if test="${not empty playerId}">
		<form action="do.finishgame" method="post" style="float:left; display:inline;">
		<input id="playerId" type="hidden" name="playerId" value="${playerId}" />
		<input name="finishGameButton" type="submit" value="<s:message code="ACTION.FINISHGAME"/>" />
		</form>
	</c:if>
	<div id="commandsInTurn" style="float:right; display: none; ">
		<input name="finishTurnButton" type="button" value="<s:message code='ACTION.FINISHTURN'/>" onclick="doAjaxPost('do.finishturnAJAX', '${playerId}', null)">
		<input name="throwButton" type="button" value="<s:message code='ACTION.THROW'/>" onclick="doAjaxPost('do.throwAJAX', '${playerId}', null)">
	</div>
</div>

</div>
</body>
</html>