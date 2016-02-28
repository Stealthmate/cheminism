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

import gui.FontManager;
import gui.MainFrame;
import logic.Substance;

public class SuggestionEntry extends JLabel {
	
	private static final Font MAIN_FONT = new Font("Arial", Font.PLAIN, 1);
	private static final int STRING_WIDTH = 10;
	
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
		super();
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
		
		//Create dummy content
		AttributedString sizestr = new AttributedString("A2");
		sizestr.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB, 1, 2);
		sizestr.addAttribute(TextAttribute.SIZE, MAIN_FONT.getSize());
		
		//Create dummy graphics
		Graphics2D g = (Graphics2D) new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).createGraphics();
		g.setFont(MAIN_FONT);
		
		TextLayout tl = new TextLayout(sizestr.getIterator(), g.getFontRenderContext());
		
		//Measure dummy size
		int min_w = (int) tl.getBounds().getWidth();
		int min_h = (int) tl.getBounds().getHeight();
		
		this.setPreferredSize(new Dimension(min_w, min_h+7));
	}
	
	@Override
	public void setPreferredSize(Dimension size) {
		super.setPreferredSize(size);
	}
	
	public Substance getSubstance() {
		return this.substance;
	}
	
	@Override
	protected void paintComponent(Graphics graphics) {
		
		Graphics2D g = (Graphics2D) graphics;
		
		//Turn on AA
		g.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		//Set Font
		
		float margin_bot = 
				(float) 
				(FontManager.HEIGHT_TO_FONT_RATIO_SUBSCRIPT * getPreferredSize().getHeight());
		
		Dimension d = 
				new Dimension(
						getPreferredSize().width, 
						(int) 
						(getPreferredSize().height * FontManager.HEIGHT_TO_FONT_RATIO_LETTER));
		g.setFont(FontManager.calculateFont(MAIN_FONT, d, STRING_WIDTH, substance.getFormula().length()));
		
		//Draw bg
		g.setColor(Color.WHITE);
		g.fillRect(0,  0,  getWidth(), getHeight());

		//Draw order number
		g.setColor(Color.BLACK);
		String numstr = Integer.toString(number) + ". ";
		int width = ((Graphics2D) g).getFontMetrics().stringWidth(numstr);
		g.drawString(numstr, 1, getHeight() - margin_bot);
		
		//Draw formatted formula
		AttributedString formula = this.substance.getIndexedFormula();
		formula.addAttribute(TextAttribute.SIZE, g.getFont().getSize());
		
		TextLayout tl = new TextLayout(formula.getIterator(), g.getFontRenderContext());
		
		g.drawString(formula.getIterator(), 1+width, getHeight() - margin_bot);
		
		//If highlighted, draw highlight
		if(isHighlighted) {
			g.setColor(MainFrame.HIGHLIGHT_COLOR);
			g.fillRect(0,  0,  getWidth(), getHeight());
		}
	}
	
}
