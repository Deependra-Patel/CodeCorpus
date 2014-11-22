package search;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;

import register.JdbcSetup;
 
public class autoComplete {
    private int totalHandles;
    private String data;
    public ArrayList<String> autoCompleteFunc(String option) {
    	ArrayList<String> handles = new ArrayList<String>();
    	
    	try {
    		JdbcSetup jd = null;
    		jd = new JdbcSetup();
    		PreparedStatement pstmt = null;
    		String selectSql;
    		String selectSql1 = "SELECT handle FROM users";
    		String selectSql2 = "SELECT (personal).name from users";
    		String selectSql3 = "SELECT name from problems";
    		String selectSql4 = "SELECT name from tags";
    		selectSql = selectSql1;
    		if(option.equals("handle")) selectSql = selectSql1;
    		else if(option.equals("nameUser")) selectSql = selectSql2;
    		else if(option.equals("problems")) selectSql = selectSql3;
    		else if(option.equals("tags")) selectSql = selectSql4;
    		
    		pstmt = jd.conn1.prepareStatement(selectSql);
    		//pstmt.setString(1,"%" + handle +"%");
    		ResultSet rs = pstmt.executeQuery();
    		System.out.println("executed");
    		while (rs.next()) {              
    		    handles.add(rs.getString(1));
    		    //System.out.println(rs.getString("handle"));
    		}
    		
    		jd.destroy();
    		
    	} catch (ServletException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		
    	}
         
        
        totalHandles = handles.size();
        //System.out.println(handles.get(0));
        return handles;
        
    }
    
    
    public static String toJavascriptArray(ArrayList<String> arr){
	    StringBuffer sb = new StringBuffer();
	    sb.append("[");
	    for(int i=0; i<arr.size(); i++){
	        sb.append("\"").append(arr.get(i)).append("\"");
	        if(i+1 < arr.size()){
	            sb.append(",");
	        }
	    }
	    sb.append("]");
	    return sb.toString();
	}
     
    
}