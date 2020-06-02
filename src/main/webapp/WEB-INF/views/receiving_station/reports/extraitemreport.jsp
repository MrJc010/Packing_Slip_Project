<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Extra Item Report"></c:param>
</c:import>
<style>
.top_row {
	display: table;
	width: 100%;
}

.top_row>div {
	display: table-cell;
	width: 50%;
	border-bottom: 1px solid #eee;
}
</style>
<section>


	<div class="container-fluid  px-5 py-3" ${hidden}>
		<div class="text-center" id="count"
			style="position: fixed; top: 30%; right: 6%; z-index: 999;"
			${setHiddenCount}>
			<div class="badge badge-info rounded-1 m-2">
				<h2 class="p-3 text-danger m-0">${count}</h2>
			</div>
			<h2 class="text-dark font-weight-bold">Received</h2>
		</div>
		<div class="row bg-light">
			<div class="col-md-2"></div>
			<div class="col-md-8 col-sm-12  py-2 px-5">
				<h4 class="text-center text-primary p-1 display-4">
					<strong>EXTRA ITEMS REPORT</strong>
				</h4>
				<form action="<%=request.getContextPath()%>/extraitems" method="GET">
					<div class="input-group mb-3">
						<input type="hidden" name="action" value="checkPPID"> <input
							type="text" class="form-control form-control-lg" id="PPID"
							aria-describedby="emailHelp" placeholder="Enter PPID"
							name="ppidNumber" required" value="${ppid}">
						<div class="input-group-append">

							<input type="submit" class="btn btn-primary btn-lg" id="checkBTN"
								value="CHECK">
						</div>

					</div>
				</form>
				<h5 id="result" class="text-center text-danger">${checkResult}</h5>
			</div>

		</div>

		<div class="container text-center mt-5" id="setHiddenResultSucess"
			${setHiddenResultSucess}>
			<div class="alert alert-success" role="alert">
				<h4>
					<label class="text-dark">${messageSuccess}</label>
				</h4>
			</div>
		</div>
		<div class="container text-center" id="setErrorMessage"
			${setErrorMessage}>
			<div class="alert alert-warning mt-5" role="alert">
				<h4>
					<label class="text-dark">${errorMessage}</label>
				</h4>
			</div>
		</div>
	</div>

	<div class="container mt-1 px-1" id="hiddenExtrainfo"
		${hiddenExtrainfo }>
		<div class="text-center mb-2">
			<h4 class="text-uppercase text-primary">Fill out all information
				on item</h4>
		</div>

		<form action="<%=request.getContextPath()%>/extraitems" method="POST">
			<input type="hidden" name="action" value="addInformation">
			<div class="form-group row">
				<label for="ppidInfo"
					class="col-sm-2 col-form-label col-form-label-sm font-weight-bold">PPID</label>
				<div class="col-sm-8">
					<input type="text" class="form-control form-control-sm"
						name="ppidInfo" value="${ppidInfox}" disabled>
				</div>
				<div class="col-sm-3"></div>
			</div>
			<div class="form-group row">
				<label for="ppidInfo"
					class="col-sm-2 col-form-label col-form-label-sm font-weight-bold">RMA</label>
				<div class="col-sm-8">
					<input type="text" class="form-control form-control-sm" name="rma"
						value="${rma}">
				</div>
				<div class="col-sm-3"></div>
			</div>
			<div class="form-group row">
				<label for="sn"
					class="col-sm-2 col-form-label col-form-label-sm font-weight-bold">SN</label>
				<div class="col-sm-8">
					<input type="text" class="form-control form-control-sm" name="sn"
						id="sn" placeholder="Scan Serial Number" value="${sn}" required>
				</div>
				<div class="col-sm-3"></div>
			</div>
			<div class="form-group row">
				<label for="rev"
					class="col-sm-2 col-form-label col-form-label-sm font-weight-bold">Revision</label>
				<div class="col-sm-8">
					<input type="text" class="form-control form-control-sm" name="rev"
						id="rev" placeholder="Scan revision" value="${rev }" required>
				</div>
				<div class="col-sm-3"></div>
			</div>
			<div class="form-group row">
				<label for="manfPN"
					class="col-sm-2 col-form-label col-form-label-sm font-weight-bold">Manufacturing
					PN</label>
				<div class="col-sm-8">
					<input type="text" class="form-control form-control-sm" id="manfPN"
						name="manfPN" placeholder="Scan Manufacturing PN"
						value="${manfPN}" required>
				</div>

				<div class="col-sm-3"></div>
			</div>
			<div class="form-group row">
				<label for="mac"
					class="col-sm-2 col-form-label col-form-label-sm font-weight-bold">MAC</label>
				<div class="col-sm-8">
					<input type="text" class="form-control form-control-sm" id="mac"
						name="mac" value="${mac}" placeholder="Scan Mac Address" required>
				</div>

				<div class="col-sm-3"></div>
			</div>
			<div class="form-group row">
				<label for="cpuSN"
					class="col-sm-2 col-form-label col-form-label-sm font-weight-bold">CPU
					SN</label>
				<div class="col-sm-8">
					<input type="text" class="form-control form-control-sm" id="cpuSN"
						name="cpuSN" value="${cpuSN}" placeholder="Scan CPU Serial Number"
						required>
				</div>

				<div class="col-sm-3"></div>
			</div>

			<div class="input-group-append justify-content-center"
				${setHIddenSubmitButton}>
				<input type="submit" class="btn btn-primary btn-lg" name="submitBTN"
					value="ADD TO LIST">
			</div>

			<div class="input-group-append justify-content-center"
				${setHIddenEditButton}>
				<input type="submit" class="btn btn-primary btn-lg" name="submitBTN"
					value="EDIT">
			</div>
		</form>

	</div>

	<button onclick="ExportExcel('xlsx')">Export</button>
	<table id="exportable_table" class="table table-bordered">
		<thead class="thead-dark">
			<tr>
				<th scope="col">PPID</th>
				<th scope="col">RMA</th>
				<th scope="col">SN</th>
				<th scope="col">REV</th>
				<th scope="col">PN</th>
				<th scope="col">MAC</th>
				<th scope="col">CPU SN</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${receivedList}" var="aRow">
				<tr>
						<c:forEach items="${aRow}" var="iRow">
							<td>
							<div class="top_row">${iRow}</div>
							</td>
						</c:forEach>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</section>
