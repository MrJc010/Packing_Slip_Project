<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Repair02 Station"></c:param>
</c:import>
<section>
	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
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

	<div class="container-fluid p-5">

		<%-- Search Bar --%>
		<div class="row bg-light">
			<div class="col-md-2"></div>
			<div class="col-md-8 col-sm-12  py-2 px-5">
				<h4 class="text-center text-primary p-1 display-4">
					<strong>REPAIR01 STATION</strong>
				</h4>
				<form action="<%=request.getContextPath()%>/repair01" method="GET">
					<input type="hidden" name="action01" value="findPPID">
					<div class="input-group mb-3">
						<input class="form-control form-control-lg" type="text"
							name="inputppid" placeHolder="Enter PPID" required value=${ppid} >
						<div class="input-group-append">

							<input type="submit" class="btn btn-primary btn-lg" value="FIND"
								name="actionSubmitRepair01">
						</div>
					</div>
				</form>
			</div>
			<div class="col-lg-2"></div>


		</div>
		<div class="container text-center mt-5" ${setHiddenResultSucess}>
			<div class="alert alert-success" role="alert">
				<h4>
					<label class="text-dark">${messageSuccess}</label>
				</h4>
			</div>
		</div>
		<div class="container text-center" ${seterrorhiddenMICI}>
			<div class="alert alert-warning mt-5" role="alert">
				<h4>
					<label class="text-dark">${errorMessage}</label>
				</h4>
			</div>
		</div>
		<div class="row justify-content-center p-5 mt-4" ${setHiddenTransferButton}>
			<form action="<%=request.getContextPath()%>/repair01" method="POST">
				<input type="hidden" name="action01" value="transferToMICI">
				<button class="btn btn-success btn-xl" type="submit">
					<h2>TRANSFER TO MICI</h2>
				</button>
			</form>
		</div>

		<%-- Information Details --%>
		<div class="row p-4 justify-content-center" ${setInfoHidden}>

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

<%-- 				<div class="row p-1 border">
					<div class="col-4">
						<h4>
							<span class="badge badge-info">Current Reversion: </span>
						</h4>
					</div>
					<div class="col-8  pl-3">
						<h4>${curRevNumber}
							<span class="text-${iconColor}}"> <i
								class="fas fa-exclamation-triangle" data-toggle="tooltip"
								data-placement="top" title="${messageIcon}"></i>
							</span>
						</h4>
					</div>
				</div> --%>

				<div class="row p-1 border">
					<div class="col-4">
						<h4>
							<span class="badge badge-info">Error Counts: </span>
						</h4>
					</div>
					<div class="col-8 pl-3">
						<h5 class="p-1"><span class="badge badge-${setErrorColor}">
								<strong>${currentErrorNumber}</strong>
							</span></h5>

					</div>
				</div>

			</div>
		</div>




		<%-- Body Revision + Errorcodes --%>
		<div class="container-fluid p3" ${reapir01BodyHidden}>
					<div class="p-5">
						<c:set var="indexValue" value="${0}" scope="page" />
						<c:forEach items="${errorList}" var="aError">
							<c:set var="indexValue" value="${indexValue +1}" scope="page" />
							<div class="accordion" id="accordionExample">
								<div class="card">
									<div class="card-header bg-dark" id="headingOne">
										<strong><span class="mb-0">
												<button class="btn btn-link text-white" type="button"
													data-toggle="collapse" data-target="#xx${indexValue}"
													aria-expanded="false" aria-controls="xx${indexValue}">
													<h3>${aError}</h3></button>
										</span> </strong>
									</div>

									<div id="xx${indexValue}" class="collapse" aria-labelledby="headingOne" data-parent="#accordionExample">
										<div class="card-body">
											<form method="POST"
												action="<%=request.getContextPath()%>/repair01">
												<input type="hidden" name="action01" value="fix" />
												<input type="hidden" name="errorValueAction"
													value="${aError}" />
												<fieldset id="disable${indexValue}"  >
													<div class="input-group mb-3">
														<div class="input-group-prepend">
															<label class="input-group-text" for="duty">Select
																Duty: </label>
														</div>
														<select class="custom-select" id="duty"
															name="duty${aError}" required>
															<option value="0" selected>Choose a duty</option>
															<option value="duty 01">duty 01</option>
															<option value="duty 02">duty 02</option>
															<option value="duty 03">duty 03</option>
														</select>
													</div>

													<br>
													<div class="form-group">
														<label for="oldPN">OLD PN</label> <input type="text"
															class="form-control" name="oldPN${aError}"
															placeholder="Enter Old PN" required>
													</div>


													<br>

													<div class="form-group">
														<label for="oldPN">NEW PN</label> <input type="text"
															class="form-control" name="newPN${aError}"
															placeholder="Enter new PN" required>
													</div>

													<br>

													<div class="form-group">
														<label for="oldPN">Location</label> <input type="text"
															class="form-control" name="area${aError}"
															placeholder="Enter area" required>
													</div>


													<br>
													<div class="form-group">
														<label for="actionInput">Action</label>
														<textarea class="form-control"
															name="action${aError}" rows="4" id="actionInput"
															aria-describedby="emailHelp" placeholder="Enter action"
															required></textarea>
														
													</div>
													<div class="text-center">
													<button type="submit" value="${aError}"
															name="submitButton"
															class="btn btn-primary mx-auto btn-lg px-5"><h3 class="m-0">SUBMIT</h3></button>
													</div>
													
												</fieldset>
											</form>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
						<script>
					
					</script>
					</div>
				
			
		</div>
	</div>
</section>
<script>
		function imgClickHandler(sr)
        {		
            $('#mimg').attr('src',sr);
            $('#myModal').modal('show');
        };

</script>
<c:import url="/WEB-INF/common/footer.jsp" />
