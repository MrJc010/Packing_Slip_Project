<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<!-- Bootstrap core CSS -->
<link href="webjars/bootstrap/4.4.1/css/bootstrap.min.css"
	rel="stylesheet">


<meta charset="ISO-8859-1">
<title>RMA Receiving</title>
</head>
<body>
	<%
		int count = 1;
	%>
	<div class="container">
		<div class="row">
			<h3>RMA Receiver</h3>
		</div>

		<div class="row">
			<button type="button" name="updateInfo" class="btn btn-primary">Primary</button>

		</div>

		<div class="row p5">
			<form id="form-1" action="/savingdata" method="POST">
				<table class="table table-striped">
					<thead>
						<tr>
							<th scope="col" class="text-left">Line#</th>
							<th scope="col" class="text-left">RMA#</th>
							<th scope="col" class="text-left">CPO SN#</th>
							<th scope="col" class="text-left">PPID</th>
							<th scope="col" class="text-left">PN</th>
							<th scope="col" class="text-left">SN</th>
							<th scope="col" class="text-left">Revision</th>

							<th scope="col" class="text-left">Special Instruction</th>
							<th scope="col" class="text-left">Manufacture PN</th>
							<th scope="col" class="text-left">CO#</th>
							<th scope="col" class="text-left">Lot#</th>
							<th scope="col" class="text-left">Problem Code</th>
							<th scope="col" class="text-left">Description</th>
							<th scope="col" class="text-left">DPS</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${items}" var="item">

							<tr>
								<td>
									<%
										out.print(count);
									%>
								</td>
								<td><input type=hidden name="rma" value="${item.rma}" readonly="readonly">${item.rma}</td>
								<td><input type="text" name="CPO_SN" value="">${item.CPO_SN}</td>
								<td><input type=hidden name="ppid" value="${item.ppid}" readonly="readonly">${item.ppid}</td>
								<td><input type="hidden" name="pn" value="${item.pn}" readonly="readonly">${item.pn}</td>
								<td><input type="text" name="sn" value="">${item.sn}</td>
								<td><input type="text" name="revision" value="">${item.revision}</td>
								<td><input type="text" name="specialInstruction" value="">${item.specialInstruction}</td>
								<td><input type="text" name="mfgPN" value="">${item.mfgPN}</td>
								<td><input type=hidden name="co" value="${item.co}" readonly="readonly">${item.co}</td>
								<td><input type=hidden name="lot" value="${item.lot}" readonly="readonly">${item.lot}</td>
								<td><input type=hidden name="problemCode" value="${item.problemCode}" readonly="readonly">${item.problemCode}</td>
								<td><input type=hidden name="description" value="${item.description}" readonly="readonly">${item.description}</td>
								<td><input type=hidden name="dps" value="${item.dps}" readonly="readonly">${item.dps}</td> 
							</tr>

							<%
								count++;
							%>
						</c:forEach>

					</tbody>
				</table>
				<input type="submit" value="Receive" style="margin: 0 auto;">
			</form>
		</div>

	</div>

	<script src="webjars/jquery/3.4.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
	<script type="extensions/editable/bootstrap-table-editable.js"></script>

</body>
</html>