package gui.mainscreen.searchbar;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Scrollable;

import logic.ResourceLoader;
import logic.Substance;

public class SearchView extends JPanel implements Scrollable {
	
	private static SuggestionEntry now_highlighted;
	
	private static void setHighlighted(SuggestionEntry se) {
		if(now_highlighted != null)
			now_highlighted.unhighlight();

		now_highlighted = se;
		if(se != null) 
			se.highlight();
	}
	
	
	public SearchView() {
		super();
		this.setLayout(new GridBagLayout());
	}
	
	/*package-private*/ void generateSuggestions(String query) {
		setHighlighted(null);
		this.removeAll();
		this.revalidate();
		this.repaint();
		
		if(query.length() == 0) return;
		
		ArrayList<Substance> substances = ResourceLoader.getSubstanceListMatching(query);

		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1.0;
		c.gridy = 100;
		c.fill = GridBagConstraints.VERTICAL;
		
		JPanel dummy = new JPanel();
		dummy.setOpaque(false);
		this.add(dummy, c);
		
		c.gridx = 0;
		c.gridy = -1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0.0;
		
		for(Substance s : substances) {
			c.gridy++;
			SuggestionEntry entry = new SuggestionEntry(s.getName());
			this.add(entry, c);
			this.revalidate();
		}
	}
	
	/*package-private*/ void highlightNext() {
		int n = this.getComponentCount();
		
		if(now_highlighted == null) {
			if(n > 0)
				setHighlighted((SuggestionEntry)this.getComponent(1));
			return;
		}
	
		for(int i=1;i<=n - 1; i++) {
			if(this.getComponent(i) == now_highlighted) {
				if(i < n - 1) {
					setHighlighted((SuggestionEntry) this.getComponent(i+1));
					return;
				}
				
				else {
					setHighlighted((SuggestionEntry) this.getComponent(1));
					return;
				}
			}
		}
	}
	
	/*package-private*/ void highlightPrevious() {
		
		int n = this.getComponentCount();
		
		if(now_highlighted == null && n > 0) {
			setHighlighted((SuggestionEntry)this.getComponent(
							n-1));
			return;
		}
		
		for(int i=1;i<=n - 1; i++) {
			if(this.getComponent(i) == now_highlighted) {
				if(i > 1) {
					setHighlighted((SuggestionEntry)this.getComponent(i-1));
				return;
				}
				else if (i == 1 && this.getComponentCount() > 0) {
					setHighlighted((SuggestionEntry)this.getComponent(n-1));
					return;
				}
			}
		}
	}
	
	/*package-private*/ String selectHighlighted() {
		
		String result = null;
		
		if(now_highlighted != null) {
			now_highlighted.select();
			result = now_highlighted.getText();
		}
		
		this.removeAll();
		this.revalidate();
		this.repaint();
		return result;
	}
	
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
