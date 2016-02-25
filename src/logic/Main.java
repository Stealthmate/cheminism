package logic;

public class Main {
	private static CarbonChain per;
	private static Carbon test;

	public static void main(String [ ] args){
		
		/*start = new Carbon();
		x = new Carbon();
		c = new Carbon();*/
		
		test = new Carbon();
		
		per = new CarbonChain(6);
		
		per.getCarbonOnPos(6).printBonds();
		
		per.extendCarbonChain(4);
		
		per.getCarbonOnPos(10).printBonds();
		
		/*per.addCarbonAtEnd();
		
		per.getCarbonOnPos(6).printBonds();
		per.getCarbonOnPos(7).printBonds();*/
		
		//per.scanChain();
		
		
		//per.setLenght(10);
		
		//per.chain[10].print('f');
		
		//c.print('d');
		
		//start.connectNext(x);
		//x.connectNext(c);
		
		//start.scanChain(start);
	}
}
