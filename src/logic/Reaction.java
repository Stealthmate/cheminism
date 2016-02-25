package logic;

import java.util.ArrayList;

import javax.swing.text.html.parser.DocumentParser;

public class Reaction {

	private static final String AND_DELIMITER = "+";
	private static final String ARROW_DELIMITER = "=";
	private static final String GROUP_MARKER = "g";
	
	private ArrayList<Substance> reactants;
	private ArrayList<Substance> products;
	
	public static Reaction parseReaction(String str) {
		Reaction r = new Reaction();

		return r;
	}
	
	private Reaction() {
		reactants = new ArrayList<>();
		products = new ArrayList<>();
	}
	
	
	
}
