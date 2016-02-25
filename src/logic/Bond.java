package logic;

public class Bond {
	
	BondTypes Bonds; //type of bond
	Atom bondTo; //bond to Atom
	
	Bond(){
		Bonds = BondTypes.None;
		bondTo = null;
	}
	
	void setBond(BondTypes x){
		Bonds = x;
	}
	
	BondTypes getBond(){
		return Bonds;
	}
	void bondTo(Atom x){
		bondTo = x;
	}
}
