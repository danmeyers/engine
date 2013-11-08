package dmeyers.engine.btree;


 public class Selector extends Composite {

	public Selector() {

	}
	

	@Override
	public Status update(float seconds) {
		
		for (BTNode bt : children){
			Status currentStatus = bt.update(seconds);
			if (currentStatus.equals(Status.SUCCESS)) return Status.SUCCESS;
			else if (currentStatus.equals(Status.RUNNING)) {
				if (lastRunning != null && !lastRunning.equals(bt)) {
					lastRunning.reset();
				}
				lastRunning = bt;
				return Status.RUNNING;
			}
		}
		
		return Status.FAIL;
	}

	@Override
	public void reset() {
		if (lastRunning != null) lastRunning.reset();
	}
}
