package logic;

import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.ArrayList;

public class Substance {	

	public static ArrayList<Substance> substances = new ArrayList<>();

	private int ID;
	private String formula;
	private String fullname;
	private ArrayList<String> trivial_names;
	
	public Substance() {
		formula = "";
		fullname = "";
		trivial_names = new ArrayList<>();
		ID = -1;
	}
	
	Substance(String name, int ID) {
		this.formula = name;
		this.ID = ID; 
	}
	
	public String getFormula() {
		return this.formula;
	}
	
	public AttributedString getIndexedFormula() {
		AttributedString formattedname = new AttributedString(formula);
		for(int i=0;i<=formula.length()-1;i++) {
			if(formula.charAt(i) >= '0' && formula.charAt(i) <= '9') {
				formattedname.addAttribute(
						TextAttribute.SUPERSCRIPT, 
						TextAttribute.SUPERSCRIPT_SUB, 
						i,
						i+1);
			}
		}
		return formattedname;
	}
	
	public static Substance getSubstanceFromName(String name) {
		for (Substance s : substances) {
			if(s.getFormula().equals(name)) return s;
		}
		return null;
	}
}
