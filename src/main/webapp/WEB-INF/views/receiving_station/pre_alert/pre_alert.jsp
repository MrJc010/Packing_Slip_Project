<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/common/header.jsp" />



<section>

	<div class="container border mt-5 ">
		<h1 class="text-center mb-4">Upload excel file</h1>
		<form method="POST" action="<%=request.getContextPath()%>/pre_alert"
			enctype="multipart/form-data">

			<div class="custom-file">
				<input type="file" name="uploadFile" class="custom-file-input"
					id="customFile1" required> <label class="custom-file-label"
					for="customFile">Choose file</label>
			</div>
			<div class="input-group-append justify-content-center">
				<input type="file" name="file"/>
				<input type="submit" class="btn btn-primary btn-md my-4"
					value="UPLOAD" />
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

	<div class="container-fluid p-5">

		<ul class="nav nav-tabs nav-pills" id="myTab" role="tablist">
			<li class="nav-item"><a class="nav-link active"
				id="packingslip-tab" data-toggle="tab" href="#packingslip"
				role="tab" aria-controls="packingslip" aria-selected="true"><strong>Packing
						Slip</strong></a></li>
			<li class="nav-item"><a class="nav-link" id="ppids-tab"
				data-toggle="tab" href="#ppids" role="tab" aria-controls="ppids"
				aria-selected="false"><strong>PPIDs</strong></a></li>
			<li class="nav-item"><a class="nav-link" id="export-tab"
				data-toggle="tab" href="#export" role="tab" aria-controls="export"
				aria-selected="false"><strong>Export Data</strong></a></li>
		</ul>

		<div class="tab-content p-4">
			<div class="tab-pane active" id="packingslip" role="tabpanel"
				aria-labelledby="packingslip-tab">
				<div class="container-fluid" ${setHidden}>

					<div>
						<h3>Packing Slip</h3>
						<form method="GET" action="<%=request.getContextPath()%>/pre_alert" enctype=multipart/form-data>
							<br> <input type="text" name="RMA Number" required/>
							<input type="submit" class="btn btn-md btn-primary" value="Save RMA" />
						</form>
						



					</div>
					<div class="row p5">
						<table class="table table-striped">
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
					<div class="row p5">
						<table class="table table-striped">
							<thead>
								<tr>
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
								<c:forEach items="${rows2}" var="aRow">
									<tr>
										<td>${aRow.pnNumber}</td>
										<td>${aRow.ppidNumber}</td>
										<td>${aRow.coNumber}</td>
										<td>${aRow.dateReceived}</td>
										<td>${aRow.lotNumber}</td>
										<td>${aRow.dpsNumber}</td>
										<td>${aRow.problemCode}</td>
										<td>${aRow.problemDescription}</td>
									</tr>


								</c:forEach>
								<tr>

								</tr>
							</tbody>
						</table>

					</div>
				</div>


			</div>


			<div class="tab-pane" id="export" role="tabpanel"
				aria-labelledby="export-tab">
				${urll}

				<a class="btn btn-primary"
					href="File_DownLoad"
					role="button"
					onclick="if(!confirm('Are you sure to export?')) return false">Export</a>
			</div>

		</div>

		<script>
			$(function() {
				$('#myTab li:last-child a').tab('show')
			})
		</script>
	</div>

</section>


<jsp:include page="/WEB-INF/common/footer.jsp" />