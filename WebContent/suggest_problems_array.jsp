<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
    <%@page import="java.io.PrintWriter"%> <%@page import="java.util.ArrayList"%>
	<link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
	<link href="./css/style.css" rel="stylesheet"/>
	    <!-- CSS Global Compulsory -->
	    <link rel="stylesheet" href="assets/plugins/bootstrap/css/bootstrap.min.css">
	    <link rel="stylesheet" href="assets/css/style.css">   
    <link rel="stylesheet" href="assets/css/custom.css">
	<script src="./js/jquery.js"></script>
	<script src="./bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" language="javascript">
	$(document).ready(function() {
	   $("tr").click(function(event){
	       var name = $(this).closest('tr').attr('id');
	       $("#"+name).load('./problemDetails', {"code":name} );
	   });
	});
</script>	     
</head>
<body>
<jsp:include page="./header.jsp" />
<div class="row">
	<div id="settings" class="col-md-7 col-md-offset-1" style="background-color:white;">
	     <h4>Suggested Problems</h4>                            
	     <table class="table table-hover">
		<% ArrayList<String> codes = (ArrayList) request.getAttribute("codes");
			if (codes.size()!=0) {
				out.println("<thead><tr><th>Code</th><th>Name</th><th>Date</th><th>Diff</th><th>Users</th><th>Acc.</th><th>Tags</th></tr></thead>");
				for(String code: codes){
	           		out.println("<tr id='"+code+"'><td><a '>"+code+"</a></td></tr>");
	            }
			} else {
				out.println("<h4>No Suggestions available</h4>");
			}
		%>
		</table>
	</div>
	<div class="col-md-2 col-md-offset-1" style="background-color:white; min-height:400px;">
		<h4>Suggested Users</h4>
		<table class="table table-hover">
		<%
		ArrayList<String> folSug = (ArrayList) request.getAttribute("folSug");
		if (folSug.size()!=0) {
			out.println("<thead><tr><th>handle</th><th>Name</th></tr></thead>");
			for(String fol: folSug){
				String arr[] = fol.split(";");
				if(arr.length==2){
					String handle = arr[0];
					String name = arr[1];
					if(name.equals("null"))
	           			out.println("<tr><td><a href='./profile?handle="+handle+"'> "+handle+"</a></td><td>NA</td></tr>");
					else out.println("<tr><td><a href='./profile?handle="+handle+"'> "+handle+"</a></td><td>"+name+"</td></tr>");
				}
            }
		} else {
			out.println("<h4>No Suggestions available</h4>");
		}
		%>
	</div>
</div>
</body>
</html>