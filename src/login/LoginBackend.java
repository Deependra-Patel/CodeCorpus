package login;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;

import register.JdbcSetup;
public class LoginBackend {
	String login(String handle, String passwd){
		try {
			JdbcSetup jd = null;
			jd = new JdbcSetup();
			PreparedStatement pstmt = null;
			String selectSql = "SELECT * FROM users WHERE handle=? and passwd=?";
			pstmt = jd.conn1.prepareStatement(selectSql);
			pstmt.setString(1, handle);
			pstmt.setString(2, passwd);
			ResultSet rs = pstmt.executeQuery();
			jd.destroy();
			if(rs.next())
				return "true";
			else return "Invalid Username/Password";
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
			return "Server Down";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Server Down";
		}
	}
}
