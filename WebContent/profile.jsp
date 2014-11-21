
<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<% 
	ArrayList<String> personalDetail = (ArrayList)request.getAttribute("personalDetail");
	ArrayList<String> problemsAttempted = (ArrayList)request.getAttribute("problemsAttempted");
	ArrayList<String> problemsSolved = (ArrayList)request.getAttribute("problemsSolved");
	ArrayList<String> interests = (ArrayList)request.getAttribute("interests");
	ArrayList<String> followers = (ArrayList)request.getAttribute("followers");	
	%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
<link href="./css/style.css" rel="stylesheet"/>
    <!-- CSS Global Compulsory -->
    <link rel="stylesheet" href="assets/plugins/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/style.css">

    <!-- CSS Implementing Plugins -->
    <link rel="stylesheet" href="assets/plugins/line-icons/line-icons.css">
    <link rel="stylesheet" href="assets/plugins/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/plugins/revolution-slider/rs-plugin/css/settings.css" type="text/css" media="screen">
    <!--[if lt IE 9]><link rel="stylesheet" href="assets/plugins/revolution-slider/rs-plugin/css/settings-ie8.css" type="text/css" media="screen"><![endif]-->


    <!-- CSS Customization -->
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
<div class="wrapper">
		<jsp:include page="./header.jsp" />
		<div class="col-lg-10 col-lg-offset-1 user-details">
            <div class="user-image">
                <img src="./images/avatar1.jpg" alt="Avatar" class="img-circle">
            </div>
            <div class="user-info-block">
                <div class="user-heading">
                    <h3><%out.println(personalDetail.get(1)); %></h3>
                    <span class="help-block"><%out.println(personalDetail.get(7)+", "+personalDetail.get(5)); %></span>
               		<%if(!personalDetail.get(1).equals(session.getAttribute("handle"))){ %>
               		<form action="profile">
               			<input type="hidden" name="handle" value="<%=personalDetail.get(1)%>">
               			<%if(!followers.contains(session.getAttribute("handle"))){ %>
	               			<input type="hidden" name="follow" value="<%=personalDetail.get(0)%>">
		               		<button type="submit"  class="btn btn-success" style="top:0px;right:10px;">
		               			Follow
		               		</button>
		               	<%} else { %>
	               			<input type="hidden" name="unfollow" value="<%=personalDetail.get(0)%>">
		               		<button type="submit"  class="btn btn-primary" style="top:0px;right:10px;">
		               			Unfollow
		               		</button>		               	
		               	<%} %>
               		</form>
               		<%} %>
                </div>
                <ul class="navigation">
                    <li class="active">
                        <a data-toggle="tab" href="#information"> 
                            <span class="glyphicon glyphicon-user"></span>Basic
                        </a>
                    </li>
                    <li>
                        <a data-toggle="tab" href="#settings">
                            <span class="glyphicon glyphicon-cog"></span> Problems Solved
                        </a>
                    </li>
                    <li>
                        <a data-toggle="tab" href="#email"> 
                            <span class="glyphicon glyphicon-envelope"></span> Interests
                        </a>
                    </li>
                    <li>
                        <a data-toggle="tab" href="#events">
                            <span class="glyphicon glyphicon-calendar"></span> Followers
                        </a>
                    </li>
                </ul>
                <div class="user-body">
                    <div class="tab-content">
                        <div id="information" class="tab-pane active">
                            <h4>Basic Information</h4>
                            <table class="table table-hover">
                            	<tr><td>Rank </td>
                            		<td><%if(personalDetail.get(2).equals("-1"))
                            				out.println("NA");
                            			else out.println(personalDetail.get(2));%></td>
                            	</tr>
                            	<tr><td>Name </td>
                            		<td><%out.println(personalDetail.get(3));%></td>
                            	</tr>
                            	<tr><td>Codechef Handle </td>
                            		<td><%out.println(personalDetail.get(1));%></td>
                            	</tr>                            	
                            	<tr><td>Email </td>
                            		<td><%out.println(personalDetail.get(6)); %></td>
                            	</tr>
                            	<tr><td>Date Of Birth</td>
                            		<td><%out.println(personalDetail.get(4)); %></td>
                            	</tr>
                            </table>
                        </div>
                        <div id="settings" class="tab-pane">
                            <h4>Problems Solved</h4>                            
                            <table class="table table-hover">
                            	<%	
                            	if(problemsAttempted!=null)
                            	for(String code: problemsAttempted){
                            		out.println("<tr id='"+code+"'><td><a '>"+code+"</a></td></tr>");
                            	}
                            	%>
                            </table>
                        </div>
                        <div id="email" class="tab-pane">
                            <h4>Interests</h4>
                               <table class="table table-hover">
                            	<%	
                            	for(String interest: interests){
                            		out.println("<tr><td><a target='_blank' href = './tagProblems?tag=" + interest + "' class = 'btn btn-primary btn-xs'>" + interest + " </a></td></tr>");
                            	}
                            	%>
                            </table>                         
                        </div>
                        <div id="events" class="tab-pane">
                            <h4>Followers</h4>
                               <table class="table table-hover">
                            	<%	
                            	if(followers!=null)
                            	for(String follower: followers){
                            		out.println("<tr><td><a target='_blank' href = './profile?handle=" + follower + "' class = 'btn btn-primary btn-xs'>" + follower + " </a></td></tr>");
                            	}
                            	%>
                            </table>                             
                        </div>
                    </div>
                </div>
            </div>
	</div></div>
</body>
</html>