<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="QC1-Station"></c:param>
</c:import>
<section>

	<div class="container p-5">
		<div class="row justify-content-center">
			<h1>QC1 Station</h1>
		</div>

		<form class="container-fluid"
			action="<%=request.getContextPath()%>/qc1" method="get">
			<div class="form-group row my-4">
				<input type="hidden" name="action" value="findPPID"> <label
					for="stationAvaiable" class="col-md-2 col-form-label"><h5>
						<strong>Enter PPID:</strong>
					</h5> </label>
				<div class="col-md-8">
					<input type="text" class="form-control" name="inputPPID0"
						value="${ppid}" required/>
				</div>
				<div class="col-md-1">
					<input type="submit" class="btn btn-md btn-primary" value="Search"
						name="action" />
				</div>
			</div>
			<div class="form-group row my-4 justify-content-center"
				${setHiddenNotification}>
				<button type="button" class="btn btn-outline-info font-weight-bold">
					${messageNotification}</button>
			</div>
			<div class="form-group row my-4 justify-content-center">
				<div class="btn-group" role="group">
					<form class="container-fluid"
						action="<%=request.getContextPath()%>/qc1" method="post">
						
						<input type="hidden" name="action" value="submitAction"> 
						
						<button name="btnSubmit" class="btn btn-success mx-5"
							${setHiddenBTNPASS} value="Pass">${passedValue}
						</button>
						<button name="btnSubmit" class="btn btn-danger mx-5"
							${setHiddenBTNFAIL} value="Fail"> ${failValue}
						</button>
					</form>

				</div>
			</div>
		</form>
	</div>

	<div class="container"></div>
	</div>
</section>


<c:import url="/WEB-INF/common/footer.jsp" />