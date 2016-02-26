package logic;

public class Bond {
	
	private static final int BOND_NONE   = 0;
	private static final int BOND_SINGLE = 1;
	private static final int BOND_DOUBLE = 2;
	private static final int BOND_TRIPLE = 3;
	
	int type;
	BondTypes Bonds; //type of bond
	Atom bondTo; //bond to Atom
	
	Bond(){
		type = BOND_NONE;
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
