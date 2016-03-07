package gui.structuredrawer;

public class StructureModel {

	/*package-private*/ static class Carbon extends Atom {
		
		Bond upbond;
		Bond downbond;
		Bond nextbond;
		
		Atom upatom;
		Atom downatom;
		Carbon nextatom;
		
		/*package-private*/ Carbon() {
			super(Atom.C.name);
			
			upbond = null;
			downbond = null;
			nextbond = null;
			
			upatom = null;
			downatom = null;
			nextatom = null;
		}
		
		/*package-private*/ void setUp(Atom a, Bond b) {
			this.upbond = b;
			this.upatom = a;
		}
		
		/*package-private*/ void setDown(Atom a, Bond b) {
			this.downbond = b;
			this.downatom = a;
		}
		
		/*package-private*/ void setNext(Carbon c, Bond b) {
			this.nextbond = b;
			this.nextatom = c;
		}
		
	}
	
	Carbon root;
	
	public StructureModel() {
		root = new Carbon();
	}
	
	public void extend(Carbon c, Bond b) {
		
		Carbon i = root;
		while(i.nextatom!=null) i = i.nextatom;
		
		i.nextatom = c;
		i.nextbond = b;
	}
	
	public void discardFirst() {
		if(root != null) if(root.nextatom != null) root = root.nextatom;
	}
	
	public int getLength() {
		Carbon carbi = root;
		int len = 0;
		while(carbi!=null) {
			System.out.println(carbi.name);
			System.out.println(carbi.nextbond);
			carbi = carbi.nextatom;
			len++;
		}
		
		return len;
	}
}
