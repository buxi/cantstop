function refreshBoard(board) {
	for (var i = 0; i < board.ways.length; i++) {
		var way = board.ways[i];
		var wayNumber = way.number;
		
		var wayTable = document.getElementById("wayTable_"+wayNumber);
		var wayTableHTML = "";
		
		// displaying Hut 
		var hutHTML = "<tr><td id='h_"+way.number+"' style='border:1px solid black; width:30px; height:30px'";		
		var hutMarker = way.hut.marker;
		if (hutMarker != null) {
			 hutHTML += " bgcolor=" + marker.color;
		}
		hutHTML += ">" + wayNumber;
		
		var hutClimber = way.hut.climber;
		if (hutClimber != null) {
			hutHTML +=  "<img height=15 src='resources/images/climber.png'>";
		}
		hutHTML += "</td></tr>";
		wayTableHTML += hutHTML;
		
		// displaying RopePoints
		var arrayLength = way.ropePoints.length;
		for (var j = 0; j < way.ropePoints.length; j++) {
			var ropePoint =  way.ropePoints[arrayLength - j - 1];
			var climber = ropePoint.climber;
			var rpHTML = "<tr><td  style='border:1px solid black; width:30px; height:30px'>";
			if (climber != null) {
				rpHTML +="<img height=15 src='resources/images/climber.png'>";
			}	
			if (climber == null && (ropePoint.markers == null || ropePoint.markers.length == 0)) {
				rpHTML += "<img height=15 src='resources/images/empty.png'>";
			}
			// displaying markers
			for (var k = 0; k < ropePoint.markers.length; k++) {
				var marker = ropePoint.markers[k];
				rpHTML += "<img height=15 src='resources/images/marker_"+marker.color+".png'>";	
			}
			rpHTML += "</td></tr>";
			wayTableHTML += rpHTML;
			
		}
		wayTable.innerHTML = wayTableHTML; 
	}
}

function refreshDices(dices, widthInfo) {
	var htmlCode = "";
	if (dices == null) { return htmlCode; }
	
	for (var i = 0; i < dices.length; i++) {
		var dice = dices[i];
		htmlCode += "<img width="+widthInfo+" src='resources/images/dice"+dice.diceValue+".png'/>";
	}
	return  htmlCode;
}

function refreshPairsToChoose(tr, choosablePairsWithId, playerId) {
	if (choosablePairsWithId == null) { return; }
	tr.empty();
	var htmlVal = "";
	$.each( choosablePairsWithId, function(pairId,pair){
		htmlVal += "<img width=25 src='resources/images/dice"+pair.firstPair.first.diceValue+".png'/>"; 
		htmlVal += "<img width=25 src='resources/images/dice"+pair.firstPair.second.diceValue+".png'/>";
		htmlVal += "<img width=25 src='resources/images/dice"+pair.secondPair.first.diceValue+".png'/>";
		htmlVal += "<img width=25 src='resources/images/dice"+pair.secondPair.second.diceValue+".png'/>";
		htmlVal += "<form  method='post'  style='float:right'>";
		// TODO localized BUTTON LABEL
		htmlVal += "<input type='button' value='WAHLEN' onclick=\"doAjaxPost('do.executePairAJAX', '"+playerId+"', '"+pairId+"') \"  />";
		//<!-- TODO real enum should be used -->
		if (pair.pairChoiceInfo == 'WITHWAYINFO') {
			htmlVal += "<select style='float:right' id='chosenPairWayNumber"+pairId+"' name='wayNumber'><option value='"+pair.firstSum+"'>"+pair.firstSum +"</option><option value='"+pair.secondSum+"'>"+pair.secondSum +"</option></select>";
		}
		else {
			htmlVal += "<input id='chosenPairWayNumber"+pairId+"'  type='hidden' name='wayNumber' value='-1' />";
		}
		htmlVal += "<input type='hidden' name='playerId' value='"+playerId+"'/>";
		htmlVal += "</form><br>";
	})
	tr.append(htmlVal);
	return;
}

function refreshPlayersClimbers(climbers) {
	var htmlCode = "";
	if (climbers == null) { return htmlCode; }
	
	for (var i = 0; i < climbers.length; i++) {
		var climber = climbers[i];
		htmlCode += "<img height=15 src='resources/images/climber.png'>";
	}
	return  htmlCode;
}
function refreshPlayersMarkers(markers) {
	var htmlCode = "";
	if (markers == null) { return htmlCode; }
	
	for (var i = 0; i < markers.length; i++) {
		var marker = markers[i];
		htmlCode += "<img height=15 src='resources/images/marker_"+marker.color+".png'>";	
	}
	return  htmlCode;
}

function refreshLastUsedPair(lastUsedPairInfo) {
	var htmlCode = "";
	if (lastUsedPairInfo == null) { return htmlCode; }
	var bgColor1 = "";
	if (lastUsedPairInfo.chosenWayNumber == lastUsedPairInfo.chosenPair.firstSum){
		bgColor1 = "grey";
	}
	
	var bgColor2 = "";
	if (lastUsedPairInfo.chosenWayNumber == lastUsedPairInfo.chosenPair.secondSum){
		bgColor2 = "grey";
	}
	
	htmlCode += "<div style='float:left; valign:middle; align:center; bgcolor:"+bgColor1+"'>";
	htmlCode += "<img width=25 src='resources/images/dice"+lastUsedPairInfo.chosenPair.firstPair.first.diceValue+".png'/>";
	htmlCode += "<img width=25 src='resources/images/dice"+lastUsedPairInfo.chosenPair.firstPair.second.diceValue+".png'/>";
	htmlCode += "</div>";

	htmlCode += "<div style='valign:middle; align:center; bgcolor:"+bgColor2+"'>";
	htmlCode += "<img width=25 src='resources/images/dice"+lastUsedPairInfo.chosenPair.secondPair.first.diceValue+".png'/>";
	htmlCode += "<img width=25 src='resources/images/dice"+lastUsedPairInfo.chosenPair.secondPair.second.diceValue+".png'/>";
	htmlCode += "</div>";
	
	return htmlCode;
}

function refreshPlayersList(players, actualPlayerId, lastUsedPairInfo) {
	if (players == null) { return; }
	
	for (var i = 0; i < players.length; i++) {
		var player = players[i];
		if (player.order == actualPlayerId ) {
			$('#inturn_'+player.order).show();
		}
		else {
			$('#inturn_'+player.order).hide();
		}
		
		$('#playersMarkers_'+player.order).html(refreshPlayersMarkers(player.markers));
		$('#playersClimbers_'+player.order).html(refreshPlayersClimbers(player.climbers));
		$('#lastUsedPair_'+player.order).hide();
		$('#lastUsedPairRow_'+player.order).html("");
		if (lastUsedPairInfo != null && lastUsedPairInfo.player.order == player.order) {
			$('#lastUsedPair_'+player.order).show();
			$('#lastUsedPairRow_'+player.order).html(refreshLastUsedPair(lastUsedPairInfo));
		}
	}
}

