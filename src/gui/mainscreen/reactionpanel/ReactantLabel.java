package gui.mainscreen.reactionpanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import gui.structuredrawer.StructureImageBuilder;
import logic.Substance;

public class ReactantLabel extends JLabel {
	
	private static final long serialVersionUID = 7319066579363738370L;
	private static final String DEFAULT_NAME = "Reactant";
	private static final Dimension IMAGE_SIZE = new Dimension(800, 600);
	private static final Font FONT_LABEL_NAME = new Font("Arial", Font.BOLD, 30);
	private static final Color HIGHLIGHT_COLOR = new Color(0x3300AFFF, true);
	
	private Substance substance;
	private boolean isSelected;
	
	private static ReactantLabel now_selected = null;
	
	public static ReactantLabel getSelected() {
		return now_selected;
	}
	
	private static void selectMe(ReactantLabel me) {
		if(now_selected != null) {
			now_selected.isSelected = false;
			now_selected.repaint();
		}
		
		now_selected = me;
		if(me != null) {
			me.isSelected = true;
			me.repaint();
		}
	}

	private void setup(Dimension d) {
		
		setHorizontalAlignment(JLabel.CENTER);
		setVerticalAlignment(JLabel.CENTER);
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) selectMe(ReactantLabel.this);
				else if(SwingUtilities.isRightMouseButton(e)) selectMe(null);
			}
		});
		
		if(d!=null) {
			setSize(d);
		}
		isSelected = false;
	}
	
	public ReactantLabel() {
		super();
		this.substance = null;
		setup(null);
		invalidate();
	}

	public ReactantLabel(Dimension d) {
		super();
		setup(d);
		this.substance = null;
	    invalidate();
	}
	
	private void drawHighlight(Graphics g) {
		
	}
	
	@Override
	protected void paintComponent(Graphics graphics) {
		
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//Fill white
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//Draw Border
		g.setColor(Color.BLACK);
		g.draw(new Rectangle2D.Double(0, 0, getBounds().getWidth() - 1, getBounds().getHeight() - 1));
		
		//Draw text
		g.setColor(Color.BLACK);
		g.setFont(FONT_LABEL_NAME);
		
		int text_x = g.getFontMetrics().stringWidth("i");
		int text_y = g.getFontMetrics().getHeight();
		
		//If empty, draw name and image
		if(this.substance != null) {
			
			//Get Image from SIB
			BufferedImage structureImage = StructureImageBuilder.buildFormulaImage(this.substance);
			
			//Compute width/height and x/y coordinates
			int w1 = structureImage.getWidth();
			int h1 = structureImage.getHeight();
			while(w1 > getSize().getWidth() || h1 > getSize().getHeight()) {
				w1 = w1*9/10;
				h1 = h1*9/10;
			}
			
			int imgw = 
					structureImage.getScaledInstance(w1, h1, Image.SCALE_SMOOTH).getWidth(null);
			int imgh = 
					structureImage.getScaledInstance(w1, h1, Image.SCALE_SMOOTH).getHeight(null);
			
			int img_x = (int) ((getWidth() - imgw)/2);
			int img_y = (int) ((getHeight() - imgh)/2);
			
			g.drawImage(
					structureImage.getScaledInstance(
							w1, 
							h1, 
							Image.SCALE_SMOOTH), 
					img_x, 
					img_y, 
					null);
			
			//Draw formula
			AttributedString formula = substance.getIndexedFormula();
			formula.addAttribute(TextAttribute.SIZE, FONT_LABEL_NAME.getSize());
			//formula.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
			g.drawString(formula.getIterator(), text_x, text_y);
		}
		//Else draw dummy name
		else g.drawString(DEFAULT_NAME, text_x, text_y);
		
		//If selected, draw highlight
		if(isSelected) {
			g.setColor(HIGHLIGHT_COLOR);
			g.fill(new Rectangle2D.Double(
					0, 
					0, 
					getBounds().getWidth() - 1, 
					getBounds().getHeight() - 1));
		}
	}
	
	public void setSubstance(Substance s) {
		this.substance = s;
		this.repaint();
	}
}
