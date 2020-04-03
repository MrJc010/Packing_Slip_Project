<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Search Info"></c:param>
</c:import>

<style>

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

<%-- =========================== --%>

@import url(http://fonts.googleapis.com/css?family=Lato:300,100,400);

*{
  margin:0;
  padding:0;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  position:relative;
  list-style: none;
  outline: 1px solid transparent;
}
li [class^=icon-],
li [class*=" icon-"] {
  width: 40px !important;
  font-size: 22px;
  display:block !important;
  position:absolute !important;
  float:none !important;
}

::-webkit-input-placeholder {
  color: rgba(255, 255, 255, 0.2)
}

:-moz-placeholder {
  color: rgba(255, 255, 255, 0.2)
}

::-moz-placeholder {
  color: rgba(255, 255, 255, 0.2)
}

:-ms-input-placeholder {
  color: rgba(255, 255, 255, 0.2)
}


html, body{
  height:100%;
}
body{
  background-color: red;
}
.page-header{
  width:100%;
  color:white;
  padding:5px 30px;
  font: normal 18px/1.5 "Lato", "Arial", sans-serif;
  overflow:hidden;
}
.demo-nav{
  float:right;
}
.page-header a{
  color: inherit;
  text-decoration: none;
}

.demo-nav li{
  display:inline-block;
  margin: 0 5px;
}
.demo-nav li a, .tutorial-link{
  display:block;
  width:100%;
  height:100%;
  padding:10px 15px;
}
.tutorial-link{
  float:left;
  width:auto;
}
.demo-nav a.active, .demo-nav a:hover, .demo-nav a:active{
  border-bottom:2px solid white;
}
h1{
  margin-bottom:50px !important;
}
h1 {
  font: 300 3.2em/1.2 "Lato", sans-serif;
  color: white;
  margin: 0
}

header {
  padding-top: 30px;
  margin-bottom: 30px;
  text-align:center;
}
header p{
  color:rgba(255,255,255,.6);
}

.reminder-container {
  margin: 0 auto;
  width: 500px;
  text-align: center
}

input[type='text'] {
  width: 430px;
  height: 50px;
  border: none;
  background-color: transparent;
  font-size: 20px;
  font-weight:300;
  color: white;
  float:left;
  padding:5px 10px;
  -webkit-transition: all .3s ease;
  -o-transition: all .3s ease;
  transition: all .3s ease;
  background-color: rgba(0,0,0,.15);
}
input[type='submit']{
  height:50px;
  width:70px;
  padding:10px;
  text-align:center;
  background-color: rgba(0,0,0,.25);
  border:0;
  border-left:0;
  color: white;
  cursor:pointer;
  padding:0;
  vertical-align:bottom;
  font:300 18px "Lato", "Arial", sans-serif;
}

button::-moz-focus-inner {
  border: 0;
  padding: 0;
}

input[type='text']:focus {
  outline: none;
}
.icon-trash,
.icon-pencil,
.icon-save {
  position: absolute;
  top: 3px;
  color: #aaa;
  cursor: pointer;
  right: 40px
}

.icon-trash {
  right: 10px
}

.notification {
  position: fixed;
  top: 0;
  left: 50%;
  z-index: 3;
  padding: 5px 10px;
  color: white;
  display: none;
  box-shadow: 0 4px 0 -2px rgba(0, 0, 0, 0.1)
}
.undo-button{
  background-color: orange;
  cursor: pointer;
  margin-left: -100px;
}
.save-notification{
  background-color: #2ecc71;
  margin-left: -50px;
}

button,
.clear-all {
  border:0;
  background-color: transparent;
  padding: 10px 20px;
  color: white;
  float: right;
  cursor: pointer;
  font-size: 16px
}
.clear-all{
  background-color: rgba(0,0,0,.25);
  font:300 18px "Lato", "Arial", sans-serif;
}

button.disabled,
.clear-all.disabled {
  background-color: rgba(0,0,0,.25);
  color: grey;
}

button.disabled .icon-trash,
.clear-all.disabled .icon-trash {
  color: #888
}

button .icon-trash {
  float: none;
  margin-right: 10px;
  opacity: 1 !important;
  position: static
}

.count {
  float: left
}

.count:after {
  content: " item(s)"
}
.reminders {
  list-style-type: none;
  max-width: 500px;
  margin: 30px auto
}

.reminders li {
  z-index:1;
  font-weight: 400;
  /*box-shadow: 0 7px 0 -4px rgba(0, 0, 0, 0.2);*/
  color: #444;
  text-align: left;
  min-height: 50px;
  line-height: 30px;
  font-size: 20px;
  background-color: white;
  margin-bottom: 10px;
  padding: 10px;
  padding-right: 60px;
  position: relative;
  opacity: 0;
  word-wrap: break-word;
  -webkit-transition: all .1s ease;
  -o-transition: all .1s ease;
  transition: all .1s ease
}

.reminders li:focus {
  outline: none;
}
li[contenteditable='true']{
  color:black;
}


@import "http://netdna.bootstrapcdn.com/font-awesome/2.0/css/font-awesome.css";

@import url(http://fonts.googleapis.com/css?family=Lato:300, 100, 400);

html {
  height: 100%;
  position: relative;
}

body {
  background-color: #3498db;
  font-family: "Lato", sans-serif;
  height: 2000px;
  color: white;
}

li.new-item {
  opacity: 0;
  -webkit-animation: new-item-animation 0.3s linear forwards;
  -o-animation: new-item-animation 0.3s linear forwards;
  animation: new-item-animation 0.3s linear forwards;
}

@keyframes new-item-animation {
  from {
    opacity: 0;
    -webkit-transform: scale(0);
    -ms-transform: scale(0);
    -o-transform: scale(0);
    transform: scale(0);
  }

  to {
    opacity: 1;
    -webkit-transform: scale(1);
    -ms-transform: scale(1);
    -o-transform: scale(1);
    transform: scale(1);
  }
}

li.restored-item {
  -webkit-animation: openspace 0.3s ease forwards,
    restored-item-animation 0.3s 0.3s cubic-bezier(0, 0.8, 0.32, 1.07) forwards;
  -o-animation: openspace 0.3s ease forwards,
    restored-item-animation 0.3s 0.3s cubic-bezier(0, 0.8, 0.32, 1.07) forwards;
  animation: openspace 0.3s ease forwards,
    restored-item-animation 0.3s 0.3s cubic-bezier(0, 0.8, 0.32, 1.07) forwards;
}

@keyframes openspace {
  to {
    height: auto;
  }
}

@keyframes restored-item-animation {
  from {
    opacity: 0;
    -webkit-transform: scale(0);
    -ms-transform: scale(0);
    -o-transform: scale(0);
    transform: scale(0);
  }

  to {
    opacity: 1;
    -webkit-transform: scale(1);
    -ms-transform: scale(1);
    -o-transform: scale(1);
    transform: scale(1);
  }
}

li.removed-item {
  -webkit-animation: removed-item-animation 0.6s
    cubic-bezier(0.55, -0.04, 0.91, 0.94) forwards;
  -o-animation: removed-item-animation 0.6s
    cubic-bezier(0.55, -0.04, 0.91, 0.94) forwards;
  animation: removed-item-animation 0.6s cubic-bezier(0.55, -0.04, 0.91, 0.94)
    forwards;
}

@keyframes removed-item-animation {
  from {
    opacity: 1;
    -webkit-transform: scale(1);
    -ms-transform: scale(1);
    -o-transform: scale(1);
    transform: scale(1);
  }

  to {
    -webkit-transform: scale(0);
    -ms-transform: scale(0);
    -o-transform: scale(0);
    transform: scale(0);
    opacity: 0;
  }
}

@-webkit-keyframes new-item-animation {
  from {
    opacity: 0;
    -webkit-transform: scale(0);
    transform: scale(0);
  }

  to {
    opacity: 1;
    -webkit-transform: scale(1);
    transform: scale(1);
  }
}

@-o-keyframes new-item-animation {
  from {
    opacity: 0;
    -o-transform: scale(0);
    transform: scale(0);
  }

  to {
    opacity: 1;
    -o-transform: scale(1);
    transform: scale(1);
  }
}

@-webkit-keyframes openspace {
  to {
    height: auto;
  }
}

@-o-keyframes openspace {
  to {
    height: auto;
  }
}

@-webkit-keyframes restored-item-animation {
  from {
    opacity: 0;
    -webkit-transform: scale(0);
    transform: scale(0);
  }

  to {
    opacity: 1;
    -webkit-transform: scale(1);
    transform: scale(1);
  }
}

@-o-keyframes restored-item-animation {
  from {
    opacity: 0;
    -o-transform: scale(0);
    transform: scale(0);
  }

  to {
    opacity: 1;
    -o-transform: scale(1);
    transform: scale(1);
  }
}

@-webkit-keyframes removed-item-animation {
  from {
    opacity: 1;
    -webkit-transform: scale(1);
    transform: scale(1);
  }

  to {
    -webkit-transform: scale(0);
    transform: scale(0);
    opacity: 0;
  }
}

@-o-keyframes removed-item-animation {
  from {
    opacity: 1;
    -o-transform: scale(1);
    transform: scale(1);
  }

  to {
    -o-transform: scale(0);
    transform: scale(0);
    opacity: 0;
  }
}

<%-- =========================== --%>
</style>
<section>
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
			<header>
				<h1>Creative Add/Remove Effects for List Items</h1>
			</header>
			<div class="notification undo-button">Item Deleted. Undo?</div>
			<div class="notification save-notification">Item Saved</div>
			<div class="reminder-container">
				<form id="input-form">
					<input type="text" id="text" placeholder="Remind me to.." /> <input
						type="submit" value="Add" />
				</form>
				<ul class="reminders"></ul>
				<footer>
					<span class="count"></span>
					<button class="clear-all">Delete All</button>
				</footer>
			</div>
		</div>
		<!--end demo-wrapper-->

	</div>

</section>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
	<c:import url="/WEB-INF/js/jquery-1.8.2.min.js"></c:import>
	<c:import url="/WEB-INF/js/modernizr-1.5.min.js"></c:import>
	<c:import url="/WEB-INF/js/scripts.js"></c:import>
</script>
<c:import url="/WEB-INF/common/footer.jsp"></c:import>
