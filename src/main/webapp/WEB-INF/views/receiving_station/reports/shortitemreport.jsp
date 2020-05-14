<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Short Item Report"></c:param>
</c:import>
<section>

	<div class="container p-5">
		<div class="row justify-content-center">
			<h1>Physical Receiving Station</h1>
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
				<tr>
					<td>John Doe</td>
					<td>john@gmail.com</td>
					<td>USA</td>
					<td>John Doe</td>
					<td>john@gmail.com</td>
					<td>USA</td>
					<td>John Doe</td>
					<td>john@gmail.com</td>
					<td>USA</td>
				</tr>
				<tr>
					<td>John Doe</td>
					<td>john@gmail.com</td>
					<td>USA</td>
					<td>John Doe</td>
					<td>john@gmail.com</td>
					<td>USA</td>
					<td>John Doe</td>
					<td>john@gmail.com</td>
					<td>USA</td>
				</tr>
				<tr>
					<td>John Doe</td>
					<td>john@gmail.com</td>
					<td>USA</td>
					<td>John Doe</td>
					<td>john@gmail.com</td>
					<td>USA</td>
					<td>John Doe</td>
					<td>john@gmail.com</td>
					<td>USA</td>
				</tr>

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