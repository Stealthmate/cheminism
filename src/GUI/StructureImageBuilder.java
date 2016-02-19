package GUI;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.net.SocketTimeoutException;

public class StructureImageBuilder {

	private static enum TextComponent {

		C("C"), H("H"), O("O"), OH("OH");

		String name;

		Point size() {

			return new Point(canvas.getFontMetrics().stringWidth(name), canvas.getFontMetrics().getAscent()-canvas.getFontMetrics().getDescent());
		}

		Point baseline(Point origin) {
			return new Point(origin.x - (size().x/2), origin.y + (size().y/2));
		}
		
		private TextComponent(String name) {
			this.name = name;
		}
	}

	private static class Point {
		float x;
		float y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		Point(float x, float y) {
			this.x = x;
			this.y = y;
		}

		Point add(Point p) {
			return new Point(p.x + x, p.y + y);
		}

		Point add(int n) {
			return new Point(x + n, y + n);
		}

		Point subtract(Point p) {
			return new Point(p.x - x, p.y - y);
		}

		Point multiply(int n) {
			return new Point(x * n, y * n);
		}

		Point multiply(Point p) {
			return new Point(x * p.x, y * p.y);
		}

		@Override
		public String toString() {
			return "(" + x + ";" + y + ")";
		}
	}

	private enum BondDirection {

		NORTH    (+0.0f, -1.0f, +0.0f, -1.5f), 
		NORTHEAST(+0.5f, -0.5f, +1.5f, -1.5f), 
		EAST     (+1.0f, +0.0f, +1.5f, +0.0f), 
		SOUTHEAST(+0.5f, +0.5f, +1.5f, +1.5f), 
		SOUTH    (+0.0f, +1.0f, +0.0f, +1.5f), 
		SOUTHWEST(-0.5f, +0.5f, -1.5f, +1.5f), 
		WEST     (-1.0f, +0.0f, -1.5f, +0.0f), 
		NORTHWEST(-0.5f, -0.5f, -1.5f, -1.5f);

		final Point add_start;
		final Point delta;

		public Point startPos(Point base, TextComponent txt) {
			return base.add(add_start.multiply(txt.size()));
		}

		private BondDirection(float dx, float dy, float addX, float addY) {
			delta = new Point(dx, dy);
			add_start = new Point(addX, addY);
		}
	}

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

	private static void drawTextComponent(Point origin, TextComponent txt) {
		canvas.drawString(txt.name, txt.baseline(origin).x, txt.baseline(origin).y);
	}

	// Returns the point where the bond ends;
	private static Point drawBond(Point start, BondDirection bd, TextComponent txtbegin, TextComponent txtend) {

		Point end = bd.startPos(start, txtbegin).add(bd.delta.multiply(BOND_LINE_LENGTH));
		
		canvas.draw(new Line2D.Double(bd.startPos(start, txtbegin).x, bd.startPos(start, txtbegin).y, end.x, end.y));

		Point next = end;
		System.out.println(end.toString());
		next.x += bd.add_start.x * txtend.size().x;
		next.y += bd.add_start.y * txtend.size().y;
		System.out.println(next.toString());
		System.out.println("End");
		return next;
	}

	private static void drawHydrogenBond(Point carbon, BondDirection bd) {

		Point hydro = drawBond(carbon, bd, TextComponent.C, TextComponent.H);
		drawTextComponent(hydro, TextComponent.H);
	}

	public static Image buildFormulaImage() {

		setScale(MainFrame.getScreenWidth(), MainFrame.getScreenHeight());

		BufferedImage canvasimg = new BufferedImage(img_w, img_h, BufferedImage.TYPE_INT_RGB);
		canvas = canvasimg.createGraphics();

		chain_width = 3;
		chain_height = 2;

		Point start = new Point(50, 50);

		drawTextComponent(start, TextComponent.C);
		//drawHydrogenBond(start, BondDirection.WEST);
		//drawHydrogenBond(start, BondDirection.NORTH);
		//drawHydrogenBond(start, BondDirection.SOUTH);
		start = drawBond(start, BondDirection.EAST, TextComponent.C, TextComponent.C);
		drawTextComponent(start, TextComponent.C);
		//drawHydrogenBond(start, BondDirection.NORTH);
		Point bi = start;
		start = drawBond(start, BondDirection.EAST, TextComponent.C, TextComponent.C);
		drawTextComponent(start, TextComponent.C);
		//drawHydrogenBond(start, BondDirection.NORTH);
		//drawHydrogenBond(start, BondDirection.SOUTH);
		//drawHydrogenBond(start, BondDirection.EAST);
		start = bi;
		start = drawBond(start, BondDirection.SOUTH, TextComponent.C, TextComponent.C);
		drawTextComponent(start, TextComponent.C);
		//drawHydrogenBond(start, BondDirection.WEST);
		//drawHydrogenBond(start, BondDirection.SOUTH);
		//drawHydrogenBond(start, BondDirection.EAST);
		

		return canvasimg;
	}

	private StructureImageBuilder() {
	}
}
