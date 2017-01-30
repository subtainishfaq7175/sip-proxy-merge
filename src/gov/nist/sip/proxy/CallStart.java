/**
 * Omada 20
 *
 * Sarlis Dimitris 03109078
 * Stathakopoulou Chrysa 03109065
 * Tzannetos Dimitris 03109010
 *
 */

package gov.nist.sip.proxy;

public class CallStart extends Observer{
	
	public CallStart(Proxy proxy) {
		super(proxy);
	}

	public void update(ProxyCall call){
		TimeServer timeserver = new TimeServer(call);
		timeserver.setStartTime();
	}
}
