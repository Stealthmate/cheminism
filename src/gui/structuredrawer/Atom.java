package gui.structuredrawer;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class Atom {

	public static final Atom EMPTY = new Atom("");
	public static final Atom H = new Atom("H");
	public static final Atom C = new Atom("C");
	public static final Atom O = new Atom("O");
	public static final Atom OH = new Atom("OH");
	
	
	public final String name;
	
	public Atom(String name) {
		this.name = name;
	}
	
	
	public Point2D getSize(FontMetrics fm) {
		double width = fm.stringWidth(name);
		double height;
		if (name.length() > 0) height = fm.getAscent() - fm.getDescent();
		else height = 0;
		
		return new Point2D.Double(width, height);
	}
	
	public void draw(Graphics2D canvas, Point2D position) {
		float blx = (float) (position.getX() - getSize(canvas.getFontMetrics()).getX()/2);
		float bly = (float) (position.getY() + getSize(canvas.getFontMetrics()).getY()/2);
		
		canvas.drawString(name, blx, bly);
	}
}
