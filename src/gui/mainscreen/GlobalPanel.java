package gui.mainscreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
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

public class GlobalPanel extends JPanel {

	private JPanel pnlSeekBar;
	private JTextField textSeekQuery;
	private static final int TEXT_SEEK_QUERY_HEIGHT = 25;
	private static final int SEEK_BAR_WIDTH = 300;
	private JPanel pnlSeekBarSuggestions;

	private JPanel pnlAction;
	private JPanel pnlViewCompoundBar;
	private JPanel pnlEquation;
	
	private JPanel pnlMenu;
	private JButton btnAddReactant;
	private JButton btnRemoveReactant;
	
	
	private JPanel pnlReactants;
	private ArrayList<ReactantLabel> reactants;
	
	
	private JButton btnReact = new JButton("REACT");

	private JPanel pnlProducts; 
	
	private void updatePreferredSize(int width, int height) {
		pnlSeekBar.setPreferredSize(
				new Dimension(SEEK_BAR_WIDTH, height));
		pnlViewCompoundBar.setPreferredSize(new Dimension(width, height/4));
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

		
		pnlReactants = new JPanel();
		pnlReactants.setLayout(new GridBagLayout());
		
		reactants.add(new ReactantLabel("Reactants are here"));
		reactants.add(new ReactantLabel("Reactants are here"));
		reactants.add(new ReactantLabel("Reactants are here"));
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.gridx = 1;
		c.gridy = 1;
		pnlReactants.add(reactants.get(0), c);
		c.gridy = 2;
		pnlReactants.add(reactants.get(1), c);
		c.gridy = 3;
		pnlReactants.add(reactants.get(2), c);

		
		pnlReactants.setOpaque(true);
		pnlReactants.setBackground(Color.RED);
		
		btnReact = new JButton("React!");
		
		pnlProducts = new JPanel();
		pnlProducts.setOpaque(true);
		pnlProducts.setBackground(Color.RED);
		
		
		pnlEquation = new JPanel();
		pnlEquation.setLayout(new GridBagLayout());
		pnlEquation.setBackground(Color.cyan);
		pnlEquation.setOpaque(true);
		
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = 0.45;
		c.weighty = 1.0;
		c.gridx = 0;
		c.fill = GridBagConstraints.BOTH;
		pnlEquation.add(pnlReactants, c);
		
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = 1;
		c.weightx = 0.1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		pnlEquation.add(btnReact, c);
		
		c.gridx = 2;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.LINE_END;
		c.fill = GridBagConstraints.BOTH;
		pnlEquation.add(pnlProducts, c);
		
		
		pnlAction.add(pnlViewCompoundBar, BorderLayout.NORTH);
		pnlAction.add(pnlEquation, BorderLayout.CENTER);
		
		this.add(pnlSeekBar, BorderLayout.WEST);
		this.add(pnlAction, BorderLayout.CENTER);
		this.invalidate();
		
		
	}
	

}
