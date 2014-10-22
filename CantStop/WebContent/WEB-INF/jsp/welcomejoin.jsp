<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><s:message code="WELCOME.TITLE" text="Welcome" /></title>
<!-- AJAX scripts START -->
<script src="resources/js/jquery.js"></script>
<script type="text/javascript">
function doAjaxPost() {  
	  // get the form values  
	  var name = $('#playerName').val();
	  
	  $.ajax({  
	    type: "POST",  
	    url: "do.addplayerAJAX",  
	    data: "playerName=" + name, 
	    success: function(response){
	    	// we have the response 
	    	if(response.status == "SUCCESS"){
	    		$('#json_status').html(response.status);
	      	  	$('#json_errorMessage').html(response.errorMessage);
	      	  	$('#game_errorMessage').html(response.gto.errorMessage);
	      	    $('#playerId').val(response.methodResult);
	      	  	$('#addPlayer').hide();
	      	  	$('#waitingForOthers').show();
		    }else{
			  	$('#json_status').html(response.status);
	      	  	$('#json_errorMessage').html(response.errorMessage);
	      	  	$('#game_errorMessage').html(response.gto.errorMessage)
		    }	
	    },  
	    error: function(e){  
	    	$('#info').html('Error: ' + e);
	    }  
	  });  
	}  

(function poll(){
	$('#ajaxLoading').show();   
	   setTimeout(function(){
	      $.ajax({ type: "POST", url: "pollingAJAX", error: function(e){  
	    	  $('#info').html('Error: ' + e.responseText);
		    }  , success: function(response){
		    $('#json_status').html("polling called");
		    $('#json_errorcode').html("");
	        $('#gameState').html(response.gameState);
	        $('#game_errorMessage').html(response.errorMessage);
	        $('#joinedPlayersList').html(response.joinedPlayersListAJAX);
			if ($('#playerId').val()!='' && response.gameState == 'ENOUGH_PLAYER' ) {
				$('#startGame').show();
			}
			if ($('#playerId').val()=='' && !response.gameFull) {
				$('#addPlayer').show();
				$('#waitingForOthers').show();
			}
			else {
				$('#addPlayer').hide();
				$('#waitingForOthers').hide();
			}
			
			if (response.gameState == 'IN_SPIEL' || response.gameState == 'IN_ROUND' ) {
				window.location = "playAJAX?playerId=" + $('#playerId').val();
			}
			
	        //Setup the next poll recursively
	        poll();
	      }, dataType: "json"});
	  }, 3000);
	   $('#ajaxLoading').hide();
	})();
</script>
<!-- AJAX scripts END -->

</head>
<body>

<!-- language selection -->
<jsp:include page="langSelectionIncl.jsp"/>


<div id="welcomeBlock">
<h2><s:message code="WELCOME.HEADER" /></h2>
<img alt="" width="200" src="resources/images/cantstop.jpg">
</div>

<!-- game state and error messages selection -->
<jsp:include page="gameStateIncl.jsp"/>


<div id="joinedPlayers">
  <s:message code="JOINED.PLAYERS"/><div id="joinedPlayersList"></div>
</div>

<div id="addPlayer" style="display: none;">
  <s:message code='JOIN.MESSAGE'/><br/>
  <input type="text" id="playerName">
  <input type="button" value="<s:message code='JOIN.BUTTON'/>" onclick="doAjaxPost()">
</div>
<div style="display: none;" id="waitingForOthers"><s:message code='WAITING.OTHERPLAYER'/> </div>
<div style="display: none;" id="startGame">
 	<form action="do.startgame" method="post">
		<input type="submit" value="<s:message code="ACTION.STARTGAME"/>" />
		<input id="playerId" type="hidden" name="playerId" value="${playerId}" />
	</form>
</div>

</body>
</html>