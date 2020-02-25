<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/common/header.jsp" />


<div class="container p-5">

	<a type="button" class="btn btn-primary mx-5"
		href="<%=request.getContextPath()%>/pre_alert" role="button">Pre-Alert</a>

	<a type="button" class="btn btn-primary"
		href="<%=request.getContextPath()%>/" role="button">Pre-Alert</a>
</div>

<jsp:include page="/WEB-INF/common/footer.jsp" />