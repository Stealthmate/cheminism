package gui.mainscreen;

import java.awt.Color;
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
		this.setBackground(Color.CYAN);
		this.setOpaque(true);
		this.invalidate();
	}
	
	public void setSubstance(Substance s) {
		name = s.getFormula();
		structure = StructureImageBuilder.buildFormulaImage(s, getWidth(), getHeight());
		repaint();
	}
	
	/*@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println(getBounds());
		g.drawString(name, 5, 5);
		g.drawImage(structure, 5, 10, null);
	}*/
	

}
