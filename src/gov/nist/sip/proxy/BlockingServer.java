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

public class BlockingServer extends DatabaseServer{
	private ProxyCall call;

	public BlockingServer(ProxyCall call) {
		super();
		this.call = call;
	}
	
	public boolean checkIfBlocked(){
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		Query="SELECT * FROM BLOCKING_LIST WHERE blocker='" + call.getCallee() + "' AND blockee='" + call.getCaller() + "'";

		try {
			rs = stmt.executeQuery(Query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
