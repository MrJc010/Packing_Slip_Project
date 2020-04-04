<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/WEB-INF/common/header.jsp">
	<c:param name="title" value="Search Info"></c:param>
</c:import>

<style>
@import url(http://fonts.googleapis.com/css?family=Lato:300,100,400);
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
.fa-trash,
.fa-pencil,
.fa-floppy-o {
  position: absolute;
  top: 12px;
  color: red;
  cursor: pointer;
  right: 40px
}

.fa-trash {
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

button.disabled .fa-trash,
.clear-all.disabled .fa-trash {
  color: #888
}

button .fa-trash {
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


@import "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css";

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



* {
  box-sizing: border-box;
}

body {
  font: 16px Arial;  
}

/*the container must be positioned relative:*/
.autocomplete {
  position: relative;
  display: inline-block;
}

input {
  border: 1px solid transparent;
  background-color: #f1f1f1;
  padding: 10px;
  font-size: 16px;
}

input[type=text] {
  background-color: #f1f1f1;
  width: 100%;
}

input[type=submit] {
  background-color: DodgerBlue;
  color: #fff;
  cursor: pointer;
}

.autocomplete-items {
  position: absolute;
  border: 1px solid #d4d4d4;
  border-bottom: none;
  border-top: none;
  z-index: 99;
  /*position the autocomplete items to be the same width as the container:*/
  top: 100%;
  left: 0;
  right: 0;
}

.autocomplete-items div {
  padding: 10px;
  cursor: pointer;
  background-color: #fff; 
  border-bottom: 1px solid #d4d4d4; 
}

/*when hovering an item:*/
.autocomplete-items div:hover {
  background-color: #e9e9e9; 
}

/*when navigating through the items using the arrow keys:*/
.autocomplete-active {
  background-color: DodgerBlue !important; 
  color: #ffffff; 
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
					<input type="text" id="text" placeholder="Remind me to.." /> 
					<input type="submit" value="Add" />
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
	
	
<script>
function autocomplete(inp, arr) {
  /*the autocomplete function takes two arguments,
  the text field element and an array of possible autocompleted values:*/
  var currentFocus;
  /*execute a function when someone writes in the text field:*/
  inp.addEventListener("input", function(e) {
      var a, b, i, val = this.value;
      /*close any already open lists of autocompleted values*/
      closeAllLists();
      if (!val) { return false;}
      currentFocus = -1;
      /*create a DIV element that will contain the items (values):*/
      a = document.createElement("DIV");
      a.setAttribute("id", this.id + "autocomplete-list");
      a.setAttribute("class", "autocomplete-items");
      /*append the DIV element as a child of the autocomplete container:*/
      this.parentNode.appendChild(a);
      /*for each item in the array...*/
      for (i = 0; i < arr.length; i++) {
        /*check if the item starts with the same letters as the text field value:*/
        if (arr[i].substr(0, val.length).toUpperCase() == val.toUpperCase()) {
          /*create a DIV element for each matching element:*/
          b = document.createElement("DIV");
          /*make the matching letters bold:*/
          b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
          b.innerHTML += arr[i].substr(val.length);
          /*insert a input field that will hold the current array item's value:*/
          b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
          /*execute a function when someone clicks on the item value (DIV element):*/
          b.addEventListener("click", function(e) {
              /*insert the value for the autocomplete text field:*/
              inp.value = this.getElementsByTagName("input")[0].value;
              /*close the list of autocompleted values,
              (or any other open lists of autocompleted values:*/
              closeAllLists();
          });
          a.appendChild(b);
        }
      }
  });
  /*execute a function presses a key on the keyboard:*/
  inp.addEventListener("keydown", function(e) {
      var x = document.getElementById(this.id + "autocomplete-list");
      if (x) x = x.getElementsByTagName("div");
      if (e.keyCode == 40) {
        /*If the arrow DOWN key is pressed,
        increase the currentFocus variable:*/
        currentFocus++;
        /*and and make the current item more visible:*/
        addActive(x);
      } else if (e.keyCode == 38) { //up
        /*If the arrow UP key is pressed,
        decrease the currentFocus variable:*/
        currentFocus--;
        /*and and make the current item more visible:*/
        addActive(x);
      } else if (e.keyCode == 13) {
        /*If the ENTER key is pressed, prevent the form from being submitted,*/
        e.preventDefault();
        if (currentFocus > -1) {
          /*and simulate a click on the "active" item:*/
          if (x) x[currentFocus].click();
        }
      }
  });
  function addActive(x) {
    /*a function to classify an item as "active":*/
    if (!x) return false;
    /*start by removing the "active" class on all items:*/
    removeActive(x);
    if (currentFocus >= x.length) currentFocus = 0;
    if (currentFocus < 0) currentFocus = (x.length - 1);
    /*add class "autocomplete-active":*/
    x[currentFocus].classList.add("autocomplete-active");
  }
  function removeActive(x) {
    /*a function to remove the "active" class from all autocomplete items:*/
    for (var i = 0; i < x.length; i++) {
      x[i].classList.remove("autocomplete-active");
    }
  }
  function closeAllLists(elmnt) {
    /*close all autocomplete lists in the document,
    except the one passed as an argument:*/
    var x = document.getElementsByClassName("autocomplete-items");
    for (var i = 0; i < x.length; i++) {
      if (elmnt != x[i] && elmnt != inp) {
        x[i].parentNode.removeChild(x[i]);
      }
    }
  }
  /*execute a function when someone clicks in the document:*/
  document.addEventListener("click", function (e) {
      closeAllLists(e.target);
  });
}
var locationList = ${locationList};
var list =[];
for (var key in locationList) {
	list.push(locationList[key]);
}
console.log(list);
/*initiate the autocomplete function on the "myInput" element, and pass along the countries array as possible autocomplete values:*/
autocomplete(document.getElementById("text"), list);
</script>

</section>

<c:import url="/WEB-INF/common/footer.jsp"></c:import>
<script>
	<c:import url="/WEB-INF/js/modernizr-1.5.min.js" />
	<c:import url="/WEB-INF/js/scripts.js" />
</script>