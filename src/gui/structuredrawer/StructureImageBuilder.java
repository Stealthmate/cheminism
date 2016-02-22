package gui.structuredrawer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import gui.MainFrame;

public class StructureImageBuilder {

	public static final Font MAIN_FONT_DRAW = new Font("Arial", Font.PLAIN, 20);
	public static final int STROKE_WIDTH = 2;
	public static final int IMAGE_SCALE_MULTIPLIER = 3;
	public static final float CHAIN_LENGTH_MULT = 0.5774f;
	
	private static int img_w;
	private static int img_h;

	private static Graphics2D canvas;

	private static int BOND_LINE_WIDTH;
	private static int BOND_LINE_LENGTH;
	private static int FONT_SIZE_LETTER;
	private static int FONT_SIZE_NUMBER;
	private static int SUBSCRIPT_OFFSET;


	public static void setScale(int screen_width, int screen_height) {
		img_w = screen_width / 5;
		img_h = screen_height / 5;

		BOND_LINE_LENGTH = 10;
		BOND_LINE_WIDTH = 10;
		FONT_SIZE_LETTER = BOND_LINE_WIDTH;
		FONT_SIZE_NUMBER = (1 / 3) * FONT_SIZE_LETTER;
	}

	public static Image buildFormulaImage() {

		setScale(MainFrame.getScreenWidth(), MainFrame.getScreenHeight());

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
		
		//canvas.setPaint(Color.RED);
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
