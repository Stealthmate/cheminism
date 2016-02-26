package gui.mainscreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import gui.mainscreen.reactionpanel.ReactionPanel;
import gui.mainscreen.searchbar.SearchPanel;

public class GlobalPanel extends JPanel {

	private static final long serialVersionUID = -2037661004330652608L;
	
	private SearchPanel pnlSearch;
	private SubstanceInfoPanel pnlSubstanceInfo;

	private JPanel pnlAction;
	private JPanel pnlViewCompoundBar;
	private ReactionPanel pnlEquation;

	private void updatePreferredSize(int width, int height) {
		pnlSearch.setPreferredSize(
				new Dimension(SearchPanel.SEEK_BAR_WIDTH, height));
		pnlViewCompoundBar.setPreferredSize(new Dimension(width, height/4));
	}
	
	public void resizeLayout(Dimension d) {
		pnlEquation.resizeLayout(new Dimension(
				(int) (pnlEquation.getBounds().getWidth()), 
				(int) (pnlEquation.getBounds().getHeight())));
	}
	
	public GlobalPanel(int width, int height) {
		
		this.addComponentListener(new ComponentAdapter() {
		
			@Override
			public void componentResized(ComponentEvent evt) {
				Component c = (Component) evt.getSource();
				invalidate();
				updatePreferredSize(c.getSize().width, c.getSize().height);
				validate();
			}
			
		});
		
		this.setLayout(new BorderLayout());
		
		pnlSearch = new SearchPanel(width, height);
		
		pnlAction = new JPanel();
		pnlAction.setLayout(new BorderLayout());
		
		pnlViewCompoundBar = new JPanel();
		pnlViewCompoundBar.setOpaque(true);
		pnlViewCompoundBar.setBackground(Color.BLUE);
		pnlViewCompoundBar.setPreferredSize(new Dimension(width, height/4));

		pnlEquation = new ReactionPanel(4*width/5, 3*height/4);
		
		pnlAction.add(pnlViewCompoundBar, BorderLayout.NORTH);
		pnlAction.add(pnlEquation, BorderLayout.CENTER);
		pnlAction.add(pnlEquation, BorderLayout.CENTER);
		JPanel leftbar = new JPanel();
		leftbar.setLayout(new BorderLayout());
		leftbar.add(pnlSearch, BorderLayout.NORTH);
		pnlSubstanceInfo = new SubstanceInfoPanel();
		leftbar.add(pnlSubstanceInfo, BorderLayout.CENTER);
		this.add(pnlSearch, BorderLayout.WEST);
		this.add(pnlAction, BorderLayout.CENTER);
		this.invalidate();
		
		
	}
}
