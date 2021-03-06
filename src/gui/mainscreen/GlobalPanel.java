package gui.mainscreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import gui.mainscreen.reactionpanel.ReactantLabel;
import gui.mainscreen.reactionpanel.ReactionPanel;
import gui.mainscreen.searchbar.SearchPanel;
import logic.Substance;

public class GlobalPanel extends JPanel {

	private static final float TOPBAR_PORTION = 1.0f / 4.0f;
	private static final float SEARCHPANEL_PORTION = 1.0f/7.0f;
	
	private SearchPanel pnlSearch;
	private SubstanceInfoPane pnlSubstanceInfo;

	private JPanel topbar;
	private JPanel pnlAction;
	private ReactionPanel pnlEquation;

	private void updatePreferredSize(int width, int height) {
		topbar.setPreferredSize(
				new Dimension(width, (int) (height*TOPBAR_PORTION)));
		
		pnlSearch.setPreferredSize(
				new Dimension((int)(width*SEARCHPANEL_PORTION), (int)(height*TOPBAR_PORTION)));
		pnlSubstanceInfo.setPreferredSize(
				new Dimension((int)(width*(1 - SEARCHPANEL_PORTION)), (int)(height*TOPBAR_PORTION)));
		pnlEquation.setPreferredSize(
				new Dimension(width, (int)(height * (1-TOPBAR_PORTION))));
		
		invalidate();
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

		topbar = new JPanel();
		topbar.setOpaque(true);
		topbar.setBackground(Color.BLUE);
		topbar.setPreferredSize(new Dimension(width, height/4));
		topbar.setLayout(new BorderLayout());

		pnlSubstanceInfo = new SubstanceInfoPane();
		SelectObserver.registerSubstanceInfoPanel(pnlSubstanceInfo);
		
		topbar.add(pnlSearch, BorderLayout.WEST);
		topbar.add(pnlSubstanceInfo, BorderLayout.CENTER);
		
		pnlEquation = new ReactionPanel(width, 3*height/4);

		this.add(topbar, BorderLayout.NORTH);
		this.add(pnlEquation, BorderLayout.CENTER);
		updatePreferredSize(width, height);
		this.invalidate();
	}
}
