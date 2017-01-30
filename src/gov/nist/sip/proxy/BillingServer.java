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

public class BillingServer extends DatabaseServer {
	private ProxyCall call;
	private Policy policy;
	
	public BillingServer(ProxyCall call) {
		super();
		this.call = call;
		
		ResultSet rs = null;
		int policy_id = 0;
		
		try {
			stmt = con.createStatement();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		Query="SELECT * FROM USERS WHERE username='" + call.getCaller() + "'";

		try {
			rs = stmt.executeQuery(Query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			if (rs.next()) {
				policy_id = rs.getInt("policy_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Query="SELECT * FROM POLICY WHERE id=" + policy_id;

		try {
			rs = stmt.executeQuery(Query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			if (rs.next()) {
				String className = rs.getString("name").replaceAll("\\s","");
				System.out.println("ClassName: gov.nist.sip.proxy." + className);
				attachPolicy((Policy) Class.forName("gov.nist.sip.proxy." + className).newInstance());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void attachPolicy(Policy p) {
		if (this.policy != null)
			removePolicy();
		this.policy = p;
	}
	
	public void removePolicy() {
		this.policy = null;
	}
	
	public void applyBillingPolicy(Date date) {
		ResultSet rs = null;
		double total_cost = 0.0;
		double call_cost = 0.0;
		String caller = call.getCaller();
		
		try {
			stmt = con.createStatement();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		Query="SELECT * FROM USERS WHERE username='" + caller + "'";

		try {
			rs = stmt.executeQuery(Query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			if (rs.next()) {
				call_cost = policy.calculateCost(call);
				total_cost = call_cost + rs.getDouble("total_cost");
				System.out.println("Total cost for the call = " + policy.calculateCost(call));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Query="UPDATE USERS SET total_cost=" + total_cost + " WHERE username='" + caller + "'";
		
		try {
			stmt.executeUpdate(Query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Query="UPDATE CALLS SET time_end='" + call.getT_end().toString() + "', cost=" + call_cost +
			  " WHERE id='" + call.getCall_id() + "'";
		
		try {
			stmt.executeUpdate(Query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
