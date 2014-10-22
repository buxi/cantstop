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
		$('#actualThrowRow').html(refreshDices(gto.dices, 40));
		$('#DICES_THROWN').show();
		
		//  displaying pairs to choose 
		if (playerId == gto.actualPlayerId)	{ 
			$('#PAIRSTOCHOOSE').show();
			refreshPairsToChoose($('#PAIRSTOCHOOSEINNERHTML'), gto.choosablePairsWithId, playerId);
		}
	}
	
	//displaying last throw
	if (gto.lastThrow != null && gto.lastThrow.size>0) {
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
<div style="width:650px">
<!-- BUILDING SCREEN -->
<jsp:include page="langSelectionIncl.jsp"/>

<!-- game state and error messages selection -->
<div style="width:500px;height:50px; overflow: hidden; white-space: nowrap; border: 1px solid black;  ">
	<jsp:include page="gameStateIncl.jsp"/>
</div>
<!--  display board -->
<div id="board" style="float:left; height:500px;">
	<jsp:include page="boardAJAX.jsp"/>
</div>

 
<!-- display playerList -->
<div id="playerList" style="float:right;  width: 150px; height:500px">
	<c:forEach items="${gameInfo.playerList}" var="player">
	<div style="border:1px solid black; height:125px">
		<div id="inturn_${player.order}" style="display: none; ">
			<s:message code="IN_TURN"/>
		</div>
		<div style="align:center;  bgColor:${player.color}">${player.name}</div>
		
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
<div id="lastThrow" style=" display: none; overflow: hidden; white-space: nowrap; border: 1px solid black;  width: 200px;">
	<s:message code="LAST.THROW"/><div id="lastThrowRow" style="float:right" /></div>
</div>	

<!-- display dices  -->
<div id="DICES_THROWN" style="display: none; overflow: hidden; white-space: nowrap; border: 1px solid black;  width: 470px;">
	<div style="float:left"><s:message code="ACTUAL.THROW"/><div id="actualThrowRow"></div></div>
	
	<!--  displaying pairs to choose -->
	<div id="PAIRSTOCHOOSE" style="float:right"><s:message code="CHOOSE_A_PAIR"/><div id="PAIRSTOCHOOSEINNERHTML"></div></div>
</div>

<!-- TODO real enum should be used -->
<c:if test="${gameInfo.gameState=='GAME_WIN'}">
	<s:message code="STATE_GAME_WIN"/> ${gameInfo.actualPlayer.name }<br>
</c:if>
<input type="hidden" id="globalPlayerId" value="${playerId}" />

<div id="actionButtonPart" style="overflow: hidden; white-space: nowrap; border: 1px solid black;  width: 400px;">
	<s:message code="ACTIONTITLE"/>
	<c:if test="${not empty playerId}">
		<form action="do.finishgame" method="post" style="display:inline;">
		<input id="playerId" type="hidden" name="playerId" value="${playerId}" />
		<input type="submit" value="<s:message code="ACTION.FINISHGAME"/>" />
		</form>
	</c:if>
	<div id="commandsInTurn" style="float:right; display: none; ">
		<input type="button" value="<s:message code='ACTION.FINISHTURN'/>" onclick="doAjaxPost('do.finishturnAJAX', '${playerId}', null)">
		<input type="button" value="<s:message code='ACTION.THROW'/>" onclick="doAjaxPost('do.throwAJAX', '${playerId}', null)">
	</div>
</div>

</div>
</body>
</html>