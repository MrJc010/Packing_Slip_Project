<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Search Info"></c:param>
</c:import>

<section>
	<style>
.note {
	text-align: center;
	height: 80px;
	background: -webkit-linear-gradient(left, #0072ff, #8811c5);
	color: #fff;
	font-weight: bold;
	line-height: 80px;
}

.form-content {
	padding: 5%;
	border: 1px solid #ced4da;
	margin-bottom: 2%;
}

.form-control {
	background-color : #ecf0f1;
	border-radius: 1.5rem;
	 
}

.btnSubmit {
	border: none;
	border-radius: 1.5rem;
	padding: 1%;
	width: 20%;
	cursor: pointer;
	background: #0062cc;
	color: #fff;
}
</style>
	<div class="container register-form text-center p-5 mt-5 shadow">
		<form class="form"
			action="<%=request.getContextPath()%>/shopfloor/create_new_locations"
			method="POST">
			<div class="note">
				<p>CREATE NEW LOCATIONS (STEP 02)</p>
			</div>

			<div class="form-content">
				<div class="row">
					<div class="col-md-2  col-sm-1"></div>
					<div class="col-md-8 col-sm-10">
						<div class="form-group mb-4">
							<input type="text" class="form-control" placeholder="PART NUMBER"
								value="" />
						</div>
						<div class="form-group  mb-4">
							<input type="text" class="form-control"
								placeholder="MODEL NUMBER" value="" />
						</div>
						<div class="form-group  mb-4">
							<input type="text" class="form-control"
								placeholder="PROJECT DESCRIPTION" value="" />
						</div>
					</div>
					<div class="col-md-2  col-sm-1"></div>
				</div>
				<button type="submit" class="btnSubmit text-center">Next</button>
			</div>
		</form>
	</div>
</section>

<c:import url="/WEB-INF/common/footer.jsp"></c:import>
