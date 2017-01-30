/**
 * Omada 20
 *
 * Sarlis Dimitris 03109078
 * Stathakopoulou Chrysa 03109065
 * Tzannetos Dimitris 03109010
 *
 */


package gov.nist.sip.proxy;

public abstract class Observer {
	private Proxy proxy;
	
	public Observer(Proxy proxy){
		this.setProxy(proxy);
	}
	
	public void update(ProxyCall call){
	}

	public Proxy getProxy() {
		return proxy;
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}
}
