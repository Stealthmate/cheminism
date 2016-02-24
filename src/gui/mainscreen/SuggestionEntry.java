package gui.mainscreen;

import java.awt.Color;
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
		this.setBackground(Color.RED);
		this.setOpaque(true);
		//this.setIcon(new ImageIcon(StructureImageBuilder.buildFormulaImage()));
		
	}
	
}
