/**
 * Omada 20
 *
 * Sarlis Dimitris 03109078
 * Stathakopoulou Chrysa 03109065
 * Tzannetos Dimitris 03109010
 *
 */


package gov.nist.sip.proxy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FriendsAndFamily extends Policy {
	
	public FriendsAndFamily() {
		super();
	}

	public double calculateCost(ProxyCall call){
		ResultSet rs = null;
		double costPerMin=0.0;
		double favoriteGroupDiscount=0.0;
		double totalDiscount=1.0;
		String caller = call.getCaller();
		String callee = call.getCallee();
		
		Date t_start = call.getT_start();
		Date t_end = call.getT_end();
		
		
		long diffMinutes = (long) Math.ceil(TimeUnit.MILLISECONDS.toMinutes(t_end.getTime() - t_start.getTime())) + 1;
		
		try {
			stmt = con.createStatement();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		Query="SELECT * FROM POLICY WHERE id=4";

		try {
			rs = stmt.executeQuery(Query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if (rs.next()) {
				costPerMin=rs.getDouble("cost_per_min");
				favoriteGroupDiscount=rs.getDouble("favorite_group_discount");
				System.out.println(costPerMin + " " + favoriteGroupDiscount + " " + favoriteGroupDiscount);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Query="SELECT * FROM favorites WHERE username='" + caller + "' AND faved='" + callee + "'";

		try {
			rs = stmt.executeQuery(Query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if (rs.next()) {
					totalDiscount=totalDiscount*(1 - favoriteGroupDiscount);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("in FriendsAndFamily total_dis:" + totalDiscount);
		
		return costPerMin*diffMinutes*totalDiscount;
	}
}
