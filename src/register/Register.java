package register;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("register here by post");
		
		String message = "Please fill ";
		String name = request.getParameter("name");
		String 	handle = request.getParameter("handle");
		String email = request.getParameter("email");
		String passwd = request.getParameter("passwd");	
		String insti = request.getParameter("insti");	
		String dob = request.getParameter("dob");
		String region = request.getParameter("region");
		if(name==null || name.equals("")) 
			message = message + "name";
		if(handle==null || handle.equals(""))
			message = message + " handle";
		if(passwd==null || passwd.equals(""))
			message = message + " password";
		if(message.equals("Please fill ")){
			RegisterBackend registers = new RegisterBackend();
			if(dob==null || (dob.length()!=10) || dob.charAt(4)!='-' || dob.charAt(7)!='-')
				dob = "1111-11-11";
			message = registers.registerUser(name, handle, email, passwd, insti, dob, region);
			if(message.equals("")){
				message = "Succesfully registered please login.";
			}
			response.sendRedirect("./index.jsp?message="+message);
		}
		else response.sendRedirect("./index.jsp?message="+message);
	}

}
