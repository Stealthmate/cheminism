package resources;

public class Substance {
	String name;
	int groupID;
	int ID;
	
	Substance() {
		name = "";
		groupID = -1;
		ID = -1;
	}
	
	Substance(String name, int ID, int groupID) {
		this.name = name;
		this.ID = ID; 
		this.groupID = groupID;
	}
	
	public String getName() {
		return this.name;
	}
}
