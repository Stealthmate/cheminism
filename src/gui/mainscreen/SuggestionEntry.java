package gui.mainscreen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class SuggestionEntry extends JLabel {
	
	private static final int BORDER_WIDTH = 1;
	
	private String name;
	private BufferedImage thumbnail;
	private boolean isHighlighted;
	
	private static SuggestionEntry now_highlighted = null;
	
	private static void highlightMe(SuggestionEntry me) {
		if(now_highlighted != null) {
			now_highlighted.isHighlighted = false;
			now_highlighted.repaint();
		}

		now_highlighted = me;
		me.isHighlighted = true;
		me.repaint();
	}
	
	public SuggestionEntry(String name) {
		super(name);
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

		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				highlightMe(SuggestionEntry.this);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				isHighlighted = false;
				repaint();
			}
			
		});
		
		//setBorder(new LineBorder(Color.BLACK, BORDER_WIDTH));
		//this.setIcon(new ImageIcon(StructureImageBuilder.buildFormulaImage()));
		
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
