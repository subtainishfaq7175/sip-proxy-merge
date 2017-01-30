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

public class FreeWeekends extends Policy {
	
	public FreeWeekends() {
		super();
	}

	public double calculateCost(ProxyCall call){
		ResultSet rs = null;
		double costPerMin=0.0;
		double weekendDiscount=0.0;
		double totalDiscount=1.0;
		
		Date t_start = call.getT_start();
		Date t_end = call.getT_end();
		
		int d_start = call.getT_start().getDay();
		//int d_end = call.getT_end().getDay();
		
		long diffMinutes = (long) Math.ceil(TimeUnit.MILLISECONDS.toMinutes(t_end.getTime() - t_start.getTime())) + 1;
		
		try {
			stmt = con.createStatement();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		Query="SELECT * FROM POLICY WHERE id=2";

		try {
			rs = stmt.executeQuery(Query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if (rs.next()) {
				costPerMin=rs.getDouble("cost_per_min");
				weekendDiscount=rs.getDouble("weekend_discount");
				System.out.println(costPerMin + " " + weekendDiscount );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		System.out.println("Call starts: "+d_start);
		if(d_start==6 || d_start==0) { //call started on Saturday or on Sunday
			totalDiscount=totalDiscount*(1 - weekendDiscount);
		}
		
		System.out.println("in FreeWeekends total_dis:" + totalDiscount);
		
		return costPerMin*diffMinutes*totalDiscount;
	}
}
