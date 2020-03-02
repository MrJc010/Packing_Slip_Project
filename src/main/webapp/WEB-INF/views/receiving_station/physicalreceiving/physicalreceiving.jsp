<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/common/header.jsp" />


<section>




	<div class="container">


		<form method="POST"
			action="<%=request.getContextPath()%>/physicalreceiving">
			<a id="alert" target="${Alert_More_Than_5}"></a>
			<script type="text/javascript">
				function confirmation() {
					var x = document.getElementById("alert").getAttribute("target");
					if (x === "More Than 5") {
						if (!confirm("This item is returned more than 5 times.\nItem will be move to Scrap01 Location")) {
					        return false;
					    }
					}
				}
			</script>

			<div class="form-group row">
				<label for="SN#" class="col-sm-2 col-form-label">SN#</label>
				<div class="col-sm-8">
					<input type="text" id="SN#" class="form-control" name="snNumber"
						placeholder="Serial Number" required>
				</div>
			</div>
			<div class="form-group row">
				<label for="Revision" class="col-sm-2 col-form-label">Revision</label>
				<div class="col-sm-8">
					<input type="text" id="Revision" class="form-control"
						name="revision" placeholder="Revision" required>
				</div>
			</div>

			<div class="form-group row">
				<label for="mfgPN" class="col-sm-2 col-form-label">ManufactoringPN</label>
				<div class="col-sm-8">
					<input type="text" id="manufactoring" class="form-control"
						name="manufactoring" placeholder="Manufactoring PN"
						pattern="46+[A-Za-z0-9]{9}|45+[A-Za-z0-9]{9}" required>
				</div>
			</div>
			
			<div class="form-group row">
				<label for="mac" class="col-sm-2 col-form-label">MAC</label>
				<div class="col-sm-8">
					<input type="text" id="mac" class="form-control"
						name="mac" placeholder="MAC Address" required>
				</div>
			</div>
			
			<div class="form-group row">
				<label for="cpu_sn" class="col-sm-2 col-form-label">CPU SN</label>
				<div class="col-sm-8">
					<input type="text" id="cpu_sn" class="form-control"
						name="cpu_sn" placeholder="CPU Serial Number" required>
				</div>
			</div>

			<div class="form-group row">
				<label for="RMA#" class="col-sm-2 col-form-label">RMA#</label>
				<div class="col-sm-8">
					<input type="text" id="RMA#" class="form-control"
						value="${rma_Number}" name="rma" placeholder="RMA Number" disabled>
				</div>
			</div>

			<div class="form-group row">
				<label for="PPID#" class="col-sm-2 col-form-label">PPID#</label>
				<div class="col-sm-8">
					<input type="text" id="RMA#" class="form-control"
						value="${ppid_Number}" name="ppid" placeholder="PPID Number"
						disabled>
				</div>
			</div>

			<div class="form-group row">
				<label for="PN#" class="col-sm-2 col-form-label">PN#</label>
				<div class="col-sm-8">
					<input type="text" id="PN#" class="form-control"
						value="${pn_Number}" placeholder="PN Number" name="pn" disabled>
				</div>
			</div>

			<div class="form-group row">
				<label for="CO#" class="col-sm-2 col-form-label">CO#</label>
				<div class="col-sm-8">
					<input type="text" id="CO#" class="form-control"
						value="${co_Number}" placeholder="CO Number" name="co" disabled>
				</div>
			</div>

			<div class="form-group row">
				<label for="problem_desc#" class="col-sm-2 col-form-label">Problem
					Description</label>
				<div class="col-sm-8">
					<input type="text" id="problem_desc#" class="form-control"
						value="${problem_desc}" name="problemDesc"
						placeholder="Problem Description" disabled>
				</div>
			</div>

			<div class="form-group row">
				<label for="Lot#" class="col-sm-2 col-form-label">Lot#</label>
				<div class="col-sm-8">
					<input type="text" id="Lot#" class="form-control" value="${lot}"
						placeholder="Lot number" name="lotNumber" disabled>
				</div>
			</div>
			<div class="form-group row">
				<label for="problem_code#" class="col-sm-2 col-form-label">Problem
					code</label>
				<div class="col-sm-8">
					<input type="text" id="problem_code#" class="form-control"
						value="${problem_code}" name="problemCode"
						placeholder="Problem code" disabled>
				</div>
			</div>

			<div class="form-group row">
				<label for="dps#" class="col-sm-2 col-form-label">DPS#</label>
				<div class="col-sm-8">
					<input type="text" id="dps#" class="form-control" value="${dps}"
						placeholder="DPS Number" name="dpnNumber" disabled>
				</div>
			</div>
			<div class="row justify-content-center">
				<button type="submit" class="btn btn-primary"
					onclick="return confirmation();">
					<strong>Receive Item</strong>
				</button>
			</div>

		</form>



	</div>

</section>


<jsp:include page="/WEB-INF/common/footer.jsp" />