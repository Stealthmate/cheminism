package gui.mainscreen.searchbar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import gui.mainscreen.reactionpanel.ReactantLabel;
import logic.Substance;

public class SuggestionEntry extends JLabel {
	
	private String name;
	private BufferedImage thumbnail;
	private boolean isHighlighted;
	
	
	/*package-private*/ void highlight() {
		isHighlighted = true;
		repaint();
	}
	
	/*package-private*/ void unhighlight() {
		isHighlighted = false;
		repaint();
	}
	
	public SuggestionEntry(String name) {
		super(name);
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				((SuggestionList) SuggestionEntry.this.getParent())
				.highlightMe(SuggestionEntry.this);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) 
					((SearchPanel)getParent().getParent()).selectSuggestion();
			}
			
		});
		
		this.name = name;
		this.isHighlighted = false;
		this.thumbnail = 
				new BufferedImage(
						300, 
						100, 
						BufferedImage.TYPE_INT_RGB);
		
		setBackground(Color.WHITE);
		setOpaque(true);

		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(isHighlighted) {
			g.setColor(new Color(0x3300AFFF, true));
			g.fillRect(0,  0,  getWidth(), getHeight());
		}
	}
	
}
