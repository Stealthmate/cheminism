package gui;

import java.awt.Dimension;
import java.awt.Font;

public class FontManager {

	public static final float WIDTH_TO_FONT_RATIO_LETTER = 0.69125f;
	public static final float WIDTH_TO_FONT_RATIO_INDEX  = 0.37875f;
	public static final float HEIGHT_TO_FONT_RATIO = 1.185f;
	public static final float HEIGHT_TO_FONT_RATIO_SUBSCRIPT = 0.37f;
	public static final float HEIGHT_TO_FONT_RATIO_LETTER = 0.815f;
	
	public static final Font calculateFont(Font base, Dimension bounds, int default_width, int content_width) {
		Font f;
		
		int width = default_width;
		if(content_width > default_width) width = content_width;
		
		f = base.deriveFont((float) bounds.width / (width*WIDTH_TO_FONT_RATIO_LETTER));
		
		if(f.getSize() * HEIGHT_TO_FONT_RATIO > bounds.getHeight()) 
			f = f.deriveFont((float) bounds.height / HEIGHT_TO_FONT_RATIO);
		
		return f;
	}
	
}
