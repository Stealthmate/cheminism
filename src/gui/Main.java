package gui;

import logic.ResourceLoader;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("Hey!");
		new MainFrame(1366, 768, "Cheminism").setVisible(true);
		ResourceLoader.load();
	}
}
