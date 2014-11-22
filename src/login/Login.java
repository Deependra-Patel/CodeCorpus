package login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		if(handle.equals("admin")){
			if(passwd.equals("123")){
				HttpSession session = request.getSession(true);
				session.setAttribute("handle", handle);
				session.setAttribute("passwd", passwd);
				request.getRequestDispatcher("./admin.jsp").forward(request, response);
			}else {
				request.setAttribute("message", "Please enter correct passsword.");
				request.getRequestDispatcher("./index.jsp").forward(request, response);				
			}
		}
		else {
			LoginBackend log = new LoginBackend();
			myPair mp = new myPair();
			mp = log.login(handle, passwd);
			String message = mp.message;
			if(message.equals("true")){
				HttpSession session = request.getSession(true);
				session.setAttribute("handle", handle);
				session.setAttribute("userid", mp.userid);
				response.sendRedirect("./home");
			}
			else {
				response.sendRedirect("./index.jsp?message="+mp.message);
			}
		}
	}
}
