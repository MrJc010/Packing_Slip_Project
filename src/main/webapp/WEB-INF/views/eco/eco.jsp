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
								name="actionSubmitRepair01" id="btnSubmit" onClick="showResult(event)">
						</div>
					</div>
				</form>
			</div>
			<div class="col-lg-2"></div>
		</div>

		<%-- Information Details --%>
	

	<div id="result1" ${resultHidden}>
	<div id="accordion">
				<div class="card">
					<div class="card-header" id="headingOne">
						<h5 class="mb-0">
							<button class="btn btn-link" data-toggle="collapse"
								data-target="#collapseOne" aria-expanded="true"
								aria-controls="collapseOne">Collapsible Group Item #1</button>
						</h5>
					</div>

					<div id="collapseOne" class="collapse show"
						aria-labelledby="headingOne" data-parent="#accordion">
						<div class="card-body">Anim pariatur cliche reprehenderit,
							enim eiusmod high life accusamus terry richardson ad squid. 3
							wolf moon officia aute, non cupidatat skateboard dolor brunch.
							Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon
							tempor, sunt aliqua put a bird on it squid single-origin coffee
							nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica,
							craft beer labore wes anderson cred nesciunt sapiente ea
							proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat
							craft beer farm-to-table, raw denim aesthetic synth nesciunt you
							probably haven't heard of them accusamus labore sustainable VHS.
						</div>
					</div>
				</div>

			</div>
	
		
	</div>

	
	</div>
</section>
<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<script>

var accordionResult = document.getElementById("accordion");
var showResult =  (event) => {
	event.preventDefault();
	console.log('run here');
	var listItems =  ['11','222','333'];
	var tempDiv = document.createElement('div');
	tempDiv.classList = 'card';
	tempDiv.innerHTML = "";
	
	if(listItems){
		console.log('in if');
		for (var key in listItems) {
			tempDiv.innerHTML += `
			<div class=\"card-header\" id=\"heading${key}\">
						<h5 class=\"mb-0\">
							<button class=\"btn btn-link\" data-toggle=\"collapse\"
								data-target=\"#collapse'+key+'\" aria-expanded=\"true\"
								aria-controls=\"collapse'+key+'\">1111</button>
						</h5>
					</div>

					<div id=\"collapse'+key+'\" class=\"collapse show\"
						aria-labelledby=\"heading' +key+ '\" data-parent=\"#accordion\">
						<div class=\"card-body\">222222
						</div>
					</div>
	`;
		}
	}
	accordionResult.appendChild(tempDiv);
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
