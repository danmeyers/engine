package dmeyers.engine;

import java.util.Map;


public abstract class Relay extends Entity {

	Input onEnable = new Input(){

		@Override
		public void run(Map<String, String> args) {
			doEnable();
		}
		
	};
	
	
	Input onDisable = new Input(){

		@Override
		public void run(Map<String, String> args) {
			toggle = false;
		}
		
	};
	
	protected boolean toggle;
	
	public Relay() {
		inputs.put("onEnable", onEnable);
		inputs.put("onDisable", onDisable);
		
	}

	@Override
	public void onTick(long nanos){}

	public void doEnable() {
		toggle = true;
	}
	
	public void doDisable() {
		toggle = false;
	}
	
	public void toggleEnabled() {
		toggle = !toggle;
	}

}