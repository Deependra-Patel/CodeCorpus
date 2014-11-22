package profile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ProblemDetails")
public class ProblemDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ProblemDetails() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		if(session.getAttribute("handle")==null){
			response.sendRedirect("./index.jsp?err=1");
		}
		else {
			String handle = (String) session.getAttribute("handle");
			String code = request.getParameter("code");
			ProfileBackend problemDetails = new ProfileBackend();
			ProblemDetailsObj obj = new ProblemDetailsObj();
			obj = problemDetails.details(code);
			request.setAttribute("details", obj.problemInfo);
			request.setAttribute("tags", obj.problemTags);		
			request.getRequestDispatcher("./problemInfo.jsp").forward(request, response);
		}
	}

}
