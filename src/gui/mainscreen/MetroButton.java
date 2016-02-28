package gui.mainscreen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.SocketTimeoutException;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import gui.FontManager;
import gui.MainFrame;

public class MetroButton extends JLabel {
	
	public boolean isSelected;
	
	public MetroButton(String name) {
		super(name);
		isSelected = false;
		this.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				isSelected = true;
				repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent me) {
				isSelected = false;
				repaint();
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		
		int w = getWidth() - 4;
		int h = getHeight() - 4;
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(FontManager.calculateFont(MainFrame.MAIN_FONT, new Dimension(w, h)
				, 10, getText().length()));
		
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
