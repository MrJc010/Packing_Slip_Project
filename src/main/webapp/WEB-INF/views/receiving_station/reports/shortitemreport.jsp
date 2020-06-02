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
		<!-- <button onclick="exportTableToExcel('tblData', 'members-data')">Export
			Table Data To Excel File</button>
 -->
		<!-- <table id="tblData" class="table table-bordered"> -->
		<button onclick="ExportExcel('xlsx')">Export</button>
		<table id="exportable_table" class="table table-bordered">
			<thead class="thead-dark">
				<tr>
					<th scope="col">PPID</th>
					<th scope="col">PN</th>
					<th scope="col">CO</th>
					<th scope="col">LOT</th>
					<th scope="col">DPS</th>
					<th scope="col">PRO CODE</th>
					<th scope="col">CODE DESCP</th>
					<th scope="col">RMA</th>
					<th scope="col">STATUS</th>
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

   <script type="text/javascript">
      function ExportExcel(type, fn, dl) {
         var elt = document.getElementById('exportable_table');
         var wb = XLSX.utils.table_to_book(elt, {sheet:"Sheet JS"});
         return dl ?
            XLSX.write(wb, {bookType:type, bookSST:true, type: 'base64'}) :
            XLSX.writeFile(wb, fn || ('SheetJSTableExport.' + (type || 'xlsx')));
      }
   </script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/xlsx.full.min.js"></script>

</section>


<c:import url="/WEB-INF/common/footer.jsp" />