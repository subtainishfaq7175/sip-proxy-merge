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

public class Default extends Policy {
	
	public Default() {
		super();
	}

	public double calculateCost(ProxyCall call){
		Date t_start = call.getT_start();
		Date t_end = call.getT_end();
		ResultSet rs = null;
		double costPerMin=0.0;
		long diffMinutes = (long) Math.ceil(TimeUnit.MILLISECONDS.toMinutes(t_end.getTime() - t_start.getTime())) + 1;
	
		try {
			stmt = con.createStatement();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		Query="SELECT * FROM POLICY WHERE id=1";

		try {
			rs = stmt.executeQuery(Query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while (rs.next()) {
				costPerMin=rs.getDouble("cost_per_min");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return costPerMin * diffMinutes;
	}
}
