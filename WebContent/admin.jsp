<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%if(session.getAttribute("handle")==null || !(session.getAttribute("handle").equals("admin") && session.getAttribute("passwd").equals("123"))){response.sendRedirect("./index.jsp?err=1");} %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin panel</title>
<link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="./css/style.css" rel="stylesheet" />
<script src="./js/jquery.js"></script>
<script src="./bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" language="javascript">

	function closeBFunc(){
		document.getElementById("addedProbList").style.display='none';
	};

	function makeDivVisible(){
		document.getElementById("addedProbList").style.display='block';
	};
	function updateAllUsers(){
		$("#addedProbList").load('./scrapeUserProblems', {
			"UpdateType" : "allUsers"
		});
		makeDivVisible();
	}
	$(document).ready(function() {
		var diff1 = "Easy";
		$("#" + diff1).click(function(event) {
			$("#addedProbList").load('./scrapeProblems', {
				"UpdateType" : diff1
			});
			makeDivVisible();
		});

		var diff2 = "Hard";
		$("#" + diff2).click(function(event) {
			$("#addedProbList").load('./scrapeProblems', {
				"UpdateType" : diff2
			});
			makeDivVisible();
		});

		var diff3 = "Medium";
		$("#" + diff3).click(function(event) {
			$("#addedProbList").load('./scrapeProblems', {
				"UpdateType" : diff3
			});
			makeDivVisible();
		});

		var diff4 = "Challenge";
		$("#" + diff4).click(function(event) {
			$("#addedProbList").load('./scrapeProblems', {
				"UpdateType" : diff4
			});
			makeDivVisible()
		});

		var diff5 = "All";
		$("#" + diff5).click(function(event) {
			$("#addedProbList").load('./scrapeProblems', {
				"UpdateType" : diff5
			});
			makeDivVisible()
		});
	});
</script>
</head>
<body>

<%
	ArrayList<String> codes = (ArrayList<String>) request.getAttribute("codes");
	ArrayList<String> names = (ArrayList<String>) request.getAttribute("names");
%>


<!-- Fixed navbar -->
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
<div class="container">
<div class="navbar-header">
<button type="button" class="navbar-toggle collapsed"
	data-toggle="collapse" data-target="#navbar" aria-expanded="false"
	aria-controls="navbar"><span class="sr-only">Toggle
navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span>
<span class="icon-bar"></span></button>
<a class="navbar-brand" href="#"><span
	class="glyphicon glyphicon-user"></span> Admin Panel</a></div>
<div id="navbar" class="navbar-collapse collapse">
<ul class="nav navbar-nav">
	<li class="active"><a href="#">Home</a></li>
	<li><a href="#about">About</a></li>
	<li><a href="#contact">Contact</a></li>
	<li class="dropdown"><a href="#" class="dropdown-toggle"
		data-toggle="dropdown">Update Problems<span class="caret"></span></a>
	<ul class="dropdown-menu" role="menu">
		<li id="Easy"><a href="#">Easy</a></li>
		<li id="Medium"><a href="#">Medium</a></li>
		<li id="Hard"><a href="#">Hard</a></li>
		<li id="Challenge"><a href="#">Challenge</a></li>
		<li id="All"><a href="#">All <span
			class="glyphicon glyphicon-flash"></span></a></li>
		<li>
		<form id="formAll" action="scrapeProblems">
		<p style="padding-left: 5mm; text-align: left;"><a href="#"
			onclick="document.getElementById('formAll').submit();">Other <span
			class="glyphicon glyphicon-flash"></span></a> <input type="hidden"
			name="UpdateType" value="easy"></p>
		</form>
		</li>
	</ul>
	</li>
	<li><button class="btn btn-primary" type = "submit" onclick="updateAllUsers();" name="updateAll">Update Data for All Users</button>
	</li>
	<li>
		<form action="./logout">
			<button type="submit" class="btn btn-primary" type = "submit">Logout</button>
		</form>
	</li>
</ul>
</div>
<!--/.nav-collapse --></div>
</nav>


<div style=" width:100%; height:60px;">
</div>

<div id="addedProbList" style="padding:10px;">
</div>


</body>
</html>