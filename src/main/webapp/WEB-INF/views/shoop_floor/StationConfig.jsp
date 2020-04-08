<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Station Config"></c:param>
</c:import>



<section>
	<div class="container-fluid p-5">
		<div class="row justify-content-center">
			<h2 class="mb-4 text-primary display-3">Station Configs</h2>
		</div>
		<div class="row justify-content-left">
			<form class="container-fluid"
				action="<%=request.getContextPath()%>/shopfloor/station_config_step_3"
				method="POST">
				<%--     <!-- Name  -->
                    <div class="form-group row">
                        <label for="nameStation" class="col-md-1 col-form-label">Name:
                        </label>
                        <div class="col-md-11">
                            <input type="text" class="form-control" id="nameStation" value="${nameStation}" />
                        </div>
                    </div> --%>

				<!-- Operate Type -->

				<div class="form-group row my-4">
					<label for="stationAvaiable" class="col-md-2 col-form-label">Station
						Config </label>
					<div class="col-md-4">
						<select id="stationAvaiable" name="stationAvaiable" class="custom-select">
						<option value="">Select</option>
						<c:forEach var="item" items="${avaiStationsDropDown}">
						<c:if test="${item == avaiStationsDropDownSelected}">
									<option selected value="${item}">${item}</option>
								</c:if>
								<c:if test="${avaiStationsDropDownSelected != item}">
									<option value="${item}">${item}</option>
								</c:if>
						</c:forEach>														
						</select>
					</div>
				</div>


				<!-- Is Active -->
				<!--   <div class="form-group row my-4">
                        <label for="nameStation" class="col-md-1 col-form-label">Active:
                        </label>
                        <div class="col-md-2">
                            <select class="custom-select">
                                <option selected>Select</option>
                                <option value="1">Yes</option>
                                <option value="2">No</option>
                            </select>
                        </div>
                    </div> -->
				<!-- From - To - Fail -->
				<div class="form-inline my-4">
					<div class="form-group mr-5">
						<label for="fromLocation" class="">From location</label> <select
							id="fromLocation" name="fromLocation" class="custom-select ml-5">
							<option value="">Select Location</option>
							<option value="BIZ_START">BIZ_START</option>
							<c:forEach var="aStation" items="${listStations}">
								<c:if test="${aStation == fromLocationValue}">
									<option selected value="${aStation}">${aStation}</option>
								</c:if>
								<c:if test="${fromLocationValue != aStation}">
									<option value="${aStation}">${aStation}</option>
								</c:if>
							</c:forEach>

						</select>

					</div>
					<div class="form-group ml-5">
						<label for="toLocation" class="mr-5">From location</label> <select
							name="toLocation" id="toLocation" class="custom-select">
							<option value="">Select Location</option>
							<c:forEach var="aStation" items="${listStations}">
								<c:if test="${aStation == toLocationValue}">
									<option selected value="${aStation}">${aStation}</option>
								</c:if>
								<c:if test="${toLocationValue != aStation}">
									<option value="${aStation}">${aStation}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
				</div>

				<!-- Serial Number  -->
				<div class="form-inline my-4">
					<div class="form-group">
						<label for="serialnumber" class="mr-4">Serial Number</label> <input
							if="serialnumber" type="text" name="serialnumber" value="${serialnumber}"
							class="form-control ml-5">
					</div>
					<div class="form-group ml-5">
						<label for="snPattern" class="mr-4">Serial Number Pattern</label>
						<input id="snPattern" name="snPattern" type="text" class="form-control ml-5">
					</div>
				</div>

				<!-- Part Number  -->
				<div class="form-inline my-4">
					<div class="form-group">
						<label for="partNumber" class="mr-4">Part Number</label> <input
							if="partNumber" name="partNumber" type="text" class="form-control ml-5">
					</div>
					<div class="form-group ml-5">
						<label for="patternPN" class="mr-4">Part Number Pattern</label> <input
							id="patternPN" name="patternPN" type="text" class="form-control ml-5">
					</div>
				</div>
				<!-- Ref_1 -->
				<div class="form-inline  my-4">
					<div class="form-group">
						<label for="Ref_1Name" class="mr-4">Ref_1 Name</label> <input
							if="Ref_1Name" name="Ref_1Name" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="ref1Pattern" class="mr-4">Ref_1 Pattern</label> <input
							id="ref1Pattern" name="ref1Pattern" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="count_Ref1Pattern" class="mr-4">Count</label> <select
							id="count_Ref1Pattern" name="count_Ref1Pattern" class="custom-select">
							<option selected>Select</option>
							<option value="1">Yes</option>
							<option value="2">No</option>
						</select>
					</div>
					<div class="form-group ml-5">
						<label for="maxref1Pattern" class="mr-4">Max_Count</label> <input
							id="maxref1Pattern" name="maxref1Pattern" type="number" class="form-control">
					</div>
				</div>

				<!-- Ref_2 -->
				<div class="form-inline  my-4">
					<div class="form-group">
						<label for="Ref_2Name" class="mr-4">Ref_2 Name</label> <input
							if="Ref_2Name" name="Ref_2Name" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="Ref_2Pattern" class="mr-4">Ref_2 Pattern</label> <input
							id="Ref_2Pattern" name="Ref_2Pattern" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="count_Ref_2Pattern" class="mr-4">Count</label> <select
							id="count_Ref_2Pattern" name="count_Ref_2Pattern" class="custom-select">
							<option selected>Select</option>
							<option value="1">Yes</option>
							<option value="2">No</option>
						</select>
					</div>
					<div class="form-group ml-5">
						<label for="maxRef_2Pattern" class="mr-4">Max_Count</label> <input
							id="maxRef_2Pattern" name="maxRef_2Pattern" type="number" class="form-control">
					</div>
				</div>


				<!-- Ref_3 -->
				<div class="form-inline  my-4">
					<div class="form-group">
						<label for="Ref_3Name" class="mr-4">Ref_3 Name</label> <input
							if="Ref_3Name" name="Ref_3Name" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="Ref_3Pattern" class="mr-4">Ref_3 Pattern</label> <input
							id="Ref_3Pattern" name="Ref_3Pattern" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="count_Ref_3Pattern" class="mr-4">Count</label> <select
							id="count_Ref_3Pattern" name="count_Ref_3Pattern" class="custom-select">
							<option selected>Select</option>
							<option value="1">Yes</option>
							<option value="2">No</option>
						</select>
					</div>
					<div class="form-group ml-5">
						<label for="maxRef_3Pattern" class="mr-4">Max_Count</label> <input
							id="maxRef_3Pattern" name="maxRef_3Pattern" type="number" class="form-control">
					</div>
				</div>


				<!-- Ref_4 -->
				<div class="form-inline  my-4">
					<div class="form-group">
						<label for="Ref_4Name" class="mr-4">Ref_4 Name</label> <input
							if="Ref_4Name" name="Ref_4Name" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="Ref_4Pattern" class="mr-4">Ref_4 Pattern</label> <input
							id="Ref_4Pattern" name="Ref_4Pattern" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="count_Ref_4Pattern" class="mr-4">Count</label> <select name="count_Ref_4Pattern"
							id="count_Ref_4Pattern" class="custom-select">
							<option selected>Select</option>
							<option value="1">Yes</option>
							<option value="2">No</option>
						</select>
					</div>
					<div class="form-group ml-5">
						<label for="maxRef_4Pattern" class="mr-4">Max_Count</label> <input
							id="maxRef_4Pattern" name="maxRef_4Pattern" type="number" class="form-control">
					</div>
				</div>



				<!-- Ref_5 -->
				<div class="form-inline  my-4">
					<div class="form-group">
						<label for="Ref_5Name" class="mr-4">Ref_5 Name</label> <input
							if="Ref_5Name" name="Ref_5Name" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="Ref_5Pattern" class="mr-4">Ref_5 Pattern</label> <input
							id="Ref_5Pattern" name="Ref_5Pattern" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="count_Ref_5Pattern" class="mr-4">Count</label> <select
							id="count_Ref_5Pattern" name="count_Ref_5Pattern" class="custom-select">
							<option selected>Select</option>
							<option value="1">Yes</option>
							<option value="2">No</option>
						</select>
					</div>
					<div class="form-group ml-5">
						<label for="maxRef_5Pattern" class="mr-4">Max_Count</label> <input
							id="maxRef_5Pattern" name="maxRef_5Pattern" type="number" class="form-control">
					</div>
				</div>


				<!-- Ref_6 -->
				<div class="form-inline  my-4">
					<div class="form-group">
						<label for="Ref_6Name" class="mr-4">Ref_6 Name</label> <input
							if="Ref_6Name" name="Ref_6Name" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="Ref_6Pattern" class="mr-4">Ref_6 Pattern</label> <input
							id="Ref_6Pattern" name="Ref_6Pattern" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="count_Ref_6Pattern" class="mr-4">Count</label> <select
							id="count_Ref_6Pattern" name="count_Ref_6Pattern" class="custom-select">
							<option selected>Select</option>
							<option value="1">Yes</option>
							<option value="2">No</option>
						</select>
					</div>
					<div class="form-group ml-5">
						<label for="maxRef_6Pattern" class="mr-4">Max_Count</label> <input
							id="maxRef_6Pattern" name="maxRef_6Pattern" type="number" class="form-control">
					</div>
				</div>

				<!-- Ref_7 -->
				<div class="form-inline  my-4">
					<div class="form-group">
						<label for="Ref_7Name" class="mr-4">Ref_7 Name</label> <input
							if="Ref_7Name" name="Ref_7Name" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="Ref_7Pattern" class="mr-4">Ref_7 Pattern</label> <input
							id="Ref_7Pattern" name="Ref_7Pattern" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="count_Ref_7Pattern" class="mr-4">Count</label> <select
							id="count_Ref_7Pattern" name="count_Ref_7Pattern" class="custom-select">
							<option selected>Select</option>
							<option value="1">Yes</option>
							<option value="2">No</option>
						</select>
					</div>
					<div class="form-group ml-5">
						<label for="maxRef_7Pattern" class="mr-4">Max_Count</label> <input
							id="maxRef_7Pattern" name="maxRef_7Pattern" type="number" class="form-control">
					</div>
				</div>

				<!-- Ref_8 -->
				<div class="form-inline  my-4">
					<div class="form-group">
						<label for="Ref_8Name" class="mr-4">Ref_8 Name</label> <input
							if="Ref_8Name" name="Ref_8Name" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="Ref_8Pattern" class="mr-4">Ref_8 Pattern</label> <input
							id="Ref_8Pattern" name="Ref_8Pattern" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="count_Ref_8Pattern" class="mr-4">Count</label> <select
							id="count_Ref_8Pattern" name="count_Ref_8Pattern" class="custom-select">
							<option selected>Select</option>
							<option value="1">Yes</option>
							<option value="2">No</option>
						</select>
					</div>
					<div class="form-group ml-5">
						<label for="maxRef_8Pattern" class="mr-4">Max_Count</label> <input
							id="maxRef_8Pattern" name="maxRef_8Pattern" type="number" class="form-control">
					</div>
				</div>


				<!-- Ref_9 -->
				<div class="form-inline  my-4">
					<div class="form-group">
						<label for="Ref_9Name" class="mr-4">Ref_9 Name</label> <input
							if="Ref_9Name" name="Ref_9Name" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="Ref_9Pattern" class="mr-4">Ref_9 Pattern</label> <input
							id="Ref_9Pattern" name="Ref_9Pattern" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="count_Ref_9Pattern" class="mr-4">Count</label> <select
							id="count_Ref_9Pattern" name="count_Ref_9Pattern" class="custom-select">
							<option selected>Select</option>
							<option value="1">Yes</option>
							<option value="2">No</option>
						</select>
					</div>
					<div class="form-group ml-5">
						<label for="maxRef_9Pattern" class="mr-4">Max_Count</label> <input
							id="maxRef_9Pattern" name="maxRef_9Pattern" type="number" class="form-control">
					</div>
				</div>

				<!-- Ref_10 -->
				<div class="form-inline  my-4">
					<div class="form-group">
						<label for="Ref_10Name" class="mr-4">Ref_10 Name</label> <input
							if="Ref_10Name" name="Ref_10Name" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="Ref_10Pattern" class="mr-4">Ref_10 Pattern</label> <input
							id="Ref_10Pattern" name="Ref_10Pattern" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="count_Ref_10Pattern" class="mr-4">Count</label> <select
							id="count_Ref_10Pattern" name="count_Ref_10Pattern" class="custom-select">
							<option selected>Select</option>
							<option value="1">Yes</option>
							<option value="2">No</option>
						</select>
					</div>
					<div class="form-group ml-5">
						<label for="maxRef_10Pattern" class="mr-4">Max_Count</label> <input
							id="maxRef_10Pattern" name="maxRef_10Pattern" type="number" class="form-control">
					</div>
				</div>
				<!-- Comparison  -->
				<div class="form-group row">
					<label for="comparison" class="col-md-1 col-form-label">Comparison:
					</label>
					<div class="col-md-11">
						<textarea id="comparison" name="comparison" rows="10" cols="100"></textarea>
					</div>
				</div>
				<!-- User  -->
				<div class="form-group row">
					<label for="userList" class="col-md-1 col-form-label">User:
					</label>
					<div class="col-md-4">
						<input type="text" class="form-control" id="userList" name="userList"
							value="${userList}" />
					</div>
				</div>

				<div class="row my-5 justify-content-center">
					<input type="submit" class="btn btn-lg btn-primary mx-5"
						value="Save" name="action" />

					<!-- 	<input type="submit"
						class="btn btn-lg btn-secondary mx-5" value="Close" name="action" /> -->
				</div>

			</form>
		</div>
</section>
<c:import url="/WEB-INF/common/footer.jsp"></c:import>