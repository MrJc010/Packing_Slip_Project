<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<!-- Bootstrap core CSS -->
<link href="webjars/bootstrap/4.4.1/css/bootstrap.min.css"
	rel="stylesheet">


<title>Excel Loading Page</title>
</head>
<body>

	<div class="container">
		<div class="row">
			
				<input type="text" name="#RMA"/>
				<button>Save</button>
				<button>Download</button>
			
		
		</div>
	
	
	
		<div class="row">
			<h3>Excel File Reader</h3>
		</div>
		<div class="row p5">
			<table class="table table-striped">
				<thead>
					<tr>
						<th scope="col" class="text-left">PN#</th>
						<th scope="col" class="text-left">PO#</th>
						<th scope="col" class="text-left">LOT#</th>
						<th scope="col" class="text-left">QTY</th>
						<th scope="col" class="text-left">RMA#</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${rows}" var="aRow">

						<tr>
							<td>${aRow.partNumber}</td>
							<td>${aRow.poNumber}</td>
							<td>${aRow.lotNumber}</td>
							<td>${aRow.quality}</td>
							<td>${aRow.RMANumber}</td>

						</tr>


					</c:forEach>

				</tbody>
			</table>

		</div>
	</div>

	<script src="webjars/jquery/3.4.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</body>
</html>