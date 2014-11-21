package search;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;

import register.JdbcSetup;

public class SearchBackend {
	ArrayList<String> searchUserById(String handle){
		ArrayList<String> arrayList = new ArrayList<String>();
		try {
			JdbcSetup jd = null;
			jd = new JdbcSetup();
			PreparedStatement pstmt = null;
			String selectSql = "SELECT handle, (personal).name FROM users WHERE LOWER(handle) like LOWER(?)";
			pstmt = jd.conn1.prepareStatement(selectSql);
			pstmt.setString(1,"%" + handle +"%");
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {              
			    arrayList.add(rs.getString("handle") + ";" + rs.getString(2));
			    System.out.println(rs.getString(1));
			    System.out.println(rs.getString(2));
			    
			}
			jd.destroy();
			
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			arrayList.add("Server Down");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			arrayList.add("Server Down");
		}
		
		return arrayList;
	}
	
	ArrayList<String> searchUserByName(String name){
		ArrayList<String> arrayList = new ArrayList<String>();
		try {
			JdbcSetup jd = null;
			jd = new JdbcSetup();
			PreparedStatement pstmt = null;
			String selectSql = "SELECT handle, (personal).name FROM users WHERE LOWER((personal).name) like LOWER(?)";
			pstmt = jd.conn1.prepareStatement(selectSql);
			pstmt.setString(1,"%" + name +"%");
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {              
			    arrayList.add(rs.getString("handle") + ";" + rs.getString(2));
			    System.out.println(rs.getString(1));
			    System.out.println(rs.getString(2));
			    
			}
			jd.destroy();
			
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			arrayList.add("Server Down");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			arrayList.add("Server Down");
		}
		
		return arrayList;
	}
	
	ArrayList<ArrayList<String>> searchProblems(String name){
		ArrayList<ArrayList<String>> arrayList = new ArrayList<ArrayList<String>>();
		try {
			JdbcSetup jd = null;
			jd = new JdbcSetup();
			PreparedStatement pstmt = null;
			String selectSql = "SELECT A.code, A.name, A.link, C.name FROM " +
					"problems as A inner join problems_tags as B on A.code = B.code " +
					"inner join tags as C on B.tagid = C.tagid WHERE LOWER(A.name) like LOWER(?) or " +
					"LOWER(A.code) like LOWER(?) order by LOWER(A.code)";
			pstmt = jd.conn1.prepareStatement(selectSql);
			pstmt.setString(1,"%" + name +"%");
			pstmt.setString(2,"%" + name +"%");
			System.out.println(pstmt.toString());
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) { 
				System.out.println("yo");
				ArrayList<String> tempList = new ArrayList<String>();
				tempList.add(rs.getString(1));
				tempList.add(rs.getString(2));
				tempList.add(rs.getString(3));
				tempList.add(rs.getString(4));
			    arrayList.add(tempList);
			    
			}
			jd.destroy();
			
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ArrayList<String> tempList = new ArrayList<String>();
			tempList.add("Server Donw");
			arrayList.add(tempList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ArrayList<String> tempList = new ArrayList<String>();
			tempList.add("Server Down");
			arrayList.add(tempList);
		}
		
		return arrayList;
	}
	
	ArrayList<ArrayList<String>> searchProblemsWithTags(String name){
		ArrayList<ArrayList<String>> arrayList = new ArrayList<ArrayList<String>>();
		try {
			JdbcSetup jd = null;
			jd = new JdbcSetup();
			PreparedStatement pstmt = null;
			String selectSql = "WITH ProblemTable as (SELECT A.code FROM " +
					"problems as A inner join problems_tags as B " +
					"on A.code = B.code inner join tags as C " +
					"on B.tagid = C.tagid WHERE C.name = ?) " +
					"SELECT A.code, A.name, A.link, C.name FROM " +
					"ProblemTable inner join problems as A on ProblemTable.code = A.code " +
					"inner join problems_tags as B " +
					"on A.code = B.code inner join tags as C on B.tagid = C.tagid";
			pstmt = jd.conn1.prepareStatement(selectSql);
			pstmt.setString(1,name);
			//pstmt.setString(2,"%" + name +"%");
			System.out.println(pstmt.toString());
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) { 
				System.out.println("yo");
				ArrayList<String> tempList = new ArrayList<String>();
				tempList.add(rs.getString(1));
				tempList.add(rs.getString(2));
				tempList.add(rs.getString(3));
				tempList.add(rs.getString(4));
			    arrayList.add(tempList);
			    
			}
			jd.destroy();
			
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ArrayList<String> tempList = new ArrayList<String>();
			tempList.add("Server Donw");
			arrayList.add(tempList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ArrayList<String> tempList = new ArrayList<String>();
			tempList.add("Server Down");
			arrayList.add(tempList);
		}
		
		return arrayList;
	}
}
