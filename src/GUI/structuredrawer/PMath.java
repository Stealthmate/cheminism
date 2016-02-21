package GUI.structuredrawer;

import java.awt.geom.Point2D;

public class PMath {

	public static Point2D add(Point2D p1, Point2D p2) {
		return new Point2D.Double(p1.getX() + p2.getX(), p1.getY() + p2.getY());
	}
	
	public static Point2D add(Point2D p, double n) {
		return new Point2D.Double(p.getX() + n, p.getY() + n);
	}
	
	public static Point2D subtract(Point2D p1, Point2D p2) {
		return new Point2D.Double(p1.getX() - p2.getX(), p1.getY() - p2.getY());
	}
	
	public static Point2D subtract(Point2D p, double n) {
		return new Point2D.Double(p.getX() - n, p.getY() - n);
	}
	
	public static Point2D multiply(Point2D p1, Point2D p2) {
		return new Point2D.Double(p1.getX() * p2.getX(), p1.getY() * p2.getY());
	}
	
	public static Point2D multiply(Point2D p, double n) {
		return new Point2D.Double(p.getX() * n, p.getY() * n);
	}
	
	public static Point2D divide(Point2D p1, Point2D p2) {
		return new Point2D.Double(p1.getX() / p2.getX(), p1.getY() / p2.getY());
	}
	
	public static Point2D divide(Point2D p, double n) {
		return new Point2D.Double(p.getX() / n, p.getY() / n);
	}
}
