<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Station Config"></c:param>
</c:import>

<style>
#canvas {
  border: 1px solid red;
}

.myCanvas {
position: fixed;
top : 8%;
right: 10%;

}


</style>


<section>
	<div class="container-fluid p-5">
		<div class="row justify-content-center">
			<h2 class="mb-4 text-primary display-3">Station Configs</h2>		
		</div>
		<div class="myCanvas"><canvas id="canvas" width="300" height="300"></canvas> </div>
		
		<div class="row justify-content-left">
			<div class="container-fluid p-5">

				<form class="container-fluid"
					action="<%=request.getContextPath()%>/shopfloor/station_config_step_3?partNumber=${partNumberURL}"
					method="post">
					<div class="form-group row my-4">
						<input type="hidden" name="action" value="get_avaiable_stations" />
						<label for="stationAvaiable" class="col-md-2 col-form-label">Station
							Config </label>
						<div class="col-md-4">

							<select id="stationAvaiable" name="stationAvaiable"
								class="custom-select">
								<option name="OptionStationAvaiableSelected" value="">Select</option>
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
						<div class="col-md-1">
							<input type="submit" value="View" />
						</div>
					</div>
				</form>

			</div>
			<form class="container-fluid"
				action="<%=request.getContextPath()%>/shopfloor/station_config_step_3?partNumber=${partNumberURL}"
				method="POST">

				<div class="form-inline my-4">
					<div class="form-group ml-5">
						<label for="fromLocation" class="mr-5">From location</label> <select
							name="fromLocation" id="fromLocation" class="custom-select">
							<option value="">Select Location</option>
							<c:forEach var="aStation" items="${listStations}">
								<c:if test="${aStation == 'DEFAULT_FAIL'}">

								</c:if>
								<c:if
									test="${aStation != 'DEFAULT_FAIL' and aStation == fromLocationValue}">
									<option selected value="${aStation}">${aStation}</option>
								</c:if>
								<c:if
									test="${aStation != 'DEFAULT_FAIL' and fromLocationValue != aStation}">
									<option value="${aStation}">${aStation}</option>
								</c:if>
							</c:forEach>

						</select>
					</div>
					<div class="form-group ml-5">
						<label for="toLocation" class="mr-5">To location</label> <select
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
							if="serialnumber" type="text" name="serialnumber"
							value="${serialnumber}" class="form-control ml-5">
					</div>
					<div class="form-group ml-5">
						<label for="snPattern" class="mr-4">Serial Number Pattern</label>
						<input id="snPattern" name="snPattern" value="${snPattern}"
							type="text" class="form-control ml-5">
					</div>
				</div>

				<!-- Part Number  -->
				<div class="form-inline my-4">
					<div class="form-group">
						<label for="partNumber" class="mr-4">Part Number</label> <input
							if="partNumber" name="partNumber" value="${partNumber}"
							type="text" class="form-control ml-5">
					</div>
					<div class="form-group ml-5">
						<label for="patternPN" class="mr-4">Part Number Pattern</label> <input
							id="patternPN" name="patternPN" type="text" value="${patternPN}"
							class="form-control ml-5">
					</div>
				</div>
				<!-- Ref_1 -->
				<div class="form-inline  my-4">
					<div class="form-group">
						<label for="RefName_1" class="mr-4">Ref_1 Name</label> <input
							value="${RefName_1}" id="RefName_1" name="RefName_1" type="text"
							class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="RefPattern_1" class="mr-4">Ref_1 Pattern</label> <input
							value="${RefPattern_1}" id="RefPattern_1" name="RefPattern_1"
							type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="RefCount_1" class="mr-4">Count</label> <select
							id="RefCount_1" name="RefCount_1"
							class="custom-select">
							<c:forEach var="anOption" items="${listCountOptions}">
								<c:if test="${anOption == RefCount_1}">
									<option selected value="${anOption}">${anOption}</option>
								</c:if>
								<c:if test="${RefCount_1 != anOption}">
									<option value="${anOption}">${anOption}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<div class="form-group ml-5">
						<label for="RefMax_1" class="mr-4">Max_Count</label> <input
							id="RefMax_1" name="RefMax_1" value="${RefMax_1}" type="number"
							class="form-control">
					</div>
				</div>

				<!-- Ref_2 -->
				<div class="form-inline  my-4">
					<div class="form-group">
						<label for="RefName_2" class="mr-4">Ref_2 Name</label> <input
							id="RefName_2" name="RefName_2" value="${RefName_2}" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="RefPattern_2" class="mr-4">Ref_2 Pattern</label> <input
							id="RefPattern_2" name="RefPattern_2" type="text" value="${RefPattern_2}"
							class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="RefCount_2" class="mr-4">Count</label> <select
							id="RefCount_2" name="RefCount_2"
							class="custom-select">
							<c:forEach var="anOption" items="${listCountOptions}">
								<c:if test="${anOption == RefCount_2}">
									<option selected value="${anOption}">${anOption}</option>
								</c:if>
								<c:if test="${RefCount_2 != anOption}">
									<option value="${anOption}">${anOption}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<div class="form-group ml-5">
						<label for="RefMax_2" class="mr-4">Max_Count</label> <input
							id="RefMax_2" name="RefMax_2" type="number" value="${RefMax_2}"
							class="form-control">
					</div>
				</div>


				<!-- Ref_3 -->
				<div class="form-inline  my-4">
					<div class="form-group">
						<label for="RefName_3" class="mr-4">Ref_3 Name</label> <input
							id="RefName_3" name="RefName_3" value="${RefName_3}" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="RefPattern_3" class="mr-4">Ref_3 Pattern</label> <input
							id="RefPattern_3" name="RefPattern_3" value="${RefPattern_3}" type="text"
							class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="RefCount_3" class="mr-4">Count</label> <select
							id="RefCount_3" name="RefCount_3"
							class="custom-select">
							<c:forEach var="anOption" items="${listCountOptions}">
								<c:if test="${anOption == RefCount_3}">
									<option selected value="${anOption}">${anOption}</option>
								</c:if>
								<c:if test="${RefCount_3 != anOption}">
									<option value="${anOption}">${anOption}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<div class="form-group ml-5">
						<label for="RefMax_3" class="mr-4">Max_Count</label> <input
							id="RefMax_3" name="RefMax_3" value="${RefMax_3}" type="number"
							class="form-control">
					</div>
				</div>


				<!-- Ref_4 -->
				<div class="form-inline  my-4">
					<div class="form-group">
						<label for="RefName_4" class="mr-4">Ref_4 Name</label> <input
							id="RefName_4" name="RefName_4" value="${RefName_4}" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="RefPattern_4" class="mr-4">Ref_4 Pattern</label> <input
							id="RefPattern_4" name="RefPattern_4" type="text" value="${RefPattern_4}"
							class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="RefCount_4" class="mr-4">Count</label> <select
							name="RefCount_4" id="RefCount_4"
							class="custom-select">
							<c:forEach var="anOption" items="${listCountOptions}">
								<c:if test="${anOption == RefCount_4}">
									<option selected value="${anOption}">${anOption}</option>
								</c:if>
								<c:if test="${RefCount_4 != anOption}">
									<option value="${anOption}">${anOption}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<div class="form-group ml-5">
						<label for="RefMax_4" class="mr-4">Max_Count</label> <input
							id="RefMax_4" name="RefMax_4" type="number" value="${RefMax_4}"
							class="form-control">
					</div>
				</div>



				<!-- Ref_5 -->
				<div class="form-inline  my-4">
					<div class="form-group">
						<label for="RefName_5" class="mr-4">Ref_5 Name</label> <input
							id="RefName_5" name="RefName_5" value="${RefName_5}" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="RefPattern_5" class="mr-4">Ref_5 Pattern</label> <input
							id="RefPattern_5" name="RefPattern_5" type="text" value="${RefPattern_5}"
							class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="RefCount_5" class="mr-4">Count</label> <select
							id="RefCount_5" name="RefCount_5"
							class="custom-select">
							<c:forEach var="anOption" items="${listCountOptions}">
								<c:if test="${anOption == RefCount_5}">
									<option selected value="${anOption}">${anOption}</option>
								</c:if>
								<c:if test="${RefCount_5 != anOption}">
									<option value="${anOption}">${anOption}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<div class="form-group ml-5">
						<label for="RefMax_5" class="mr-4">Max_Count</label> <input
							id="RefMax_5" name="RefMax_5" type="number" value="${RefMax_5}"
							class="form-control">
					</div>
				</div>


				<!-- Ref_6 -->
				<div class="form-inline  my-4">
					<div class="form-group">
						<label for="RefName_6" class="mr-4">Ref_6 Name</label> <input
							id="RefName_6" name="RefName_6" value="${RefName_6}" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="RefPattern_6" class="mr-4">Ref_6 Pattern</label> <input
							id="RefPattern_6" name="RefPattern_6" type="text" value="${RefPattern_6}"
							class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="RefCount_6" class="mr-4">Count</label> <select
							id="RefCount_6" name="RefCount_6"
							class="custom-select">
							<c:forEach var="anOption" items="${listCountOptions}">
								<c:if test="${anOption == RefCount_6}">
									<option selected value="${anOption}">${anOption}</option>
								</c:if>
								<c:if test="${RefCount_6 != anOption}">
									<option value="${anOption}">${anOption}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<div class="form-group ml-5">
						<label for="RefMax_6" class="mr-4">Max_Count</label> <input
							id="RefMax_6" name="RefMax_6" type="number" value="${RefMax_6}"
							class="form-control">
					</div>
				</div>

				<!-- Ref_7 -->
				<div class="form-inline  my-4">
					<div class="form-group">
						<label for="RefName_7" class="mr-4">Ref_7 Name</label> <input
							id="RefName_7" name="RefName_7" value="${RefName_7}" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="RefPattern_7" class="mr-4">Ref_7 Pattern</label> <input
							id="RefPattern_7" name="RefPattern_7" type="text" value="${RefPattern_7}"
							class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="RefCount_1" class="mr-4">Count</label> <select
							id="RefCount_1" name="RefCount_1"
							class="custom-select">
							<c:forEach var="anOption" items="${listCountOptions}">
								<c:if test="${anOption == RefCount_7}">
									<option selected value="${anOption}">${anOption}</option>
								</c:if>
								<c:if test="${RefCount_7 != anOption}">
									<option value="${anOption}">${anOption}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<div class="form-group ml-5">
						<label for="RefMax_7" class="mr-4">Max_Count</label> <input
							id="RefMax_7" name="RefMax_7" type="number" value="${RefMax_7}"
							class="form-control">
					</div>
				</div>

				<!-- Ref_8 -->
				<div class="form-inline  my-4">
					<div class="form-group">
						<label for="RefName_8" class="mr-4">Ref_8 Name</label> <input
							id="RefName_8" name="RefName_8" value="${RefName_8}" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="RefPattern_8" class="mr-4">Ref_8 Pattern</label> <input
							id="RefPattern_8" name="RefPattern_8" type="text" value="${RefPattern_8}"
							class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="RefCount_8" class="mr-4">Count</label> <select
							id="RefCount_8" name="RefCount_8"
							class="custom-select">
							<c:forEach var="anOption" items="${listCountOptions}">
								<c:if test="${anOption == RefCount_8}">
									<option selected value="${anOption}">${anOption}</option>
								</c:if>
								<c:if test="${RefCount_8 != anOption}">
									<option value="${anOption}">${anOption}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<div class="form-group ml-5">
						<label for="RefMax_8" class="mr-4">Max_Count</label> <input
							id="RefMax_8" name="RefMax_8" type="number" value="${RefMax_8}"
							class="form-control">
					</div>
				</div>


				<!-- Ref_9 -->
				<div class="form-inline  my-4">
					<div class="form-group">
						<label for="RefName_9" class="mr-4">Ref_9 Name</label> <input
							id="RefName_9" name="RefName_9" value="${RefName_9}" type="text" class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="RefPattern_9" class="mr-4">Ref_9 Pattern</label> <input
							id="RefPattern_9" name="RefPattern_9" type="text" value="${RefPattern_9}"
							class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="RefCount_9" class="mr-4">Count</label> <select
							id="RefCount_9" name="RefCount_9"
							class="custom-select">
							<c:forEach var="anOption" items="${listCountOptions}">
								<c:if test="${anOption == RefCount_9}">
									<option selected value="${anOption}">${anOption}</option>
								</c:if>
								<c:if test="${RefCount_9 != anOption}">
									<option value="${anOption}">${anOption}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<div class="form-group ml-5">
						<label for="RefMax_9" class="mr-4">Max_Count</label> <input
							id="RefMax_9" name="RefMax_9" type="number" value="${RefMax_9}"
							class="form-control">
					</div>
				</div>

				<!-- Ref_10 -->
				<div class="form-inline  my-4">
					<div class="form-group">
						<label for="RefName_10" class="mr-4">Ref_10 Name</label> <input
							id="RefName_10" name="RefName_10" type="text" value="${RefName_10}"
							class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="RefPattern_10" class="mr-4">Ref_10 Pattern</label> <input
							id="RefPattern_10" name="RefPattern_10" type="text" value="${RefPattern_10} "
							class="form-control">
					</div>
					<div class="form-group ml-5">
						<label for="RefCount_10" class="mr-4">Count</label> <select
							id="RefCount_10" name="RefCount_10"
							class="custom-select">
							<c:forEach var="anOption" items="${listCountOptions}">
								<c:if test="${anOption == RefCount_10}">
									<option selected value="${anOption}">${anOption}</option>
								</c:if>
								<c:if test="${RefCount_10 != anOption}">
									<option value="${anOption}">${anOption}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<div class="form-group ml-5">
						<label for="RefMax_10" class="mr-4">Max_Count</label> <input
							id="RefMax_10" name="RefMax_10" type="number" value="${RefMax_10}"
							class="form-control">
					</div>
				</div>
				<!-- Comparison  -->
				<div class="form-group row">
					<label for="comparison" class="col-md-1 col-form-label">Comparison:
					</label>
					<div class="col-md-11">
						<textarea id="comparison" name="comparison" rows="10" cols="100">${comparison}</textarea>
					</div>
				</div>
				<!-- User  -->
				<div class="form-group row">
					<label for="userList" class="col-md-1 col-form-label">User:
					</label>
					<div class="col-md-4">
						<input type="text" class="form-control" id="userList"
							name="userList" value="${userList}" />
					</div>
				</div>

				<div class="row my-5 justify-content-center">
					<input type="submit" class="btn btn-lg btn-primary mx-5"
						value="${buttonName}" name="action" />

					<!-- 	<input type="submit"
						class="btn btn-lg btn-secondary mx-5" value="Close" name="action" /> -->
				</div>
					
			</form>
		</div>
