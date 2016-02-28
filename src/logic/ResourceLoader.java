package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class ResourceLoader {

	public static final String FILENAME_SUBSTANCES = "Substances.txt";
	public static final String FILENAME_REACTIONS = "Reactions.txt";
	
	private static final String SUBSTANCES_DELIMITER = " ";
	
	private static ArrayList<Reaction> reactions = new ArrayList<>();
	
	private static Substance parseSubstance(String str, int id) {
		
		String line = str;
		
		int delim = str.indexOf(SUBSTANCES_DELIMITER);
		String formula = line.substring(0, delim);
		line = line.substring(delim + 1);
		
		delim = line.indexOf(" / ");
		String fullname;
		if(delim > 0) {
			fullname = line.substring(0, delim);
			line = line.substring(delim+3);
		}
		else {
			fullname = line.substring(0);
			line = "";
		}
		Substance s = new Substance(id, formula.replace("_", " "), fullname.replace("_", " "), false);

		s.addTrivialName(line.replace('_', ' '));
		
		return s;
	}
	
	private static void loadSubstances() {
		try {
			List<String> lines = Files.readAllLines(new File(FILENAME_SUBSTANCES).toPath());
			int i = 0;
			for(String line : lines) {
				Substance.substances.add(parseSubstance(line, i));
				i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void loadReactions() {
		try {
			List<String> lines = Files.readAllLines(new File(FILENAME_SUBSTANCES).toPath());
			for(String line : lines) {
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void load() {
		loadSubstances();
		loadReactions();
	}
	
	public static ArrayList<Substance> getSubstanceListMatching(String query) {
		ArrayList<Substance> matches = new ArrayList<>();
		
		for(Substance s : Substance.substances) {
			if(s.getFormula().toLowerCase().startsWith(query.toLowerCase())) matches.add(s);
		}
		
		return matches;
	}
	
}
