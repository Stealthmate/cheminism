package logic;

public class Carbon {
		
		public int numberOfBonds;
		
		Carbon next;
		Carbon prev;
		
		Carbon(){
			numberOfBonds = 0;
			System.out.println("int");
		}
		
		public void connectNext(Carbon atom){
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
				char s='d';
				x.print(s);
				x=x.next;
				s++;
			}
		}
	
	
	public static void print(char z){
		System.out.println(z);
	}
	
}
