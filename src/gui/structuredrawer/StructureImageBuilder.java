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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gui.MainFrame;
import gui.structuredrawer.StructureModel.Carbon;
import logic.Substance;

public class StructureImageBuilder {

	private static final char SINGLE_BOND = '-';
	private static final char DOUBLE_BOND = '=';
	private static final char TRIPLE_BOND = '#';
	
	private static final char OPEN_SUB = '[';
	private static final char CLOSE_SUB = ']';
	
	private static final char OPEN_DOUBLE_SUB = '(';
	private static final char CLOSE_DOUBLE_SUB = ')';
	
	private static final Pattern PATTERN_ATOM = Pattern.compile("^[A-Za-z]+");
	private static final Pattern PATTERN_BOND = Pattern.compile("^[\\-=#]");
	private static final Pattern PATTERN_SUBCHAIN = Pattern.compile("^\\[(.*?)\\]");
	
	private static final float WIDTH_TO_FONT_RATIO_LETTER = 0.69125f;
	private static final float WIDTH_TO_FONT_RATIO_INDEX  = 0.37875f;
	private static final float HEIGHT_TO_FONT_RATIO = 1.185f;
	private static final float HEIGHT_TO_FONT_RATIO_SUBSCRIPT = 0.37f;
	
	private static final int MAX_FORMULA_LENGTH = 10;
	
