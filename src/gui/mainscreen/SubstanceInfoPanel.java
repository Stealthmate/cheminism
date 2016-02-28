package gui.mainscreen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import gui.structuredrawer.StructureImageBuilder;
import logic.Substance;

public class SubstanceInfoPanel extends JPanel {
	
	private static final Font FONT_FORMULA = new Font("Arial", Font.PLAIN, 25);
	private static final Font FONT_FULLNAME = new Font("Arial", Font.PLAIN, 20);
	private static final Font FONT_TRIVIALNAME = new Font("Arial", Font.PLAIN, 17);
	
	
	private Substance substance;
	
	public SubstanceInfoPanel() {
		super();
		this.substance = new Substance();
		//this.setBackground(Color.CYAN);
		this.setOpaque(true);
		this.invalidate();
	}
	
	public void setSubstance(Substance s) {
		this.substance = s;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);

		g.setFont(FONT_FORMULA);
		int x = 5;
		int y = g.getFontMetrics().getHeight();
		
		g.drawString(substance.getFormula(), x, y);
		y += g.getFontMetrics().getHeight() + 5;
		g.setFont(FONT_FULLNAME);
		g.drawString(substance.getFullName(), x, y);
		y += g.getFontMetrics().getHeight() + 5;
		g.setFont(FONT_TRIVIALNAME);
		
		
		
		/*g.drawImage(
				StructureImageBuilder.buildFormulaImage(
						substance, 
						getWidth(), 
						getHeight()), 
				5, 10, null);
		g.drawString(substance.getFullName(), 0, 30);*/
	}
	

}
