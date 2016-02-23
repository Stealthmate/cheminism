package gui.mainscreen.reactionpanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ReactionPanel extends JPanel {

	private static final long serialVersionUID = 1795331097827539882L;

	private static final String REACT_BUTTON_NAME = "React";
	
	private JPanel pnlReactants;
	private JButton btnReact;
	private JPanel pnlProducts;
	
	public void resizeLayout(Dimension d) {
		System.out.println("Dim " + d);
	    int btnw = (int) d.width/10;
	    int btnh = (int) d.height/20;
	    
		int rect_w = (d.width - btnw)/2;
		int rect_h = d.height;
		
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
		
		for(int i=0;i<=2;i++)
			pnlReactants.getComponent(i).setPreferredSize(new Dimension(rect_w, rect_h/3));
		pnlReactants.revalidate();
		
		
		revalidate();
	}
	
	public ReactionPanel(int parent_width, int parent_height) {
		super();
		
		this.setBackground(Color.cyan);
		this.setOpaque(true);
		
		this.setLayout(null);
		
		pnlReactants = new JPanel();
		pnlReactants.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.gridx = 1;
		c.gridy = 1;
		pnlReactants.add(new ReactantLabel(), c);
		c.gridy = 2;
		pnlReactants.add(new ReactantLabel(), c);
		c.gridy = 3;
		pnlReactants.add(new ReactantLabel(), c);

		
		pnlReactants.setOpaque(true);
		pnlReactants.setBackground(Color.RED);
		
		btnReact = new JButton(REACT_BUTTON_NAME);
		
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
