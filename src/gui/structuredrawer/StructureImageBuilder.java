package gui.structuredrawer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.net.SocketTimeoutException;
import java.text.AttributedString;

import gui.MainFrame;
import logic.Substance;

public class StructureImageBuilder {

	private static final float WIDTH_TO_FONT_RATIO_LETTER = 0.69125f;
	private static final float WIDTH_TO_FONT_RATIO_INDEX  = 0.37875f;
	private static final float HEIGHT_TO_FONT_RATIO = 1.185f;
	private static final float HEIGHT_TO_FONT_RATIO_SUBSCRIPT = 0.37f;
	
	private static final int MAX_FORMULA_LENGTH = 10;
	
	private static final Font MAIN_FONT_DRAW = new Font("Arial", Font.PLAIN, 20);
	private static final Font MAIN_FONT_INORGANIC = new Font("Arial", Font.PLAIN, 36);
	
	private static final int IMAGE_TYPE = BufferedImage.TYPE_INT_RGB;
	
	private static final int MARGIN_L = 20;
	private static final int MARGIN_R = 50;
	private static final int MARGIN_T = 10;
	private static final int MARGIN_B = 20;
	
	public static final int STROKE_WIDTH = 2;
	public static final int IMAGE_SCALE_MULTIPLIER = 3;
	public static final float CHAIN_LENGTH_MULT = 0.5774f;

	private static Graphics2D canvas;
	private static BufferedImage canvasimg;
	
	private static AttributedString SAMPLE_FORMULA() {
		AttributedString sample = new AttributedString("A2()");
		sample.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB, 1, 2);
		return sample;
	}
	
	public static BufferedImage buildFormulaImage(Substance s, int width, int height) {

		//Create image with w/h of provided area
		canvasimg = new BufferedImage(width, height, IMAGE_TYPE);
		//Prepare canvas
		canvas = canvasimg.createGraphics();
		canvas.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//canvas.setColor(Color.CYAN);
		canvas.fill(new Rectangle2D.Double(0, 0, canvasimg.getWidth(), canvasimg.getHeight()));
		
		//Compute font size based on width
		Font f = MAIN_FONT_INORGANIC.deriveFont(
				width / (WIDTH_TO_FONT_RATIO_LETTER * MAX_FORMULA_LENGTH));
		
		//If height is not enough to house formula, downscale to match
		if(f.getSize() * HEIGHT_TO_FONT_RATIO > height) {
			f = f.deriveFont(height / HEIGHT_TO_FONT_RATIO);
		}
				
		canvas.setFont(f);
		
		
		if(s.isOrganic()) {
			return null;
		}
		//If the compound i inorganic, draw its formula
		else {
			
			//Get and prepare formula as AttributedString
			AttributedString formula = s.getIndexedFormula();
			formula.addAttribute(TextAttribute.SIZE, canvas.getFont().getSize());
			
			//Make a dummy TextLayout to 
			//get height of formula with indexes (if actual formula doesn't have such
			TextLayout dummy = new TextLayout(SAMPLE_FORMULA().getIterator(), canvas.getFontRenderContext());
			
			//Compute Y coordinate of formula
			int fy = 
					(int) (
							(height - dummy.getBounds().getHeight() 
									+ (canvas.getFont().getSize() 
											* HEIGHT_TO_FONT_RATIO_SUBSCRIPT)) / 2);
			
			//Set TextLayout to actual formula in order to get its width
			dummy = new TextLayout(formula.getIterator(), canvas.getFontRenderContext());
			//Compute X coordinate
			int fx = -3 +(int) (width - dummy.getBounds().getWidth()) / 2;
			
			//Draw
			canvas.setColor(Color.BLACK);
			canvas.drawString(
					formula.getIterator(), 
					fx,
					fy);
			
			//Return the image
			return canvasimg;
			
		}
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
}
