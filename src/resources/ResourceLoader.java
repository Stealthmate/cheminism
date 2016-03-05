package resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import javax.imageio.stream.ImageOutputStreamImpl;

import logic.Reaction;
import logic.Substance;

public class ResourceLoader {

	public static final String FILENAME_SUBSTANCES = "Substances.txt";
	public static final String FILENAME_REACTIONS = "Reactions.txt";
	
	private static final String SUBSTANCES_DELIMITER = " ";
	
	private static ArrayList<Reaction> reactions = new ArrayList<>();
	
	private static Substance parseSubstance(String str, int id) {
		
		String line = str;
		int delim = str.indexOf(SUBSTANCES_DELIMITER);
		
		String formula = line.substring(0, delim);
		String state = Substance.STATE_ARBITRARY;
		if(formula.startsWith(Substance.STATE_AQUEOUS)) {
			state = Substance.STATE_AQUEOUS;
		}
		else if(formula.startsWith(Substance.STATE_SOLID)) {
			state = Substance.STATE_SOLID;
		}
		else if(formula.startsWith(Substance.STATE_CONCENTRATED)) {
			state = Substance.STATE_CONCENTRATED;
		}
		else if(formula.startsWith(Substance.STATE_DILUTED)) {
			state = Substance.STATE_DILUTED;
		}
		
		if(!state.equals(Substance.STATE_ARBITRARY)) {
			formula = formula.substring(state.length());
		}
		
		line = line.substring(delim + 1);
		
		delim = line.indexOf(" / ");
		
		String fullname;
		
		
		boolean isOrganic = false;
		
		if(delim > 0) {
			fullname = line.substring(0, delim);
			line = line.substring(delim+3);
		}
		else {
			fullname = line.substring(0);
			line = "";
		}
		if(formula.contains("-") || formula.contains("=") || formula.contains("#") || fullname.equals("Benzene") || fullname.equals("Methane")) 
			isOrganic = true;
		
		ArrayList<String> trivial_names = new ArrayList<>();
		
		Substance s = 
				new Substance(
						id, 
						formula.replace("_", " "), 
						fullname.replace("_", " "), 
						state,
						trivial_names, 
						isOrganic);

		s.addTrivialName(line.replace('_', ' '));
		
		return s;
	}
	
	private static void loadSubstances() {
		
		try(InputStream is = ResourceLoader.class.getResourceAsStream(FILENAME_SUBSTANCES)) {
		
			Scanner s = new Scanner(is).useDelimiter("\\A");
			String result = s.hasNext() ? s.next() : "";
	
			List<String> lines = Arrays.asList(result.split("\n"));
			int i = 0;
			for(String line : lines) {
				Substance.substances.add(parseSubstance(line, i));
				i++;
			}
			
		} catch (IOException e) {
			System.out.println("God bless us");
			e.printStackTrace();
		} finally {
			System.out.println("All seems well");
		}
	}
	
	private static void loadReactions() {
		
		try(InputStream is = ResourceLoader.class.getResourceAsStream(FILENAME_REACTIONS)) {
		
			Scanner s = new Scanner(is).useDelimiter("\\A");
			String result = s.hasNext() ? s.next() : "";
	
			List<String> lines = Arrays.asList(result.split("\n"));
			
			for(String line : lines) {
				/*TO DO IMPLEMENT*/
			}
			
		} catch (IOException e) {
			System.out.println("God bless us");
			e.printStackTrace();
		} finally {
			System.out.println("All seems well");
		}
	}
	
	public static void load() {
		
		loadSubstances();
		loadReactions();
	}
	
	public static ArrayList<Substance> getSubstanceListMatching(String query) {
		ArrayList<Substance> matches = new ArrayList<>();
		
		for(Substance s : Substance.substances) {
			if(s.getFormula().substring(s.getState().length() + 1).toLowerCase().startsWith(query.toLowerCase())) matches.add(s);
		}
		
		return matches;
	}
	
}
