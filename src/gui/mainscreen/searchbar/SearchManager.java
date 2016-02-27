package gui.mainscreen.searchbar;

import javax.swing.JTextField;

import gui.mainscreen.SelectObserver;
import logic.Substance;

public class SearchManager {

	private static SearchPanel searchpanel;
	
	private static SuggestionEntry highlighted_entry;
	
	/*package-private*/ static void registerSearchPanel(SearchPanel sp) {
		if(sp == null) {
			System.out.println("Invalid suggestion list");
			return;
		}
		
		searchpanel = sp;
	}
	
	/*package-private*/ static SuggestionEntry getHighlighted() {
		return highlighted_entry;
	}
	
	/*package-private*/ static void highlightNext() {
		searchpanel.highlightNext();
	}
	
	/*package-private*/ static void highlightPrev() {
		searchpanel.highlightPrev();
	}
	
	/*package-private*/ static void highlight(SuggestionEntry se) {
		if(highlighted_entry != null)
			highlighted_entry.unhighlight();

		highlighted_entry = se;
		if(highlighted_entry != null) {
			
			highlighted_entry.highlight();

			searchpanel.updateText(highlighted_entry.getSubstance().getFormula());
		}
	}
	
	/*package-private*/ static void executeQuery(String query) {
		
		searchpanel.selectHighlighted();
		
		Substance s = Substance.querySubstance(query);
		
		if(s!=null) {
			SelectObserver.setActiveReactantSubstance(s);
		}
	}
	
}
