package GUI;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

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
