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

<title><s:message code="PLAYROOM" text="Playroom" /></title>
<script src="resources/js/jquery.js"></script>
<script type="text/javascript">
function doAjaxPost() {  
	  // get the form values  
	  $.ajax({  
	    type: "POST",  
	    url: "polling",  
	    success: function(response){
	      // we have the response 
	    	  
	    	  $('#info2').html(response.gameState);
	    	  $('#name').val(response.errorMessage);
	    },  
	    error: function(e){  
	    	$('#info').html('Error: ' + e);
	    }  
	  });  
	}  
	
(function poll(){
	   setTimeout(function(){
	      $.ajax({ type: "POST", url: "polling", error: function(e){  
	    	  $('#info').html('Error: ' + e.responseText);
		    }  , success: function(response){
		    $('#info').html("polling called");
	        $('#info2').html(response.gameState);

	        //Setup the next poll recursively
	        poll();
	      }, dataType: "json"});
	  }, 3000);
	})();
</script>
</head>
<body>

<!-- BUILDING SCREEN -->
<c:if test="${not empty errorMsg}"><div style="color: red"><s:message code="${errorMsg}"/></div><br/></c:if>
<c:if test="${not empty gameInfo.errorMessage}"><div style="color: red"><s:message code="${gameInfo.errorMessage}"/></div><br/></c:if>
<s:message code="GAME_STATUS"/>  <s:message code="STATE_${gameInfo.gameState}"/> <br>
 <input type="text" id="name">
 <div id="info" style="color: green;"></div>
  <div id="info2" style="color: green;"></div>
<input type="button" value="Add Users" onclick="doAjaxPost()">
</body>
</html>