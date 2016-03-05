package logic.reaction;

import java.util.ArrayList;

public class ConditionSet {
	
	public static final int NO_TEMPERATURE = -1;
	public static final int TEMPERATURE_ARBITRARY = 123456;
	
	public static final String NO_CATALYST = "NONE";
	
	public int t;
	public boolean p;
	public String cat;
	
	public ConditionSet() {
		t = TEMPERATURE_ARBITRARY;
		p = false;
		cat = NO_CATALYST;
	}
	
	public ConditionSet(int t, boolean p, String cat) {
		this.t = t;
		this.p = p;
		this.cat = cat;
	}
	
	@Override
	public String toString() {
		String res = "";
		
		res += "Temperature: " + t + "\n";
		res += "Pressure: " + p + "\n";
		res += "Catalyst: " + cat + "\n";
		
		return res;
	}
	
}
