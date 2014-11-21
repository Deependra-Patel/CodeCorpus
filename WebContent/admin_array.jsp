<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="java.io.PrintWriter"%> <%@page import="java.util.ArrayList"%>

<table class="table table-hover">
	<%
		ArrayList<String> codes = (ArrayList) request.getAttribute("codes");
		if (codes != null) {
			if (codes.size() > 0) {
				out.println("<h3>List Of added Problems</h3>");
				out.println("<button class=\"btn btn-primary\"  id=\"closeB\"  onclick=\"closeBFunc()\">close</button><br>");
				out.println("<table id='addedProb' class=\"table table-hover\">");
				out.println("<tbody>");
				out.println("<tr id = 'attrname' ><td>Problem Code</td></tr>");
				for (String code : codes) {
					out.println("<tr id='" + code + "'><td>" + code
							+ "</td></tr>");
				}
				out.println("</tbody>");
				out.println("</table>");
			} else {
				out.println("<h4>No New problems added</h4>");
			}
		}
	%>
</table>
