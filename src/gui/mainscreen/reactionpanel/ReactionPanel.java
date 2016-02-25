package gui.mainscreen.reactionpanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ReactionPanel extends JPanel {

	private static final long serialVersionUID = 1795331097827539882L;

	private static final String REACT_BUTTON_NAME = "React";
	private static final int PADDING_X = 3;
	private static final int PADDING_Y = 3;
	private static final Dimension REACT_BUTTON_MINIMUM_SIZE = new Dimension(100, 30);
	
	private JPanel pnlReactants;
	private JButton btnReact;
	private JPanel pnlProducts;
	
	public void resizeLayout(Dimension d) {
		
	    int btnw = (int) d.width/10;
	    if (btnw < REACT_BUTTON_MINIMUM_SIZE.width) btnw = REACT_BUTTON_MINIMUM_SIZE.width;
	    int btnh = (int) d.height/20;
	    if (btnh < REACT_BUTTON_MINIMUM_SIZE.height) btnh = REACT_BUTTON_MINIMUM_SIZE.height;
	    
		int rect_w = (d.width - btnw)/2 - 2*PADDING_X;
		int rect_h = d.height - PADDING_Y - PADDING_Y;
		
		pnlReactants.setBounds(new Rectangle(PADDING_X, PADDING_Y, rect_w, rect_h));
		
		btnReact.setBounds(new Rectangle(
				rect_w + 2*PADDING_X, 
				(d.height-btnh)/2,  
				btnw, 
				btnh));
		
		pnlProducts.setBounds(new Rectangle(
				PADDING_X + rect_w + PADDING_X + btnw + PADDING_X, 
				PADDING_Y, 
				rect_w, 
				rect_h));
		
		for(int i=0;i<=2;i++)
			pnlReactants.getComponent(i).setPreferredSize(new Dimension(rect_w, rect_h/3));
		pnlReactants.revalidate();
		
		
		revalidate();
	}
	
	public ReactionPanel(int parent_width, int parent_height) {
		super();
		
		this.setOpaque(true);
		
		this.setLayout(null);
		
		pnlReactants = new JPanel();
		pnlReactants.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(PADDING_Y, PADDING_X, PADDING_Y/2, PADDING_X);
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 1;
		pnlReactants.add(new ReactantLabel(), c);
		c.gridy = 2;
		c.insets.top /= 2;
		pnlReactants.add(new ReactantLabel(), c);
		c.gridy = 3;
		c.insets.bottom *= 2;
		pnlReactants.add(new ReactantLabel(), c);

		
		pnlReactants.setOpaque(true);
		//pnlReactants.setBackground(Color.RED);
		
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
