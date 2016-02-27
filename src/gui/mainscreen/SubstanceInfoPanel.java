package gui.mainscreen;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import gui.structuredrawer.StructureImageBuilder;
import logic.Substance;

public class SubstanceInfoPanel extends JPanel {
	
	private String name;
	private BufferedImage structure;
	private String info;
	
	public SubstanceInfoPanel() {
		super();
		this.name = "";
		this.structure = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		this.info = "";
	}
	
	/*
	 * TODO:
	 * Implement proper scaling of SIB images and REactantLabel
	 * 
	 * */
	
	
	public void setSubstance(Substance s) {
		name = s.getFormula();
		//structure = StructureImageBuilder.buildFormulaImage(s, 1);
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		//Graphics2D canvas = (Graphics2D) g;
		
		g.drawString(name, 5, 5);
		g.drawImage(structure, 5, 10, null);
	}
	

}
