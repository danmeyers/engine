package dmeyers.engine.btree;

import java.util.LinkedList;
import java.util.List;


public class Composite implements BTNode {

	List<BTNode> children = new LinkedList<BTNode>();
	BTNode lastRunning;

	@Override
	public Status update(float seconds) {
		return Status.FAIL;
	}

	@Override
	public void reset() {
	}
	
	public void addToList(BTNode bt){
		children.add(bt);
	}


}
