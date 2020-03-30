<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Search Info"></c:param>
</c:import>


<section>

	<div class="container-fluid p-5">

		<%-- Search Bar --%>
		<div class="row my-1 justify-content-center">
			<div>
				<form action="<%=request.getContextPath()%>/repair01" method="GET">
					<div class="form-row">
						<div class="form-group col-md-10 px-3">
							<h2 class="text-center text-primary p-1 display-5">
								<strong>Enter and Search your PPID</strong>
							</h2>
							<input type="hidden" name="action01" value="findPPID">
							<div class="input-group mb-3">
								<input class="form-control form-control-lg" type="text"
									name="inputppid" placeHolder="Enter PPID" required>
								<div class="input-group-append">

									<input type="submit" class="btn btn-primary btn-lg"
										value="SEARCH" name="actionSubmitRepair01">
								</div>
							</div>

						</div>

					</div>
					<div class="form-row mx-auto">
						<div class="form-group col-md-1 px-3">
							<label for="refInput"><strong>Ref</strong></label> <select
								id="refInput" class="form-control" name="refInput">
								<option selected>Choose...</option>
								<option>...</option>
							</select>
						</div>
						<div class="form-group col-md-1 px-3">
							<label for="optionInput"><strong>Option</strong></label> <select
								id="optionInput" class="form-control" name="optionInput">
								<option selected>Choose...</option>
								<option>...</option>
							</select>
						</div>
						<div class="form-group col-md-2 px-3">
							<label for="inputRefValue"><strong>Enter Ref
									Value To Search</strong></label> <input type="text" class="form-control"
								id="inputRefValue" name="inputRefValue" />
						</div>

						<div class="form-group col-md-4 px-3">
							<label for="inputPPID"><strong>PPID</strong></label> <input
								type="text" class="form-control" id="inputPPID" name="inputPPID" />
						</div>

						<div class="form-group col-md-4 px-3">
							<label for="inputStationName"><strong>Station
									Name</strong></label> <input type="text" class="form-control"
								id="inputStationName" name="inputStationName" />
						</div>
					</div>



				</form>

			</div>


		</div>

	</div>
</section>
<script>
	console.log("Script");
</script>
<jsp:include page="/WEB-INF/common/footer.jsp" />