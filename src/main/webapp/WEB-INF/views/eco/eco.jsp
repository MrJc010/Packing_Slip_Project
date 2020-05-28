<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="ECO Station"></c:param>
</c:import>





<section>
	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel"></h4>
				</div>
				<div class="modal-body text-center">
					<img class="img-fluid" id="mimg" src="" style="min-height: 800px; height: 800px;">
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>

	<div class="container-fluid p-5">

		<%-- Search Bar --%>
		<div class="row bg-light">
			<div class="col-md-2"></div>
			<div class="col-md-8 col-sm-12  py-2 px-5">
				<h3 class="text-center text-primary p-1 display-3">
					<strong>ECO STATION</strong>
				</h3>
				<form action="<%=request.getContextPath()%>/eco" method="GET">
					
					<div class="input-group mb-3">
					<input type="hidden" name="action01" value="findPPID">
						<input class="form-control form-control-lg" type="text"
							name="inputppid" aria-describedby="emailHelp" placeHolder="Enter PPID" required>
						<div class="input-group-append">
							<input type="submit" class="btn btn-primary btn-lg" value="FIND"
								name="actionSubmitRepair01">
						</div>
					</div>
				</form>
			</div>
			<div class="col-md-2-2"></div>
		</div>


		
			<div class="container text-center mt-5" ${setHiddenResultSucess}>
			<div class="alert alert-success" role="alert">${messageSuccess}</div>
		</div>
		<div class="container text-center" ${seterrorhiddenMICI}>
			<div class="alert alert-warning mt-5" role="alert">
				<label class="text-dark"><h4>${errorMessage}</h4></label>
			</div>
		</div>
		
		
		<%-- Information Details --%>


		<div class="mt-5 mb-5" id="result1" ${resultHidden}>
			<h1 class="text-center">Revision Update Instruction</h1>


			<div id="accordion">


				<div class="card  text-center">
					<div class="card-header bg-dark text-white">
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
									<img class="img-thumbnail" data-src="" id="img${indexValue}"
										alt="${item[0]}"  onclick=imgClickHandler() >
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

var imgLink= null;
function imgClickHandler()
{		
	console.log("test img" + imgLink );
    $('#mimg').attr('src',imgLink);
    $('#myModal').modal('show');
};

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
 
 	imgLink = myList['2'][index][2];
}
var modalMessage = document.getElementById("modal-message");		
        
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