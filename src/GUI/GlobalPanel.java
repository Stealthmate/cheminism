package GUI;

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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
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
	private JButton btnAddReactant = new JButton("Add reactant");
	
	
	private JPanel pnlReactants;
	private ArrayList<ReactantLabel> reactants;
	
	
	private JButton btnReact = new JButton("REACT");
	
	private class ProductsPanel extends JPanel {
		
		private static final long serialVersionUID = 2L;
		
		private ArrayList<JLabel> products = new ArrayList<>(4);
		
		ProductsPanel() {
			super();
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		}
		
		
		public void addProduct(String name) {
			add(new JLabel(name));
			revalidate();
			validate();
			
		}
	};
	private JPanel pnlProducts;

	
	
	private void updatePreferredSize(int width, int height) {
		pnlSeekBar.setPreferredSize(
				new Dimension(SEEK_BAR_WIDTH, height));
		pnlViewCompoundBar.setPreferredSize(new Dimension(width, height/4));
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
		
		pnlSeekBarSuggestions = new JPanel();
		pnlSeekBarSuggestions.setOpaque(true);
		pnlSeekBarSuggestions.setBackground(Color.GRAY);
		pnlSeekBarSuggestions.setLayout(new GridLayout());
		pnlSeekBarSuggestions.setPreferredSize(
				new Dimension(width/5, height-TEXT_SEEK_QUERY_HEIGHT));
		
		pnlSeekBar.add(textSeekQuery);
		pnlSeekBar.add(pnlSeekBarSuggestions);
		
		pnlAction = new JPanel();
		pnlAction.setLayout(new BorderLayout());
		
		pnlViewCompoundBar = new JPanel();
		pnlViewCompoundBar.setOpaque(true);
		pnlViewCompoundBar.setBackground(Color.BLUE);
		pnlViewCompoundBar.setPreferredSize(new Dimension(width, height/4));
		
		pnlEquation = new JPanel();
		pnlEquation.setLayout(new BoxLayout(pnlEquation, BoxLayout.LINE_AXIS));
		
		pnlReactants = new JPanel();
		pnlReactants.setOpaque(true);
		pnlReactants.setBackground(Color.RED);
		pnlReactants.setPreferredSize(new Dimension(200, 500));
		
		btnReact = new JButton("React!");
		
		pnlProducts = new JPanel();
		pnlProducts.setOpaque(true);
		pnlProducts.setBackground(Color.RED);
		pnlProducts.setPreferredSize(new Dimension(200, 500));
		
		pnlEquation.add(pnlReactants, BorderLayout.WEST);
		pnlEquation.add(btnReact, BorderLayout.CENTER);
		pnlEquation.add(pnlProducts, BorderLayout.EAST);
		
		
		pnlAction.add(pnlViewCompoundBar, BorderLayout.NORTH);
		pnlAction.add(pnlEquation, BorderLayout.CENTER);
		
		this.add(pnlSeekBar, BorderLayout.WEST);
		this.add(pnlAction, BorderLayout.CENTER);
		this.invalidate();
		
		
	}
	
	private void addReactant() {
		
		reactants.add(new ReactantLabel("LOLOLOLOLOOLOLSAHFKABSLDKGJBDLSAJGBLDASJGOLOL"));
		
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.0;
		c.gridy = reactants.size()-1;
		pnlReactants.add(reactants.get(reactants.size()-1), c);
		
		pnlReactants.revalidate();
	}
	
	private void addProduct(String name) {
		
		reactants.add(new ReactantLabel(name));
		
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.0;
		c.gridy = reactants.size()-1;
		pnlProducts.add(reactants.get(reactants.size()-1), c);
		
		pnlProducts.revalidate();
		
		System.out.println(pnlReactants.getSize());
		System.out.println(pnlProducts.getSize());
	}

	

}
