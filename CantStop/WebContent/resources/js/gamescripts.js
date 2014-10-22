function refreshBoard(board) {
	for (var i = 0; i < board.ways.length; i++) {
		var way = board.ways[i];
		var wayNumber = way.number;
		
		var wayTable = document.getElementById("wayTable_"+wayNumber);
		var wayTableHTML = "";
		
		// displaying Hut 
		var hutHTML = "<tr><td id='h_"+way.number+"' width=10 ";		
		var hutMarker = way.hut.marker;
		if (hutMarker != null) {
			 hutHTML += " bgcolor=" + marker.color;
		}
		hutHTML += ">" + wayNumber;
		
		var hutClimber = way.hut.climber;
		if (hutClimber != null) {
			hutHTML += wayNumber + "<img height=15 src='resources/images/climber.png'>";
		}
		hutHTML += "</td></tr>";
		wayTableHTML += hutHTML;
		
		// displaying RopePoints
		var arrayLength = way.ropePoints.length;
		for (var j = 0; j < way.ropePoints.length; j++) {
			var ropePoint =  way.ropePoints[arrayLength - j - 1];
			var climber = ropePoint.climber;
			var rpHTML = "<tr><td width='10'>";
			if (climber != null) {
				rpHTML="<img height=15 src='resources/images/climber.png'>";
			}	
			if (climber == null && (ropePoint.markers == null || ropePoint.markers.length == 0)) {
				rpHTML += "<img height=15 src='resources/images/empty.png'>";
			}
			// displaying markers
			rpHTML += "<table><tr>";
			for (var k = 0; k < ropePoint.markers.length; k++) {
				var marker = ropePoint.markers[k];
				rpHTML += "<td bgcolor='"+marker.color+"'>&nbsp;&nbsp;&nbsp;</td>";	
			}
			rpHTML += "</tr></table>";
				
			rpHTML += "</td></tr>";
			wayTableHTML += rpHTML;
			
		}
		wayTable.innerHTML = wayTableHTML; 
	}
}

function refreshDices(dices) {
	var htmlCode = "";
	if (dices == null) { return htmlCode; }
	
	for (var i = 0; i < dices.length; i++) {
		var dice = dices[i];
		htmlCode += "<td>"; 
		htmlCode += "<img width=25 src='resources/images/dice"+dice.diceValue+".png'/>";
		htmlCode += "</td>";
	}
	return  htmlCode;
}

function refreshPairsToChoose(tr, choosablePairsWithId, playerId) {
	if (choosablePairsWithId == null) { return; }
	tr.empty();
	var htmlVal = "";
	$.each( choosablePairsWithId, function(pairId,pair){
		htmlVal += "<div>";
		htmlVal += "<img width=30 src='resources/images/dice"+pair.firstPair.first.diceValue+".png'/>"; 
		htmlVal += "<img width=30 src='resources/images/dice"+pair.firstPair.second.diceValue+".png'/>";
		htmlVal += "<img width=30 src='resources/images/dice"+pair.secondPair.first.diceValue+".png'/>";
		htmlVal += "<img width=30 src='resources/images/dice"+pair.secondPair.second.diceValue+".png'/>";
		htmlVal += "<form  method='post'>";
		// TODO localized BUTTON LABEL
		htmlVal += pairId + "<input type='button' value='BUTTON.PAIRWAHL' onclick=\"doAjaxPost('do.executePairAJAX', '"+playerId+"', '"+pairId+"') \"  />";
		//<!-- TODO real enum should be used -->
		if (pair.pairChoiceInfo == 'WITHWAYINFO') {
			htmlVal += "<select id='chosenPairWayNumber"+pairId+"' name='wayNumber'><option value='"+pair.firstSum+"'>"+pair.firstSum +"</option><option value='"+pair.secondSum+"'>"+pair.secondSum +"</option></select>";
		}
		else {
			htmlVal += "<input id='chosenPairWayNumber"+pairId+"'  type='hidden' name='wayNumber' value='-1' />";
		}
		htmlVal += "<input type='hidden' name='playerId' value='"+playerId+"'/>";
		htmlVal += "</form></div>";
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
		htmlCode += "<td bgcolor="+marker.color+">&nbsp;&nbsp;&nbsp;</td>";
	}
	return  htmlCode;
}

function refreshLastUsedPair(lastUsedPairInfo, actualPlayerId) {
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
	
	htmlCode += "<td valign=middle align=center bgcolor='"+bgColor1+"'>";
	htmlCode += "<img width=30 src='resources/images/dice"+lastUsedPairInfo.chosenPair.firstPair.first.diceValue+".png'/>";
	htmlCode += "<img width=30 src='resources/images/dice"+lastUsedPairInfo.chosenPair.firstPair.second.diceValue+".png'/>";
	htmlCode += "</td>";

	htmlCode += "<td valign=middle align=center bgcolor='"+bgColor2+"'>";
	htmlCode += "<img width=30 src='resources/images/dice"+lastUsedPairInfo.chosenPair.secondPair.first.diceValue+".png'/>";
	htmlCode += "<img width=30 src='resources/images/dice"+lastUsedPairInfo.chosenPair.secondPair.second.diceValue+".png'/>";
	htmlCode += "</td>";
	
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
		$('#lastUsedPair_'+player.order).show();
		$('#lastUsedPairRow_'+player.order).html(refreshLastUsedPair(lastUsedPairInfo, actualPlayerId));
	}
}

