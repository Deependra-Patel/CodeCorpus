package login;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;

import register.JdbcSetup;
class myPair{
	String message;
	int userid;
}
public class LoginBackend {
	myPair login(String handle, String passwd){
		myPair mp = new myPair();
		try {
			JdbcSetup jd = null;
			jd = new JdbcSetup();
			PreparedStatement pstmt = null;
			String selectSql = "SELECT userid FROM users WHERE handle=? and passwd=?";
			pstmt = jd.conn1.prepareStatement(selectSql);
			pstmt.setString(1, handle);
			pstmt.setString(2, passwd);
			ResultSet rs = pstmt.executeQuery();
			jd.destroy();
			if(rs.next()){
				mp.userid = rs.getInt(1);
				mp.message = "true";
			}
			else mp.message = "Invalid Username/Password";
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
			mp.message = "Server Down";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.message = "Server Down";
		}
		return mp;
	}
}
