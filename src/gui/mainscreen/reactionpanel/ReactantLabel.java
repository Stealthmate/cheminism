package gui.mainscreen.reactionpanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import gui.MainFrame;
import gui.structuredrawer.StructureImageBuilder;

public class ReactantLabel extends JLabel implements MouseListener {
	
	private static final long serialVersionUID = 7319066579363738370L;
	private static final String DEFAULT_NAME = "Reactant";
	private static final Dimension IMAGE_SIZE = new Dimension(800, 600);
	
	private BufferedImage structureImage;
	private String name;

	private void setup(Dimension d) {
		
		Graphics2D g = (Graphics2D) structureImage.createGraphics();
		//g.setPaint(Color.WHITE);
		//g.fillRect(0, 0, 800, 600);
		
		setHorizontalAlignment(JLabel.CENTER);
		setVerticalAlignment(JLabel.CENTER);
		
		if(d!=null) {
			setSize(d);
		}
	}
	
	public ReactantLabel() {
		super(DEFAULT_NAME);
		this.name = DEFAULT_NAME;
		structureImage = new BufferedImage(
				IMAGE_SIZE.width, IMAGE_SIZE.height, BufferedImage.TYPE_INT_RGB);
		structureImage = (BufferedImage) StructureImageBuilder.buildFormulaImage();
		
		setup(null);
		invalidate();
	}

	public ReactantLabel(Dimension d) {
		super(DEFAULT_NAME);
		this.name = DEFAULT_NAME;
		structureImage = new BufferedImage(
				IMAGE_SIZE.width, IMAGE_SIZE.height, BufferedImage.TYPE_INT_RGB);
		setup(d);
	    invalidate();
	}
	
	@Override
	protected void paintComponent(Graphics graphics) {
		
		int w1 = structureImage.getWidth();
		int h1 = structureImage.getHeight();
		while(w1 > getSize().getWidth() || h1 > getSize().getHeight()) {
			w1 = w1*9/10;
			h1 = h1*9/10;
		}
		
		Graphics2D g = (Graphics2D) graphics;
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		int imgw = structureImage.getScaledInstance(w1, h1, Image.SCALE_SMOOTH ).getWidth(null);
		int imgh = structureImage.getScaledInstance(w1, h1, Image.SCALE_SMOOTH ).getHeight(null);
		int imgx = (int) ((getBounds().getWidth() - imgw)/2);
		int imgy = (int) ((getBounds().getHeight() - imgh)/2);
		
		g.drawImage(structureImage.getScaledInstance(w1, h1, Image.SCALE_SMOOTH ), imgx, imgy, null);
		g.setColor(Color.BLACK);
		g.draw(new Rectangle2D.Double(0, 0, getBounds().getWidth() - 1, getBounds().getHeight() - 1));
		g.drawString(name, 5, 15);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(SwingUtilities.isRightMouseButton(e)) {
			//TODO Add Popup menu Builder
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	

}
