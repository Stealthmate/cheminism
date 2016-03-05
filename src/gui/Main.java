package gui;

import resources.ResourceLoader;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("Hey!");
		new MainFrame(
				MainFrame.DEFAULT_SIZE.width,
				MainFrame.DEFAULT_SIZE.height, 
				"Cheminism").setVisible(true);
		ResourceLoader.load();
	}
}
