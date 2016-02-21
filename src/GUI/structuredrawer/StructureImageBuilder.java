package GUI.structuredrawer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import GUI.MainFrame;

public class StructureImageBuilder {

	private static int img_w;
	private static int img_h;

	private static Graphics2D canvas;

	private static int BOND_LINE_WIDTH;
	private static int BOND_LINE_LENGTH;
	private static int FONT_SIZE_LETTER;
	private static int FONT_SIZE_NUMBER;
	private static int SUBSCRIPT_OFFSET;

	private static int chain_width; // 1.0*Straight bonds + 0.5*Angled bonds
	private static int chain_height;

	public static void setScale(int screen_width, int screen_height) {
		img_w = screen_width / 5;
		img_h = screen_height / 5;

		BOND_LINE_LENGTH = 10;
		BOND_LINE_WIDTH = 10;
		FONT_SIZE_LETTER = BOND_LINE_WIDTH;
		FONT_SIZE_NUMBER = (1 / 3) * FONT_SIZE_LETTER;
	}

	private static int space() {
		return (3 * FONT_SIZE_LETTER) + BOND_LINE_LENGTH;
	}

	public static Image buildFormulaImage() {

		setScale(MainFrame.getScreenWidth(), MainFrame.getScreenHeight());

		BufferedImage canvasimg = new BufferedImage(img_w, img_h, BufferedImage.TYPE_INT_RGB);
		canvas = canvasimg.createGraphics();
		
		chain_width = 3;
		chain_height = 2;
		
		canvas.fill(new Rectangle2D.Double(0, 0, canvasimg.getWidth(), canvasimg.getHeight()));
		canvas.setPaint(Color.BLACK);
		canvas.setFont(new Font("Arial", Font.PLAIN, 20));
		canvas.setStroke(new BasicStroke(2));
		
		Point2D start = new Point2D.Double(100, 90);
		Point2D bond = start;
		/*Atom.C.draw(canvas, start);
		bond = Bond.draw(canvas, start, Atom.C, Atom.H, Bond.Direction.N, Bond.SINGLE);
		Atom.H.draw(canvas, bond);
		bond = Bond.draw(canvas, start, Atom.C, Atom.H, Bond.Direction.S, Bond.SINGLE);
		Atom.H.draw(canvas, bond);
		bond = Bond.draw(canvas, start, Atom.C, Atom.H, Bond.Direction.SE, Bond.SINGLE);
		Atom.H.draw(canvas, bond);
		bond = Bond.draw(canvas, start, Atom.C, Atom.H, Bond.Direction.SW, Bond.SINGLE);
		Atom.H.draw(canvas, bond);*/
		

		start = Bond.draw(canvas, start, Atom.EMPTY, Atom.EMPTY, Bond.Direction.NE, Bond.SINGLE);
		bond = Bond.draw(canvas, start, Atom.EMPTY, Atom.O, Bond.Direction.N, Bond.DOUBLE);
		Atom.O.draw(canvas, bond);
		start = Bond.draw(canvas, start, Atom.EMPTY, Atom.O, Bond.Direction.SE, Bond.SINGLE);
		Atom.O.draw(canvas, start);
		start = Bond.draw(canvas, start, Atom.O, Atom.EMPTY, Bond.Direction.NE, Bond.SINGLE);
		start = Bond.draw(canvas, start, Atom.EMPTY, Atom.EMPTY, Bond.Direction.SE, Bond.SINGLE);
		
		/*
		
		Atom.C.draw(canvas, start);
		bond = Bond.draw(canvas, start, Atom.C, Atom.H, Bond.Direction.NW, Bond.SINGLE);
		Atom.H.draw(canvas, bond);
		bond = Bond.draw(canvas, start, Atom.C, Atom.H, Bond.Direction.SW, Bond.SINGLE);
		Atom.H.draw(canvas, bond);
		bond = Bond.draw(canvas, start, Atom.C, Atom.H, Bond.Direction.S, Bond.SINGLE);
		Atom.H.draw(canvas, bond);
		
		start = Bond.draw(canvas, start, Atom.C, Atom.H, Bond.Direction.NE, Bond.SINGLE);
		Atom.C.draw(canvas, start);
		bond = Bond.draw(canvas, start, Atom.C, Atom.H, Bond.Direction.N, Bond.SINGLE);
		Atom.OH.draw(canvas, bond);
		bond = Bond.draw(canvas, start, Atom.C, Atom.H, Bond.Direction.NE, Bond.SINGLE);
		Atom.H.draw(canvas, bond);
		bond = Bond.draw(canvas, start, Atom.C, Atom.O, Bond.Direction.SE, Bond.DOUBLE);
		Atom.O.draw(canvas, bond);
*/
		/*drawTextComponent(start, TextComponent.C);
		drawHydrogenBond(start, BondDirection.WEST);
		drawHydrogenBond(start, BondDirection.NORTH);
		drawHydrogenBond(start, BondDirection.SOUTH);
		start = drawBond(start, BondDirection.EAST, TextComponent.C, TextComponent.C);
		drawTextComponent(start, TextComponent.C);
		drawHydrogenBond(start, BondDirection.NORTH);
		Point2D bi = start;
		start = drawBond(start, BondDirection.EAST, TextComponent.C, TextComponent.C);
		drawTextComponent(start, TextComponent.C);
		drawHydrogenBond(start, BondDirection.EAST);
		drawHydrogenBond(start, BondDirection.NORTH);
		drawHydrogenBond(start, BondDirection.SOUTH);
		start = bi;
		start = drawBond(start, BondDirection.SOUTH, TextComponent.C, TextComponent.C);
		drawTextComponent(start, TextComponent.C);
		drawHydrogenBond(start, BondDirection.SOUTH);
		drawHydrogenBond(start, BondDirection.WEST);
		drawHydrogenBond(start, BondDirection.EAST);*/
		

		return canvasimg;
	}

	private StructureImageBuilder() {
	}
}
