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
			<p>
				Calling data from function getUnReceiveItem. This function is providing JSON format.
			</p>
		</div>
		<button onclick="exportTableToExcel('tblData', 'members-data')">Export Table Data To Excel File</button>

		<table id="tblData">
			<tr>
				<th>ppid</th>
				<th>pn</th>
				<th>co</th>
				<th>lot</th>
				<th>dps</th>
				<th>pro_code</th>
				<th>code_descp</th>
				<th>rma</th>
				<th>status</th>
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