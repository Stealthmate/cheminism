package resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class ResourceLoader {

	public static final String FILENAME_SUBSTANCES = "Substances.txt";
	public static final String FILENAME_REACTIONS = "Reactions.txt";
	
	private static final String SUBSTANCES_DELIMITER = " ";
	
	private static ArrayList<Substance> substances = new ArrayList<>();
	private static ArrayList<Reaction> reactions = new ArrayList<>();
	
	private static void loadSubstances() {
		try {
			List<String> lines = Files.readAllLines(new File(FILENAME_SUBSTANCES).toPath());
			for(String line : lines) {
				
				int idelim1 = line.indexOf(SUBSTANCES_DELIMITER);
				int idelim2 = line.substring(idelim1+1).indexOf(SUBSTANCES_DELIMITER) + idelim1 + 1;
				String name = line.substring(0, idelim1);
				int groupid = Integer.parseInt(line.substring(idelim1+1, idelim2));
				int id = Integer.parseInt(line.substring(idelim2+1));
				
				substances.add(new Substance(name, id, groupid));
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
		
		for(Substance s : substances) {
			if(s.getName().startsWith(query)) matches.add(s);
		}
		
		return matches;
	}
	
}
