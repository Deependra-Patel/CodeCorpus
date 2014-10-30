
<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
<link href="./css/style.css" rel="stylesheet"/>
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
	<% 
	ArrayList<String> personalDetail = (ArrayList)request.getAttribute("personalDetail");
	ArrayList<String> problemsAttempted = (ArrayList)request.getAttribute("problemsAttempted");
	ArrayList<String> problemsSolved = (ArrayList)request.getAttribute("problemsSolved");
	ArrayList<String> interests = (ArrayList)request.getAttribute("interests");
	ArrayList<String> followers = (ArrayList)request.getAttribute("followers");	
	%>
    <div class="collapse navbar-collapse" id="loggedin">
      <ul class="nav navbar-nav navbar-right">
		<li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Iasmani Pinazo <span class="glyphicon glyphicon-user pull-right"></span></a>
          <ul class="dropdown-menu">
            <li><a href="#">Account Settings <span class="glyphicon glyphicon-cog pull-right"></span></a></li>
            <li class="divider"></li>
            <li><a href="#">User stats <span class="glyphicon glyphicon-stats pull-right"></span></a></li>
            <li class="divider"></li>
            <li><a href="#">Messages <span class="badge pull-right"> 42 </span></a></li>
            <li class="divider"></li>
            <li><a href="#">Favourites Snippets <span class="glyphicon glyphicon-heart pull-right"></span></a></li>
            <li class="divider"></li>
            <li><a href="#">Sign Out <span class="glyphicon glyphicon-log-out pull-right"></span></a></li>
          </ul>
        </li>         
      </ul>
    </div>
    
<div class="container">
	<div class="row">
		<div class="col-lg-10 col-lg-offset-1 user-details">
            <div class="user-image">
                <img src="./images/avatar1.jpg" alt="Avatar" class="img-circle">
            </div>
            <div class="user-info-block">
                <div class="user-heading">
                    <h3><%out.println(personalDetail.get(1)); %></h3>
                    <span class="help-block"><%out.println(personalDetail.get(7)+", "+personalDetail.get(5)); %></span>
                </div>
                <ul class="navigation">
                    <li class="active">
                        <a data-toggle="tab" href="#information">
                            <span class="glyphicon glyphicon-user"></span>
                        </a>
                    </li>
                    <li>
                        <a data-toggle="tab" href="#settings">
                            <span class="glyphicon glyphicon-cog"></span>
                        </a>
                    </li>
                    <li>
                        <a data-toggle="tab" href="#email">
                            <span class="glyphicon glyphicon-envelope"></span>
                        </a>
                    </li>
                    <li>
                        <a data-toggle="tab" href="#events">
                            <span class="glyphicon glyphicon-calendar"></span>
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
        </div>
      
	</div>
</div>
</body>
</html>