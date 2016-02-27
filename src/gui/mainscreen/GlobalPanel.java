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
	
	private SearchPanel pnlSearch;
	private SubstanceInfoPanel pnlSubstanceInfo;

	private JPanel leftbar;
	private JPanel pnlAction;
	private JPanel pnlViewCompoundBar;
	private ReactionPanel pnlEquation;

	private void updatePreferredSize(int width, int height) {
		leftbar.setPreferredSize(
				new Dimension(width/5, height));
		pnlViewCompoundBar.setPreferredSize(new Dimension(4*width/5, height/4));
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
		leftbar = new JPanel();
		leftbar.setBackground(Color.RED);
		leftbar.setOpaque(true);
		leftbar.setLayout(new BorderLayout());
		leftbar.add(pnlSearch, BorderLayout.NORTH);
		pnlSubstanceInfo = new SubstanceInfoPanel();
		leftbar.add(pnlSubstanceInfo, BorderLayout.CENTER);
		this.add(leftbar, BorderLayout.WEST);
		this.add(pnlAction, BorderLayout.CENTER);
		updatePreferredSize(width, height);
		this.invalidate();
	}
	
	public void selectReactant(int i) {
		ReactantLabel.selectMe(pnlEquation.getReactant(i));
	}
}
