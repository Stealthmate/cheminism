package gui.mainscreen.reactionpanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.net.SocketTimeoutException;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import gui.structuredrawer.StructureImageBuilder;

public class ReactantLabel extends JLabel {
	
	private static final long serialVersionUID = 7319066579363738370L;
	private static final String DEFAULT_NAME = "Reactant";
	private static final Dimension IMAGE_SIZE = new Dimension(800, 600);
	
	private BufferedImage structureImage;
	private String name;
	private boolean isSelected;
	
	private static ReactantLabel now_selected = null;
	
	private static void selectMe(ReactantLabel me) {
		if(now_selected != null) {
			now_selected.isSelected = false;
			now_selected.repaint();
		}

		now_selected = me;
		me.isSelected = true;
		me.repaint();
	}

	private void setup(Dimension d) {
		
		setHorizontalAlignment(JLabel.CENTER);
		setVerticalAlignment(JLabel.CENTER);
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				selectMe(ReactantLabel.this);	
			}
		});
		
		if(d!=null) {
			setSize(d);
		}
		isSelected = false;
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
		g.drawString(name, 5, 15);
		g.draw(new Rectangle2D.Double(0, 0, getBounds().getWidth() - 1, getBounds().getHeight() - 1));
		if(isSelected) {
			g.setColor(new Color(0x3300AFFF, true));
			g.fill(new Rectangle2D.Double(0, 0, getBounds().getWidth() - 1, getBounds().getHeight() - 1));
		}
		
	}

}
