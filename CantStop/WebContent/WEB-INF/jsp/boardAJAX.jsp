<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<!-- displays an empty board, a Javascript should do the rest 
	 and show the figures and markers  -->
<c:set var="board" value="${gameInfo.board }"/>
<table><tr>
<c:forEach items="${board.ways}" var="way">
	<td><table border=1 id="wayTable_${way.number}"></table></td>
</c:forEach>
</tr></table>