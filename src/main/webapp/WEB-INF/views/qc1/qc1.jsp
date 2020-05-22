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
			action="<%=request.getContextPath()%>/qc1"
			method="get">
			<div class="form-group row my-4">
			<input type="hidden" name="action" value="findPPID">
				<label for="stationAvaiable" class="col-md-2 col-form-label"><h5>
						<strong>Enter PPID:</strong>
					</h5> </label>
				<div class="col-md-8">
					<input type="text" class="form-control" name="inputPPID0"
						value="${inputPPID}" />
				</div>
				<div class="col-md-1">
					<input type="submit" class="btn btn-md btn-primary" value="Search"
						name="action" />
				</div>
			</div>			
			<div class="form-group row my-4 justify-content-center"
				${setHiddenNotification}>
				<button type="button" class="btn btn-outline-info">
					${messageNotification}</button>
			</div>
			<div class="form-group row my-4 justify-content-center">
				<div class="btn-group" role="group">
					<button name="action" class="btn btn-success mx-5">
						<h1>PASS</h1>
					</button>
					<button name="action" class="btn btn-warning">
						<h1>FAIL</h1>
					</button>
				</div>
			</div>
		</form>
	</div>


	<div class="container">
		<div class="row">
			<h1>Pass</h1>
		</div>
		
		<div class="row">
		<h1>FAIL</h1>
		
		</div>
	</div>
</section>


<c:import url="/WEB-INF/common/footer.jsp" />