<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Repair02 Station"></c:param>
</c:import>
<section>

	<div class="container-fluid p-5">

		<%-- Search Bar --%>
		<div class="row jumbotron my-1">
			<div class="col-lg-2"></div>
			<div class="col-lg-8  col-sm-12">
				<h3 class="text-center text-primary p-1 display-5">
					<strong>Enter and Search your PPID</strong>
				</h3>
				<form action="<%=request.getContextPath()%>/repair01" method="GET">
					<input type="hidden" name="action01" value="findPPID">
					<div class="input-group mb-3">
						<input class="form-control form-control-lg" type="text"
							name="inputppid" placeHolder="Enter PPID" required>
						<div class="input-group-append">

							<input type="submit" class="btn btn-primary btn-lg" value="FIND"
								name="actionSubmitRepair01">
						</div>
					</div>
				</form>
			</div>
			<div class="col-lg-2"></div>


		</div>
		<div>
			<div class="alert alert-success" role="alert"
				${setTransferMessageSuccess}>
				<strong>This PPID is PASSED!</strong>
			</div>
		</div>
		<div class="row justify-content-center p-5" ${setHiddenTransfer}>
			<form action="<%=request.getContextPath()%>/repair01" method="POST">
				<input type="hidden" name="action01" value="TransferAction">
				<button class="btn btn-success btn-xl" type="submit">
					<h2>TRANSFER TO MICI</h2>
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



		<div class="my-3 container text-center" ${setRepair01HiddenError}>

			<div class="alert alert-danger" role="alert"
				${setErrorMessageHidden }>
				<strong><span class="display-3">${setErrorMessage}</span></strong>
			</div>

			<div class="alert alert-success" role="alert"
				${setSuccessMessageHidden }>
				<strong><span class="display-4">${setSuccessMessage }</span></strong>
			</div>
		</div>

			<!-- REPAIR ERROR  -->

				<div class="p-5">
					<c:set var="indexValue" value="${0}" scope="page" />
					<c:forEach items="${errorList}" var="aError">
						<c:set var="indexValue" value="${indexValue +1}" scope="page" />
						<div class="accordion" id="accordionExample">
							<div class="card">
								<div class="card-header" id="headingOne">
									<strong><span class="mb-0">
											<button class="btn btn-link" type="button"
												data-toggle="collapse" data-target="#xx${indexValue}"
												aria-expanded="false" aria-controls="xx${indexValue}">
												${aError}</button>
									</span> </strong>
									<button class="float-right btn btn-primary"
										name="edit${aError}" onclick="enableForm(${indexValue})">Edit</button>
								</div>

								<div id="xx${indexValue}" class="collapse"
									aria-labelledby="headingOne" data-parent="#accordionExample">
									<div class="card-body">
										<form method="POST"
											action="<%=request.getContextPath()%>/repair01">
											<input type="hidden" name="action01" value="SubmitAction" />
											<input type="hidden" name="errorValueAction"
												value="${aError}" />
											<fieldset id="disable${indexValue}" disabled>
												<div class="input-group mb-3">
													<div class="input-group-prepend">
														<label class="input-group-text" for="duty">Select
															Duty: </label>
													</div>
													<select class="custom-select" id="duty"
														name="duty${aError}" required>
														<option value="0" selected="">Choose a duty</option>
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
													<textarea type="text" class="form-control"
														name="action${aError}" rows="4" id="actionInput"
														aria-describedby="emailHelp" placeholder="Enter action"
														required></textarea>
													<button type="submit" value="${aError}" name="submitButton"
														class="btn btn-primary my-5 text-center mx-auto">Submit</button>
												</div>
											</fieldset>
										</form>
									</div>
								</div>
							</div>
						</div>		</c:forEach>
					<script>
						function enableForm(errorCode) {
							document.getElementById("disable" + errorCode).disabled = false;
						}
					</script>
				</div>
			</div>
		</div>
	</div>
</section>
<c:import url="/WEB-INF/common/footer.jsp" />
