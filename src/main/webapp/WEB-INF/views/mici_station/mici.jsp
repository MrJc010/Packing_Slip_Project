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

				<form method="GET" action="<%=request.getContextPath()%>/mici">

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

		<div class="container text-center" ${setHiddenResultSucess}>
			<div class="alert alert-success" role="alert">${ppid}is
				updated successfully!</div>
		</div>

	</div>

	<div class="container  text-center" ${seterrorhiddenMICI}>
		<div class="alert alert-danger mt-5" role="alert">
			<label>${currentStatusAtMICI}</label>
		</div>

	</div>

	<div class="container mt-5 text-center" ${sethideMICI}>

		<form method="POST" action="<%=request.getContextPath()%>/mici">

			<div ${setHidenInfo}>

				<div>
					<label for="PPID">PPID#: ${ppidCheckAtMICI}</label>
				</div>
				<div ${seterrorhiddenproblemMICI}>
					<div>
						<label>Problem Code: ${problemcodeAtMICI}</label>
					</div>
					<div>
						<label>Problem Description:#: ${problemDescpAtMICI}</label>
					</div>
				</div>
				<div>
					<label for="statusAtMICI">${currentStatusAtMICI}</label>
				</div>

			</div>

			<%-- 		<div${setHidenResult}>
				<span class="badge badge-success mb-5">
					<h3 class="display-5">Successful!</h3>
				</span>
			</div> --%>


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
	<hr />



	<script type="text/javascript">
    var list = [1, 2, 3];
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
    btn.innerHTML = "Delete";
    divError.appendChild(btn);

    for (var i = 0; i < list.length; i++) {
      var divError = document.getElementById("errorDiv" + i);
      var op = document.createElement('option');
      op.appendChild(document.createTextNode(list[i]));
      op.value = list[i];
      selectX.appendChild(op);
    }

    function deleteErrorDiv() {
    
      var aID = event;
      console.log(aID.target.value);
      var node = document.getElementById("selectionMICI");
      console.log(node);
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

    	      var btn = document.createElement('a');
    	      btn.value = "errorDiv" + count;
    	      btn.onclick = deleteErrorDiv;
    	      btn.innerHTML = "Delete";
    	      divError.appendChild(btn);
    	      for (var i = 0; i < list.length; i++) {
    	        var op = document.createElement('option');
    	        op.appendChild(document.createTextNode(list[i]));
    	        op.value = list[i];
    	        selectX.appendChild(op);

    	      }
    	      count++;
    	}

    }
    
  </script>

</section>
<jsp:include page="/WEB-INF/common/footer.jsp" />