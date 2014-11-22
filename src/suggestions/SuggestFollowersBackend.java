package suggestions;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.swing.text.html.HTMLDocument.Iterator;

import register.JdbcSetup;

public class SuggestFollowersBackend {
	int numResults = 15;
	ArrayList<String> suggestFollowersHandle(String handle){
		ArrayList<String> arrayList = new ArrayList<String>();
		
		int userid = 0;
		//find userid from handle
		try {
			JdbcSetup jd = null;
			jd = new JdbcSetup();
			PreparedStatement pstmt = null;
			String selectSql = "SELECT userid FROM users WHERE handle = ?";
			pstmt = jd.conn1.prepareStatement(selectSql);
			pstmt.setString(1,handle);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {              
			    userid = rs.getInt("userid");
			    
			    
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
		
		
		ArrayList<Integer> uids = suggestFollowersUids(userid);

		String filterFollowers = "SELECT userid from users where userid in (";
		
		
		Integer [] uids_array = uids.toArray(new Integer[uids.size()]);
		
		for(int i = 0; i < uids.size(); i++){
			String temp = uids_array[i].toString();
			filterFollowers+=temp;
			if(i != uids.size() - 1){
				filterFollowers+=",";
			}
			else if(i == uids.size()-1){
				filterFollowers+=")";
			}
		}
		
		ArrayList<Integer> filteredUids = new ArrayList<Integer>();
		filterFollowers+=" and userid not in " +
				"(SELECT followed from followers where follower = ?) ";
		
		try {
			JdbcSetup jd = null;
			jd = new JdbcSetup();
			PreparedStatement pstmt = null;
			
			pstmt = jd.conn1.prepareStatement(filterFollowers);
			pstmt.setInt(1, userid);
			System.out.println(pstmt);
			//Object[] temp = {1,2,3};
			//Array array = jd.conn1.createArrayOf("VARCHAR", temp);
			//System.out.println(array);
			//pstmt.setArray(1,array);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {              
				int uid = rs.getInt("userid");
				filteredUids.add(uid);
			    
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
		
		String query = "SELECT handle, (personal).name FROM users WHERE userid in (";
		
		Integer [] filteredUids_array = filteredUids.toArray(new Integer[filteredUids.size()]);
		boolean done = false;
		for(int i = 0; i < uids.size(); i++){
			for(int j = 0; j < filteredUids.size(); j++){
				if(uids_array[i]==filteredUids_array[j]){
					String temp = filteredUids_array[j].toString();
					query+=temp;
					if(i < numResults){
						query+=",";
						
					}
					else if(i >= numResults){
						query+=")";
						done = true;
					}
					break;
					}
			}
			if (done) break;
		}
		
		/*
		for(int i = 0; i < filteredUids.size() && i < numResults; i++){
			
			String temp = filteredUids_array[i].toString();
			query+=temp;
			if(i != filteredUids.size() - 1){
				query+=",";
				
			}
			else if(i == filteredUids.size()-1){
				query+=")";
				
			}
		}*/
		
		//for(int i = 0; i < uids.size(); i++){
			//int parameter = uids_array[i];
			try {
				JdbcSetup jd = null;
				jd = new JdbcSetup();
				PreparedStatement pstmt = null;
				pstmt = jd.conn1.prepareStatement(query);
				System.out.println(pstmt);
				//Object[] temp = {1,2,3};
				//Array array = jd.conn1.createArrayOf("VARCHAR", temp);
				//System.out.println(array);
				//pstmt.setArray(1,array);
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
		//}
		
		return arrayList;
	}
	
	
	ArrayList<Integer> suggestFollowersUids(Integer userid){
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		HashMap<Integer, Double> weights = new HashMap<Integer, Double>();
		
		double c1 = 10.0;
		double c2 = 60.0;
		double c3 = 60.0;
		double c4 = 1.0;
		
		//Fetching the users who have in their interest table, tags which correspond to the problems solved by the user
		try {
			JdbcSetup jd = null;
			jd = new JdbcSetup();
			PreparedStatement pstmt = null;
			String selectSql = "SELECT userid FROM users_interests WHERE tagid in (SELECT tagid FROM problems_tags " +
					"where code in (SELECT code FROM attempted WHERE userid = ? and solved = true)) and userid != ?";
			pstmt = jd.conn1.prepareStatement(selectSql);
			
			pstmt.setInt(1,userid);
			pstmt.setInt(2,userid);
			System.out.println(pstmt);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int uid = rs.getInt("userid");
				if(weights.get(uid) != null){
					double newWeight = weights.get(uid) + c1;
					weights.put(uid, newWeight);
				}
				else{
					weights.put(uid, c1);
				}
			}
			jd.destroy();
			
			
			
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//arrayList.add("Server Down");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//arrayList.add("Server Down");
		}
		
		//Fetching the users based on the current followers
		try {
			JdbcSetup jd = null;
			jd = new JdbcSetup();
			PreparedStatement pstmt = null;
			String selectSql = "SELECT followed from followers where " +
					"follower in (SELECT followed FROM followers WHERE follower = ?) and followed != ?";
			pstmt = jd.conn1.prepareStatement(selectSql);
			pstmt.setInt(1,userid);
			pstmt.setInt(2,userid);
			System.out.println(pstmt);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int uid = rs.getInt("followed");
				if(weights.get(uid) != null){
					double newWeight = weights.get(uid) + c2;
					weights.put(uid, newWeight);
				}
				else{
					weights.put(uid, c2);
				}
			}
			jd.destroy();
			
			
			
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//arrayList.add("Server Down");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//arrayList.add("Server Down");
		}
		
		//Fetching the users based on interests
		try {
			JdbcSetup jd = null;
			jd = new JdbcSetup();
			PreparedStatement pstmt = null;
			String selectSql = "SELECT distinct userid FROM users_interests WHERE tagid in (SELECT tagid from " +
					"users_interests where userid = ?) and userid != ?";
			pstmt = jd.conn1.prepareStatement(selectSql);
			pstmt.setInt(1,userid);
			pstmt.setInt(2,userid);
			System.out.println(pstmt);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int uid = rs.getInt("userid");
				if(weights.get(uid) != null){
					double newWeight = weights.get(uid) + c3;
					weights.put(uid, newWeight);
				}
				else{
					weights.put(uid, c3);
				}
			}
			jd.destroy();
			
			
			
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//arrayList.add("Server Down");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//arrayList.add("Server Down");
		}
		
		
		
		//Fetching the users based on common problems solved
		try {
			JdbcSetup jd = null;
			jd = new JdbcSetup();
			PreparedStatement pstmt = null;
			String selectSql = "SELECT B.userid from " +
					"attempted as A inner join attempted as B on A.code = B.code and A.userid != B.userid " +
					"where A.userid = ?";
			pstmt = jd.conn1.prepareStatement(selectSql);
			pstmt.setInt(1,userid);
			System.out.println(pstmt);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int uid = rs.getInt("userid");
				if(weights.get(uid) != null){
					double newWeight = weights.get(uid) + c4;
					weights.put(uid, newWeight);
				}
				else{
					weights.put(uid, c4);
				}
			}
			jd.destroy();
			
			
			
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//arrayList.add("Server Down");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//arrayList.add("Server Down");
		}
		
		
		
		ValueComparator1 bvc =  new ValueComparator1(weights);
		
		
		TreeMap<Integer,Double> sorted_weights = new TreeMap<Integer,Double>(bvc);
		
		sorted_weights.putAll(weights);
		System.out.println(sorted_weights);
		Object[] uids = sorted_weights.keySet().toArray();
		int size = sorted_weights.size();
		for(int i = 0; i < 2*numResults && i < size; i++){
			arrayList.add((Integer) uids[i]);
		}
		
		return arrayList;
	}
	
	

}

class ValueComparator1 implements Comparator<Integer> {

    Map<Integer, Double> base;
    public ValueComparator1(Map<Integer, Double> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(Integer a, Integer b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}
