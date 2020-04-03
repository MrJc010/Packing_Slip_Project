<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Search Info"></c:param>
</c:import>

<section>
	<style>
@import "http://netdna.bootstrapcdn.com/font-awesome/2.0/css/font-awesome.css";

@import url(http://fonts.googleapis.com/css?family=Lato:300,100,400);

html {
  height: 100%;
  position: relative
}

body {
  background-color: #3498db;
  font-family: "Lato", sans-serif;
  height: 2000px;
  color: white
}


li.new-item {
  opacity: 0;
  -webkit-animation: new-item-animation .3s linear forwards;
  -o-animation: new-item-animation .3s linear forwards;
  animation: new-item-animation .3s linear forwards
}

@keyframes new-item-animation {
  from {
    opacity: 0;
    -webkit-transform: scale(0);
    -ms-transform: scale(0);
    -o-transform: scale(0);
    transform: scale(0)
  }

  to {
    opacity: 1;
    -webkit-transform: scale(1);
    -ms-transform: scale(1);
    -o-transform: scale(1);
    transform: scale(1)
  }
}

li.restored-item {
  -webkit-animation: openspace .3s ease forwards, 
    restored-item-animation .3s .3s cubic-bezier(0,.8,.32,1.07) forwards;
  -o-animation: openspace .3s ease forwards, 
    restored-item-animation .3s .3s cubic-bezier(0,.8,.32,1.07) forwards;
  animation: openspace .3s ease forwards, 
    restored-item-animation .3s .3s cubic-bezier(0,.8,.32,1.07) forwards
}

@keyframes openspace {
  to {
    height: auto
  }
}

@keyframes restored-item-animation {
  from {
    opacity: 0;
    -webkit-transform: scale(0);
    -ms-transform: scale(0);
    -o-transform: scale(0);
    transform: scale(0)
  }

  to {
    opacity: 1;
    -webkit-transform: scale(1);
    -ms-transform: scale(1);
    -o-transform: scale(1);
    transform: scale(1)
  }
}

li.removed-item {
  -webkit-animation: removed-item-animation .6s cubic-bezier(.55,-0.04,.91,.94) forwards;
  -o-animation: removed-item-animation .6s cubic-bezier(.55,-0.04,.91,.94) forwards;
  animation: removed-item-animation .6s cubic-bezier(.55,-0.04,.91,.94) forwards
}

@keyframes removed-item-animation {
  from {
    opacity: 1;
    -webkit-transform: scale(1);
    -ms-transform: scale(1);
    -o-transform: scale(1);
    transform: scale(1)
  }

  to {
    -webkit-transform: scale(0);
    -ms-transform: scale(0);
    -o-transform: scale(0);
    transform: scale(0);
    opacity: 0
  }
}

@-webkit-keyframes new-item-animation {
  from {
    opacity: 0;
    -webkit-transform: scale(0);
    transform: scale(0)
  }

  to {
    opacity: 1;
    -webkit-transform: scale(1);
    transform: scale(1)
  }
}

@-o-keyframes new-item-animation {
  from {
    opacity: 0;
    -o-transform: scale(0);
    transform: scale(0)
  }

  to {
    opacity: 1;
    -o-transform: scale(1);
    transform: scale(1)
  }
}

@-webkit-keyframes openspace {
  to {
    height: auto
  }
}

@-o-keyframes openspace {
  to {
    height: auto
  }
}

@-webkit-keyframes restored-item-animation {
  from {
    opacity: 0;
    -webkit-transform: scale(0);
    transform: scale(0)
  }

  to {
    opacity: 1;
    -webkit-transform: scale(1);
    transform: scale(1)
  }
}

@-o-keyframes restored-item-animation {
  from {
    opacity: 0;
    -o-transform: scale(0);
    transform: scale(0)
  }

  to {
    opacity: 1;
    -o-transform: scale(1);
    transform: scale(1)
  }
}

@-webkit-keyframes removed-item-animation {
  from {
    opacity: 1;
    -webkit-transform: scale(1);
    transform: scale(1)
  }

  to {
    -webkit-transform: scale(0);
    transform: scale(0);
    opacity: 0
  }
}

@-o-keyframes removed-item-animation {
  from {
    opacity: 1;
    -o-transform: scale(1);
    transform: scale(1)
  }

  to {
    -o-transform: scale(0);
    transform: scale(0);
    opacity: 0
  }
}

.note {
	text-align: center;
	height: 80px;
	background: -webkit-linear-gradient(left, #0072ff, #8811c5);
	color: #fff;
	font-weight: bold;
	line-height: 80px;
}

.form-content {
	padding: 5%;
	border: 1px solid #ced4da;
	margin-bottom: 2%;
}

.form-control {
	background-color: #ecf0f1;
	border-radius: 1.5rem;
}

.btnSubmit {
	border: none;
	border-radius: 1.5rem;
	padding: 1%;
	width: 20%;
	cursor: pointer;
	background: #0062cc;
	color: #fff;
}
</style>
	<div class="container register-form text-center p-5 mt-2 shadow">

		<div class="note">
			<p>CREATE NEW LOCATIONS (STEP 02)</p>
		</div>
		<div class="border rounded p-2 m-2  justify-content-left">
			<div>
				<ul class="mt-2 text-left">
					<li class="mx-4 mb-2"><strong>Part Number: <span
							class="badge badge-success"><strong>${partNumber}</strong></span></strong></li>
					<li class="mx-4 mb-2"><strong>Model: <span
							class="badge badge-success"><strong>${model}</strong></span></strong></li>
					<li class="mx-4 mb-2"><strong>Description: <span
							class="badge badge-secondary">${desc}</span></strong></li>
				</ul>
			</div>
		</div>

		<div class="demo-wrapper">

			<h1>Creative Add/Remove Effects for List Items</h1>

			<div class="notification undo-button">Item Deleted. Undo?</div>
			<div class="notification save-notification">Item Saved</div>
			<div class="reminder-container">
				<form id="input-form">
					<input class="bg-info" type="text" id="text"
						placeholder="Remind me to.." /> <input
						class="btn btn-danger" type="submit" value="Add" />
				</form>
				<ul class="reminders"></ul>
				
					<span class="count"></span>
					<button class="clear-all">Delete All</button>
				
			</div>
		</div>

	</div>

</section>
<script>

<c:import url="/WEB-INF/js/jquery-1.8.2.min.js"></c:import>
<c:import url="/WEB-INF/js/modernizr-1.5.min.js"></c:import>
<c:import url="/WEB-INF/js/scripts.js"></c:import>

</script>
<c:import url="/WEB-INF/common/footer.jsp"></c:import>
