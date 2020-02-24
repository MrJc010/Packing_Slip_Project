<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/common/header.jsp" />


<section>

	<div class="container">
		<h1> Pre Alert Upload File Excel</h1>
		<form method="post" action="<%=request.getContextPath() %>/prealert" enctype="multipart/form-data">
			Select file to upload: <input type="file" name="uploadFile" /> <br />
			<br /> <input type="submit" value="Upload" />
		</form>
	</div>

</section>


<jsp:include page="/WEB-INF/common/footer.jsp" />