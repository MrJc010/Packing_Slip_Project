<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Searching Item"></c:param>
</c:import>

<section>

	<div class="container-fluid  p-5" ${hidden}>

		<div class="row bg-light">
			<div class="col-md-2"></div>
			<div class="col-md-8 col-sm-12  py-2 px-5">
				<h4 class="text-center text-primary p-1 display-4">
					<strong>PHYSICAL RECEIVING</strong>
				</h4>

				<form action="<%=request.getContextPath()%>/searchitem"
					method="POST">
					<div class="input-group mb-3">
						<input id="input-2" class="form-control form-control-lg"
							name="ppid" placeholder="Enter PPID number" type="text" required
							value="${ppidValue}" required /> <input type="submit"
							class="btn btn-primary btn-lg" value="SEARCH">

					</div>


				</form>

			</div>
			<div class="col-lg-2"></div>
		</div>

		<%-- 		<div class="row justify-content-center">
			<c:if test="${Successfull == 'Successfull'}">
				<script type="text/javascript">
					var msg = "Item received succcessfully. Move to Physical Station.";
					alert(msg);
				</script>
			</c:if>
			<c:set var="salary" scope="session" value="${re}" />
			<c:if test="${re == \"Cannot Find Item With Your Info!\"}">
				<script type="text/javascript">
					var msg = "Cannot Find Item With Your Info!";
					alert(msg);
				</script>
			</c:if>
		</div> --%>

		<div class="row justify-content-center my-5" ${setHiddenError}>
			<div class="alert alert-warning" role="alert">
				<h1 class="display-4">
					<strong>${errorMessage}</strong>
				</h1>
				<h1 class="display-4">
					<strong>Please check with manager.</strong>
				</h1>

			</div>
		</div>

		<div class="row justify-content-center my-5" ${setHiddenSuccess}>
			<div class="alert alert-success" role="alert">

				<h1 class="display-4">
					<strong>${successMessage}</strong>
				</h1>
			</div>
		</div>
	</div>
</section>


<c:import url="/WEB-INF/common/footer.jsp" />