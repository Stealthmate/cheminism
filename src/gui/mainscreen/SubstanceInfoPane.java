package gui.mainscreen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JTextPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import logic.Substance;

public class SubstanceInfoPane extends JTextPane {

	private static final int WIDTH_FORMULA = 40;
	private static final int WIDTH_FULLNAME = 50;
	
	private static final Font FONT_FORMULA = new Font("Arial", Font.PLAIN, 25);
	private static final Font FONT_FULLNAME = new Font("Arial", Font.PLAIN, 20);
	private static final Font FONT_TRIVIALNAME = new Font("Arial", Font.PLAIN, 17);
	
	private static final String FORMULA_PREFIX = 
			"<div style=\""
			+ "font-family: \'Arial\';"
			+ "font-size: 20px;"
			+ "\">";
	private static final String FORMULA_SUFFIX = "</div>";
	
	private static final String FULLNAME_PREFIX = 
			"<div style=\""
			+ "font-family: \'Arial\';"
			+ "font-size: 15px;"
			+ "\">";
	private static final String FULLNAME_SUFFIX = "</div>";
	
	private static final String AKA_PREFIX = 
			"<div style=\""
			+ "font-family: \'Arial\';"
			+ "font-size: 12px;"
			+ "\">"
			+ "Also known as: ";
	
	private static final String AKA_SUFFIX = "</div>";
	
	private Substance substance;
	
	public SubstanceInfoPane() {
		super();
		this.setEditable(false);
		this.substance = new Substance();
		this.setContentType("text/html");
		CompoundBorder cb = 
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setBorder(cb);
		this.setOpaque(true);
		
		this.invalidate();
	}
	
	@Override
	public void setPreferredSize(Dimension d) {
		super.setPreferredSize(d);
	}
	
	private String htmlFormula() {
		String formula = substance.getFormula();
		
		Pattern index = Pattern.compile("[^>][0-9]+");
		Matcher m = index.matcher(formula);
		String result = formula;
		String match;
		while(m.find()) {
			
			match = m.group(0);
			match = m.replaceFirst(
					match.charAt(0)
					+ "<sub>" 
					+ match.substring(1)
					+ "</sub>");
			formula = match;
			m = index.matcher(match);
		}
		
		return FORMULA_PREFIX + formula + FORMULA_SUFFIX;
	}
	
	public void setSubstance(Substance s) {
		this.setText("");
		this.substance = s;
		
		StringBuilder stb = new StringBuilder("<html><head>"
				+ "<style>"
				+ "sub {"
				+ "font-size: 13px;"
				+ "}"
				+ "</style></head>");
		
		stb.append(htmlFormula());
		stb.append("\n");
		stb.append(FULLNAME_PREFIX + substance.getFullName() + FULLNAME_SUFFIX);
		
		stb.append(AKA_PREFIX + substance.getTrivialNames() + AKA_SUFFIX);
		
		stb.append("</html>");
		this.setText(stb.toString());
		this.revalidate();
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		/*Graphics2D g = (Graphics2D) graphics;
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if(substance.getFormula().length() < 1) return;
		
		int x = 5;
		int y = 5;
		
		g.setColor(Color.BLACK);
		g.setFont(FontManager.calculateFont(FONT_FORMULA, new Dimension(100, 50), WIDTH_FORMULA));
			
		AttributedString formula = substance.getIndexedFormula();
		formula.addAttribute(TextAttribute.SIZE, g.getFont().getSize());
			
		TextLayout tl = new TextLayout(formula.getIterator(), g.getFontRenderContext());
			
		y += (int) tl.getBounds().getHeight();
			
		g.drawString("Formula: ", x, y);
		x += g.getFontMetrics().stringWidth("Formula: ");
		g.drawString(formula.getIterator(), x, y);
		
		x = 5;
		y += g.getFontMetrics().getHeight() + 5;
		g.setFont(FontManager.calculateFont(FONT_FULLNAME, new Dimension(300,  100), WIDTH_FULLNAME));
		g.drawString("Full name: ", x, y);
		x += g.getFontMetrics().stringWidth("Full name: ");
		g.drawString(substance.getFullName(), x, y);
		
		g.setFont(FONT_TRIVIALNAME);
		y = g.getFontMetrics().getHeight() + 5;
		x = getWidth() - g.getFontMetrics().stringWidth("Also known as: ");
		g.drawString("Also known as: ", x, y);
		y += g.getFontMetrics().getHeight();
		x = getWidth() - g.getFontMetrics().stringWidth(substance.getTrivialNames());
		g.drawString(substance.getTrivialNames(), x, y);*/
	}
	

}
