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
								name="inputppid" placeHolder="Enter PPID" required>
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
							<option selected>Choose...</option>
							<option>...</option>
						</select>
					</div>
					<div class="form-group col-md-1 px-3">
						<label for="optionInput"><strong>Option</strong></label> <select
							id="optionInput" class="form-control" name="optionInput">
							<option selected>Choose...</option>
							<option>...</option>
						</select>
					</div>
					<div class="form-group col-md-2 px-3">
						<label for="inputRefValue"><strong>Enter Ref
								Value To Search</strong></label> <input type="text" class="form-control"
							id="inputRefValue" name="inputRefValue" />
					</div>

					<div class="form-group col-md-4 px-3">
						<label for="inputPPID"><strong>PPID</strong></label> <input
							type="text" class="form-control" id="inputPPID" name="inputPPID" />
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

						<div class='input-group date' id='datetimepicker6'>
							<input type='text' class='datepicker-here form-control'
								data-language='en' name="fromDateInput"/>

						</div>

					</div>

					<div class="form-group col-md-2 px-3">
						<label for="toDateInput"><strong>To Date</strong></label>
						<div class='input-group date' id='datetimepicker6'>
							<input type='text' class='datepicker-here form-control'
								data-language='en' name="toDateInput"/>

						</div>
					</div>

					<div class="form-group col-md-4 px-5">
						<label for="inputPPID"><strong>From Location</strong></label> <input
							type="text" class="form-control" id="inputPPID" name="inputPPID" />
					</div>

					<div class="form-group col-md-4 px-5">
						<label for="inputStationName"><strong>To Location</strong></label>
						<input type="text" class="form-control" id="inputStationName"
							name="inputStationName" />
					</div>
				</div>


			</form>




		</div>

	</div>
</section>

<c:import url="/WEB-INF/common/footer.jsp"></c:import>

