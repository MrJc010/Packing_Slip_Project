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
								name="inputppid" placeHolder="Enter PPID">
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
							<option selected>Ref...</option>
							<option>Ref_1</option>
							<option>Ref_2</option>
							<option>Ref_3</option>
						</select>
					</div>
					<div class="form-group col-md-1 px-3">
						<label for="optionInput"><strong>Option</strong></label> <select
							id="optionInput" class="form-control" name="optionInput">
							<option selected>Option...</option>
							<option>Option_1</option>
							<option>Option_2</option>
							<option>Option_3</option>
						</select>
					</div>
					<div class="form-group col-md-2 px-3">
						<label for="inputRefValue"><strong>Enter Ref
								Value To Search</strong></label> <input type="text" class="form-control"
							id="inputRefValue" name="inputRefValue" />
					</div>

					<div class="form-group col-md-4 px-3">
						<label for="inputEmployee"><strong>Employee</strong></label> <input
							type="text" class="form-control" id="inputEmployee"
							name="inputEmployee" />
					</div>

					<div class="form-group col-md-4 px-3">
						<label for="inputStationName"><strong>Station
								Name</strong></label> <input type="text" class="form-control"
							id="inputStationName" name="inputStationName" />
					</div>
				</div>

				<!-- Row 3  -->
				<div class="form-row mx-auto">
					<div class="form-group col-md-2 px-3">
						<label for="fromDateInput"><strong>From Date</strong></label>

						<div class='input-group date my-auto' id='datetimepicker6'>
							<input type='text' class='datepicker-here form-control'
								data-language='en' id="fromDateInput" name="fromDateInput" /> <i
								class="text-primary fa fa-calendar fa-lg my-auto p-1"
								aria-hidden="true"></i>


						</div>

					</div>

					<div class="form-group col-md-2 px-3">
						<label for="toDateInput"><strong>To Date</strong></label>
						<div class='input-group date' id='datetimepicker6'>
							<input type='text' class='datepicker-here form-control'
								data-language='en' id="toDateInput" name="toDateInput" /> <i
								class="text-primary fa fa-calendar fa-lg my-auto p-1"
								aria-hidden="true"></i>
						</div>
					</div>

					<div class="form-group col-md-4 px-5">
						<label for="inputFromStation"><strong>From
								Location</strong></label> <input type="text" class="form-control"
							id="inputFromStation" name="inputFromStation" />
					</div>

					<div class="form-group col-md-4 px-5">
						<label for="inputToStation"><strong>To Location</strong></label> <input
							type="text" class="form-control" id="inputToStation"
							name="inputToStation" />
					</div>
				</div>


			</form>

		</div>

		<%-- Display PPID SEARCH Section --%>
		<div ${set_Hidden_PPID_Case}>
			<div class="justify-content-center bg-light mt-5" ${setError_PPID_Case}>
				<div class="alert alert-warning text-center p-5" role="alert">
					<span class="display-2">${errorPPIDMessage}</span>
				</div>
			</div>
			<div class="justify-content-center border" ${setSuccess_PPID_Case}>

				<table class="table table-hover table-responsive py-5">
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
					<tr  class="table-secondary">
						<c:forEach var="item" items="${ppidInfo}">
							
								<th>${item }</th>
							
					</c:forEach>
					</tr>
					</tbody>
				</table>
			</div>
		</div>

	</div>
</section>

<c:import url="/WEB-INF/common/footer.jsp"></c:import>

