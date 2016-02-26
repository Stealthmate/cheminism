package gui.mainscreen.searchbar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import logic.Substance;

public class SuggestionEntry extends JLabel {
	
	private static final Font SUGGESTION_FONT = new Font("Arial", Font.PLAIN, 14);
	
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
				((SuggestionList) SuggestionEntry.this.getParent())
				.highlightMe(SuggestionEntry.this);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) 
					((SearchPanel)getParent().getParent().getParent().getParent()).selectSuggestion();
			}
			
		});
		this.isHighlighted = false;
		
		setBackground(Color.WHITE);
		setOpaque(true);
	}
	
	public Substance getSubstance() {
		return this.substance;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		((Graphics2D)g).setRenderingHint(
				RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(SUGGESTION_FONT);
		
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
		
		AttributedString formula = this.substance.getIndexedFormula();
		formula.addAttribute(TextAttribute.SIZE, g.getFont().getSize());
		g.drawString(formula.getIterator(), 1+width, getHeight());
	}
	
}