</section>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>
var arrayItems = <%=request.getAttribute("listStationsJson")%>;
console.log(arrayItems);
var connection = ${connection};
var connectors = [];
for(var key in connection){	
	var index1 = arrayItems.indexOf(key);
	console.log(typeof index1);
	console.log(index1);
	var index2 = arrayItems.indexOf(connection[key]);
	console.log("============");
	console.log(typeof index2);
	console.log(index2);
	console.log(key);
	console.log("ss : " + connection[key]);
	connectors.push({ box1: index1, box2: index2});
}

for(let x of arrayItems){
	console.log(x);
}
var items = [...arrayItems];
var canvas = document.getElementById('canvas');
var ctx = canvas.getContext('2d');

var canvasOffset = $('#canvas').offset();
var offsetX = canvasOffset.left;
var offsetY = canvasOffset.top;

var startX;
var startY;
var isDown = false;
var dragTarget;

var boxes = [];

var x = 50;
var y = 25;
var w = 75;
var h = 30;
items.forEach(() => {
  boxes.push({ x, y, w, h });
  y += 55;
});




draw();

$('#canvas').mousedown(function (e) {
  handleMouseDown(e);
});
$('#canvas').mousemove(function (e) {
  handleMouseMove(e);
});
$('#canvas').mouseup(function (e) {
  handleMouseUp(e);
});
$('#canvas').mouseout(function (e) {
  handleMouseOut(e);
});

