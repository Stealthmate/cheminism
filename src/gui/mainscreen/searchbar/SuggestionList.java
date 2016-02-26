package gui.mainscreen.searchbar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Scrollable;

import logic.ResourceLoader;
import logic.Substance;

public class SuggestionList extends JPanel implements Scrollable {
	
	private static final int ENTRIES_PER_PAGE = 5;
	
	private static SuggestionEntry now_highlighted;
	
	private static void setHighlighted(SuggestionEntry se) {
		if(now_highlighted != null)
			now_highlighted.unhighlight();

		now_highlighted = se;
		if(se != null) 
			se.highlight();
	}

	private ArrayList<SuggestionEntry> entries;
	private int current_page = -1;
	
	public SuggestionList() {
		super();
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.RED);
		this.setOpaque(true);
		this.invalidate();
		entries = new ArrayList<>(30);
	}
	
	private void showPage(int pg) {
		this.removeAll();
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1.0;
		c.gridy = 100;
		c.fill = GridBagConstraints.VERTICAL;
		
		JPanel dummy = new JPanel();
		dummy.setOpaque(false);
		//this.add(dummy, c);
		
		c.gridx = 0;
		c.gridy = -1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0.0;
		for(int i = 0; i<=ENTRIES_PER_PAGE-1 && pg * ENTRIES_PER_PAGE + i <=entries.size()-1;i++) {
			c.gridy++;
			this.add(entries.get(pg * ENTRIES_PER_PAGE + i), c);
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
			current_page = (int) Math.ceil(entries.size() / ENTRIES_PER_PAGE);
		}
		current_page -= 1;
		showPage(current_page);
	}
	
	/*package-private*/ void generateSuggestions(String query) {
		setHighlighted(null);
		this.removeAll();
		this.revalidate();
		this.repaint();
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
	
	/*private-package*/  void highlightMe(SuggestionEntry se) {
		setHighlighted(se);
		((SearchPanel)getParent().getParent().getParent())
		.updateHighlight(se.getSubstance().getFormula());
	}
	
	/*package-private*/ String highlightNext() {
		int n = this.getComponentCount();
		
		//If nothing is highlighted and there are suggestions, highlight the top one
		if(now_highlighted == null && n > 0) {
			setHighlighted((SuggestionEntry)this.getComponent(0));
			return now_highlighted.getSubstance().getFormula();
		}
	
		//Else cycle to the next one
		for(int i=0;i <= n - 1; i++) {
			if(this.getComponent(i) == now_highlighted) {
				if(i < n - 1) {
					setHighlighted((SuggestionEntry) this.getComponent(i+1));
					return now_highlighted.getSubstance().getFormula();
				}
				
				else {
					showNextPage();
					setHighlighted((SuggestionEntry) this.getComponent(0));
					return now_highlighted.getSubstance().getFormula();
				}
			}
		}
		return "";
	}
	
	/*package-private*/ String highlightPrevious() {
		
		int n = this.getComponentCount();
		
		//If nothing is highlighted and there are suggestions, select last one
		if(now_highlighted == null && n > 0) {
			setHighlighted((SuggestionEntry)this.getComponent(
							n-1));
			return now_highlighted.getSubstance().getFormula();
		}
		
		//Else cycle to previous one
		for(int i=0;i<=n - 1; i++) {
			if(this.getComponent(i) == now_highlighted) {
				if(i > 0) {
					setHighlighted((SuggestionEntry)this.getComponent(i-1));
					return now_highlighted.getSubstance().getFormula();
				}
				else if (i == 0 && this.getComponentCount() > 0) {
					showPreviousPage();
					setHighlighted((SuggestionEntry)this.getComponent(this.getComponentCount()-1));
					return now_highlighted.getSubstance().getFormula();
				}
			}
		}
		
		return "";
	}
	
	/*package-private*/ String selectHighlighted() {
		
		this.removeAll();
		this.revalidate();
		this.repaint();
		
		//Value of highlighted suggestion (substance formula)
		String value = null;
		
		if(now_highlighted != null) {
			now_highlighted.unhighlight();
			value = now_highlighted.getSubstance().getFormula();
		}
		
		return value;
	}
	
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return new Dimension(getPreferredSize().width, 50);
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle arg0, int arg1, int arg2) {
		return 0;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return true;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
