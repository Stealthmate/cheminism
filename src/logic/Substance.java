package logic;

import java.util.ArrayList;

public class Substance {
	

	public static ArrayList<Substance> substances = new ArrayList<>();
	
	String name;
	int groupID;
	int ID;
	
	Substance() {
		name = "";
		groupID = -1;
		ID = -1;
	}
	
	Substance(String name, int ID) {
		this.name = name;
		this.ID = ID; 
		this.groupID = groupID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public static Substance getSubstanceFromName(String name) {
		System.out.println(" " + name);
		for (Substance s : substances) {
			if(s.getName().equals(name)) return s;
		}
		return null;
	}
}
