<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/common/header.jsp" />


<section>

	<div class="container">

		<H1 class="text-center">Repair 01</H1>


		<div class="row">


			<div class="col-lg-2"></div>
			<div class="col-lg-8">
				<form action="<%=request.getContextPath()%>/repair01" method="GET">
					<input type="hidden" name="action01" value="findPPID">
					<div class="input-group mb-3">

						<input class="form-control" type="text" name="inputppid"
							placeHolder="Enter PPID" required>
						<div class="input-group-append">

							<input type="submit" class="btn btn-primary " value="FIND"
								name="actionSubmitRepair01">
						</div>

					</div>
				</form>
			</div>
			<div class="col-lg-2"></div>


		</div>

		<div class="row mb-4">
			<h2>PPID: ${setPPID}</h2>
		</div>
		<div class="my-3 container text-center" ${setRepair01HiddenError }>

			<div class="alert alert-danger" role="alert">
				<strong><span class="display-3">${setErrorMessage }</span></strong>
			</div>
		</div>



		<ul class="nav nav-tabs" id="myTab" role="tablist">
			<li class="nav-item"><a class="nav-link active" id="home-tab"
				data-toggle="tab" href="#home" role="tab" aria-controls="home"
				aria-selected="true">UPGRADE REVISION</a></li>
			<li class="nav-item"><a class="nav-link" id="profile-tab"
				data-toggle="tab" href="#profile" role="tab" aria-controls="profile"
				aria-selected="false">REPAIR ERROR</a></li>

		</ul>
		<div class="tab-content" id="myTabContent">


			<!-- update revision -->
			<div class="tab-pane fade show active" id="home" role="tabpanel"
				aria-labelledby="home-tab">
				<div class="container" ${setRepair01Hidden}>
					<div class="row p-5">
						<h2>Update revision</h2>

					</div>
					<form method="POST" action="<%=request.getContextPath()%>/repair01">
						<input type="hidden" name="action01" value="updateRevision">
						<div class="card">
							<h5 class="card-header">
								<strong>Introduction to update <span
									class="badge badge-primary">A${curRevNumber }-A${nextRevNumber}</span></strong>
							</h5>
							<div class="card-body">
								<h5 class="card-title">Please follow the steps to upgrade
									revision:</h5>
								<ul class="list-group list-group-flush">
									<li class="list-group-item">Current Revision:<strong>A${curRevNumber}</strong></li>
									<li class="list-group-item">Part Number: <strong>${partNumber}</strong></li>
									<li class="list-group-item">Location: <strong>${location}</strong></li>
									<li class="list-group-item">Description: <strong>${desc}</strong></li>
									<li class="list-group-item">ECO action: <strong>${ecoAction}</strong></li>
									<li class="list-group-item">Old Material PN: <strong>${oldMaterialPN}</strong></li>
									<li class="list-group-item">New Material PN: <strong>${newMaterialPN}</strong></li>
									<li class="list-group-item">Shortcut: <strong>${shortcut}</strong></li>
								</ul>
								<input type="submit" class="btn btn-success" value="DONE" />
							</div>
						</div>
					</form>

				</div>
			</div>

			<!-- REPAIR ERROR  -->

			<div class="tab-pane fade" id="profile" role="tabpanel"
				aria-labelledby="profile-tab">



				<div ${setRepair01HiddenFix}>
					<c:set var="indexValue" value="${0}" scope="page" />
					<c:forEach items="${errorList}" var="aError">
						<c:set var="indexValue" value="${indexValue +1}" scope="page" />
						<div class="accordion" id="accordionExample">
							<div class="card">
								<div class="card-header" id="headingOne">


									<h2 class="mb-0">
										<button class="btn btn-link" type="button"
											data-toggle="collapse" data-target="#xx${indexValue}"
											aria-expanded="false" aria-controls="xx${indexValue}">
											${aError}</button>
									</h2>
									<button class="float-right my-2 btn btn-primary"
										name="edit${aError}" onclick="enableForm(${indexValue})">Edit</button>
								</div>

								<div id="xx${indexValue}" class="collapse"
									aria-labelledby="headingOne" data-parent="#accordionExample">
									<div class="card-body">
										<form method="POST"
											action="<%=request.getContextPath()%>/repair01">
											<input type="hidden" name="actionForm" value="${indexValue}" />
											<fieldset id="disable${indexValue}" disabled>
												<div class="input-group mb-3">
													<div class="input-group-prepend">
														<label class="input-group-text" for="duty">Select
															Duty: </label>
													</div>
													<select class="custom-select" id="duty"
														name="duty${aError}">
														<option value="0" selected="">Choose a duty</option>
														<option value="1">duty 01</option>
														<option value="2">duty 02</option>
														<option value="3">duty 03</option>
													</select>
												</div>

												<br>
												<div class="form-group">
													<label for="oldPN">OLD PN</label> <input type="text"
														class="form-control" name="oldPN${aError}"
														placeholder="Enter Old PN">
												</div>


												<br>

												<div class="form-group">
													<label for="oldPN">NEW PN</label> <input type="text"
														class="form-control" name="newPN${aError}"
														placeholder="Enter new PN">
												</div>

												<br>

												<div class="form-group">
													<label for="oldPN">AREA</label> <input type="text"
														class="form-control" name="area${aError}"
														placeholder="Enter area">
												</div>


												<br>
												<div class="form-group">
													<label for="actionInput">Action</label>
													<textarea type="text" class="form-control"
														name="action${aError}" rows="4" id="actionInput"
														aria-describedby="emailHelp" placeholder="Enter action"></textarea>
													<button type="submit" value="${aError}" name="submitButton"
														class="btn btn-primary my-5 text-center mx-auto">Submit</button>
												</div>
											</fieldset>
										</form>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
					<script>
						function enableForm(errorCode) {
							console.log("called");
							document.getElementById("disable" + errorCode).disabled = false;
						}
					</script>
				</div>
			</div>
		</div>
	</div>


</section>

<jsp:include page="/WEB-INF/common/footer.jsp" />