function draw() {
  // clear the canvas
  for (var i = 0; i < boxes.length; i++) {
    ctx.fillStyle = 'green';
    ctx.clearRect(0, 0, canvas.width, canvas.height);
  }

  for (var i = 0; i < boxes.length; i++) {
    var box = boxes[i];
    ctx.fillRect(box.x, box.y, box.w, box.h);
    ctx.fillStyle = 'green';
    ctx.font = '10pt sans-serif';
    ctx.fillText(items[i], boxes[i].x, boxes[i].y - 2);
  }
  for (var i = 0; i < connectors.length; i++) {
    var connector = connectors[i];
    var box1 = boxes[connector.box1];
    var box2 = boxes[connector.box2];
    ctx.beginPath();
    ctx.moveTo(box1.x + box1.w / 2, box1.y + box1.h / 2);
    ctx.lineTo(box2.x + box2.w / 2, box2.y + box2.h / 2);
    ctx.stroke();
  }
}

function hit(x, y) {
  for (var i = 0; i < boxes.length; i++) {
    var box = boxes[i];
    if (x >= box.x && x <= box.x + box.w && y >= box.y && y <= box.y + box.h) {
      dragTarget = box;
      return true;
    }
  }
  return false;
}

function handleMouseDown(e) {
  startX = parseInt(e.clientX - offsetX);
  startY = parseInt(e.clientY - offsetY);

  // Put your mousedown stuff here
  isDown = hit(startX, startY);
}

function handleMouseUp(e) {
  // Put your mouseup stuff here
  dragTarget = null;
  isDown = false;
}

function handleMouseOut(e) {
  handleMouseUp(e);
}

function handleMouseMove(e) {
  if (!isDown) {
    return;
  }

  mouseX = parseInt(e.clientX - offsetX);
  mouseY = parseInt(e.clientY - offsetY);

  // Put your mousemove stuff here
  var dx = mouseX - startX;
  var dy = mouseY - startY;
  startX = mouseX;
  startY = mouseY;
  dragTarget.x += dx;
  dragTarget.y += dy;
  draw();
}


</script>
<c:import url="/WEB-INF/common/footer.jsp"></c:import>