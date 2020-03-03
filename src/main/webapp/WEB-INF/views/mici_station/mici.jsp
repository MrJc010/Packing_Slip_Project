<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/common/header.jsp" />


<style>
.navbar-nav li:hover>ul.dropdown-menu {
	display: block;
}

.dropdown-submenu {
	position: relative;
}

.dropdown-submenu>.dropdown-menu {
	top: 0;
	left: 100%;
	margin-top: -6px;
}

/* rotate caret on hover */
.dropdown-menu>li>a:hover:after {
	text-decoration: underline;
	transform: rotate(-90deg);
}
</style>

<section>

	<div class="container" ${hidden}>
		<h1 class="text-center mt-4 my-4">This is MICI</h1>

		<div class="row">
			<div class="col-md-2 col-sm-0"></div>
			<div class="col-md-8 col-sm-2 text-center">

				<form method="get" action="<%=request.getContextPath()%>/mici">
					
					<div class="form-group">
						<label for="PPID">Enter PPID: </label> <input type="text"
							class="form-control" id="PPID" aria-describedby="emailHelp"
							placeholder="Enter PPID" name="ppidNumber" required>
					</div>
					<input type="hidden" name="page" value="check">
					<div class="form-group">
						<label for="serialNumbber">Enter Serial Number</label> <input
							type="text" class="form-control" id="serialNumbber"
							name="serialnumber" placeholder="Enter Serial Number" required>
					</div>

					<button type="submit" class="btn btn-primary">Check</button>
				</form>


			</div>
			<div class="col-md-2 col-sm-0"></div>
		</div>



	</div>

	<div class="container mt-5 text-center" ${sethide}>

		<form method="POST" action="<%=request.getContextPath()%>/mici">
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<label class="input-group-text" for="inputGroupSelect01">Select
						Error Code: </label>
				</div>
				<select class="custom-select" id="errorCode" name="errorCode">
					<option value="0" selected>Choose...</option>
					<option value="1">One</option>
					<option value="2">Two</option>
					<option value="3">Three</option>
				</select>
			</div>
			<button type="submit" class="btn btn-success mr-5" name="action"
				value="passButton" ${disable}>
				<span class="display-3">PASS</span>
			</button>
			<button type="submit" class="btn btn-danger" name="action"
				value="failButton" ${disable}>
				<span class="display-3">FAIL</span>
			</button>
		</form>


	</div>
	<hr />

</section>
<jsp:include page="/WEB-INF/common/footer.jsp" />