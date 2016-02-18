package GUI;

import java.awt.Dimension;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	
	
	
	public MainFrame(int width, int height, String name) {
		super(name);
		setSize(new Dimension(width, height));
		this.add(new GlobalPanel(width, height));
		this.invalidate();
	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		}
	
	
	
}
