package gui.mainscreen;

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

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import gui.MainFrame;

public class ReactantLabel extends JLabel implements MouseListener {
	
	
	public ReactantLabel() {
		super("Empty");
		setHorizontalAlignment(JLabel.CENTER);
		setVerticalAlignment(JLabel.CENTER);
		invalidate();
	}

	public ReactantLabel(String name) {
		super(name);
		setOpaque(true);
		setBackground(Color.YELLOW);
		setHorizontalAlignment(JLabel.CENTER);
		setVerticalAlignment(JLabel.CENTER);
		FontRenderContext frc = new FontRenderContext(null, true, true);
		
	    TextLayout layout = new TextLayout("Reactants are here", MainFrame.MAIN_FONT, frc);
	    Rectangle2D bounds = layout.getBounds();
	    this.setPreferredSize(new Dimension((int)bounds.getWidth(), (int)bounds.getHeight() + 100));
	    
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
