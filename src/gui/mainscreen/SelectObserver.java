package gui.mainscreen;

import java.util.ArrayList;

import gui.mainscreen.reactionpanel.ReactantLabel;
import gui.mainscreen.searchbar.SuggestionEntry;
import logic.Substance;

public class SelectObserver {

	public static final class Lock {
		private Lock() {}
	}
	
	private static Lock l = new Lock();
	
	private static ReactantLabel selected_reactant = null;
	
	private static ArrayList<ReactantLabel> reactants = new ArrayList<>(3);
	
	public static void registerReactant(ReactantLabel rl) {
		reactants.add(rl);
	}
	
	public static void setActiveReactantSubstance(Substance s) {
		if(selected_reactant != null) {
			selected_reactant.setSubstance(s);
		}
	}
	
	private static void select(ReactantLabel rl) {
		if(selected_reactant != null) {
			selected_reactant.deselect(l);
		}
		
		selected_reactant = rl;
		if(selected_reactant != null) selected_reactant.select(l);
	}
	
	public static void selectReactant(ReactantLabel rl) {
		
		if(rl == null) {
			select(null);
			return;
		}
		
		boolean found = false;
		
		for(ReactantLabel react : reactants) {
			if(react.equals(rl)) {
				found = true;
			}
		}
		
		if(!found) {
			System.out.println("Invalid reactant");
			return;
		}
		
		select(rl);
		
	}
	
	public static void selectReactant(int i) {
		
		if(i < 0 || i > reactants.size() - 1) {
			System.out.println("Invalid index: " + i);
			return;
		}
		
		select(reactants.get(i));
	}
	
}
