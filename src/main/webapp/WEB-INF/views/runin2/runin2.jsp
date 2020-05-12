<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Runin2-Station"></c:param>
</c:import>
<section>

	<div class="container p-5">
		<div class="row justify-content-center">
			<h1>Runin2 Station</h1>
		</div>

		<form class="container-fluid"
			action="<%=request.getContextPath()%>/shopfloor/station_config_step_3?partNumber=${partNumberURL}"
			method="post">
			<div class="form-group row my-4">
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
					${messageNotification} Lorem ipsum, dolor sit amet consectetur
					adipisicing elit. Non sit culpa et possimus obcaecati pariatur
					dolorem expedita sunt, architecto impedit at numquam deleniti hic,
					delectus, nobis quod minima. Soluta, praesentium.</button>
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

</section>


<c:import url="/WEB-INF/common/footer.jsp" />