package gui.mainscreen.searchbar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Scrollable;

import gui.mainscreen.SelectObserver;
import logic.ResourceLoader;
import logic.Substance;

public class SuggestionList extends JPanel {
	
	private static final int ENTRIES_PER_PAGE = 5;

	private static final int INDEX_FIRST_ENTRY = 1;
	
	private static final JPanel dummy = new JPanel();
	
	private ArrayList<SuggestionEntry> entries;
	private int n_entries = 0;
	
	private int current_page = -1;
	
	
	public SuggestionList() {
		super();
		this.setLayout(new GridBagLayout());
		
		//Compute height of single suggestion
		int height = (int) new SuggestionEntry(new Substance(), 1).getPreferredSize().getHeight();
		this.setPreferredSize(new Dimension(0, height * 6));//6 because of dummy tablet

		this.setBackground(Color.WHITE);
		this.invalidate();
		
		dummy.setOpaque(true);
		dummy.setBackground(Color.WHITE);
		
		entries = new ArrayList<>(5);
		n_entries = 0;
	}
	
	private void showPage(int pg) {
		this.removeAll();
		n_entries = 0;
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.weighty = 1.0;
		c.gridy = 100;
		c.fill = GridBagConstraints.VERTICAL;
		this.add(dummy, c);
		
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0.0;
		
		for(int i = 0; i<=ENTRIES_PER_PAGE-1 && pg * ENTRIES_PER_PAGE + i <=entries.size() - 1;i++) {

			this.add(entries.get(pg * ENTRIES_PER_PAGE + i), c);
			c.gridy++;
			n_entries++;
		}
		
		this.revalidate();
		this.repaint();
	}
	
	private void showNextPage() {
		
		if((current_page+1) * ENTRIES_PER_PAGE > entries.size()-1) {
			current_page = -1;
		}
		current_page += 1;
		showPage(current_page);
	}
	
	private void showPreviousPage() {
		if(current_page == 0) {
			current_page = (int) Math.ceil(entries.size() / ENTRIES_PER_PAGE) + 1;
		}
		current_page -= 1;		
		showPage(current_page);
	}
	
	/*package-private*/ void generateSuggestions(String query) {
		SearchManager.highlight(null);
		this.removeAll();
		this.revalidate();
		this.repaint();
		n_entries = 0;
		entries.clear();
		current_page = -1;
		
		if(query.length() == 0) return;
		
		ArrayList<Substance> substances = ResourceLoader.getSubstanceListMatching(query);

		for(int i=0; i<=substances.size()-1;i++) {
			SuggestionEntry entry = new SuggestionEntry(substances.get(i), i+1);
			entries.add(entry);
		}
		
		showNextPage();
	}
	
	/*private-package*/  String highlightMe(SuggestionEntry se) {
		SearchManager.highlight(se);
		return SearchManager.getHighlighted().getSubstance().getFormula();
	}
	
	/*package-private*/ String highlightNext() {
		
		//If nothing is highlighted and there are suggestions, highlight the top one
		if(SearchManager.getHighlighted() == null && n_entries > 0) {
			SearchManager.highlight((SuggestionEntry)this.getComponent(INDEX_FIRST_ENTRY));
			return SearchManager.getHighlighted().getSubstance().getFormula();
		}
	
		//Else cycle to the next one
		for(int i=0;i <= n_entries - 1; i++) {
			if(this.getComponent(i) == SearchManager.getHighlighted()) {
				if(i < n_entries - 1) {
					SearchManager.highlight((SuggestionEntry) this.getComponent(i+1));
					return SearchManager.getHighlighted().getSubstance().getFormula();
				}
				
				else {
					showNextPage();
					SearchManager.highlight((SuggestionEntry) this.getComponent(INDEX_FIRST_ENTRY));
					return SearchManager.getHighlighted().getSubstance().getFormula();
				}
			}
		}
		return "";
	}
	
	/*package-private*/ String highlightPrev() {
		
		//If nothing is highlighted and there are suggestions, select last one
		if(SearchManager.getHighlighted() == null && n_entries > 0) {
			SearchManager.highlight((SuggestionEntry)this.getComponent(
							n_entries-1));
			return SearchManager.getHighlighted().getSubstance().getFormula();
		}
		
		//Else cycle to previous one
		for(int i=0;i<=n_entries - 1;i++) {
			if(this.getComponent(i) == SearchManager.getHighlighted()) {
				if(i > INDEX_FIRST_ENTRY) {
					SearchManager.highlight((SuggestionEntry)this.getComponent(i-1));
					return SearchManager.getHighlighted().getSubstance().getFormula();
				}
				else if (i == INDEX_FIRST_ENTRY && this.getComponentCount() > 0) {
					showPreviousPage();
					SearchManager.highlight((SuggestionEntry)this.getComponent(n_entries - 1));
					return SearchManager.getHighlighted().getSubstance().getFormula();
				}
			}
		}
		
		return "";
	}

}
