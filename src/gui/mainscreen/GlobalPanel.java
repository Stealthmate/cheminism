package gui.mainscreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.MainFrame;
import gui.mainscreen.reactionpanel.ReactionPanel;

public class GlobalPanel extends JPanel {

	private static final long serialVersionUID = -2037661004330652608L;
	
	private JPanel pnlSeekBar;
	private JTextField textSeekQuery;
	private static final int TEXT_SEEK_QUERY_HEIGHT = 45;
	private static final int SEEK_BAR_WIDTH = 300;
	private JPanel pnlSeekBarSuggestions;

	private JPanel pnlAction;
	private JPanel pnlViewCompoundBar;
	private ReactionPanel pnlEquation;

	private void updatePreferredSize(int width, int height) {
		pnlSeekBar.setPreferredSize(
				new Dimension(SEEK_BAR_WIDTH, height));
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
		
		pnlSeekBar = new JPanel();
		pnlSeekBar.setLayout(new BoxLayout(pnlSeekBar, BoxLayout.PAGE_AXIS));
		
		textSeekQuery = new JTextField();
		textSeekQuery.setMaximumSize(new Dimension(10000, TEXT_SEEK_QUERY_HEIGHT));
		textSeekQuery.setPreferredSize(new Dimension(width/5, TEXT_SEEK_QUERY_HEIGHT));
		textSeekQuery.setFont(MainFrame.MAIN_FONT);
		
		pnlSeekBarSuggestions = new JPanel();
		pnlSeekBarSuggestions.setOpaque(true);
		
		pnlSeekBarSuggestions.setLayout(new GridLayout());
		pnlSeekBarSuggestions.setPreferredSize(
				new Dimension(width/5, height-TEXT_SEEK_QUERY_HEIGHT));
		
		
		SuggestionEntry entry = new SuggestionEntry("Hello!");
		pnlSeekBarSuggestions.add(entry);
		
		
		pnlSeekBar.add(textSeekQuery);
		pnlSeekBar.add(pnlSeekBarSuggestions);
		
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
		this.add(pnlSeekBar, BorderLayout.WEST);
		this.add(pnlAction, BorderLayout.CENTER);
		this.invalidate();
		
		
	}
	

}
