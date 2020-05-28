<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="QC1-Station"></c:param>
</c:import>
<section>

	<div class="container-fluid p-5">

<%-- 		<form class="container-fluid"
			action="<%=request.getContextPath()%>/qc1" method="get">
			<div class="form-group row my-4">
				<input type="hidden" name="action" value="findPPID"> <label
					for="stationAvaiable" class="col-md-2 col-form-label"><h5>
						<strong>Enter PPID:</strong>
					</h5> </label>
				<div class="col-md-8">
					<input type="text" class="form-control" name="inputPPID0"
						value="${ppid}" required />
				</div>
				<div class="col-md-1">
					<input type="submit" class="btn btn-md btn-primary" value="Search"
						name="action" />
				</div>
			</div>
		</form> --%>
		
		
		<div class="row bg-light">
			<div class="col-md-2"></div>
			<div class="col-md-8 col-sm-12  py-2 px-5">
				<h3 class="text-center text-primary p-1 display-3">
					<strong>QC1 STATION</strong>
				</h3>
				<form action="<%=request.getContextPath()%>/qc1" method="GET">
					<div class="input-group mb-3">
						<input type="hidden" name="action" value="findPPID"> <input
							type="text" class="form-control form-control-lg" id="PPID"
							aria-describedby="emailHelp" placeholder="Enter PPID"
							name="inputPPID0" required value="${ppid}"> 
							<div class="input-group-append">
							<input type="submit" name="action" class="btn btn-primary btn-lg" value="FIND">
						</div>	
					</div>
				</form>
			</div>

		</div>
		
		
		<form class="container-fluid"
			action="<%=request.getContextPath()%>/qc1" method="post">
			<div class="form-group row my-4 justify-content-center"
				${setHiddenNotification}>
				<p class="btn-outline-info font-weight-bold disable">
					${messageNotification}</p>
			</div>
			<div class="form-group row my-4 justify-content-center">
				<div class="btn-group" role="group">

					<input type="hidden" name="action" value="submitAction">

					<button name="btnSubmit" class="btn btn-success mx-5"
						${setHiddenBTNPASS} value="Pass">${passedValue}</button>
					<button name="btnSubmit" class="btn btn-danger mx-5"
						${setHiddenBTNFAIL} value="Fail">${failValue}</button>

				</div>
			</div>
		</form>
	</div>

	<div class="container"></div>
	</div>
</section>


<c:import url="/WEB-INF/common/footer.jsp" />