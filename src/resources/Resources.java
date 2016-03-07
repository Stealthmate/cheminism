package resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import logic.Substance;
import logic.reaction.Reaction;

public class Resources {

	public static final String FILENAME_SUBSTANCES = "Substances.txt";
	public static final String FILENAME_REACTIONS = "Reactions.txt";

	/*package-private*/ static ArrayList<Substance> substances = new ArrayList<>();
	/*package-private*/ static ArrayList<Reaction> reactions = new ArrayList<>();
	
	
	
	private static void loadSubstances() {
		
		try(InputStream is = Resources.class.getResourceAsStream(FILENAME_SUBSTANCES)) {
		
			byte [] buff = new byte[is.available()];
			is.read(buff);
			
			SubstanceParser.parseSubstances(new String(buff, Charset.forName("UTF-8")).substring(1));
			
		} catch (IOException e) {
			System.out.println("God bless us");
			e.printStackTrace();
		} finally {
			System.out.println("All seems well");
		}
	}
	
	private static void loadReactions() {
		
		try(InputStream is = Resources.class.getResourceAsStream(FILENAME_REACTIONS)) {
		
			BufferedReader bf = new BufferedReader(new InputStreamReader(is));
			String res = "";
			
			//If this is not here, things get weird. No idea why
			bf.read();
			bf.read();
			bf.read();
			
			while(bf.ready()) res += bf.readLine() + "\n";
			
			ReactionParser.parseReactions(res);
			
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
		
		for(Substance s : substances) {
			if(s.getFormula().substring(s.getState().length()).toLowerCase().startsWith(query.toLowerCase())) matches.add(s);
		}
		
		return matches;
	}
	
	
	public static Substance querySubstance(String name) {
		for (Substance s : substances) {
			if(s.getFormula().equals(name)) return s;
		}
		return null;
	}

	public static Substance queryOrganicSubstanceByName(String name) {
		for (Substance s : substances) {
			if(s.isOrganic() && s.getFullName().equals(name)) return s;
		}
		return null;
	}
	
}