<script>
	var inputPPid = document.getElementById('PPID');
	inputPPid.addEventListener('click', function() {
		inputPPid.value = null;
		document.getElementById("setHiddenResultSucess").hidden = true;
		document.getElementById("setErrorMessage").hidden = true;
		document.getElementById("hiddenExtrainfo").hidden = true;
		document.getElementById("checkBTN").hidden = true;
		document.getElementById("result").innerText = "";
	});

	inputPPid.addEventListener('input', function(event) {
		if (event.target.value !== "") {
			document.getElementById("checkBTN").hidden = false;
		}
	});
</script>

<script type="text/javascript">
	function ExportExcel(type, fn, dl) {
		var today = new Date();
		var date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-'
				+ today.getDate();
		var time = today.getHours() + ":" + today.getMinutes() + ":"
				+ today.getSeconds();
		var dateTime = date + '-' + time;
		var elt = document.getElementById('exportable_table');
		var wb = XLSX.utils.table_to_book(elt, {
			sheet : "Sheet JS"
		});
		return dl ? XLSX.write(wb, {
			bookType : type,
			bookSST : true,
			type : 'base64'
		}) : XLSX.writeFile(wb, fn
				|| ('ExtraItemReport-' + dateTime + '.' + (type || 'xlsx')));
	}
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/xlsx.full.min.js"></script>
<c:import url="/WEB-INF/common/footer.jsp" />