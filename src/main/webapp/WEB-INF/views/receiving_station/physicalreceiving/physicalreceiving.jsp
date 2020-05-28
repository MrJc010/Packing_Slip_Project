<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Physical Receiving"></c:param>
</c:import>



<section id="mainSection">
	<div class="text-center" id="count"
		style="position: fixed; top: 70px; right: 10px;">
		<div class="badge badge-info rounded-circle m-2">
			<h2 class="p-3 text-danger mb-2">${count}</h2>
		</div>
		<h2 class="text-dark font-weight-bold">Receiving</h2>
	</div>
	<div class="container-fluid p-sm-0 p-lg-3">
		<h1 class="text-uppercase text-center text-primary mb-3">Physical
			Receiving</h1>
		<div class="row">
			<div class="col-lg-2"></div>
			<div class="col-sm-12 col-lg-8 px-3">
				<!-- Display Details of PPID -->
				<div class="row">
					<div class="col-3">
						<strong>PPID#:</strong>
					</div>
					<div class="col-9">
						<span class="text-uppercase text-dark font-weight-bold"> ${ppid_Number}</span>
					</div>
				</div>


				<div class="row">
					<div class="col-3">
						<strong>RMA#:</strong>
					</div>
					<div class="col-9">
						<span class="text-uppercase text-dark font-weight-bold"> ${rma_Number}</span>
					</div>
				</div>


				<div class="row">
					<div class="col-3">
						<strong>PN#:</strong>
					</div>
					<div class="col-9">
						<span class="text-uppercase text-dark font-weight-bold"> ${pn_Number}</span>
					</div>
				</div>


				<div class="row">
					<div class="col-3">
						<strong>CO#:</strong>
					</div>
					<div class="col-9">
						<span class="text-uppercase text-dark font-weight-bold"> ${co_Number}</span>
					</div>
				</div>


				<div class="row">
					<div class="col-3">
						<strong>LOT#:</strong>
					</div>
					<div class="col-9">
						<span class="text-uppercase text-dark font-weight-bold"> ${lot}</span>
					</div>
				</div>
				<div class="row">
					<div class="col-3">
						<strong>DPS#:</strong>
					</div>
					<div class="col-9">
						<span class="text-uppercase text-dark font-weight-bold"> ${dps}</span>
					</div>
				</div>
				<div class="row">
					<div class="col-3">
						<strong>Problem Code#:</strong>
					</div>
					<div class="col-9">
						<span class="text-uppercase text-dark font-weight-bold"> ${problem_code}</span>
					</div>
				</div>
				<div class="row">
					<div class="col-3">
						<strong>Problem Description#:</strong>
					</div>
					<div class="col-9">
						<textarea class="w-100 " disabled>${problem_desc}</textarea>
					</div>
				</div>
			</div>
			<div class="col-lg-2"></div>

		</div>


		<div class="row">
			<div class="col-lg-2"></div>
			<div class="col-lg-8 col-sm-12 py-2">
				<form method="POST"
					action="<%=request.getContextPath()%>/physicalreceiving">

					<div class="form-group row">
						<label for="SN#" class="col-sm-3 col-form-label text-primary"><strong>SN#</strong></label>
						<div class="col-sm-8">
							<input type="text" id="SN#" class="form-control" name="snNumber"
								placeholder="Serial Number" required>
						</div>
					</div>
					<div class="form-group row">
						<label for="Revision" class="col-sm-3 col-form-label text-primary"><strong>Revision</strong></label>
						<div class="col-sm-8">
							<input type="text" id="Revision" class="form-control"
								name="revision" placeholder="Revision (example A01)" required>
						</div>
					</div>

					<div class="form-group row">
						<label for="mfgPN" class="col-sm-3 col-form-label text-primary"><strong>ManufactoringPN</strong></label>
						<div class="col-sm-8">
							<input type="text" id="manufactoring" class="form-control"
								name="manufactoring" placeholder="Manufactoring PN" required>
							<!-- 						 pattern="46+[A-Za-z0-9]{9}|45+[A-Za-z0-9]{9}" -->
						</div>
					</div>

					<div class="form-group row">
						<label for="mac" class="col-sm-3 col-form-label text-primary"><strong>MAC</strong></label>
						<div class="col-sm-8">
							<input type="text" id="mac" class="form-control" name="mac"
								placeholder="MAC Address" required>
						</div>
					</div>

					<div class="form-group row">
						<label for="cpu_sn" class="col-sm-3 col-form-label text-primary"><strong>CPU
								SN</strong></label>
						<div class="col-sm-8">
							<input type="text" id="cpu_sn" class="form-control" name="cpu_sn"
								placeholder="CPU Serial Number" required>
						</div>
					</div>

					<div class="row justify-content-center my-3">
						<button id="print-btn" type="submit" class="btn btn-primary">
							<strong>Receive Item</strong>
						</button>
						<!-- <button id="print-btn" >Print Out</button> -->
					</div>
				</form>
				<br />

			</div>
			<div class="col-md-2"></div>

		</div>

	</div>

</section>

<script>
	var jsMain = document.getElementById("mainSection");
	var doc = new jsPDF();
	var btn = document.getElementById("print-btn");
</script>

<c:import url="/WEB-INF/common/footer.jsp" />