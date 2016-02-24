package logic;

public enum ChemicalElement {

	H  ("H" , "Hydrogenium", 1, 1, 2.100f),
	Li ("Li", "Lithium"    , 1, 2, 0.980f),
	Na ("Na", "Natrium"    , 1, 3, 0.930f),
	K  ("K" , "Kalium"     , 1, 4, 0.820f),
	Rb ("Rb", "Rubidium"   , 1, 5, 0.820f);
	
	;
	
	private final String symbol;
	private final String name;
	private final int group;
	private final int period;
	private final float electronegativity;

	private ChemicalElement(
			String symbol, 
			String name, 
			int group, 
			int period, 
			float electronegativity) {
		this.symbol = symbol;
		this.name = name;
		this.group = group;
		this.period = period;
		this.electronegativity = electronegativity;
	}
	
}
