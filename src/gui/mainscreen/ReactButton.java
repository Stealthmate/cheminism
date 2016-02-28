package gui.mainscreen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.net.SocketTimeoutException;

import javax.swing.JLabel;

import gui.MainFrame;

public class ReactButton extends JLabel {
	
	public boolean isSelected;
	
	public ReactButton(String name) {
		super(name);
		isSelected = false;
	}
	
	@Override
	protected void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		int text_x = (getWidth() - g.getFontMetrics().stringWidth(getText()))/2;
		
		int text_y = (getHeight() + g.getFontMetrics().getHeight())/2 - g.getFontMetrics().getDescent();
		
		g.setColor(Color.BLACK);
		g.drawString(getText(), text_x ,text_y);
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);	
		
		if(isSelected) {
			g.setColor(MainFrame.HIGHLIGHT_COLOR);
			g.fillRect(0, 0, getWidth()-1, getHeight()-1);
		}
	}
}
