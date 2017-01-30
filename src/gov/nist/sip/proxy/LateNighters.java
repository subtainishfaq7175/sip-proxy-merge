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

public class LateNighters extends Policy {
	
	public LateNighters() {
		super();
	}

	public double calculateCost(ProxyCall call){
		ResultSet rs = null;
		double costPerMin=0.0;
		double lateNightDiscount=0.0;
		double favoriteGroupDiscount=0.0;
		double totalDiscount=0.0;
		String caller = call.getCaller();
		String callee = call.getCallee();
		
		Date t_start = call.getT_start();
		Date t_end = call.getT_end();
		
		int h_start = call.getT_start().getHours();
		//int h_end = call.getT_end().getHours();
		
		long diffMinutes = (long) Math.ceil(TimeUnit.MILLISECONDS.toMinutes(t_end.getTime() - t_start.getTime())) + 1;
		
		try {
			stmt = con.createStatement();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		Query="SELECT * FROM POLICY WHERE id=3";

		try {
			rs = stmt.executeQuery(Query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if (rs.next()) {
				costPerMin=rs.getDouble("cost_per_min");
				lateNightDiscount=rs.getDouble("late_night_discount");
				favoriteGroupDiscount=rs.getDouble("favorite_group_discount");
				System.out.println(costPerMin + " " + lateNightDiscount + " " + favoriteGroupDiscount);
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
					totalDiscount= favoriteGroupDiscount;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("in LateNighters fav_dis:" + totalDiscount);
		
		System.out.println("Call starts: "+h_start);
		if(h_start>22 || h_start<5) {
			totalDiscount=totalDiscount+lateNightDiscount;
			//totalDiscount=Math.max(totalDiscount, lateNightDiscount);
		}
		
		System.out.println("in LateNighters late_dis:" + totalDiscount);
		
		return costPerMin*diffMinutes*(1.0-totalDiscount);
	}
}
