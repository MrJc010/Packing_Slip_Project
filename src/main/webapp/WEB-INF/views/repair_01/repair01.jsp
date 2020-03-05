<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/common/header.jsp" />


<section>

	<div class="container">

		<H1 class="text-center">Repair 01</H1>


		<div class="row">
			<div class="col-lg-2"></div>
			<div class="col-lg-8">
				<form action="<%=request.getContextPath()%>/repair01" method="GET">
					<div class="input-group mb-3">

						<input class="form-control" type="text" name="inputppid"
							placeHolder="Enter PPID" required>
						<div class="input-group-append">

							<input type="submit" class="btn btn-primary " value="FIND"
								name="actionSubmitRepair01">
						</div>

					</div>
				</form>
			</div>
			<div class="col-lg-2"></div>


		</div>









		<div class="my-3" ${setRepair01HiddenError }>${setErrorMessage }
		</div>
		<div ${setRepair01Hidden}>

			<c:forEach items="${errorList}" var="aError">
				<div class="accordion" id="accordionExample">
					<div class="card">
						<div class="card-header" id="headingOne">


							<h2 class="mb-0">
								<button class="btn btn-link" type="button"
									data-toggle="collapse" data-target="#xx${aError}"
									aria-expanded="false" aria-controls="xx${aError}">
									${aError}</button>
							</h2>
							<button class="float-right my-2 btn btn-primary" name="edit${aError}" onclick=enableForm("${aError}")>Edit</button>
						</div>

						<div id="xx${aError}" class="collapse"
							aria-labelledby="headingOne" data-parent="#accordionExample">
							<div class="card-body">
								<form action="">
									<fieldset id="disable${aError}" disabled>
										<div class="input-group mb-3">
											<div class="input-group-prepend">
												<label class="input-group-text" for="duty">Select
													Duty: </label>
											</div>
											<select class="custom-select" id="duty" name="duty${aError}">
												<option value="0" selected="">Choose a duty</option>
												<option value="1">duty 01</option>
												<option value="2">duty 02</option>
												<option value="3">duty 03</option>
											</select>
										</div>

										<br>
										<div class="form-group">
											<label for="oldPN">OLD PN</label> <input type="text"
												class="form-control" name="oldPN${aError}"
												placeholder="Enter Old PN">
										</div>


										<br>

										<div class="form-group">
											<label for="oldPN">NEW PN</label> <input type="text"
												class="form-control" name="newPN${aError}"
												placeholder="Enter new PN">
										</div>

										<br>

										<div class="form-group">
											<label for="oldPN">AREA</label> <input type="text"
												class="form-control" name="area${aError}"
												placeholder="Enter area">
										</div>


										<br>
										<div class="form-group">
											<label for="actionInput">Action</label>
											<textarea type="text" class="form-control" name="action${aError}"
												rows="4" id="actionInput" aria-describedby="emailHelp"
												placeholder="Enter action"></textarea>
											<button type="submit" value="submit${aError}" name="submitThree"
												class="btn btn-primary my-5 text-center mx-auto">Submit</button>
										</div>
									</fieldset>
								</form>



							</div>
						</div>
					</div>
				</div>
			</c:forEach>


			<script>
			




			function enableForm(errorCode) {
				console.log("====");

				document.getElementById("disable" + errorCode).disabled = false;
			}
		</script>
		
		</div>
	</div>


</section>

<jsp:include page="/WEB-INF/common/footer.jsp" />