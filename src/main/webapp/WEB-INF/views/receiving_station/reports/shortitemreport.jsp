<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Short Item Report"></c:param>
</c:import>
<section>

	<div class="container p-5">
		<%-- Search Bar --%>
		<div class="row bg-light">
			<div class="col-md-2"></div>
			<div class="col-md-8 col-sm-12  py-2 px-5">
				<h4 class="text-center text-primary p-1 display-4">
					<strong>SHORT ITEM REPORT</strong>
				</h4>
				<form action="<%=request.getContextPath()%>/shortitem" method="GET">

					<div class="input-group mb-3">
						<input type="hidden" name="action" value="findRMA"> <input
							class="form-control form-control-lg" type="text" name="inputrma"
							aria-describedby="emailHelp" placeHolder="Enter RMA" required>
						<div class="input-group-append">
							<input type="submit" class="btn btn-primary btn-lg" value="FIND"
								name="actionSubmitRMA">
						</div>
					</div>
				</form>
			</div>
			<div class="col-md-2-2"></div>
		</div>
		<div>
			<p>Calling data from function getUnReceiveItem. This function is
				providing JSON format.</p>
		</div>
		<button onclick="exportTableToExcel('tblData', 'members-data')">Export
			Table Data To Excel File</button>

		<table id="tblData" class="table table-bordered">
			<thead class="thead-dark">
				<tr>
					<th scope="col">ppid</th>
					<th scope="col">pn</th>
					<th scope="col">co</th>
					<th scope="col">lot</th>
					<th scope="col">dps</th>
					<th scope="col">pro_code</th>
					<th scope="col">code_descp</th>
					<th scope="col">rma</th>
					<th scope="col">status</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${passingList}" var="aRow">
					<tr>
						<c:forEach items="${aRow}" var="iRow">
							<td>${iRow}</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>

	<script>
	function exportTableToExcel(tableID, filename = ''){
	    var downloadLink;
	    var dataType = 'application/vnd.ms-excel';
	    var tableSelect = document.getElementById(tableID);
	    var tableHTML = tableSelect.outerHTML.replace(/ /g, '%20');
	    
	    // Specify file name
	    filename = filename?filename+'.xls':'excel_data.xls';
	    
	    // Create download link element
	    downloadLink = document.createElement("a");
	    
	    document.body.appendChild(downloadLink);
	    
	    if(navigator.msSaveOrOpenBlob){
	        var blob = new Blob(['\ufeff', tableHTML], {
	            type: dataType
	        });
	        navigator.msSaveOrOpenBlob( blob, filename);
	    }else{
	        // Create a link to the file
	        downloadLink.href = 'data:' + dataType + ', ' + tableHTML;
	    
	        // Setting the file name
	        downloadLink.download = filename;
	        
	        //triggering the function
	        downloadLink.click();
	    }
	}
	
	</script>

</section>


<c:import url="/WEB-INF/common/footer.jsp" />