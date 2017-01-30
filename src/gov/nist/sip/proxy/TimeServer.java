/**
 * Omada 20
 *
 * Sarlis Dimitris 03109078
 * Stathakopoulou Chrysa 03109065
 * Tzannetos Dimitris 03109010
 *
 */


package gov.nist.sip.proxy;

import java.sql.*;
import java.util.Date;

public class TimeServer extends DatabaseServer{
	private ProxyCall call;
	
	public TimeServer(ProxyCall call) {
		super();
		this.call = call;
	}
	
	public void setStartTime(){
		
		try {
			stmt = con.createStatement();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		Date date = new Date();
		call.setT_start(date);
		
		Query="INSERT INTO CALLS VALUES ('" + call.getCall_id() + "', '" + call.getCaller() + "', '" + call.getCallee() + "', '" + date.toString() + "', NULL, 0)";
		
		try {
			stmt.executeUpdate(Query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Date setEndTime(){
		Date date = new Date();
		call.setT_end(date);
		return date;
	}
}
