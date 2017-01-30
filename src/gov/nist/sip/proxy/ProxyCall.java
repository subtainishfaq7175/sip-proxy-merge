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

public class ProxyCall {
	private String call_id;
	private String caller;
	private String callee;
	private Date t_start;
	private Date t_end;
	
	public ProxyCall(String call_id, String caller, String callee) {
		this.call_id = call_id;
		this.caller = caller;
		this.callee = callee;
	}

	public String getCaller() {
		return caller;
	}

	public String getCallee() {
		return callee;
	}

	
	public String getCall_id() {
		return call_id;
	}
	
	public void setCallee(String callee) {
		this.callee = callee;
	}

	public Date getT_start() {
		return t_start;
	}
	
	public void setT_start(Date t_start) {
		this.t_start = t_start;
	}
	
	public Date getT_end() {
		return t_end;
	}
	
	public void setT_end(Date t_end) {
		this.t_end = t_end;
	}

}

