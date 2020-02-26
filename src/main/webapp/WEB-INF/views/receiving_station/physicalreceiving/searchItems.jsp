<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/common/header.jsp" />


<section>

	<div class="container">
		<h1>Searching Item</h1>
		<form action="<%=request.getContextPath()%>/searchitem" method="POST">
			<input id="input-2" name="ppid" placeholder="Enter PPID number"
				type="text" required /><br> <input id="input-3" name="dps"
				placeholder="Enter DPS number" type="text" required /><br> <input
				type="submit" value="Search" />
		</form>
		<c:set var="salary" scope="session" value="${re}" />
		<c:if test="${re == \"Cannot Find Item With Your Info!\"}">
			<script type="text/javascript">
    			var msg = "Cannot Find Item With Your Info!";
				alert(msg);
			</script>
		</c:if>
	</div>

</section>


<jsp:include page="/WEB-INF/common/footer.jsp" />