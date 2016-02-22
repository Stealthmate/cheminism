package gui.structuredrawer;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Bond {
	
	public enum Direction {
		NE(+0.866, -0.500),// /
		N (+0.000, -1.000),// |
		NW(-0.866, -0.500),// \
		SW(-0.866, +0.500),// /
		S (+0.000, +1.000),// |
		SE(+0.866, +0.500);// \
		
		private final Point2D direction;
		
		private Direction(double x, double y) {
			direction = new Point2D.Double(x, y);
		}
	}
	
	public static final int SINGLE = 1;
	public static final int DOUBLE = 2;
	public static final int TRIPLE = 3;
	
	public static final int LENGTH = 20;
	public static final float MULTI_LINE_OFFSET = 2;
	
	public static Point2D draw(Graphics2D canvas, Point2D pos, Atom a1, Atom a2, Direction d, int type) {
		
		switch(type) {
		case 1: return drawSingle(canvas, pos, a1, a2, d);
		case 2: return drawDouble(canvas, pos, a1, a2, d);
		case 3: return drawTriple(canvas, pos, a1, a2, d);
		default: throw new IllegalArgumentException("Invalid bond type: " + type);
		}
	}
	
	private static Point2D drawSingle(Graphics2D canvas, Point2D pos, Atom a1, Atom a2, Direction d) {
		
		Point2D start = PMath.add(
				pos, 
				PMath.multiply(
						d.direction, 
						a1.getSize(canvas.getFontMetrics())));
		
		Point2D end = PMath.add(start, PMath.multiply(d.direction, LENGTH));
		
		Line2D bondline = new Line2D.Float(start, end);

		canvas.draw(bondline);

		
		
		return PMath.add(
				end, 
				PMath.multiply(
						d.direction, 
						a2.getSize(canvas.getFontMetrics())));
	}
	
	private static Point2D drawDouble(Graphics2D canvas, Point2D pos, Atom a1, Atom a2, Direction d) {
		
		Point2D start = PMath.add(
				pos, 
				PMath.multiply(
						d.direction, 
						a1.getSize(canvas.getFontMetrics())));
		
		Point2D end = PMath.add(start, PMath.multiply(d.direction, LENGTH));
		
		Point2D normal = new Point2D.Double(d.direction.getY(), -d.direction.getX());
		
		Line2D bondline1 = new Line2D.Float(
				PMath.add(
						start, 
						PMath.multiply(normal, MULTI_LINE_OFFSET)), 
				PMath.add(
						end,
						PMath.multiply(normal, MULTI_LINE_OFFSET)));
		
		normal = new Point2D.Double(-d.direction.getY(), d.direction.getX());
		System.out.println(normal);
		
		Line2D bondline2 = new Line2D.Float(
				PMath.add(
						start, 
						PMath.multiply(normal, MULTI_LINE_OFFSET)), 
				PMath.add(
						end,
						PMath.multiply(normal, MULTI_LINE_OFFSET)));
		
		canvas.draw(bondline1);
		canvas.draw(bondline2);
		
		return PMath.add(
				end, 
				PMath.multiply(
						d.direction, 
						a2.getSize(canvas.getFontMetrics())));
	}
	
	private static Point2D drawTriple(Graphics2D canvas, Point2D pos, Atom a1, Atom a2, Direction d) {
		//TODO
		return null;
	}
	
}
