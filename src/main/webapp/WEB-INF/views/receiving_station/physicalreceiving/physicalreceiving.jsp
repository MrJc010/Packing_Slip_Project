<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/common/header.jsp" />


<section>




	<div class="container">


		<form method="POST"
			action="<%=request.getContextPath()%>/physicalreceiving">

			<div class="form-group row">
				<label for="CPO#" class="col-sm-2 col-form-label">CPO#</label>
				<div class="col-sm-8">
					<input type="text" id="CPO#" class="form-control" name="cpoNumber"
						placeholder="CPO Number" required>
				</div>
			</div>

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
				<label for="specialInstruction" class="col-sm-2 col-form-label">Special
					Instruction</label>
				<div class="col-sm-8">
					<input type="text" id="specialInstruction" class="form-control"
						name="specialInstruction" placeholder="Special Instruction"
						required>
				</div>
			</div>

			<div class="form-group row">
				<label for="mfgPN" class="col-sm-2 col-form-label">Manufactoring
					PN</label>
				<div class="col-sm-8">
					<input type="text" id="manufactoring" class="form-control"
						name="manufactoring" placeholder="Manufactoring PN" required>
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
					onclick="if(${Alert_More_Than_5}== 'More Than 5') if(!confirm('Are you sure to export?')) return false">
					<strong>Receive Item</strong>
				</button>
			</div>

		</form>
		
		<c:set var="morethanfive" scope="session" value="${Alert_More_Than_5}" />
			<c:if test="${re == \"More Than 5\"}">
				<script type="text/javascript">
					var msg = "This item is returned more than 5 times.\nItem will be move to Scrap01 Location";
					confirm(msg);
				</script>
			</c:if>



	</div>

</section>


<jsp:include page="/WEB-INF/common/footer.jsp" />