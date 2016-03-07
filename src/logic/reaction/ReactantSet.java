package logic.reaction;

import java.util.ArrayList;

import logic.Substance;

public class ReactantSet {
	
	private ArrayList<Substance> reactants;
	private ArrayList<Integer> mols;
	
	public ReactantSet(ArrayList<Substance> reactants, ArrayList<Integer> mols) {
		
		this.reactants = reactants;
		this.mols = mols;
		this.sort();
	}
	
	private void swapReactants(int i, int j) {

		Substance s = reactants.get(i);
		reactants.set(i, reactants.get(j));
		reactants.set(j, s);
		
		int m = mols.get(i);
		mols.set(i, mols.get(j));
		mols.set(j, m);
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
		
		boolean match_mol = mols.get(i).equals(rs.mols.get(i));
		
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
			res += "    " + s.toString() + "\n";
		}
		
		return res;
	}
}
