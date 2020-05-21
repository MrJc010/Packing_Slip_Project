<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="V1-Station"></c:param>
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
				<h3 class="text-center text-primary p-1 display-5">
					<strong>V1 Stations</strong>
				</h3>
				<form method="GET" action="<%=request.getContextPath()%>/mici">

					<div class="form-group">
						<input type="hidden" name="page" value="check"><label
							for="PPID">Enter PPID: </label> <input type="text"
							class="form-control" id="PPID" aria-describedby="emailHelp"
							placeholder="Enter PPID" name="ppidNumber" required
							value="${ppid}">
					</div>
					<%-- 
					<div class="form-group">
						<label for="serialNumbber">Enter Serial Number</label> <input
							type="text" class="form-control" id="serialNumbber"
							name="serialnumber" placeholder="Enter Serial Number" required
							value="${sn}">
					</div> --%>
					<div class="form-groud text-center my-3">
						<button type="submit" class="btn btn-primary btn-lg">Check</button>
					</div>

				</form>

			</div>

		</div>

		<div class="container text-center mt-5" ${setHiddenResultSucess}>
			<div class="alert alert-success" role="alert">${ppid}isupdated
				successfully!</div>
		</div>
		<div class="container  text-center" ${seterrorhiddenMICI}>
			<div class="alert alert-danger mt-5" role="alert">
				<label>${currentStatusAtMICI}</label>
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
					<div>
						<label for="statusAtMICI"><strong>${currentStatusAtMICI}</strong></label>
					</div>

				</div>
			</div>
			<div class="col-md-4"></div>

		</div>


		<div class="row text-center">
			<div class="col-md-2"></div>
			<div class="col-md-8 col-sm-12 justify-content-center">
				<form method="POST" action="<%=request.getContextPath()%>/mici">



					<div id="selectionMICI" class="row justify-content-center">

						<div class=" input-group mb-3" id="errorDiv1">

							<div class="input-group-prepend">
								<label class="input-group-text" for="inputGroupSelect01">Select
									Error Code: </label>
							</div>
							<select class="custom-select" id="errorCode1" name="errorCode1">

							</select>
						</div>

					</div>
					<a onClick=functionx()> <span
						class="btn btn-outline-primary border my-4">
							<H3>Add Error Code</H3>
					</span>
					</a><br />


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
			<div class="col-md-2"></div>


		</div>


	</div>
	<hr />



	<script type="text/javascript">
    var list = ${listErrorCodes};
    var count = ${currentCountMICI};

    var selectX = document.getElementById("errorCode1");
    var divError = document.getElementById("errorDiv1");
    var op = document.createElement('option');
    op.appendChild(document.createTextNode('Choose...'));
    op.value = '0';
    selectX.appendChild(op);

    var btn = document.createElement('span');
    btn.value = "errorDiv1";
    btn.onclick = deleteErrorDiv;
    btn.className = "btn btn-outline-danger mx-3"
    btn.innerHTML = "Delete";
    divError.appendChild(btn);

    var i = 0;
    for (var key in list) {
    	if (list.hasOwnProperty(key)) {
     	    var divError = document.getElementById("errorDiv" + i);
    	    var op = document.createElement('option');
    	    op.appendChild(document.createTextNode(key + " -> " + list[key]));
    	    op.value = key;
    	    selectX.appendChild(op);
    	    i+=1;
    	}
      }

    function deleteErrorDiv() {
    
      var aID = event;

      var node = document.getElementById("selectionMICI");

      node.removeChild(document.getElementById(aID.target.value));

    }

    function functionx() {
    	if(count <= 10){
    	      var node = document.getElementById("selectionMICI");
    	      var last = document.getElementById("errorDiv"+count);
    	      var x = document.createElement('div');
    	      x.className = 'input-group mb-3';
    	      x.id = 'errorDiv' + count;
    	      x.innerHTML =
    	        // '<div class=\" input-group mb-3\" id=\"errorDiv' + count + '\">' +
    	        '<div class=\"input-group-prepend\">' +
    	        '<label class=\"input-group-text\" for=\"inputGroupSelect01\">Select Error Code: </label>' +
    	        '</div>' +
    	        '<select class=\"custom-select\" id=\"errorCode' + count + '\" name=\"errorCode' + count + '\">' +
    	        '<option value=\"0\" selected>Choose...</option> ' +
    	        '< /select> ';
    	      // '< /div>';
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
    	      for (var key in list) {
    	      	if (list.hasOwnProperty(key)) {
    
    	       	    var divError = document.getElementById("errorDiv" + i);
    	      	    var op = document.createElement('option');
    	      	    op.appendChild(document.createTextNode(key + " -> " + list[key]));
    	      	    op.value = key;
    	      	    selectX.appendChild(op);
    	      	    i+=1;
    	      	}
    	     	}
    	      count++;
    	}

    
    }
  </script>


</section>


<c:import url="/WEB-INF/common/footer.jsp" />