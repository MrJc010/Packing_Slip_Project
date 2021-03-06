<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/common/header.jsp">	
	<c:param name="title" value="Search Info"></c:param>	
</c:import>

<section>

	<div class="container-fluid p-5">

		<%-- Search Bar --%>
		<div class="my-1 justify-content-center bg-light">
			<form action="<%=request.getContextPath()%>/search" method="GET">

				<!-- Row 1  -->
				<div class="form-row mx-auto justify-content-center">
					<div class="form-group col-md-8 px-3">
						<label for="searchPPIDInput"><strong>Enter and
								Search your PPID</strong></label>
						<div class="input-group mb-2">
							<input type="hidden" name="action01" value="searchPPIDInput">
							<input class="form-control form-control-lg" type="text"
								name="inputppid" placeHolder="Enter PPID" value="${inputppid}">
							<div class="input-group-append">

								<input type="submit" class="btn btn-primary btn-lg"
									value="SEARCH" name="actionSubmitRepair01">
							</div>
						</div>
					</div>

				</div>
				<!-- Row 2  -->
				<div class="form-row mx-auto">
					<div class="form-group col-md-1 px-3">
						<label for="refInput"><strong>Ref</strong></label> <select
							id="refInput" class="form-control" name="refInput">
							<option selected>Select..</option>
							<c:forEach var="ref" items="${listRefs}">

								<option value="${ref}"
									${(refInput == ref) ? " selected='selected'" : ""}>${ref}</option>
							</c:forEach>
						</select>



					</div>
					<div class="form-group col-md-1 px-3">
						<label for="optionInput"><strong>Option</strong></label> <select
							id="optionInput" class="form-control" name="optionInput">
							<option selected>Select..</option>

							<c:forEach var="Aoption" items="${listOptions}">
								<option value="${Aoption}"
									${(optionInput == Aoption) ? " selected='selected'" : ""}>${Aoption}</option>
							</c:forEach>


						</select>
					</div>
					<div class="form-group col-md-2 px-3">
						<label for="inputRefValue"><strong>Values</strong></label> <input type="text" class="form-control"
							id="inputRefValue" name="inputRefValue" value="${inputRefValue}" />
					</div>

					<div class="form-group col-md-4 px-3">
						<label for="inputEmployee"><strong>Employee</strong></label> <input
							type="text" class="form-control" id="inputEmployee"
							name="inputEmployee" value="${inputEmployee}" />
					</div>

					<div class="form-group col-md-4 px-3">
						<label for="inputStationName"><strong>Station
								Name</strong></label> <input type="text" class="form-control"
							id="inputStationName" name="inputStationName"
							value="${inputStationName}" />
					</div>
				</div>

				<!-- Row 3  -->
				<div class="form-row mx-auto">
					<div class="form-group col-md-2 px-3">
						<label for="fromDateInput"><strong>From Date</strong></label>

						<div class='input-group date my-auto align-items-center'
							id='datetimepicker6'>

							<input type='text' class='form-control'
								data-language='en' id="fromDateInput" name="fromDateInput"
								value="${fromDateInput}" /> 
								
						<!-- 		<label for="fromDateInput">
								<i class="text-primary fa fa-calendar fa-lg px-2 py-2"
								aria-hidden="true"></i>
 -->

							</label>


						</div>

					</div>

					<div class="form-group col-md-2 px-3">
						<label for="toDateInput"><strong>To Date</strong></label>
						<div class='input-group date my-auto' id='datetimepicker6'>
							<input type='text' class='form-control'
								data-language='en' id="toDateInput" name="toDateInput"
								value="${toDateInput}" /> 
						</div>
					</div>

					<div class="form-group col-md-4 px-5">
						<label for="inputFromStation"><strong>From
								Location</strong></label> <input type="text" class="form-control"
							id="inputFromStation" name="inputFromStation"
							value="${inputFromStation}" />
					</div>

					<div class="form-group col-md-4 px-5">
						<label for="inputToStation"><strong>To Location</strong></label> <input
							type="text" class="form-control" id="inputToStation"
							name="inputToStation" value="${inputToStation}" />
					</div>
				</div>


			</form>

		</div>

		<%-- Display PPID SEARCH Section --%>

		<div class="justify-content-center border" ${set_Hidden_PPID_Case}>

			<table class="table table-hover py-5">
				<thead>
					<tr class="table-primary">
						<th scope="col">PPID</th>
						<th scope="col">SN</th>
						<th scope="col">MAC</th>
						<th scope="col">CPU_SN</th>
						<th scope="col">Revision</th>
						<th scope="col">Manf_PN</th>
						<th scope="col">From Location</th>
						<th scope="col">To Location</th>
						<th scope="col">UserID</th>
						<th scope="col">Date</th>
					</tr>
				</thead>
				<tbody>
					<tr class="table-secondary">
						<c:forEach var="item" items="${ppidInfo}">

							<th>${item }</th>

						</c:forEach>
					</tr>
				</tbody>
			</table>
		</div>


		<%-- Display Search By Station --%>


		<div class="justify-content-center" ${set_Hidden_Station_Search}>

			<table class="table table-hover py-5">
				<thead>
					<tr class="table-primary">
						<%-- Show for REPAIR01 --%>
						<c:if test="${stationName == 'REPAIR01' }">
							<th scope="col">PPID</th>
							<th scope="col">ErrorCode</th>
							<th scope="col">Duty</th>
							<th scope="col">Old PN</th>
							<th scope="col">New PN</th>
							<th scope="col">Area Repair</th>
							<th scope="col">UserID</th>
							<th scope="col">Time</th>
							<th scope="col">Action</th>

						</c:if>

						<%-- Show for PHYSICAL --%>
						<c:if test="${stationName == 'PHYSICAL' }">
							<th scope="col">PPID</th>
							<th scope="col">Serial Number</th>
							<th scope="col">MAC</th>
							<th scope="col">CPU_SN</th>
							<th scope="col">Revision</th>
							<th scope="col">mPN</th>
							<th scope="col">UserID</th>
							<th scope="col">Time</th>
						</c:if>

						<%-- Show for MICI --%>
						<c:if test="${stationName == 'MICI' }">

							<th scope="col">PPID</th>
							<th scope="col">Error code</th>
							<th scope="col">User ID</th>
							<th scope="col">Time</th>

						</c:if>

					</tr>
				</thead>
				<tbody>
					<c:forEach var="itemList" items="${stationResultList}">
						<tr class="table-secondary">
							<c:forEach var="aItem" items="${itemList}">
								<c:if test="${ not empty aItem}">
									<th>${aItem}</th>
								</c:if>
								<c:if test="${empty aItem}">
									<th>N/A</th>
								</c:if>

							</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<%-- Display error Message --%>
		<div class="justify-content-center bg-light mt-5" ${setError_Case}>
			<div class="alert alert-warning text-center p-5" role="alert">
				<span class="display-2">${errorMessage}</span>
			</div>
		</div>
	</div>
</section>
<script>
	function stopDefAction(evt) {
		evt.preventDefault();
	}
</script>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script
		src="https://cdnjs.cloudflare.com/ajax/libs/gijgo/1.9.13/combined/js/gijgo.min.js"></script>
<script>
<c:import url="/WEB-INF/js/datePickerSupport.js"></c:import>
</script>
<c:import url="/WEB-INF/common/footer.jsp"/>

 


