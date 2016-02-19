package GUI;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	private static int WIDTH;
	private static int HEIGHT;
	
	public static int getFrameWidth() {
		return WIDTH;
	}
	
	public static int getFrameHeight() {
		return HEIGHT;
	}
	
	public static int getScreenWidth() {
		return (int) Toolkit.getDefaultToolkit().getScreenSize().width;
	}
	
	public static int getScreenHeight() {
		return (int) Toolkit.getDefaultToolkit().getScreenSize().height;
	}
	
	
	public MainFrame(int width, int height, String name) {
		super(name);

		WIDTH = width;
		HEIGHT = height;
		
		setSize(new Dimension(width, height));
		this.add(new GlobalPanel(width, height));
		this.invalidate();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		}
	
	
	
}
