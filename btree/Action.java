package dmeyers.engine.btree;


abstract public class Action implements BTNode {
	
	boolean isRunning;
	float totalSeconds;
	float secondsLeft;

	public Action() {
	}
	
	public Action(float timeLimit){
		totalSeconds = timeLimit;
		secondsLeft = timeLimit;
	}

	@Override
	public Status update(float seconds) {
		if (secondsLeft > 0f) secondsLeft -= seconds;
		else secondsLeft = totalSeconds;
		
		if (isRunning) return Status.RUNNING;
		return Status.SUCCESS;
	}

	@Override
	public void reset() {
		isRunning = false;
	}
	
	public boolean isRunning(){
		return isRunning;
	}

}
