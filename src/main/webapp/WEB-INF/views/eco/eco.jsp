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
								name="actionSubmitRepair01" id="btnSubmit" onClick="asyncCall()">
						</div>
					</div>
				</form>
			</div>
			<div class="col-lg-2"></div>
		</div>

		<%-- Information Details --%>
	

	<div id="result1" ${resultHidden}>
	<h1>aaaaa</h1>
	
		
	</div>

	
	</div>
</section>
<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<script>



var testValue = null;
function test() {
	return new Promise(resolve => {
		setTimeout(()=> {
			testValue = `<%=request.getAttribute("myObject")%>`;
			console.log("testValue" + testValue);
		},2000);
	});
}

async function asyncCall(){
	console.log('Start');
	var result = await test();
	console.log(result);
	console.log('End');
}
var  showResult =  () => {
	var listItems =  ['11','222','333'];
	/* divResult.innerHTML = ""; */
/* 	var htmlString = '<ul>';
	if(listItems){
		for (var key in listItems) {
			console.log(listItems[key][0]);
			htmlString += `<li>${listItems[key][0]}</li>`;
		}
	}
	htmlString += '</ul>';
	divResult.innerHTML = htmlString; */
}
var modalMessage = document.getElementById("modal-message");
		function imgClickHandler(sr)
        {		
            $('#mimg').attr('src',sr);
            $('#myModal').modal('show');
        };
        
        var isNoInformationUpdate = `<%=request.getAttribute("isNoInformationUpdate")%>`;
if(isNoInformationUpdate ==="True"){
	$(document).ready(function(){
	    // Show modal on page load
	    
	    $("#myModal").modal({
	        backdrop: 'static',
	        keyboard: false
	      });    	   
	    modalMessage.innerText = "No action needed. Click \"Upgrade\" button to go next.";
	    modalMessage.classList = "";
	    modalMessage.classList.add("text-warning");
	});
}
</script>
<c:import url="/WEB-INF/common/footer.jsp" />
