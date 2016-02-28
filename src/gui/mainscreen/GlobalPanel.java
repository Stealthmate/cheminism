package gui.mainscreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import gui.mainscreen.reactionpanel.ReactantLabel;
import gui.mainscreen.reactionpanel.ReactionPanel;
import gui.mainscreen.searchbar.SearchPanel;
import logic.Substance;

public class GlobalPanel extends JPanel {

	private static final float TOPBAR_PORTION_H = 1.0f / 4.0f;
	private static final float SEARCHPANEL_PORTION_W = 1.0f/7.0f;
	private static final float SEARCHPANEL_PORTION_H = 5.0f/6.0f;
	
	private SearchPanel pnlSearch;
	private SubstanceInfoPane pnlSubstanceInfo;
	private MetroButton btnOrganicBuilder;

	private JPanel topbar;
	private JPanel pnlAction;
	private ReactionPanel pnlEquation;

	private void updatePreferredSize(int width, int height) {
		topbar.setPreferredSize(
				new Dimension(width, (int) (height*TOPBAR_PORTION_H)));
		
		btnOrganicBuilder.setPreferredSize(new Dimension(
				(int) (width * SEARCHPANEL_PORTION_W),
				(int) (height * TOPBAR_PORTION_H * (1-SEARCHPANEL_PORTION_H))));
		
		
		pnlSearch.setPreferredSize(
				new Dimension(
						(int)(width*SEARCHPANEL_PORTION_W), 
						(int)(height*TOPBAR_PORTION_H * SEARCHPANEL_PORTION_H)));
		pnlSubstanceInfo.setPreferredSize(
				new Dimension((int)(width*(1 - SEARCHPANEL_PORTION_W)), (int)(height*TOPBAR_PORTION_H)));
		pnlEquation.setPreferredSize(
				new Dimension(width, (int)(height * (1-TOPBAR_PORTION_H))));
		
		invalidate();
	}
	
	public void resizeLayout(Dimension d) {
		pnlEquation.setPreferredSize(new Dimension(
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
		
		btnOrganicBuilder = new MetroButton("Organic builder");
		btnOrganicBuilder.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent me) {
				if(SwingUtilities.isLeftMouseButton(me)) {
					System.out.println("Organic Builder");
				}
			}
			
		});
		JPanel dummy = new JPanel();
		dummy.setLayout(new BorderLayout());
		dummy.add(btnOrganicBuilder, BorderLayout.NORTH);
		dummy.add(pnlSearch, BorderLayout.CENTER);
		dummy.setBorder(new EmptyBorder(2, 2, 2, 2));
		
		topbar.add(dummy, BorderLayout.WEST);
		topbar.add(pnlSubstanceInfo, BorderLayout.CENTER);
		
		pnlEquation = new ReactionPanel(width, 3*height/4);

		this.add(topbar, BorderLayout.NORTH);
		this.add(pnlEquation, BorderLayout.CENTER);
		updatePreferredSize(width, height);
		this.invalidate();
	}
}
