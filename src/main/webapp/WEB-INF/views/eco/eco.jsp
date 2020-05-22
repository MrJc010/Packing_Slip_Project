<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="ECO Station"></c:param>
</c:import>
<section>

	<div class="container-fluid p-5">

		<%-- Search Bar --%>
		<div class="row jumbotron my-1">
			<div class="col-lg-2"></div>
			<div class="col-lg-8  col-sm-12">
				<h3 class="text-center text-primary p-1 display-5">
					<strong>Enter and Search your PPID</strong>
				</h3>
				<form action="<%=request.getContextPath()%>/eco" method="GET">
					<input type="hidden" name="action01" value="findPPID">
					<div class="input-group mb-3">
						<input class="form-control form-control-lg" type="text"
							name="inputppid" placeHolder="Enter PPID" required>
						<div class="input-group-append">

							<input type="submit" class="btn btn-primary btn-lg" value="FIND"
								name="actionSubmitRepair01">
						</div>
					</div>
				</form>
			</div>
			<div class="col-lg-2"></div>
		</div>

		<%-- Information Details --%>


		<div id="result1" ${resultHidden}>
			<h1 class="text-center">Revision Update Instruction</h1>


			<div id="accordion">


				<div class="card  text-center">
					<div class="card-header">
						<div class="row">
							<div class="col-4">
								<strong><h3>ID</h3></strong>
							</div>

							<div class="col-4">
								<strong><h3>FROM REVISION</h3></strong>
							</div>

							<div class="col-4">
								<strong><h3>TO REVISION</h3></strong>
							</div>
						</div>
					</div>
				</div>


				<c:set var="indexValue" value="${-1}" scope="page" />
				<c:forEach var="item" items="${listItems}">
					<c:set var="indexValue" value="${indexValue +1}" scope="page" />
					<div class="card  text-center">
						<div class="card-header" id="heading${indexValue}">
							<div class="row">
								<div class="col-4">
									<h3 class="mb-0">
										<button class="btn btn-link font-weight-bold"
											data-toggle="collapse" id="btn${indexValue}"
											data-target="#collapse${indexValue}" aria-expanded="true"
											aria-controls="collapse${indexValue}"
											onClick="clickBTN(event)">
											<h3 id="th3${indexValue}">${item[0]}</h3>
										</button>
									</h3>
								</div>

								<div class="col-4">


									<h3 class="mb-0">
										<button class="btn disbaled font-weight-bold">
											<h3>${item[1]}</h3>
										</button>
									</h3>

								</div>

								<div class="col-4">
									<h3 class="mb-0">
										<button class="btn disbaled font-weight-bold">
											<h3>${item[2]}</h3>
										</button>
									</h3>
								</div>
							</div>


						</div>

						<div id="collapse${indexValue}" class="collapse"
							aria-labelledby="heading${indexValue}" data-parent="#accordion">
							<div class="row">
								<div class="col-4 text-center">
									<strong><h5>LOCATION</h5></strong>
								</div>
								<div class="col-4">

									<strong><h5>INSTRUCTION DETAILS</h5></strong>
								</div>
								<div class="col-4">
									<strong><h5>IMAGE</h5></strong>
								</div>
							</div>
							<div class="card-body row">
								<div class="col-4" id="left${indexValue}">ggg</div>
								<div class="col-4" id="middle${indexValue}">ggg</div>
								<div class="col-4" id="right${indexValue}">
									<img class="img-thumbnail" src="" id="img${indexValue}"
										alt="${item[0]}" />
								</div>

							</div>
						</div>
					</div>

				</c:forEach>

			</div>
			<!-- hidden button -->
			<div class="text-center p-5">
				<form action="<%=request.getContextPath()%>/eco" method="post">
					<input type="hidden" name="action01" value="transfertoMICI">
					<button type="submit" class="btn btn-primary">TRANSFER TO
						MICI</button>
				</form>
			</div>
		</div>



	</div>


	</div>
</section>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<script>

var myList = <%=request.getAttribute("instructionDetail")%>;
console.log(myList);

function clickBTN(event){
	var contentBTN = event.target.textContent;
	var index = event.target.id.substring(3);
	var leftEle = document.getElementById("left"+index);
	var middleEle = document.getElementById("middle"+index);
	var rightEle = document.getElementById("right"+index);
	var imgEle = document.getElementById("img"+index);
	leftEle.innerHTML = myList['2'][index][0];
	middleEle.innerHTML = myList['2'][index][1];
	console.log(myList['2'][index][2]);
	imgEle.src = myList['2'][index][2];
}

var modalMessage = document.getElementById("modal-message");
		function imgClickHandler(sr)
        {		
            $('#mimg').attr('src',sr);
            $('#myModal').modal('show');
        };
        
        var isNoInformationUpdate = `<%=request.getAttribute("isNoInformationUpdate")%>
	`;
	if (isNoInformationUpdate === "True") {
		$(document)
				.ready(
						function() {
							// Show modal on page load

							$("#myModal").modal({
								backdrop : 'static',
								keyboard : false
							});
							modalMessage.innerText = "No action needed. Click \"Upgrade\" button to go next.";
							modalMessage.classList = "";
							modalMessage.classList.add("text-warning");
						});
	}
</script>
<c:import url="/WEB-INF/common/footer.jsp" />
