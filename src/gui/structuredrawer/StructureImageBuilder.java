package gui.structuredrawer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

import gui.MainFrame;
import logic.Substance;

public class StructureImageBuilder {

	private static final Font MAIN_FONT_DRAW = new Font("Arial", Font.PLAIN, 20);
	private static final Font MAIN_FONT_INORGANIC = new Font("Arial", Font.PLAIN, 40);
	private static final Dimension INORGANIC_IMAGE_SCALE = new Dimension(40, 70);
	private static final int IMAGE_TYPE = BufferedImage.TYPE_INT_RGB;
	
	private static final int MARGIN_L = 2;
	private static final int MARGIN_R = 2;
	private static final int MARGIN_T = 3;
	private static final int MARGIN_B = 30;
	
	public static final int STROKE_WIDTH = 2;
	public static final int IMAGE_SCALE_MULTIPLIER = 3;
	public static final float CHAIN_LENGTH_MULT = 0.5774f;

	private static Graphics2D canvas;
	private static BufferedImage canvasimg;
	
	public static BufferedImage buildFormulaImage(Substance s, float scale) {

		int width = s.getFormula().length() * INORGANIC_IMAGE_SCALE.width;
		int height = INORGANIC_IMAGE_SCALE.height;
		System.out.println(width + " " + height);
		canvasimg = new BufferedImage(width, height, IMAGE_TYPE);
		canvas = canvasimg.createGraphics();
		canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//canvas.setColor(Color.CYAN);
		canvas.fill(new Rectangle2D.Double(0, 0, canvasimg.getWidth(), canvasimg.getHeight()));
		
		if(s!= null) {
			return buildInorganicImg(s);
		}
		else return buildOrganicImg();
	}
	
	public static BufferedImage buildOrganicImg() {

		canvas.setFont(MAIN_FONT_DRAW);
		canvas.setColor(Color.BLACK);
		canvas.setStroke(new BasicStroke(STROKE_WIDTH));
		
		Point2D start = new Point2D.Double(MARGIN_L, canvasimg.getHeight() - MARGIN_T);
		Point2D bond = start;

		start = Bond.draw(canvas, start, Atom.EMPTY, Atom.EMPTY, Bond.Direction.NE, Bond.SINGLE);
		bond = Bond.draw(canvas, start, Atom.EMPTY, Atom.O, Bond.Direction.N, Bond.DOUBLE);
		Atom.O.draw(canvas, bond);
		start = Bond.draw(canvas, start, Atom.EMPTY, Atom.O, Bond.Direction.SE, Bond.SINGLE);
		Atom.O.draw(canvas, start);
		start = Bond.draw(canvas, start, Atom.O, Atom.EMPTY, Bond.Direction.NE, Bond.SINGLE);
		start = Bond.draw(canvas, start, Atom.EMPTY, Atom.EMPTY, Bond.Direction.SE, Bond.SINGLE);

		return canvasimg;
	}

	private static BufferedImage buildInorganicImg(Substance s) {
		
		canvas.setFont(MAIN_FONT_INORGANIC);
		
		AttributedString formattedformula = s.getIndexedFormula();
		formattedformula.addAttribute(TextAttribute.SIZE, canvas.getFont().getSize());

		canvas.setColor(Color.BLACK);
		canvas.drawString(
				formattedformula.getIterator(), 
				MARGIN_L, MARGIN_T + canvas.getFontMetrics().getHeight());
		
		return canvasimg;
		
	}
}
