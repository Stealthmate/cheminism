package gui.mainscreen.searchbar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import logic.Substance;

public class SuggestionEntry extends JLabel {
	
	private static final Font MAIN_FONT = new Font("Arial", Font.PLAIN, 14);
	
	private Substance substance;
	
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
	
	public SuggestionEntry(Substance substance, int number) {
		super("dummy");
		this.number = number;
		this.substance = substance;
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				SearchManager.highlight(SuggestionEntry.this);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				SearchManager.highlight(SuggestionEntry.this);
			}
			
		});
		this.isHighlighted = false;
		
		setBackground(Color.WHITE);
		setOpaque(true);
		
		AttributedString sizestr = new AttributedString("A2");
		sizestr.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB, 1, 2);
		sizestr.addAttribute(TextAttribute.SIZE, MAIN_FONT.getSize());
		Graphics2D g = (Graphics2D) new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).createGraphics();
		g.setFont(MAIN_FONT);
		TextLayout tl = new TextLayout(sizestr.getIterator(), g.getFontRenderContext());
		int min_w = (int) tl.getBounds().getWidth();
		int min_h = (int) tl.getBounds().getHeight();
		this.setPreferredSize(new Dimension(min_w, min_h+7));
	}
	
	public Substance getSubstance() {
		return this.substance;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		//Turn on AA
		((Graphics2D)g).setRenderingHint(
				RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		//Set Font
		g.setFont(MAIN_FONT);
		
		//Draw bg
		g.setColor(Color.WHITE);
		g.fillRect(0,  0,  getWidth(), getHeight());

		//Draw order number
		g.setColor(Color.BLACK);
		String numstr = Integer.toString(number) + ". ";
		int width = ((Graphics2D) g).getFontMetrics().stringWidth(numstr);
		g.drawString(numstr, 1, g.getFontMetrics().getHeight());
		
		//Draw formatted formula
		AttributedString formula = this.substance.getIndexedFormula();
		formula.addAttribute(TextAttribute.SIZE, g.getFont().getSize());
		g.drawString(formula.getIterator(), 1+width, g.getFontMetrics().getHeight());		
		
		//If highlighted, draw highlight
		if(isHighlighted) {
			g.setColor(new Color(0x3300AFFF, true));
			g.fillRect(0,  0,  getWidth(), getHeight());
		}
	}
	
}
