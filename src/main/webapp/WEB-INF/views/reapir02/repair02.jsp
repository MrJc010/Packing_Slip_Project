<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Repair02-Station"></c:param>
</c:import>
<section>

	<!-- Modal -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-xl modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel"></h4>
					</div>
					<div class="modal-body">
						<img class="img-fluid" id="mimg" src="">
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		
		
		<div class="container-fluid  p-5" ${hidden}>

		<%-- Search Bar --%>
		<div class="row bg-light">
			<div class="col-md-2"></div>
			<div class="col-md-8 col-sm-12  py-2 px-5">
				<h3 class="text-center text-primary p-1 display-5">
					<strong>REPAIR02 STATION</strong>
				</h3>
				<form method="GET" action="<%=request.getContextPath()%>/mici">

					<div class="form-group">
						<input type="hidden" name="page" value="check"> <input type="text"
							class="form-control" id="PPID" aria-describedby="emailHelp"
							placeholder="Enter PPID" name="ppidNumber" required
							value="${ppid}">
					</div>

					<div class="form-groud text-center my-3">
						<button type="submit" class="btn btn-primary btn-lg">Check</button>
					</div>

				</form>

			</div>

		</div>
		
		<div class="container text-center mt-2" ${setHiddenResultSucess}>
			<div class="alert alert-success" role="alert">
			<label class="text-dark"><h4>${messageSuccess}</h4></label>
			</div>
		</div>
		<div class="container text-center" ${seterrorhiddenMICI}>
			<div class="alert alert-warning mt-2" role="alert">
				<label class="text-dark"><h4>${errorMessage}</h4></label>
			</div>
		</div>
		
		
		
		<div class="row justify-content-center p-2" ${setHiddenTransfer}>
			<form action="<%=request.getContextPath()%>/repair02" method="POST">
				<input type="hidden" name="action" value="TransferAction">
				<button class="btn btn-success btn-xl" type="submit">
					<h2>TRANSFER TO MICI</h2>
				</button>
			</form>
		</div>
		<div class="row justify-content-center p-2" ${setHiddenBGA}>
			<form action="<%=request.getContextPath()%>/repair02" method="POST">
				<input type="hidden" name="action" value="TransferBGA">
				<button class="btn btn-success btn-xl" type="submit">
					<h2>TRANSFER TO BGA</h2>
				</button>
			</form>
		</div>
		<%-- Information Details --%>
		<div class="row p-4 justify-content-center" ${setInfoPPIDetails}>

			<div class="col-md-8 p-3  col-sm-12 text-left">
				<div class="row p-1 border">
					<div class="col-4">
						<h4>
							<span class="badge badge-info">PPID: </span>
						</h4>
					</div>
					<div class="col-8  pl-3">
						<h4>${setPPID}</h4>
					</div>
				</div>

				<div class="row p-1 border">
					<div class="col-4">
						<h4>
							<span class="badge badge-info">Current Reversion: </span>
						</h4>
					</div>
					<div class="col-8  pl-3">
						<h4>${curRevNumber}
							<span class="text-${iconColor} }"> <i
								class="fas fa-exclamation-triangle" data-toggle="tooltip"
								data-placement="top" title="${messageIcon}"></i>
							</span>
						</h4>
					</div>
				</div>

				<div class="row p-1 border">
					<div class="col-4">
						<h4>
							<span class="badge badge-info">Error Counts: </span>
						</h4>
					</div>
					<div class="col-8 pl-3">
						<span class="badge badge-${setErrorColor}"><h5 class="p-1">
								<strong>${currentErrorNumber}</strong>
							</h5></span>

					</div>
				</div>

			</div>
		</div>


	

	</div>

</section>


<c:import url="/WEB-INF/common/footer.jsp" />