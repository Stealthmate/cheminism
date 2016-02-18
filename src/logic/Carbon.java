package logic;

public class Carbon {
	
	public static class Atom {
		
		public int numberOfBonds;
		
		Carbon next;
		Carbon prev;
		
		Atom(){
			numberOfBonds = 0;
			next = null;
			prev = null;
		}
		
		public void connectNext(Carbon atom){
			this.next = atom;
		}
		
		public void connectPrev(Carbon atom){
			this.prev = atom;
		}
	}
	
	public static void print(){
		System.out.println("wer");
	}
	
}
