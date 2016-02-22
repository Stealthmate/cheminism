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
	private static final String LONGEST_NAME = "Reactant";
	
	
	private BufferedImage structureImage;
	private String name;

	private void setup() {
		setOpaque(true);
		setBackground(Color.YELLOW);
		setHorizontalAlignment(JLabel.CENTER);
		setVerticalAlignment(JLabel.CENTER);
		FontRenderContext frc = new FontRenderContext(null, true, true);
		
	    TextLayout layout = new TextLayout(LONGEST_NAME, MainFrame.MAIN_FONT, frc);
	    
	    
	}
	
	public ReactantLabel() {
		super("Empty");
		setHorizontalAlignment(JLabel.CENTER);
		setVerticalAlignment(JLabel.CENTER);
		invalidate();
	}

	public ReactantLabel(String name) {
		super(name);
		setup();
	    
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
