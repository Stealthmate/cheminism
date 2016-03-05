package logic.reaction;

import java.util.ArrayList;

import logic.Substance;

public class ReactantSet {
	
	private ArrayList<Substance> reactants;
	
	public ReactantSet(ArrayList<Substance> reactants) {
		
		this.reactants = reactants;
		this.sort();
	}
	
	private void swapReactants(int i, int j) {

		Substance s = reactants.get(0);
		
		s = reactants.get(i);
		reactants.set(i, reactants.get(j));
		reactants.set(j, s);
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
		
		return match_subst;
		
	}
	
	public boolean matches(ReactantSet rs) {
		
		if(this.reactants.size() != rs.reactants.size()) return false;
		
		rs.sort();
		
		for(int i=0;i<=reactants.size() - 1; i++) {
			if(!matchesReactant(i, rs)) return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		String res = "";
		
		res+= "Reactants:\n";
		for(Substance s : reactants) {
			res += "    " + s.toString();
		}
		
		return res;
	}
}
