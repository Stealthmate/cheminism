package logic;

public class CarbonAtom extends Atom{
	
	CarbonAtom(){
		super();
		for(int i = 0; i < 4; i++){
			bondNum.add(new Bond()); // initialise bonds
		}//set bonds to None Type
		bondNum.get(0).setBond(BondTypes.None);
		bondNum.get(1).setBond(BondTypes.None);
		bondNum.get(2).setBond(BondTypes.None);
		bondNum.get(3).setBond(BondTypes.None);
	}
	
	void add(Atom Additive){
		
	}
	
	@Override
	void printBonds(){
		
		super.printBonds();
		System.out.println("btw milen e pedal");
	}
		/*public int numberOfBonds;
		
		Carbon(){
			numberOfBonds = 0;
			System.out.println("int");
		}
		
		/*public void connectNext(Carbon atom){
			this.next = atom;
			numberOfBonds++;
			if(numberOfBonds>4)
			{
				return;
			}
		}
		
		public void connectPrev(Carbon atom){
			this.prev = atom;
			numberOfBonds++;
			if(numberOfBonds>4)
			{
				return;
			}
		}
		
		public void scanChain(Carbon Start)
		{
			Carbon x = new Carbon();
			x = Start;
			while(x!=null)
			{
				//char s='d';
				//x.print();
				x=x.next;
				//s++;
			}
		}
	*/
	
	
}
