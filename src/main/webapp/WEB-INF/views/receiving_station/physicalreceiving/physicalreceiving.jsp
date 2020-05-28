<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Physical Receiving"></c:param>
</c:import>



<section id="mainSection">

	<div class="container-fluid py-2">


		<div class="row px-5">
			<div class="col-md-2"></div>
			<div
				class="col-md-8 col-sm-12 shadow-lg p-3 mb-5 bg-white rounded sh bg-light py-2 px-5">
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
								name="revision" placeholder="Revision" required>
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



					<div class="form-group row">
						<label for="RMA#" class="col-sm-3 col-form-label text-primary"><strong>RMA#</strong></label>
						<div class="col-sm-8">
							<input type="text" id="RMA#" class="form-control"
								value="${rma_Number}" name="rma" placeholder="RMA Number"
								disabled>
						</div>
					</div>

					<div class="form-group row">
						<label for="PPID#" class="col-sm-3 col-form-label text-primary"><strong>PPID#</strong></label>
						<div class="col-sm-8">
							<input type="text" id="RMA#" class="form-control"
								value="${ppid_Number}" name="ppid" placeholder="PPID Number"
								disabled>
						</div>
					</div>

					<div class="form-group row">
						<label for="PN#" class="col-sm-3 col-form-label text-primary"><strong>PN#</strong></label>
						<div class="col-sm-8">
							<input type="text" id="PN#" class="form-control"
								value="${pn_Number}" placeholder="PN Number" name="pn" disabled>
						</div>
					</div>

					<div class="form-group row">
						<label for="CO#" class="col-sm-3 col-form-label text-primary"><strong>CO#</strong></label>
						<div class="col-sm-8">
							<input type="text" id="CO#" class="form-control"
								value="${co_Number}" placeholder="CO Number" name="co" disabled>
						</div>
					</div>

					<div class="form-group row">
						<label for="problem_desc#"
							class="col-sm-3 col-form-label text-primary"><strong>Problem
								Description</strong></label>
						<div class="col-sm-8">
							<input type="text" id="problem_desc#" class="form-control"
								value="${problem_desc}" name="problemDesc"
								placeholder="Problem Description" disabled>
						</div>
					</div>

					<div class="form-group row">
						<label for="Lot#" class="col-sm-3 col-form-label text-primary"><strong>Lot#</strong></label>
						<div class="col-sm-8">
							<input type="text" id="Lot#" class="form-control" value="${lot}"
								placeholder="Lot number" name="lotNumber" disabled>
						</div>
					</div>
					<div class="form-group row">
						<label for="problem_code#"
							class="col-sm-3 col-form-label text-primary"><strong>Problem
								code</strong></label>
						<div class="col-sm-8">
							<input type="text" id="problem_code#" class="form-control"
								value="${problem_code}" name="problemCode"
								placeholder="Problem code" disabled>
						</div>
					</div>

					<div class="form-group row">
						<label for="dps#" class="col-sm-3 col-form-label text-primary"><strong>DPS#</strong></label>
						<div class="col-sm-8">
							<input type="text" id="dps#" class="form-control" value="${dps}"
								placeholder="DPS Number" name="dpnNumber" disabled>
						</div>
					</div>

					<div class="row justify-content-center my-3">
						<button id="print-btn" type="submit" class="btn btn-primary"
							>
							<strong>Receive Item</strong>
						</button>
						<!-- <button id="print-btn" >Print Out</button> -->
					</div>
				</form>
				<br/>
				
			</div>
			<div class="col-md-2"></div>

		</div>



	</div>

</section>

<script>
var jsMain = document.getElementById("mainSection");
var doc = new jsPDF();
doc.fromHTML(
		jsMain,
	    15,
	    15,
	    {
	      'width': 180
	    });
	doc.autoPrint();
	doc.output("dataurlnewwindow");

var btn = document.getElementById("print-btn");

</script>

<c:import url="/WEB-INF/common/footer.jsp" />