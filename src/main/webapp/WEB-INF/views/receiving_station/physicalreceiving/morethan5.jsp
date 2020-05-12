<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="More than 5 page"></c:param>
</c:import>

<section>

	<div class="container mt-5 text-center">
		<h1 class="display-3 text-primary">
			<strong>PPID IS MORE THAN 5 TIME RECEIVED</strong>
		</h1>

		<span class="display-4 text-center my-5 p-5"> PPID: <%=request.getParameter("ppid")%></span>


		<div class="border border-warning rounded mt-5 p-5">
			<div class="row text-center mt-5">
				<span class="display-4 text-info">Do you want to <span class="text-danger"><strong>RECYCLE</strong></span> this
					item? (Will move to MoveToScrap01)</span>
			</div>

			<div class="row text-center mt-5 justify-content-center">
				<form method="POST"
					action="<%=request.getContextPath()%>/morethanfive">

					<input class="btn btn-danger btn-lg mx-5" type="submit"
						name="buttonAction" value="RECYCLE" /> <input
						class="btn btn-secondary btn-lg mx-5" type="submit"
						name="buttonAction" value="CANCEL" />

				</form>

			</div>

		</div>


	</div>


</section>

<jsp:include page="/WEB-INF/common/footer.jsp" />