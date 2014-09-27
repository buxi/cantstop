<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
New Board Design
<c:set var="board" value="${gameInfo.board }"/>
<table><tr>
<c:forEach items="${board.ways}" var="way">
	<td><table border=1>
		<!-- // displaying Hut -->
		<c:set var="marker" value="${way.hut.marker}"/>
		<tr><td width=10 <c:if test="${ marker != null}"> bgcolor="${marker.color }"</c:if> >
			${way.number}	
			<c:set var="hut" value="${way.hut}"/>
			<c:set var="climber" value="${hut.climber}"/>
			<c:if test="${climber != null}">
				<!-- display climber -->
				<img height=15 src="resources/images/climber.png">
			</c:if>
		</td></tr>
		
		<!-- // displaying RopePoints -->
		<c:set var="arrayLength" value="${fn:length(way.ropePoints)}"/>
		
		<c:forEach items="${way.ropePoints}"  varStatus="i">
			<tr><td width="10">
			<c:set var="ropePoint" value="${way.ropePoints[arrayLength - i.count]}"/>
			<c:set var="climber" value="${ropePoint.climber}"/>
			<c:if test="${climber != null}">
				<!-- display climber -->
				<img height=15 src="resources/images/climber.png">
			</c:if>
			<c:if test="${climber == null && empty(ropePoint.markers)}">
				<!-- display empty -->
				<img height=15 src="resources/images/empty.png">
			</c:if>
			<!-- displaying markers -->
			<table><tr><c:forEach items="${ropePoint.markers}" var="marker"><td bgcolor="${marker.color }">&nbsp;&nbsp;&nbsp;</td></c:forEach></tr></table>
			</td></tr>
		</c:forEach>
	</table></td>
</c:forEach>
</tr></table>