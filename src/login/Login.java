package login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String handle = request.getParameter("handle");
		String passwd = request.getParameter("passwd");
		LoginBackend log = new LoginBackend();
		String res = log.login(handle, passwd);
		if(res.equals("true")){
			response.sendRedirect("./home.jsp");
		}
		else {
			PrintWriter pr =  response.getWriter();
			pr.print(res);
		}
	}
}
