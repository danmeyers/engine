package dmeyers.engine;

import java.util.ArrayList;
import java.util.List;

public class Output {

	private List<Connection> connections = new ArrayList<Connection>();
	
	public Output() {
	}
	
	public void connect(Connection c) {
		connections.add(c);
	}
	
	public void run() {
		for (Connection c : connections){
			c.run();
		}
	}
	
	public int totalConnections() {
		return connections.size();
	}

}
