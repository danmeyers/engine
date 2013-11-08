package dmeyers.engine.btree;


public interface BTNode {
	public Status update(float seconds);
	public void reset();
}
