/**
 * Omada 20
 *
 * Sarlis Dimitris 03109078
 * Stathakopoulou Chrysa 03109065
 * Tzannetos Dimitris 03109010
 *
 */


package gov.nist.sip.proxy;

import java.util.Date;

public class CallEnd extends Observer{
	
	public CallEnd(Proxy proxy) {
		super(proxy);
	}
	
	public void update(ProxyCall call){
		TimeServer timeserver = new TimeServer(call);
		Date date = timeserver.setEndTime();
		BillingServer billing = new BillingServer(call);
		billing.applyBillingPolicy(date);
	}
	
}
