package gui.structuredrawer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class StructureImageBuilder {

	public static final Font MAIN_FONT_DRAW = new Font("Arial", Font.PLAIN, 20);
	
	public static final int STROKE_WIDTH = 2;
	public static final int IMAGE_SCALE_MULTIPLIER = 3;
	public static final float CHAIN_LENGTH_MULT = 0.5774f;

	private static Graphics2D canvas;

	public static Image buildFormulaImage() {

		int chain_length = 4;
		int chain_height = 3;
		int multx = (int) (chain_length * Bond.LENGTH * CHAIN_LENGTH_MULT * IMAGE_SCALE_MULTIPLIER);
		int multy = (int) (chain_height * Bond.LENGTH * IMAGE_SCALE_MULTIPLIER * 0.666);
		
		BufferedImage canvasimg = 
				new BufferedImage(
						multx, 
						multy, 
						BufferedImage.TYPE_INT_RGB);
		
		canvas = canvasimg.createGraphics();
		canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		canvas.fill(new Rectangle2D.Double(0, 0, canvasimg.getWidth(), canvasimg.getHeight()));
		canvas.setPaint(Color.BLACK);
		canvas.setFont(MAIN_FONT_DRAW);
		canvas.setStroke(new BasicStroke(STROKE_WIDTH));
		
		Point2D start = new Point2D.Double(10, canvasimg.getHeight() - 30);
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

	private StructureImageBuilder() {
	}
}
