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
import java.util.HashSet;

public class ForwardingServer extends DatabaseServer{
	private ProxyCall call;
	private String forwarder;

	public ForwardingServer(ProxyCall call) {
		super();
		this.call = call;
		this.forwarder = call.getCallee();
	}
	
	public String checkIfForwarded(){
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		Query="SELECT * FROM FORWARDING_LIST WHERE forwarder='" + forwarder + "'";

		try {
			rs = stmt.executeQuery(Query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while (rs.next()) {
				return rs.getString("forwardee");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public String findFinalCallee(){
		String toUser = checkIfForwarded();
		HashSet<String> fList = new HashSet<String>();
		fList.add(forwarder);
		forwarder = toUser;
		
		while (toUser != "") {
			if (!fList.contains(toUser)) {
				call.setCallee(toUser);
				BlockingServer blockingServer = new BlockingServer(call);
				if (blockingServer.checkIfBlocked()) {
					System.out.println("In findFinalCallee, Caller: " + call.getCaller() + " is blocked from callee: " + toUser);
					return null;
				}
				forwarder = toUser;
				fList.add(forwarder);
				toUser = checkIfForwarded();
				
			}
			else {
				return null;
			}
		}
		return forwarder;
	}
}
