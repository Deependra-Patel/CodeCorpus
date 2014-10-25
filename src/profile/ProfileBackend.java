package profile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;

import register.JdbcSetup;

class UserDetails{
	ArrayList<String> personalDetail = new ArrayList<String>();
	ArrayList<String> problemsAttempted = new ArrayList<String>();
	ArrayList<String> problemsSolved = new ArrayList<String>();
	ArrayList<String> interests = new ArrayList<String>();	
} 
public class ProfileBackend {
	 UserDetails profileDetails(String handle){
		try {
			UserDetails ud = new UserDetails();
			JdbcSetup jd = null;
			jd = new JdbcSetup();
			PreparedStatement pstmt = null;
			String selectSql = "SELECT userid, handle, rank, (personal).name, (personal).dob, (personal).region, (personal).email_id, (personal).institution FROM users WHERE handle=?";
			
			pstmt = jd.conn1.prepareStatement(selectSql);
			pstmt.setString(1, handle);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()){
				ud.personalDetail.add(String.valueOf(rs.getInt(1)));
				ud.personalDetail.add(rs.getString(2));
				ud.personalDetail.add(String.valueOf(rs.getInt(3)));
				ud.personalDetail.add(rs.getString(4));
				ud.personalDetail.add(rs.getDate(5).toString());
				ud.personalDetail.add(rs.getString(6));
				ud.personalDetail.add(rs.getString(7));
				ud.personalDetail.add(rs.getString(8));
			}
			else return null;
			
			selectSql = "SELECT code, solved FROM users NATURAL JOIN attempted WHERE handle=?";
			pstmt = jd.conn1.prepareStatement(selectSql);
			pstmt.setString(1, handle);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				ud.problemsAttempted.add(rs.getString(1));
				ud.problemsSolved.add(String.valueOf(rs.getBoolean(2)));
			}
			
			selectSql = "SELECT tags.name from users NATURAL JOIN users_interests INNER JOIN tags ON users_interests.tagid=tags.tagid WHERE handle=?";
			pstmt = jd.conn1.prepareStatement(selectSql);
			pstmt.setString(1, handle);
			rs = pstmt.executeQuery();
			jd.destroy();
			while(rs.next()){
				ud.interests.add(rs.getString(1));
			}			
			return ud;
			
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			System.out.println("Error with servlet.");
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
			System.out.println("Error with sql query");
			return null;
		}
	}
	 ArrayList<String> details(String code){
			JdbcSetup jd;
			try {
				jd = new JdbcSetup();
				PreparedStatement pstmt = null;
				String selectSql = "select code, name, link, doa, difficulty, (statistic).solved, (statistic).accuracy from problems where code=?";
				
				try {
					pstmt = jd.conn1.prepareStatement(selectSql);
					pstmt.setString(1, code); 
					ResultSet rs = pstmt.executeQuery();
					ArrayList<String> details = new ArrayList<String>();
					jd.destroy();
					System.out.println("faltu");
					if(rs.next()){
						System.out.println(rs.getString(1));						
						details.add(rs.getString(1));
						details.add(rs.getString(2));
						details.add(rs.getString(3));
						details.add(rs.getString(4));
						details.add(rs.getString(5));
						details.add(rs.getString(6));
						details.add(rs.getString(7));
						return details;
					}
					else return null;					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}		 
	 }
}
