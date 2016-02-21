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

	public static Image buildFormulaImage(BufferedImage canvasimg) {

		setScale(MainFrame.getScreenWidth(), MainFrame.getScreenHeight());

		canvas = canvasimg.createGraphics();
		
		canvas.fill(new Rectangle2D.Double(0, 0, canvasimg.getWidth(), canvasimg.getHeight()));
		canvas.setPaint(Color.BLACK);
		canvas.setFont(new Font("Arial", Font.PLAIN, 20));
		canvas.setStroke(new BasicStroke(2));
		
		Point2D start = new Point2D.Double(canvasimg.getWidth()/3, canvasimg.getHeight()/2 + 10);
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
