<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Physical Receiving"></c:param>
</c:import>



<section id="mainSection">
	<div class="text-center" id="count"
		style="position: fixed; top: 20%; right: 10px;" ${setHiddenBeforeReceived}>
		<div class="badge badge-info rounded-circle m-2">
			<h2 class="p-3 text-danger mb-2">${count}</h2>
		</div>
		<h2 class="text-dark font-weight-bold">Receiving</h2>
	</div>
	<div class="container-fluid px-5 pt-5 pb-2">
		<div class="row bg-light">
			<div class="col-md-2"></div>
			<div class="col-md-8 col-sm-12  py-5 px-5">
				<h4
					class="text-center text-text-uppercase text-primary p-1 display-4">
					<strong>Physical Receiving</strong>
				</h4>
			</div>
		</div>
	</div>
	<div class="container-fluid" ${setHiddenBeforeReceived}>
		<div class="row">
			<div class="col-lg-2"></div>
			<div class="col-sm-12 col-lg-8 px-3">
				<!-- Display Details of PPID -->
				<div class="row">
					<div class="col-3">
						<strong>PPID#:</strong>
					</div>
					<div class="col-9">
						<span class="text-uppercase text-dark font-weight-bold">
							${ppid_Number}</span>
					</div>
				</div>


				<div class="row">
					<div class="col-3">
						<strong>RMA#:</strong>
					</div>
					<div class="col-9">
						<span class="text-uppercase text-dark font-weight-bold">
							${rma_Number}</span>
					</div>
				</div>


				<div class="row">
					<div class="col-3">
						<strong>PN#:</strong>
					</div>
					<div class="col-9">
						<span class="text-uppercase text-dark font-weight-bold">
							${pn_Number}</span>
					</div>
				</div>


				<div class="row">
					<div class="col-3">
						<strong>CO#:</strong>
					</div>
					<div class="col-9">
						<span class="text-uppercase text-dark font-weight-bold">
							${co_Number}</span>
					</div>
				</div>


				<div class="row">
					<div class="col-3">
						<strong>LOT#:</strong>
					</div>
					<div class="col-9">
						<span class="text-uppercase text-dark font-weight-bold">
							${lot}</span>
					</div>
				</div>
				<div class="row">
					<div class="col-3">
						<strong>DPS#:</strong>
					</div>
					<div class="col-9">
						<span class="text-uppercase text-dark font-weight-bold">
							${dps}</span>
					</div>
				</div>
				<div class="row">
					<div class="col-3">
						<strong>Problem Code#:</strong>
					</div>
					<div class="col-9">
						<span class="text-uppercase text-dark font-weight-bold">
							${problem_code}</span>
					</div>
				</div>
				<div class="row">
					<div class="col-3">
						<strong>Problem Description#:</strong>
					</div>
					<div class="col-9">
						<span class="text-uppercase text-dark font-weight-bold">
							${problem_desc}</span>
					</div>
				</div>
			</div>
			<div class="col-lg-2"></div>

		</div>


		<div class="row" ${setHiddenBeforeReceived}>
			<div class="col-lg-2"></div>
			<div class="col-lg-8 col-sm-12 py-2">
				<form method="POST"
					action="<%=request.getContextPath()%>/physicalreceiving">

					<div class="form-group row">
						<label for="SN" class="col-sm-3 col-form-label text-primary"><strong>SN#</strong></label>
						<div class="col-sm-8">
							<input type="text" id="SN" class="form-control" name="snNumber"
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
						<label for="mfgPN" class="col-sm-3 col-form-label text-primary"><strong>Manufactoring
								PN</strong></label>
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

					</div>
				</form>

				<br />

			</div>
			<div class="col-md-2"></div>

		</div>

		<div class="row" ${setHiddenAfterReceived}>
			<div class="col-lg-2"></div>
			<div class="col-lg-8 col-sm-12 text-center">

				<div class="alert alert-success" role="alert">
					<h3 class="text-uppercase">${ppid_Number} received at Physical
						Station, and ready to transfer to MICI Station</h3>
				</div>

				<button class="btn btnlg btn-primary" id="print-btn"
					onClick=outPDF()>PRINT RECEIPT</button>
					
					
				<a class="btn btnlg btn-warning" id="print-btn" href="<%=request.getContextPath()%>/searchitem"
					>SCAN NEW ITEM</a>

			</div>
			<div class="col-lg-2"></div>

		</div>

	</div>

</section>

<script>
	var jsMain = document.getElementById("mainSection");
	var doc = new jsPDF();
	doc.setFontSize(20);
	doc.text("PACKING LIST", 180, 17, null, null, "right");
	doc.line(20, 20, 190, 20);
	doc.setFontSize(40);
	doc.text("PHYSICAL RECEIVING", 105, 50, null, null, "center");

	2
	var today = new Date();
	var date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-'
			+ today.getDate();
	doc.setFontSize(20);
	doc.text(date, 180, 67, null, null, "right");

	doc.setFontSize(20);
	var ppid = `${ppid_Number}`;
	var rma = `${rma_Number}`;
	var pn = `${pn_Number}`;
	var co = `${co_Number}`;
	var lot = `${lot}`;
	var dps = `${dps}`;
	var problemCode = `${problem_code}`;
	var problemDescription = `${problem_desc}`;
	
	
	var btn = document.getElementById("print-btn");

	function outPDF() {
		
		var sn = document.querySelector('#SN');
		var revision = document.querySelector('#Revision');
		var manufactoringPN = document.querySelector('#manufactoring');
		var macAddress = document.querySelector('#mac');
		var cpuSN = document.querySelector('#cpu_sn');
		// Or use javascript directly:
		
		doc.autoTable({
			startY : 70,
			headStyles : {
				fontStyle : 'bold',
				fontSize : 15,
			},
			head : [ [ 'Title', 'Details' ] ],
			body : [ [ 'PPID', ppid ], [ 'RMA', rma ], [ 'PN', pn ], [ 'CO', co ],
					[ 'LOT', lot ], [ 'DPS', dps ],
					[ 'Problem Code', problemCode ],
					[ 'Problem Description', problemDescription ], [ 'SN', sn.value ],[ 'Revision', revision.value ],
					[ 'Manufactoring PN', manufactoringPN.value ],
					[ 'MAC Address', macAddress.value ], [ 'CPU SN', cpuSN.value ], ],
		})
		var fileName = rma +".pdf";
		doc.save(fileName);
	}
</script>

<c:import url="/WEB-INF/common/footer.jsp" />