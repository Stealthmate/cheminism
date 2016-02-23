package resources;

public class Substance {
	int groupID;
	int ID;
	
	Substance() {
		groupID = -1;
		ID = -1;
	}
	
	Substance(int ID, int groupID) {
		this.ID = ID; 
		this.groupID = groupID;
	}
}
