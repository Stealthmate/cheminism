package gui.mainscreen;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import gui.MainFrame;
import gui.structuredrawer.StructureImageBuilder;

public class SuggestionEntry extends JLabel {
	
	private String name;
	private BufferedImage thumbnail;
	
	public SuggestionEntry(String name) {
		super(name);
		this.name = name;
		this.thumbnail = 
				new BufferedImage(
						300, 
						100, 
						BufferedImage.TYPE_INT_RGB);
		
		Graphics2D graphics = ((BufferedImage) thumbnail).createGraphics();
		
		graphics.drawString("C", 20, 200);
		graphics.drawString("C", 20 + graphics.getFont().getSize()*2, 200);
		
		this.setIcon(new ImageIcon(StructureImageBuilder.buildFormulaImage()));
		
	}
	
}
