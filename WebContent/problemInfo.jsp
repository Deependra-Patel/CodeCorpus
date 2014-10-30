<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<%ArrayList<String> problemDetails = (ArrayList)request.getAttribute("details");
	ArrayList<String> tags = (ArrayList)request.getAttribute("tags");
		out.println("<td> <a href='"+problemDetails.get(2)+"' target='_blank'>"+problemDetails.get(0)+"</a></td>");
		out.println("<td>"+problemDetails.get(1)+"</td>");
		out.println("<td>"+problemDetails.get(3)+"</td>");
		out.println("<td>"+problemDetails.get(4)+"</td>");	
		out.println("<td>"+problemDetails.get(5)+"</td>");
		out.println("<td>"+problemDetails.get(6)+"</td><td>");	
		for(String tag:tags){
			out.println("<a href = './tagProblems?tag='" + tag + "' class = 'btn btn-primary btn-xs'>" + tag + " </a>");
		}
		out.println("</td>");
	%>
