package dmeyers.engine;

import java.util.Hashtable;
import java.util.Map;

public class Connection {

	private Input target;
	private Map<String, String> args = new Hashtable<String,String>();
	
	public Connection(Input i, Map<String,String> args) {
		target = i;
		this.args = args;
	}

	public void run() {
		target.run(args);
	}
	
}
