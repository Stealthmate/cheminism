package gui.mainscreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import gui.MainFrame;
import gui.mainscreen.reactionpanel.ReactantLabel;
import gui.mainscreen.reactionpanel.ReactionPanel;

public class GlobalPanel extends JPanel {

	private JPanel pnlSeekBar;
	private JTextField textSeekQuery;
	private static final int TEXT_SEEK_QUERY_HEIGHT = 45;
	private static final int SEEK_BAR_WIDTH = 300;
	private JPanel pnlSeekBarSuggestions;

	private JPanel pnlAction;
	private JPanel pnlViewCompoundBar;
	private ReactionPanel pnlEquation;
	
	private JPanel pnlReactants;
	private ArrayList<ReactantLabel> reactants;
	
	
	private JButton btnReact = new JButton("REACT");

	private JPanel pnlProducts; 
	
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
		
		reactants = new ArrayList<>(4);
		
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
		//pnlSeekBarSuggestions.setBackground(Color.GRAY);
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
