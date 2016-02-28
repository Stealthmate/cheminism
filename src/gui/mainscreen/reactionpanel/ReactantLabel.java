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
import java.net.SocketTimeoutException;
import java.text.AttributedString;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import gui.FontManager;
import gui.MainFrame;
import gui.mainscreen.SelectObserver;
import gui.structuredrawer.StructureImageBuilder;
import logic.Substance;

public class ReactantLabel extends JLabel {
	
	private static final String DEFAULT_NAME = "Reactant";
	private static final Font FONT_LABEL_NAME = new Font("Arial", Font.BOLD, 30);
	private static final Color HIGHLIGHT_COLOR = new Color(0x3300AFFF, true);
	private static final int DEFAULT_WIDTH = 30;
	
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
	
	public void select(SelectObserver.Lock l) {
		if(l == null) {
			System.out.println("DO NOT CALL THIS IF YOU'RE NOT SELECTOBSERVER!!!!");
			return;
		}
		
		
		this.isSelected = true;
		this.repaint();
	}
	
	public void deselect(SelectObserver.Lock l) {
		if(l == null) {
			System.out.println("DO NOT CALL THIS IF YOU'RE NOT SELECTOBSERVER!!!!");
			return;
		}
		
		this.isSelected = false;
		repaint();
	}

	private void setup(Dimension d) {
		
		setHorizontalAlignment(JLabel.CENTER);
		setVerticalAlignment(JLabel.CENTER);
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) 
					SelectObserver.selectReactant(ReactantLabel.this);
				else if(SwingUtilities.isRightMouseButton(e)) 
					SelectObserver.selectReactant(null);
			}
		});
		
		if(d!=null) {
			setPreferredSize(d);
		}
		isSelected = false;
	}
	
	public ReactantLabel() {
		super();
		this.substance = new Substance();
		setup(null);
		invalidate();
	}

	public ReactantLabel(Dimension d) {
		super();
		setup(d);
		this.substance = new Substance();
	    invalidate();
	}
	
	@Override
	protected void paintComponent(Graphics graphics) {
		
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		//Fill white
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		//Draw text
		g.setColor(Color.BLACK);
		
		g.setFont(FontManager.calculateFont(
				FONT_LABEL_NAME, 
				getPreferredSize(), 
				DEFAULT_WIDTH, 
				substance.getFormula().length()));
		
		int text_x = g.getFontMetrics().stringWidth("i");
		int text_y = g.getFontMetrics().getHeight();
		
		//If empty, draw name and image
		if(this.substance.getFullName().length() > 0) {
			
			//Get Image from SIB
			BufferedImage structureImage = 
					StructureImageBuilder.buildFormulaImage(
							this.substance,
							getWidth(),
							getHeight() - text_y);
			
			g.drawImage(
					structureImage, 
					0, 
					text_y, 
					null);
			
			//Draw full name
			g.drawString(this.substance.getFullName(), text_x, text_y);
		}
		//Else draw dummy name
		else {
			g.drawString(DEFAULT_NAME, text_x, text_y);
		}
		
		//Draw Border
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, (int) getBounds().getWidth() - 1, (int) getBounds().getHeight() - 1);
		
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
