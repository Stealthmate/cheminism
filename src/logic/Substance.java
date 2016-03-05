package logic;

import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;

public class Substance {	

	public static final String STATE_ARBITRARY = "";
	public static final String STATE_AQUEOUS = "aq";
	public static final String STATE_SOLID = "s";
	public static final String STATE_CONCENTRATED = "c";
	public static final String STATE_DILUTED = "d";
	
	public static ArrayList<Substance> substances = new ArrayList<>();
	
	private int ID;
	private String formula;
	private String fullname;
	private String state;
	private ArrayList<String> trivial_names;
	private boolean isOrganic;
	
	
	public Substance() {
		ID = -1;
		formula = "";
		fullname = "";
		state = STATE_ARBITRARY;
		trivial_names = new ArrayList<>();
		isOrganic = false;
	}
	
	public Substance(
			int ID, 
			String formula, 
			String fullname, 
			String state, 
			ArrayList<String> trivial_names, 
			boolean isOrganic) {
		this.ID = ID; 
		this.formula = formula;
		this.fullname = fullname;
		this.state = state;
		if(trivial_names != null) this.trivial_names = trivial_names;
		else this.trivial_names = new ArrayList<>();
		this.isOrganic = isOrganic;
	}
	
	public void addTrivialName(String name) {
		trivial_names.add(name);
	}
	
	public void addTrivialNames(List<String> names) {
		trivial_names.addAll(names);
	}
	
	public boolean isOrganic() {
		return isOrganic;
	}
	
	public String getFormula() {
		return this.state + " " + this.formula;
	}
	
	public String getFullName() {
		return this.fullname;
	}
	
	public AttributedString getIndexedFormula() {
		
		String fullformula = state + " " + formula;
		
		AttributedString formattedname = new AttributedString(fullformula);
		
		
		for(int i=0;i<=fullformula.length()-1;i++) {
			if(fullformula.charAt(i) >= '0' && fullformula.charAt(i) <= '9') {
				formattedname.addAttribute(
						TextAttribute.SUPERSCRIPT, 
						TextAttribute.SUPERSCRIPT_SUB, 
						i,
						i+1);
			}
		}
		
		if(!state.equals(STATE_ARBITRARY)) {
			formattedname.addAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE, 0, state.length());
		}
		
		return formattedname;
	}
	
	public String getState() {
		return this.state;
	}
	
	public String getTrivialNames() {
		String result = "";
		for(String s : trivial_names)
			result += s + ", ";
		if(result.contains(", ")) result = result.substring(0,  result.indexOf(", "));
		return result;
	}
	
	public static Substance querySubstance(String name) {
		for (Substance s : substances) {
			if(s.getFormula().equals(name)) return s;
		}
		return null;
	}
}
