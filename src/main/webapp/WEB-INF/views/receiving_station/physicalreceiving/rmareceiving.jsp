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
<style type="text/css">
</style>
</head>
<body>
	<%
		int count = 1;
	%>
	<div class="container">
		<div class="row">
			<h3>RMA Receiver</h3>
		</div>
		<div class="row p5">
			<form id="form-1" action="/savingdata" method="POST">
				<table class="table table-striped">
					<c:forEach items="${items}" var="item">
						<tr>
							<th scope="col" class="text-left">RMA#</th>
							<td><input type=hidden name="rma" value="${item.rma}" readonly="readonly">${item.rma}</td>
						</tr>

						<tr>
							<th scope="col" class="text-left">CPO SN#</th>
							<td><input type="text" name="CPO_SN" value="">${item.CPO_SN}</td>
						</tr>

						<tr>
							<th scope="col" class="text-left">PPID</th>
							<td><input type=hidden name="ppid" value="${item.ppid}"
								readonly="readonly">${item.ppid}</td>
						</tr>

						<tr>
							<th scope="col" class="text-left">PN</th>
							<td><input type="hidden" name="pn" value="${item.pn}"
								readonly="readonly">${item.pn}</td>
						</tr>

						<tr>
							<th scope="col" class="text-left">SN</th>
							<td><input type="text" name="sn" value="">${item.sn}</td>
						</tr>


						<tr>
							<th scope="col" class="text-left">Revision</th>
							<td><input type="text" name="revision" value="">${item.revision}</td>
						</tr>

						<tr>
							<th scope="col" class="text-left">Special Instruction</th>
							<td><input type="text" name="specialInstruction" value="">${item.specialInstruction}</td>
						</tr>


						<tr>
							<th scope="col" class="text-left">Manufacture PN</th>
							<td><input type="text" name="mfgPN" value="">${item.mfgPN}</td>
						</tr>

						<tr>
							<th scope="col" class="text-left">CO#</th>
							<td><input type=hidden name="co" value="${item.co}" readonly="readonly">${item.co}</td>
						</tr>


						<tr>
							<th scope="col" class="text-left">Lot#</th>
							<td><input type=hidden name="lot" value="${item.lot}" readonly="readonly">${item.lot}</td>
						</tr>

						<tr>
							<th scope="col" class="text-left">Problem Code</th>
							<td><input type=hidden name="problemCode"
								value="${item.problemCode}" readonly="readonly">${item.problemCode}</td>
						</tr>
						<tr>
							<th scope="col" class="text-left">Description</th>
							<td><input type=hidden name="description"
								value="${item.description}" readonly="readonly">${item.description}</td>
						</tr>

						<tr>
							<th scope="col" class="text-left">DPS</th>
							<td><input type=hidden name="dps" value="${item.dps}" readonly="readonly">${item.dps}</td>
						</tr>
					</c:forEach>
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