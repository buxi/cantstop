function refreshBoard(board) {
	for (var i = 0; i < board.ways.length; i++) {
		var way = board.ways[i];
		var wayNumber = way.number;
		
		var wayTable = document.getElementById("wayTable_"+wayNumber);
		var wayTableHTML = "";
		//debugger;
		// displaying Hut 
		var hutHTML = "<tr><td id='h_"+way.number+"' style='align:center; valign: middle ; border:1px solid black; width:30px; height:30px'";		
		var hutMarker = way.hut.marker;
		if (hutMarker != null) {
			 hutHTML += " bgcolor=" + hutMarker.color;
		}
		else {
			hutHTML += " bgcolor=white";
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
			var rpHTML = "<tr><td  style='background-color:white; border:1px solid black; width:30px; height:30px'>";
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
		htmlCode += "<img style='margin:1px' width="+widthInfo+" src='resources/images/dice"+dice.diceValue+".png'/>";
	}
	return  htmlCode;
}

function refreshPairsToChoose(tr, choosablePairsWithId, playerId) {
	if (choosablePairsWithId == null) { return; }
	tr.empty();
	var htmlVal = "";
	$.each( choosablePairsWithId, function(pairId,pair){
		htmlVal += "<div style='overflow: hidden; white-space: nowrap; border: 0px solid black;'>";
		
		if (pair.pairChoiceInfo == 'WITHWAYINFO') {
			htmlVal += "<a class='dicepairs' style='float:left' onclick=\"doAjaxPostWithWayNumber('do.executePairAJAX', '"+playerId+"', '"+pairId+"', '"+pair.firstSum+"') \" >";
			htmlVal += "<img style='margin:1px' width=25 src='resources/images/dice"+pair.firstPair.first.diceValue+".png'/>"; 
			htmlVal += "<img style='margin:1px' width=25 src='resources/images/dice"+pair.firstPair.second.diceValue+".png'/>";
			htmlVal += "</a>";
		
			htmlVal += "<a class='dicepairs' style='float:left' onclick=\"doAjaxPostWithWayNumber('do.executePairAJAX', '"+playerId+"', '"+pairId+"', '"+pair.secondSum+"') \" >";
			htmlVal += "<img style='margin:1px' width=25 src='resources/images/dice"+pair.secondPair.first.diceValue+".png'/>";
			htmlVal += "<img style='margin:1px' width=25 src='resources/images/dice"+pair.secondPair.second.diceValue+".png'/>";
			htmlVal += "</a>";
		}
		else {
			htmlVal += "<a class='dicepairs' style='float:left' onclick=\"doAjaxPost('do.executePairAJAX', '"+playerId+"', '"+pairId+"') \" >";
			htmlVal += "<img style='margin:1px' width=25 src='resources/images/dice"+pair.firstPair.first.diceValue+".png'/>"; 
			htmlVal += "<img style='margin:1px' width=25 src='resources/images/dice"+pair.firstPair.second.diceValue+".png'/>";
			htmlVal += "<img style='margin:1px' width=25 src='resources/images/dice"+pair.secondPair.first.diceValue+".png'/>";
			htmlVal += "<img style='margin:1px' width=25 src='resources/images/dice"+pair.secondPair.second.diceValue+".png'/>";
			htmlVal += "</a>";
		}
		htmlVal += "<br></div>";
	})
	tr.append(htmlVal);
	return;
}

function refreshPlayersClimbers(climbers) {
	var htmlCode = "";
	if (climbers == null) { return htmlCode; }
	
	for (var i = 0; i < climbers.length; i++) {
		var climber = climbers[i];
		htmlCode += "<img style='margin:2px' height=15 src='resources/images/climber.png'>";
	}
	return  htmlCode;
}
function refreshPlayersMarkers(markers) {
	var htmlCode = "";
	if (markers == null) { return htmlCode; }
	
	for (var i = 0; i < markers.length; i++) {
		var marker = markers[i];
		htmlCode += "<img style='margin:2px' height=15 src='resources/images/marker_"+marker.color+".png'>";	
	}
	return  htmlCode;
}

function refreshLastUsedPair(lastUsedPairInfo) {
	var htmlCode = "";
	if (lastUsedPairInfo == null) { return htmlCode; }
	var border1 = "";
	if (lastUsedPairInfo.chosenWayNumber == lastUsedPairInfo.chosenPair.firstSum){
		border1 = "border:2px solid red;";
	}
	
	var border2 = "";
	if (lastUsedPairInfo.chosenWayNumber == lastUsedPairInfo.chosenPair.secondSum){
		border2 = "border:2px solid red;";
	}
	htmlCode += "<div>"; 
	
	htmlCode += "<div style='width:50; float:left; "+border1+"'>";
	htmlCode += "<img style='margin:1px' width=25 src='resources/images/dice"+lastUsedPairInfo.chosenPair.firstPair.first.diceValue+".png'/>";
	htmlCode += "<img style='margin:1px' width=25 src='resources/images/dice"+lastUsedPairInfo.chosenPair.firstPair.second.diceValue+".png'/>";
	htmlCode += "</div>";

	htmlCode += "<div style='width:50; float:left; "+border2+"'>";
	htmlCode += "<img style='margin:1px' width=25 src='resources/images/dice"+lastUsedPairInfo.chosenPair.secondPair.first.diceValue+".png'/>";
	htmlCode += "<img style='margin:1px' width=25 src='resources/images/dice"+lastUsedPairInfo.chosenPair.secondPair.second.diceValue+".png'/>";
	htmlCode += "</div>";
	
	htmlCode += "</div>";
	return htmlCode;
}

function refreshPlayersList(players, actualPlayerId, lastUsedPairInfo) {
	if (players == null) { return; }
	
	for (var i = 0; i < players.length; i++) {
		var player = players[i];
		if (player.order == actualPlayerId ) {
			$('#inturn_'+player.order).show();
			$('#inturnplayer_'+player.order).css({"border-color": "red", 
	             "border-weight":"1px", 
	             "border-style":"solid"});
			
		}
		else {
			$('#inturn_'+player.order).hide();
			$('#inturnplayer_'+player.order).css({"border-color": "grey", 
	             "border-weight":"0px", 
	             "border-style":"solid"});
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

