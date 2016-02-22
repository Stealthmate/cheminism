package gui.mainscreen.reactionpanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.util.ArrayList;
import java.util.EventListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import gui.MainFrame;

public class ReactionPanel extends JPanel {

	private static final String REACT_BUTTON_NAME = "React";
	
	private JPanel pnlReactants;
	private ArrayList<ReactantLabel> reactants;
	private JButton btnReact;
	private JPanel pnlProducts;
	
	public void resizeLayout(Dimension d) {
		System.out.println("Dim " + d);
	    int btnw = (int) d.width/10;
	    int btnh = (int) d.height/20;
	    
		int rect_w = (d.width - btnw)/2;
		int rect_h = d.height;
		System.out.println(btnw + " " + btnh);
		System.out.println(rect_w + " " + rect_h);
		
		pnlReactants.setBounds(new Rectangle(0, 0, rect_w, rect_h));
		
		btnReact.setBounds(new Rectangle(
				(d.width-btnw)/2, 
				(d.height-btnh)/2,  
				btnw, 
				btnh));
		
		pnlProducts.setBounds(new Rectangle(rect_w + btnw, 
				0, 
				rect_w, 
				rect_h));
		
		System.out.println("Bounds" + pnlProducts.getBounds());
		
		revalidate();
	}
	
	public ReactionPanel(int parent_width, int parent_height) {
		super();
		
		this.setBackground(Color.cyan);
		this.setOpaque(true);
		
		this.setLayout(null);
		
		this.addComponentListener(new ComponentAdapter() {
			
			@Override
			public void componentResized(ComponentEvent e) {
				//resizeLayout(new Dimension(e.getComponent().getSize().width, e.getComponent().getSize().height));
			}
			
		});
		
		pnlReactants = new JPanel();
		pnlReactants.setLayout(new GridBagLayout());
		
		reactants = new ArrayList<>();
		
		reactants.add(new ReactantLabel("Reactants are here"));
		reactants.add(new ReactantLabel("Reactants are hereasd"));
		reactants.add(new ReactantLabel("Reactants are hereasdfgasdasdh"));
		
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
		pnlProducts.setBackground(Color.MAGENTA);
		
		resizeLayout(new Dimension(parent_width, parent_height));
		
		this.add(pnlReactants);
		this.add(btnReact);
		this.add(pnlProducts);
		
		this.invalidate();
	}

	
}
