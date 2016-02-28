package gui.mainscreen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.net.SocketTimeoutException;
import java.text.AttributedString;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.FontManager;
import gui.MainFrame;
import gui.structuredrawer.StructureImageBuilder;
import logic.Substance;

public class SubstanceInfoPanel extends JPanel {

	private static final int WIDTH_FORMULA = 40;
	private static final int WIDTH_FULLNAME = 50;
	
	private static final Font FONT_FORMULA = new Font("Arial", Font.PLAIN, 25);
	private static final Font FONT_FULLNAME = new Font("Arial", Font.PLAIN, 20);
	private static final Font FONT_TRIVIALNAME = new Font("Arial", Font.PLAIN, 17);
	
	
	private Substance substance;
	
	private JLabel lblFormula;
	private JLabel lblFullName;
	private JLabel lblTrivialNames;
	private JLabel info;
	
	public SubstanceInfoPanel() {
		super();
		this.substance = new Substance();
		//this.setBackground(Color.CYAN);
		this.setLayout(new GridBagLayout());
		this.setOpaque(true);
		this.invalidate();
	}
	
	public void setSubstance(Substance s) {
		this.removeAll();
		this.substance = s;
		/*lblFormula = new JLabel("Formula: " + s.getFormula());
		lblFormula.setAlignmentX(Component.LEFT_ALIGNMENT);
		lblFullName = new JLabel("Full name: " + s.getFullName());
		lblFullName.setAlignmentX(Component.LEFT_ALIGNMENT);
		lblTrivialNames = new JLabel("Also known as: " + s.getTrivialNames());
		
		JPanel topleft = new JPanel();
		topleft.setLayout(new BoxLayout(topleft, BoxLayout.Y_AXIS));
		topleft.add(lblFormula);
		topleft.add(lblFullName);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		this.add(topleft, c);
		
		System.out.println(lblTrivialNames.getText());
		
		c.gridx = 1;
		this.add(lblTrivialNames, c);
		this.revalidate();*/
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		
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
		g.drawString(substance.getTrivialNames(), x, y);
	}
	

}
