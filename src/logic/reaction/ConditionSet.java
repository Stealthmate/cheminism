package logic.reaction;

import java.util.ArrayList;

public class ConditionSet {
	
	public static final int NO_TEMP = -1;
	public static final int TEMP_ARBITRARY = 123456;
	
	public static final int PRESSURE_OFF = 0;
	public static final int PRESSURE_ON = 1;
	
	public static final String NO_CATALYST = "-1";
	
	public int t;
	public int p;
	public String cat;
	
	public ConditionSet() {
		t = TEMP_ARBITRARY;
		p = PRESSURE_OFF;
		cat = NO_CATALYST;
	}
	
	public ConditionSet(int t, int p, String cat) {
		this.t = t;
		this.p = p;
		this.cat = cat;
	}
	
}
