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

import gui.MainFrame;
import gui.structuredrawer.StructureImageBuilder;
import logic.Substance;

public class ReactantLabel extends JLabel {
	
	private static final String DEFAULT_NAME = "Reactant";
	private static final Font FONT_LABEL_NAME = new Font("Arial", Font.BOLD, 30);
	private static final Color HIGHLIGHT_COLOR = new Color(0x3300AFFF, true);
	private static final float DEFAULT_SCALE = 2.3f;
	
	private Substance substance;
	private boolean isSelected;
	
	private static ReactantLabel now_selected = null;
	
	public static ReactantLabel getSelected() {
		return now_selected;
	}
	
	public static void selectMe(ReactantLabel me) {
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
			setPreferredSize(d);
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
	
	@Override
	protected void paintComponent(Graphics graphics) {
		
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		float scale = DEFAULT_SCALE *
				(float) Math.sqrt(
						Math.pow(getPreferredSize().width, 2) + Math.pow(getPreferredSize().height, 2))
				/ (float) Math.sqrt(
						Math.pow(
								MainFrame.DEFAULT_SIZE.width, 2) + Math.pow(MainFrame.DEFAULT_SIZE.height, 2));
		
		//Fill white
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		//Draw text
		g.setColor(Color.BLACK);
		g.setFont(FONT_LABEL_NAME.deriveFont(
				(float) (FONT_LABEL_NAME.getSize() * scale)));
		
		int text_x = g.getFontMetrics().stringWidth("i");
		int text_y = g.getFontMetrics().getHeight();
		
		//If empty, draw name and image
		if(this.substance != null) {
			
			//Get Image from SIB
			BufferedImage structureImage = 
					StructureImageBuilder.buildFormulaImage(
							this.substance,
							scale);
			
			//Compute width/height and x/y coordinates
			int w1 = structureImage.getWidth();
			int h1 = structureImage.getHeight();
			while(w1 > getSize().getWidth() || h1 > getSize().getHeight() - text_y) {
				w1 = w1*9/10;
				h1 = h1*9/10;
			}
			
			int imgw = 
					structureImage.getScaledInstance(w1, h1, Image.SCALE_SMOOTH).getWidth(null);
			int imgh = 
					structureImage.getScaledInstance(w1, h1, Image.SCALE_SMOOTH).getHeight(null);
			
			int img_x =  (getWidth() - imgw)/2;
			int img_y = text_y;
			
			g.drawImage(
					structureImage.getScaledInstance(
							w1, 
							h1, 
							Image.SCALE_SMOOTH), 
					img_x, 
					img_y, 
					null);
			
			//Draw full name
			g.drawString(this.substance.getFullName(), text_x, text_y);
		}
		//Else draw dummy name
		else g.drawString(DEFAULT_NAME, text_x, text_y);
		
		//Draw Border
		g.setColor(Color.BLACK);
		g.draw(new Rectangle2D.Double(0, 0, getBounds().getWidth() - 1, getBounds().getHeight() - 1));
		
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
