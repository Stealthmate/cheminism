package gui;

import java.awt.Dimension;
import java.awt.Font;

public class FontManager {

	public static final float WIDTH_TO_FONT_RATIO_LETTER = 0.69125f;
	public static final float WIDTH_TO_FONT_RATIO_INDEX  = 0.37875f;
	public static final float HEIGHT_TO_FONT_RATIO = 1.185f;
	public static final float HEIGHT_TO_FONT_RATIO_SUBSCRIPT = 0.37f;
	public static final float HEIGHT_TO_FONT_RATIO_LETTER = 0.815f;
	
	public static final Font calculateFont(Font base, Dimension bounds, int string_width) {
		Font f;
		f = base.deriveFont((float) bounds.width / (string_width*WIDTH_TO_FONT_RATIO_LETTER));
		
		if(f.getSize() * HEIGHT_TO_FONT_RATIO > bounds.getHeight()) 
			f = f.deriveFont((float) bounds.height / HEIGHT_TO_FONT_RATIO);
		
		return f;
	}
	
}
