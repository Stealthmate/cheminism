package gui.mainscreen.reactionpanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import gui.mainscreen.MetroButton;
import gui.mainscreen.SelectObserver;

public class ReactionPanel extends JPanel {

	private static final long serialVersionUID = 1795331097827539882L;

	private static final String REACT_BUTTON_NAME = "React";
	private static final int PADDING_X = 3;
	private static final int PADDING_Y = 3;
	private static final Dimension REACT_BUTTON_MINIMUM_SIZE = new Dimension(100, 30);
	
	private JPanel pnlReactants;
	private MetroButton btnReact;
	private JPanel pnlProducts;
	
	@Override
	public void setPreferredSize(Dimension d) {
		
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
			pnlReactants.getComponent(i).setPreferredSize(new Dimension(
					rect_w - 2*PADDING_X, 
					(rect_h - 6*PADDING_Y)/3));
	}
	
	public ReactionPanel(int parent_width, int parent_height) {
		super();
		
		this.setOpaque(true);
		this.setBackground(Color.WHITE);
		this.setLayout(null);
		
		pnlReactants = new JPanel();
		pnlReactants.setLayout(new GridBagLayout());
		
		Dimension rlsize = new Dimension(parent_width-2*PADDING_X, (parent_height - 6*PADDING_Y)/2);
		
		ReactantLabel react1 = new ReactantLabel(rlsize);
		ReactantLabel react2 = new ReactantLabel(rlsize);
		ReactantLabel react3 = new ReactantLabel(rlsize);
		SelectObserver.registerReactant(react1);
		SelectObserver.registerReactant(react2);
		SelectObserver.registerReactant(react3);
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(PADDING_Y, PADDING_X, PADDING_Y/2, PADDING_X);
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 1;
		pnlReactants.add(react1, c);
		c.gridy = 2;
		c.insets.top /= 2;
		pnlReactants.add(react2, c);
		c.gridy = 3;
		c.insets.bottom *= 2;
		pnlReactants.add(react3, c);
		
		pnlReactants.setOpaque(true);
		//pnlReactants.setBackground(Color.RED);
		
		btnReact = new MetroButton(REACT_BUTTON_NAME);
		
		btnReact.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				if(SwingUtilities.isLeftMouseButton(me)) {
					System.out.println("Not yet implemented");
				}
			}
		});
		
		pnlProducts = new JPanel();
		pnlProducts.setOpaque(true);
		pnlProducts.setBackground(Color.MAGENTA);
		
		setPreferredSize(new Dimension(parent_width, parent_height));
		
		this.add(pnlReactants);
		this.add(btnReact);
		this.add(pnlProducts);
		
		this.invalidate();
	}

	public ReactantLabel getReactant(int i) {
		if(i >= 0 && i <=2) return (ReactantLabel) pnlReactants.getComponent(i);
		return null;
	}
}
