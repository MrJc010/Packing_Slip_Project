<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/common/header.jsp" />


<section>

	<div class="container" ${hidden}>
		<h1 class="text-center mt-4 my-4">This is MICI</h1>

		<div class="row">
			<div class="col-md-2 col-sm-0"></div>
			<div class="col-md-8 col-sm-2 text-center">

				<form method="get" action="<%=request.getContextPath()%>/mici">
					<input type="hidden" name="page" value="check">
					<div class="form-group">
						<label for="PPID">Enter PPID: </label> <input type="text"
							class="form-control" id="PPID" aria-describedby="emailHelp"
							placeholder="Enter PPID" name="ppidNumber" required>
					</div>
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
	<hr />
	<div class="container">
		<h2>
			<span class="text-danger">Problem Code: </span>${problemcode}</h2>
		<span class="text-dark lead"><h3><strong>Problem Description: </strong></h3>${problemdecription}</span>

		<div class="mt-5 row justify-content-md-center">
			<div class="col-lg-6 text-center">
				<form method="post" action="<%=request.getContextPath()%>/mici">
					<input type="hidden" name="result" value="pass">
					<button type="submit" class="btn btn-success" ${disable}>
						<span class="display-3">PASS</span>
					</button>

				</form>

			</div>
			<div class="col-lg-6 text-center">

				<form method="post" action="<%=request.getContextPath()%>/mici">
					<input type="hidden" name="result" value="fail">
					<button type="submit" class="btn btn-danger" ${disable}>
						<span class="display-3">FAIL</span>
					</button>

				</form>
			</div>
		</div>

	</div>
</section>
<jsp:include page="/WEB-INF/common/footer.jsp" />