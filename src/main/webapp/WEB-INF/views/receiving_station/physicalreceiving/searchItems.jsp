<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Searching Item"></c:param>
</c:import>

<section>

	<div class="container-fluid px-5 ">

		<div class="jumbotron row">
			<div class="col-lg-2"></div>
			<div class="col-lg-8  col-sm-12">
				<h1 class="text-center text-primary p-2 display-4">
					<strong>Searching Item</strong>
				</h1>

				<form action="<%=request.getContextPath()%>/searchitem"
					method="POST">

					<input id="input-2" class="form-control form-control-lg"
						name="ppid" placeholder="Enter PPID number" type="text" required
						value="${ppidValue}" />


					<div class="row justify-content-center mt-5">
						<Button type="submit" class="btn btn-primary btn-lg">
							<strong>Search Item</strong>
						</Button>
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
</section>


<c:import url="/WEB-INF/common/footer.jsp" />