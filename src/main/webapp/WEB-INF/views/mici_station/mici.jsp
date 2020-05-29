<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="MICI Station"></c:param>
</c:import>


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
	<div class="container-fluid  p-5" ${hidden}>

		<div class="row bg-light">
			<div class="col-md-2"></div>
			<div class="col-md-8 col-sm-12  py-2 px-5">
				<h4 class="text-center text-primary p-1 display-4">
					<strong>MICI STATION</strong>
				</h4>
				<form action="<%=request.getContextPath()%>/mici" method="GET">
					<div class="input-group mb-3">
						<input type="hidden" name="page" value="check"> <input
							type="text" class="form-control form-control-lg" id="PPID"
							aria-describedby="emailHelp" placeholder="Enter PPID"
							name="ppidNumber" required value="${ppid}"> 
							<div class="input-group-append">

							<input type="submit" class="btn btn-primary btn-lg" value="FIND">
						</div>
							
					</div>
				</form>
				


			</div>

		</div>

		<div class="container text-center mt-5" ${setHiddenResultSucess}>
			<div class="alert alert-success" role="alert">
				<h4>
					<label class="text-dark">${messageSuccess}</label>
				</h4>
			</div>
		</div>
		<div class="container text-center" ${seterrorhiddenMICI}>
			<div class="alert alert-warning mt-5" role="alert">
				<h4>
					<label class="text-dark">${errorMessage}</label>
				</h4>
			</div>
		</div>
	</div>



	<div class="container-fluid" ${sethideMICI}>

		<div class="row mb-5">
			<div class="col-md-4"></div>
			<div class="col-md-4 col-sm-12 justify-content-center">
				<div ${setHidenInfo}>

					<div>
						<label for="PPID"><strong>PPID#:
								${ppidCheckAtMICI}</strong></label>
					</div>
					<div ${seterrorhiddenproblemMICI}>
						<div>
							<label><strong>Problem Code:
									${problemcodeAtMICI}</strong></label>
						</div>
						<div>
							<label><strong>Problem Description:#:
									${problemDescpAtMICI}</strong></label>
						</div>
					</div>
					<div class="alert alert-white text-center" role="alert">
						<h4>
							<label class="text-success"> <strong>${currentStatusAtMICI}</strong>
							</label>
						</h4>
					</div>
				</div>
			</div>
			<div class="col-md-4"></div>

		</div>


		<div class="row text-center">
			<div class="col-md-2"></div>
			<div class="col-md-8 col-sm-12 justify-content-center">
				<form method="POST" action="<%=request.getContextPath()%>/mici">
					<div id="selectionMICI" class="row justify-content-center"
						${hiddenSelectErrorCode}></div>
					<div class="text-center"></div>


					<div class="text-center">


						<a id="addErrorCode"
							class="btn btn-danger  border shadow p-3 mb-5 rounded mr-4"
							onClick=functionx() ${hiddenAddErrorCodeBTN}> <span
							class="h3 text-white"> ADD ERROR CODE </span>
						</a>

						<button type="submit"
							class="btn btn-success border  shadow p-3 mb-5 rounded mr-4"
							name="action" id="passBTN" value="${PassBTNValue}" ${disable}
							${hiddenPassBTN}>
							<span class="h3 text-white">${PassBTNValue}</span>
						</button>
						<div>
							<button type="submit"
								class="btn btn-danger  border shadow p-3 mb-5 rounded mr-4"
								name="action" id="failBTN" value="failButton" ${disable}
								${hiddenFailBTN}>
								<span class="h3 text-white px-5">MOVE TO REPAIR01</span>
							</button>
						</div>

					</div>


				</form>
			</div>
			<div class="col-md-2"></div>

		</div>


	</div>




	<script type="text/javascript">
		var list = ${listErrorCodes};
		var count = ${currentCountMICI};
		var node = document.getElementById("selectionMICI");
		var passBTN = document.getElementById("passBTN");
		var failBTN = document.getElementById("failBTN");
		function deleteErrorDiv() {

			var aID = event;

			var node = document.getElementById("selectionMICI");

			node.removeChild(document.getElementById(aID.target.value));

			if (node.childElementCount === 0) {
				passBTN.hidden = false;
				failBTN.hidden = true;
				node.hidden = true;
			}

		}

		function functionx() {

			passBTN.hidden = 'true';
			failBTN.hidden = 'false';
			node.hidden = 'false';

			if (count <= 10) {

				var last = document.getElementById("errorDiv" + count);
				var x = document.createElement('div');
				x.className = 'input-group mb-3';
				x.id = 'errorDiv' + count;
				x.innerHTML = '<div class=\"input-group-prepend\">'
						+ '<label class=\"input-group-text\" for=\"inputGroupSelect01\">Select Error Code: </label>'
						+ '</div>'
						+ '<select class=\"custom-select\" id=\"errorCode' + count + '\" name=\"errorCode' + count + '\">'
						+ '<option value=\"0\" selected>Choose...</option> '
						+ '< /select> ';

				node.appendChild(x);
				var selectX = document.getElementById("errorCode" + count);
				var divError = document.getElementById("errorDiv" + count);

				var btn = document.createElement('button');
				btn.value = "errorDiv" + count;
				btn.onclick = deleteErrorDiv;
				btn.innerHTML = "Delete";
				btn.className = "btn btn-outline-danger mx-3"
				divError.appendChild(btn);
				var i = 0;
				for ( var key in list) {
					console.log(key);					
					if (list.hasOwnProperty(key)) {

						var divError = document.getElementById("errorDiv" + i);
						var op = document.createElement('option');
						op.appendChild(document.createTextNode(key + " -> "
								+ list[key]));
						op.value = key;
						selectX.appendChild(op);
						i += 1;
					}
				}
				count++;
			}

		}
	</script>

</section>
<jsp:include page="/WEB-INF/common/footer.jsp" />