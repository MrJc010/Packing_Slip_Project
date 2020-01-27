<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<!-- Bootstrap core CSS -->
<link href="webjars/bootstrap/4.4.1/css/bootstrap.min.css"
	rel="stylesheet">


<title>List PPID</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<h3>List PPID Read From Excel</h3>
		</div>
		<div class="row p5">
			<table class="table table-striped">
				<thead>
					<tr>
						<th scope="col" class="text-left">PN#</th>
						<th scope="col" class="text-left">PPID#</th>
						<th scope="col" class="text-left">CO#</th>
						<th scope="col" class="text-left">SYS-DATE-REC</th>
						<th scope="col" class="text-left">LOT#</th>
						<th scope="col" class="text-left">DPS#</th>
						<th scope="col" class="text-left">PROBLEM-CODE</th>
						<th scope="col" class="text-left">PROBLEM-DESC</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${rows2}" var="aRow">

						<tr>
							<td>${aRow.partNumber}</td>
							<td>${aRow.poNumber}</td>
							<td>${aRow.lotNumber}</td>
							<td>${aRow.quality}</td>
							<td>${aRow.RMANumber}</td>
							
							<td>CMP-PMH0T</td>
							<td>CN0PMH0T1296361EA034</td>
							<td>18343956W</td>
							<td>2/12/2018</td>
							<td>017340459</td>
							<td>344433183</td>
							<td>PB3I</td>
							<td>SR Online : networkconnection:Windows 10:</td>
						</tr>


					</c:forEach>
					<tr>

					</tr>
				</tbody>
			</table>

		</div>
	</div>


	<script src="webjars/jquery/3.4.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</body>
</html>