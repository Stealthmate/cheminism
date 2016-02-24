package gui.mainscreen.searchbar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class SuggestionEntry extends JLabel {
	
	private static final int BORDER_WIDTH = 1;
	
	private String name;
	private BufferedImage thumbnail;
	private boolean isHighlighted;
	
	private static SuggestionEntry now_highlighted = null;
	
	public static SuggestionEntry getHighlighted() {
		return now_highlighted;
	}
	
	/*package-private*/ static void setHighlighted(SuggestionEntry me) {
		if(now_highlighted != null) {
			now_highlighted.isHighlighted = false;
			now_highlighted.repaint();
		}

		now_highlighted = me;
		if(me != null) {
			me.isHighlighted = true;
			me.repaint();
		}
	}
	
	public SuggestionEntry(String name) {
		super(name);
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				setHighlighted(SuggestionEntry.this);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				setHighlighted(null);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) select();
			}
			
		});
		
		this.name = name;
		this.isHighlighted = false;
		this.thumbnail = 
				new BufferedImage(
						300, 
						100, 
						BufferedImage.TYPE_INT_RGB);
		
		Graphics2D graphics = ((BufferedImage) thumbnail).createGraphics();
		setBackground(Color.WHITE);
		setOpaque(true);

		
	}
	
	/*package-private*/ void select() {
		System.out.println("Not yet implemented");
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