	private static final Font MAIN_FONT_DRAW = new Font("Arial", Font.PLAIN, 12);
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
			return buildOrganicImg(s.getFormula());
		}
		//If the compound i inorganic, draw its formula
		else {
			if(s.getFormula().length() < 1) return canvasimg;
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
	
	public static void drawSimpleChain(String struct, Point2D start, Bond.Direction d) {
		String ptr = struct;
		Bond.Direction dir = d;
		Pattern pat = Pattern.compile("^[A-Za-z0-9]+");
		Matcher match = pat.matcher(ptr);

		Atom previous = null;
		
		while(match.find()) {
			Atom current = new Atom(match.group(0));
			
			current.draw(canvas, start);
			
			if(match.group(0).length() >= ptr.length()) break;
			
			previous = current;
			int b;
			
			switch(ptr.charAt(match.group(0).length())) {
			case SINGLE_BOND : b = Bond.SINGLE; break;
			case DOUBLE_BOND: b = Bond.DOUBLE; break;
			case TRIPLE_BOND: b = Bond.TRIPLE; break;
			default: b = -1;
			}
			
			start = Bond.draw(canvas, start, previous, current, dir, b);
			dir = dir.reverse();

			ptr = ptr.substring(match.group(0).length()+1);
			match = pat.matcher(ptr);
		}
	}
	
	public static String drawSubChain(String struct, Point2D start, Atom startatom, Bond.Direction d) {

		Matcher matchAtom = PATTERN_ATOM.matcher(struct);
		
		Pattern pos1 = Pattern.compile("^1([\\-=#])");
		Matcher m1 = pos1.matcher(struct);
		String ptr = struct;
		
		if(m1.find()) {
			Point2D bondend = start;
			
			ptr = ptr.substring(m1.group(0).length());

			matchAtom = PATTERN_ATOM.matcher(ptr);
			matchAtom.find();
			
			switch(m1.group(1).charAt(0)) {
			case SINGLE_BOND:
				bondend = Bond.draw(
						canvas, start, startatom, new Atom(
								matchAtom.group(0)), d, Bond.SINGLE);break;
			case DOUBLE_BOND:
				bondend = Bond.draw(
						canvas, start, startatom, new Atom(
								matchAtom.group(0)), d, Bond.DOUBLE);break;
			case TRIPLE_BOND:
				bondend = Bond.draw(
						canvas, start, startatom, new Atom(
								matchAtom.group(0)), d, Bond.TRIPLE);break;
			}
			
			new Atom(matchAtom.group(0)).draw(canvas, bondend);
			
			ptr = ptr.substring(matchAtom.group(0).length());
			
			Matcher sub = PATTERN_SUBCHAIN.matcher(ptr);

			if(sub.find()) {
				drawSubChain(sub.group(1), bondend, new Atom(matchAtom.group(0)), d);
				ptr = ptr.substring(sub.group(1).length() + 2);
			}
			
			
		}
		
		Pattern pos2 = Pattern.compile(",2([\\-=#])");
		Matcher m2 = pos2.matcher(struct);
		ptr = struct;
		d = Bond.Direction.SE;
		
		if(m2.find()) {
			ptr = ptr.substring(ptr.indexOf("2-"));
			
			Point2D bondend = start;

			ptr = ptr.substring(m2.group(0).length()-1);

			matchAtom = PATTERN_ATOM.matcher(ptr);
			matchAtom.find();
			
			switch(m2.group(1).charAt(0)) {
			case SINGLE_BOND:
				bondend = Bond.draw(
						canvas, start, startatom, new Atom(
								matchAtom.group(0)), d, Bond.SINGLE);break;
			case DOUBLE_BOND:
				bondend = Bond.draw(
						canvas, start, startatom, new Atom(
								matchAtom.group(0)), d, Bond.DOUBLE);break;
			case TRIPLE_BOND:
				bondend = Bond.draw(
						canvas, start, startatom, new Atom(
								matchAtom.group(0)), d, Bond.TRIPLE);break;
			}
			
			new Atom(matchAtom.group(0)).draw(canvas, bondend);
			
			ptr = ptr.substring(matchAtom.group(0).length());
			Matcher sub = PATTERN_SUBCHAIN.matcher(ptr);

			if(sub.find()) {
				drawSubChain(sub.group(1), bondend, new Atom(matchAtom.group(0)), d);
				ptr = ptr.substring(sub.group(1).length() + 2);
			}
			
			
		}
		
		return ptr;
		
	}
	
	private static void drawBranches(String struct, Point2D start, Atom startatom) {
		
	}
	
	public static BufferedImage buildOrganicImg(String struct) {

		StructureModel structmodel = StructureModelBuilder.buildModel(struct);
		
		
		canvas.setFont(MAIN_FONT_DRAW);
		canvas.setColor(Color.BLACK);
		canvas.setStroke(new BasicStroke(STROKE_WIDTH));

		Point2D start = new Point2D.Double(MARGIN_L + 10, (canvasimg.getHeight() - MARGIN_T)/2 + 10);
		
		Carbon carbi = structmodel.root;
		//System.out.println(structmodel.getLength());
		do {
			System.out.println("Draw carbi");
			carbi.draw(canvas, start);
			if(carbi.upatom != null) {
				System.out.println("Draw upchain");
				Point2D bond = Bond.draw(canvas, start, carbi, carbi.upatom, carbi.upbond.d, carbi.upbond.type);
				carbi.upatom.draw(canvas, bond);
			}
			if(carbi.downatom != null) {
				System.out.println("Draw downchain");
				Point2D bond = Bond.draw(canvas, start, carbi, carbi.downatom, carbi.downbond.d, carbi.downbond.type);
				carbi.downatom.draw(canvas, bond);
			}
		
			System.out.println("Draw next");
			if(carbi.nextatom != null) start = Bond.draw(canvas, start, carbi, carbi.nextatom, carbi.nextbond.d, carbi.nextbond.type);
			carbi = carbi.nextatom;
		}
		while(carbi!=null);
		
		
		
		return canvasimg;
		
		
		/*String ptr = struct;
		Matcher matchAtom = PATTERN_ATOM.matcher(ptr);
		Matcher matchBond = PATTERN_BOND.matcher(ptr);
		Matcher matchSub = PATTERN_SUBCHAIN.matcher(ptr);
		
		Bond.Direction d = Bond.Direction.NE;
		Atom thisatom = null;
		while(ptr.length() > 0) {
			
			if(matchSub.find()) {
				//System.out.println("Sub");
				if(d.equals(Bond.Direction.NE)) drawSubChain(matchSub.group(1), start, thisatom, Bond.Direction.S);
				else drawSubChain(matchSub.group(1), start, thisatom, Bond.Direction.N);
				//System.out.println(ptr);
				ptr = ptr.substring(matchSub.group(1).length() + 2);
				//System.out.println(ptr);
			}
			if(matchBond.find()) {
				//System.out.println("Bond");
				matchAtom = PATTERN_ATOM.matcher(ptr.substring(1));
				matchAtom.find();
				switch(matchBond.group(0).charAt(0)) {
				case SINGLE_BOND: 
					start = Bond.draw(canvas, start, thisatom, new Atom(matchAtom.group(0)), d, Bond.SINGLE);break;
				case DOUBLE_BOND: 
					start = Bond.draw(canvas, start, thisatom, new Atom(matchAtom.group(0)), d, Bond.DOUBLE);break;
				case TRIPLE_BOND: 
					start = Bond.draw(canvas, start, thisatom, new Atom(matchAtom.group(0)), d, Bond.TRIPLE);break;
				}
				
				d = d.reverse();
				int incr = matchBond.group(0).length();

				if(ptr.charAt(incr - 1) == '[') incr -= 1;
				ptr = ptr.substring(incr);
				
			}
			
			if(matchAtom.find()) {
				//System.out.println("Atom");
				thisatom = new Atom(matchAtom.group(0));
				thisatom.draw(canvas, start);
				
				ptr = ptr.substring(thisatom.name.length());
			}
			
			matchAtom = PATTERN_ATOM.matcher(ptr);
			matchBond = PATTERN_BOND.matcher(ptr);
			matchSub = PATTERN_SUBCHAIN.matcher(ptr);
			
			
		}

		return canvasimg;*/
	}
}
