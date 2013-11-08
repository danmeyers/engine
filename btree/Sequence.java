package dmeyers.engine.btree;

import java.util.ListIterator;


public class Sequence extends Composite {

	public Sequence() {
	}
	
	BTNode lastRunning = null;
	

	@Override
	public Status update(float seconds) {
		if (!children.isEmpty()){
			ListIterator<BTNode> iterator; 
			if (lastRunning != null) {
				iterator = children.listIterator(children.indexOf(lastRunning));
			}
			else iterator = children.listIterator();
			
			while (iterator.hasNext()){
				BTNode next = iterator.next();

				Status currentStatus = next.update(seconds);
				
				if (currentStatus.equals(Status.FAIL)) {
					lastRunning = null;
					return Status.FAIL;
				}
				else if (currentStatus.equals(Status.RUNNING)) {
					if (lastRunning != null && !lastRunning.equals(next)) {
						lastRunning.reset();
					}
					lastRunning = next;
					return Status.RUNNING;
				}
			}
			
			lastRunning = null;
			return Status.SUCCESS;
		}
		return Status.FAIL;
	}

	@Override
	public void reset() {
		if (lastRunning != null) lastRunning.reset();
		lastRunning = null;
	}


}
