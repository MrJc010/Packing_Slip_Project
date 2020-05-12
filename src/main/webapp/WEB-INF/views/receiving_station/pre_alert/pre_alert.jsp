<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Pre-Alert Station"></c:param>
</c:import>
<section>
	<div class="container-fluid p-3">
		<div class="float-right mx-5">
			<form class="float-right" method="GET"
				action="<%=request.getContextPath()%>/pre_alert">
				<input type="hidden" name="resetButton" value="resetPage" />
				<button type="submit" class="btn btn-danger btn-lg">
					<strong>Reset Page</strong>
				</button>

			</form>
		</div>

	</div>
	<div class="container-fluid mt-5 px-5 ">

		<div class="jumbotron row">
			<div class="col-lg-2"></div>
			<div class="col-lg-8  col-sm-12">

				<h1 class="text-center text-primary p-2 display-4">
					<strong>Upload Your Excel File</strong>
				</h1>
				<form method="POST" action="<%=request.getContextPath()%>/pre_alert"
					enctype="multipart/form-data" class="mt-2">


					<div class="custom-file">
						<input type="file" name="uploadFile"
							class="custom-file-input form-control-lg" id="customFile1"
							required> <label
							class="custom-file-label bg-light text-dark" for="customFile">Choose
							file</label>
					</div>
					<div class="input-group-append justify-content-center">
						<input type="submit" class="btn btn-primary btn-md my-4"
							value="UPLOAD" />


					</div>
					<div class="alert alert-danger" role="alert" ${setErrorHidden}>
						<strong>${message }</strong>
					</div>

					<div class="alert alert-success" role="alert" ${setSuccesHidden}>
						<strong>Excel file was loaded successfully</strong>
					</div>

					<script>
						document
								.querySelector('.custom-file-input')
								.addEventListener(
										'change',
										function(e) {
											var fileName = document
													.getElementById("customFile1").files[0].name;
											var nextSibling = e.target.nextElementSibling
											nextSibling.innerText = fileName
										})
					</script>

				</form>
			</div>
			<div class="col-lg-2"></div>
		</div>


	</div>

	<div class="container-fluid p-5" ${setHideInfo}>

		<ul class="nav nav-tabs " id="myTab" role="tablist">
			<li class="nav-item"><a class="nav-link active"
				id="packingslip-tab" data-toggle="tab" href="#packingslip"
				role="tab" aria-controls="packingslip" aria-selected="true"><strong>Packing
						Slip</strong></a></li>
			<li class="nav-item"><a class="nav-link" id="ppids-tab"
				data-toggle="tab" href="#ppids" role="tab" aria-controls="ppids"
				aria-selected="false"><strong>PPIDs</strong></a></li>
			<li class="nav-item"><a class="nav-link" href="File_DownLoad"
				role="button"
				onclick="if(!confirm('Are you sure to export?')) return false"
				${setHiddenExport}><strong>Export</strong></a></li>
		</ul>

		<div class="tab-content p-4">
			<div class="tab-pane active" id="packingslip" role="tabpanel"
				aria-labelledby="packingslip-tab">
				<div class="container-fluid" ${setHidden}>

					<div>
						<h3>Click button to get RMA number</h3>
						<form method="GET"
							action="<%=request.getContextPath()%>/pre_alert"
							enctype=multipart/form-data>
							<!-- <br> <input type="text" name="RMA Number" required/> -->
							<input type="submit" name="rmaButton"
								class="btn btn-md btn-primary my-4" value="GET RMA" />
						</form>

					</div>
					<div class="row p5 table-responsive">
						<table class="table table-striped ">
							<thead>
								<tr>
									<th scope="col" class="text-left">PN#</th>
									<th scope="col" class="text-left">PO#</th>
									<th scope="col" class="text-left">LOT#</th>
									<th scope="col" class="text-left">QTY</th>
									<th scope="col" class="text-left">RMA#</th>
								</tr>
							</thead>
							<tbody>

								<c:forEach items="${rows}" var="aRow">

									<tr>

										<td>${aRow.partNumber}</td>
										<td>${aRow.poNumber}</td>
										<td>${aRow.lotNumber}</td>
										<td>${aRow.quality}</td>
										<td>${aRow.RMANumber}</td>

									</tr>


								</c:forEach>

							</tbody>
						</table>

					</div>
				</div>


			</div>
			<div class="tab-pane" id="ppids" role="tabpanel"
				aria-labelledby="ppids-tab">


				<div class="container-fluid">
					<div class="row">
						<h3>List PPID Read From Excel</h3>
					</div>
					<div class="row p5 table-responsive">
						<table class="table table-striped">
							<thead>
								<tr>
									<th scope="col" class="text-left">ROW #</th>
									<th scope="col" class="text-left">PN#</th>
									<th scope="col" class="text-left">PPID#</th>
									<th scope="col" class="text-left">CO#</th>
									<th scope="col" class="text-left">SYS-DATE-REC</th>
									<th scope="col" class="text-left">LOT#</th>
									<th scope="col" class="text-left">DPS#</th>
									<th scope="col" class="text-left">PROBLEM-CODE</th>
									<th scope="col" class="text-left">PROBLEM-DESC</th>
								</tr>
							</thead>
							<tbody>
								<%
									int count = 1;
								%>
								<c:forEach items="${rows2}" var="aRow">
									<tr>
										<td><%=count%></td>
										<td>${aRow.pnNumber}</td>
										<td>${aRow.ppidNumber}</td>
										<td>${aRow.coNumber}</td>
										<td>${aRow.dateReceived}</td>
										<td>${aRow.lotNumber}</td>
										<td>${aRow.dpsNumber}</td>
										<td>${aRow.problemCode}</td>
										<td>${aRow.problemDescription}</td>
									</tr>

									<%
										count++;
									%>
								</c:forEach>
								<tr>

								</tr>
							</tbody>
						</table>

					</div>
				</div>


			</div>

		</div>


	</div>

</section>


<c:import url="/WEB-INF/common/footer.jsp" />