package logic.reaction;

import java.util.ArrayList;

import logic.Substance;

public class ReactantSet {
	
	public static enum ReactantFlags {
		AQ(),
		S();
	}
	
	private ArrayList<Substance> reactants;
	private ArrayList<ReactantFlags> flags;
	
	public ReactantSet(ArrayList<Substance> reactants, ArrayList<ReactantFlags> flags) {
		
		this.reactants = reactants;
		if(flags != null) this.flags = flags;
		else flags = new ArrayList<>(reactants.size());
		this.sort();
	}
	
	private void swapReactants(int i, int j) {

		Substance s = reactants.get(0);
		ReactantFlags rf = flags.get(0);
		
		s = reactants.get(i);
		reactants.set(i, reactants.get(j));
		reactants.set(j, s);
		
		rf = flags.get(i);
		flags.set(i, flags.get(j));
		flags.set(j, rf);
	}
	
	public void sort() {
		
		int l = reactants.size();
		for(int i=0;i<=l-1;i++) {
			for(int j=i+1;j<=l-1;j++) {
				if(reactants.get(i).getFullName().compareTo(reactants.get(j).getFullName()) > 0) {
					swapReactants(i, j);
				}
				
			}
		}
	}
	
	private boolean matchesReactant(int i, ReactantSet rs) {
		
		boolean match_subst = reactants.get(i).equals(rs.reactants.get(i));
		boolean match_flags = flags.get(i).equals(rs.flags.get(i));
		
		return match_subst && match_flags;
		
	}
	
	public boolean matches(ReactantSet rs) {
		
		if(this.reactants.size() != rs.reactants.size()) return false;
		
		rs.sort();
		
		for(int i=0;i<=reactants.size() - 1; i++) {
			if(!matchesReactant(i, rs)) return false;
		}
		
		return true;
	}
}
