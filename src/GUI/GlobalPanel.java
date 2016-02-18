package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GlobalPanel extends JPanel {

	private static JButton ADD_REACTANT = new JButton("Add reactant");
	
	private static class ReactantsPanel extends JPanel {
		
		private ArrayList<ReactantLabel> reactants = new ArrayList<>(4);
		
		ReactantsPanel() {
			super();
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		}
		
		
		void addReactant() {
			add(new ReactantLabel());
			revalidate();
			validate();
			
		}
	};
	ReactantsPanel Reactants;
	
	private static JButton REACT = new JButton("REACT");
	
	private static class ProductsPanel extends JPanel {
		
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
	ProductsPanel Products;
	
	public GlobalPanel() {
		
		Reactants = new ReactantsPanel();
		
		Products = new ProductsPanel();
		
		ADD_REACTANT.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) {
					Reactants.addReactant();
				}
				else if(SwingUtilities.isRightMouseButton(e)) {
					Products.addProduct("Qsno li e? Vaprosi?");
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}
			
		});
		
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		c.insets = new Insets(50, 50, 0, 0);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		add(ADD_REACTANT, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.3;
		c.gridx = 1;
		c.gridy = 1;
		add(Reactants, c);
	
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.3;
		c.gridx = 2;
		c.gridy = 1;
		add(REACT, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.3;
		c.gridx = 3;
		c.gridy = 1;
		add(Products, c);
	}
	
	
	
	

}
