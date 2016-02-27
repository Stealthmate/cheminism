package logic;

import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;

public class Substance {	

	public static ArrayList<Substance> substances = new ArrayList<>();

	private int ID;
	private String formula;
	private String fullname;
	private ArrayList<String> trivial_names;
	
	private boolean isOrganic;
	
	
	public Substance() {
		formula = "Null";
		fullname = "Null";
		trivial_names = new ArrayList<>();
		ID = -1;
		isOrganic = false;
	}
	
	Substance(int ID, String formula, String fullname, boolean isOrganic) {
		this.ID = ID; 
		this.formula = formula;
		this.fullname = fullname;
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
		return this.formula;
	}
	
	public String getFullName() {
		return this.fullname;
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
