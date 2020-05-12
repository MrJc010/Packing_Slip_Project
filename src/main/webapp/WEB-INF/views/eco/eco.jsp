<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="ECO Station"></c:param>
</c:import>
<section>
	<!-- The Modal -->
	<div class="modal fade" id="myModal">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">
						<strong>Bizcom Notification</strong>
					</h4>
					<button id="closeBTN-modal" type="button" class="close"
						data-dismiss="modal">
						<strong class="text-danger">&times;</strong>
					</button>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
					<h3 id="modal-message" class="text-primary">xxx</h3>
					
				</div>


			</div>
		</div>
	</div>

	<div class="container-fluid p-5">

		<%-- Search Bar --%>
		<div class="row jumbotron my-1">
			<div class="col-lg-2"></div>
			<div class="col-lg-8  col-sm-12">
				<h3 class="text-center text-primary p-1 display-5">
					<strong>Enter and Search your PPID</strong>
				</h3>
				<form action="<%=request.getContextPath()%>/eco" method="GET">
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
			<form action="<%=request.getContextPath()%>/eco" method="POST">
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

				<%-- <div class="row p-1 border">
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
				</div> --%>

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



		<%-- Body Revision + Errorcodes --%>
		<div class="container-fluid p3" ${setHiddenBodyRepair01}>
			<div class="row">
				<ul class="nav nav-tabs" id="myTab" role="tablist">
					<li class="nav-item"><a class="nav-link active" id="home-tab"
						data-toggle="tab" href="#home" role="tab" aria-controls="home"
						aria-selected="true">UPGRADE REVISION</a></li>

				</ul>

			</div>
			<div class=" tab-content" id="myTabContent">


				<!-- update revision -->
				<div class="row tab-pane fade show active" id="home" role="tabpanel"
					aria-labelledby="home-tab">

					<div class="row" ${setRepair01Hidden}>
						<div class="col-8">

							<div class=" p-2">

								<form method="POST" action="<%=request.getContextPath()%>/eco">
									<input type="hidden" name="action01" value="updateRevision">
									<div class="card">
										<h2 class="card-header text-center">
											<strong class="text-primary">Introduction to update
												<span class="badge badge-primary">${curRevNumber }-${nextRevNumber}</span>
											</strong>
										</h2>
										<div class="card-body">
											<h2 class="card-title text-primary">Please follow the
												steps to upgrade revision:</h2>
											<ul class="list-group list-group-flush">
												<li class="list-group-item">


													<h3>
														<span class="badge badge-info">Current Revision:</span><strong>
															${curRevNumber}</strong>
													</h3>
												</li>
												<li class="list-group-item"><h3>
														<span class="badge badge-info"> Part Number: </span> <strong>
															${partNumber}</strong>
													</h3></li>
												<li class="list-group-item"><h3>
														<span class="badge badge-info"> Location: </span> <strong>
															${location}</strong>
													</h3></li>
												<li class="list-group-item"><h3>
														<span class="badge badge-info"> Description: </span> <strong>
															${desc}</strong>
													</h3></li>
												<li class="list-group-item"><h3>
														<span class="badge badge-info"> ECO action: </span> <strong>
															${ecoAction}</strong>
													</h3></li>
												<li class="list-group-item"><h3>
														<span class="badge badge-info"> Old Material PN: </span> <strong>
															${oldMaterialPN}</strong>
													</h3></li>
												<li class="list-group-item"><h3>
														<span class="badge badge-info"> New Material PN: </span> <strong>
															${newMaterialPN}</strong>
													</h3></li>
											</ul>
											<input type="submit" class="btn btn-success mt-2"
												value="UPDATE" />
										</div>
									</div>
								</form>

							</div>

						</div>
						<div class="col-4">
							<div>
								<img
									src="https://i.ibb.co/gvbJGDL/Yk3-S935-ORSey-REs-OG6o-LIw.png"
									class="rounded mx-auto d-block img-responsive img-thumbnail"
									alt="Introduction_Shortcut"
									style="min-height: 300px; height: 300px;"
									onclick=imgClickHandler(
									"https://i.ibb.co/gvbJGDL/Yk3-S935-ORSey-REs-OG6o-LIw.png")>

							</div>

						</div>
					</div>
				</div>





			</div>
		</div>
	</div>
</section>
<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<script>
var modalMessage = document.getElementById("modal-message");
		function imgClickHandler(sr)
        {		
            $('#mimg').attr('src',sr);
            $('#myModal').modal('show');
        };
        
        var isNoInformationUpdate = `<%=request.getAttribute("isNoInformationUpdate")%>`;
if(isNoInformationUpdate ==="True"){
	$(document).ready(function(){
	    // Show modal on page load
	    
	    $("#myModal").modal({
	        backdrop: 'static',
	        keyboard: false
	      });    	   
	    modalMessage.innerText = "No action needed. Click \"Upgrade\" button to go next.";
	    modalMessage.classList = "";
	    modalMessage.classList.add("text-warning");
	});
}
</script>
<c:import url="/WEB-INF/common/footer.jsp" />
