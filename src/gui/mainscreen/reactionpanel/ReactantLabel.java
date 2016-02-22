package gui.mainscreen.reactionpanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import gui.MainFrame;

public class ReactantLabel extends JLabel implements MouseListener {
	
	private static final long serialVersionUID = 7319066579363738370L;
	private static final String DEFAULT_NAME = "Reactant";
	
	private BufferedImage structureImage;
	private String name;

	private void setup(Dimension d) {
		setOpaque(true);
		setBackground(Color.YELLOW);
		setHorizontalAlignment(JLabel.CENTER);
		setVerticalAlignment(JLabel.CENTER);
		
		if(d!=null) setSize(d);
	}
	
	public ReactantLabel() {
		super(DEFAULT_NAME);
		setup(null);
		invalidate();
	}

	public ReactantLabel(Dimension d) {
		super(DEFAULT_NAME);
		setup(d);
	    invalidate();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(SwingUtilities.isRightMouseButton(e)) {
			//TODO Add Popup menu Builder
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
	
	

}
