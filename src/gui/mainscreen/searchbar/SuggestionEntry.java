package gui.mainscreen.searchbar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class SuggestionEntry extends JLabel {
	
	private String name;
	private BufferedImage thumbnail;
	private boolean isHighlighted;
	private int number;
	
	
	
	
	/*package-private*/ void highlight() {
		isHighlighted = true;
		repaint();
	}
	
	/*package-private*/ void unhighlight() {
		isHighlighted = false;
		repaint();
	}
	
	public SuggestionEntry(String name, int number) {
		super(name);
		this.number = number;
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				((SuggestionList) SuggestionEntry.this.getParent())
				.highlightMe(SuggestionEntry.this);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) 
					((SearchPanel)getParent().getParent().getParent().getParent()).selectSuggestion();
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
		//super.paintComponent(g);
		
		g.setColor(Color.WHITE);
		g.fillRect(0,  0,  getWidth(), getHeight());
		
		if(isHighlighted) {
			g.setColor(new Color(0x3300AFFF, true));
			g.fillRect(0,  0,  getWidth(), getHeight());
		}
		
		g.setColor(Color.BLACK);
		String numstr = Integer.toString(number) + ". ";
		int width = ((Graphics2D) g).getFontMetrics().stringWidth(numstr);
		g.drawString(numstr, 1, getHeight());
		g.drawString(getText(), 1+width, getHeight());
	}
	
}
