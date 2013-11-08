package dmeyers.engine.geom;


public class Range {

	Float min = null;
	Float max = null;
	
	public Range(float min, float max) {
		this.min = min;
		this.max = max;
	}
	
	public Range() {
	}

	public void setMinMax(float m){
		if (min == null || m < min) min = m;
		if (max == null || m > max) max = m;
	}
	
	public void setMinMax(float trymin, float trymax){
		if (min == null || trymin < min) min = trymin;
		if (max == null || trymax > max) max = trymax;
	}
	
	public float getMin(){
		return min;
	}
	
	public float getMax(){
		return max;
	}
	
	public boolean minLessThanMax(Range r){
		if (this.min <= r.getMax()) return true;
		return false;
	}
	
	public boolean overlap(Range r){
		if (this.minLessThanMax(r) && r.minLessThanMax(this)) return true;
		return false;
	}
}
