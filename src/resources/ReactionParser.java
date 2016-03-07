package resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logic.reaction.ConditionSet;
import logic.reaction.ProductSet;
import logic.reaction.ReactantSet;
import logic.reaction.Reaction;
import logic.Substance;

public class ReactionParser {
	
	private static final String SUBSTANCE_DELIM = "+";
	
	private static final String REACTANT_PRODUCT_DELIM = "=";
	
	private static final String REACTION_REVERSE_FLAG = "r";
	
	private static final String CONDITIONS_DELIM = ";";
	private static final String CONDITIONS_VALUE_DELIM = ":";
	private static final String CONDITION_TEMPERATURE = "t";
	private static final String CONDITION_PRESSURE = "p";
	private static final String CONDITION_CATALYST = "c";
	
	private static final String PRODUCT_FLAG_PRECIPITATE = "!";
	private static final String PRODUCT_FLAG_GAS = "?";
	
	private static final Pattern PATTERN_REACTANTS = 
			Pattern.compile("(.+?)" + REACTANT_PRODUCT_DELIM);
	
	private static final Pattern PATTERN_ORGANIC_SUBSTANCE = 
			Pattern.compile("\\{(.+)\\}");
	
	private static final Pattern PATTERN_MOL = 
			Pattern.compile("^[0-9]+");
	
	private static final Pattern PATTERN_CONDITIONS = 
			Pattern.compile(REACTANT_PRODUCT_DELIM + "(.*)" + REACTANT_PRODUCT_DELIM);
	
	private static final Pattern PATTERN_PRODUCTS = 
			Pattern.compile(REACTANT_PRODUCT_DELIM + ".*" + REACTANT_PRODUCT_DELIM + "(.+)");
	
	private static Reaction parseReaction(String input) throws InvalidReactionException {
		
		//Parse reactants
		Matcher match = PATTERN_REACTANTS.matcher(input);
		
		if(!match.find()) throw new InvalidReactionException("Could not parse reactants", input);
		
		String reactants = match.group(1);
		
		String splitdelim = "\\" + SUBSTANCE_DELIM; //escape +
		String[] reactants_arr = reactants.split(splitdelim);
		
		ArrayList<Substance> substReactants = new ArrayList<>(3);
		ArrayList<Integer> mols = new ArrayList<>(3);
		
		
		for(String s : reactants_arr) {

			String str = s;
			
			match = PATTERN_MOL.matcher(str);
			if(match.find()) {
				mols.add(Integer.parseInt(match.group(0)));
				str = s.substring(match.group(0).length());
			}
			else mols.add(1);
			
			match = PATTERN_ORGANIC_SUBSTANCE.matcher(s);
			if(match.find()) {
				Substance sub = Resources.queryOrganicSubstanceByName(match.group(1));
				if(sub != null) str = sub.getFormula();
				else {
					System.out.println("INVALID ORGANIC SUBSTANCE " + match.group(1) + " IN " + input);
				}
			}
			
			Substance sub = Resources.querySubstance(str);
			if(sub!=null) substReactants.add(sub);
			else {
				System.out.println("INVALID SUBSTANCE " + input);
			}
		}
		
		ReactantSet reactset = new ReactantSet(substReactants, mols);
		//System.out.println(reactset.toString());
		
		//Parse conditions
		match = PATTERN_CONDITIONS.matcher(input);
		
		if(!match.find()) throw new InvalidReactionException("Could not parse conditions", input);
		
		String conditions = match.group(1);
		
		int temp = ConditionSet.NO_TEMPERATURE;
		boolean pressure = false;
		String catalyst = ConditionSet.NO_CATALYST;
		boolean reversable = false;
		
		
		if(conditions.startsWith(REACTION_REVERSE_FLAG)) {
			reversable = true;
			conditions = conditions.substring(REACTION_REVERSE_FLAG.length());
		}
		
		if(conditions.startsWith(CONDITION_TEMPERATURE)) {
			temp = ConditionSet.TEMPERATURE_ARBITRARY;
			conditions = conditions.substring(CONDITION_TEMPERATURE.length());
			if(conditions.startsWith(CONDITIONS_VALUE_DELIM)) {
				//System.out.println(conditions);
				
				int endindex = conditions.indexOf(CONDITIONS_DELIM);
				if(endindex < 0) endindex = conditions.length();
				String value = 
						conditions.substring(CONDITIONS_VALUE_DELIM.length(), endindex);
				
				temp = Integer.parseInt(value);
				conditions = conditions.substring(value.length());
			}
		}
		
		if(conditions.startsWith(CONDITION_PRESSURE)) {
			pressure = true;
			conditions = conditions.substring(CONDITION_PRESSURE.length());
		}
		
		if(conditions.startsWith(CONDITION_CATALYST)) {
			
			conditions = conditions.substring(CONDITION_CATALYST.length());
			
			String value = conditions.substring(0, conditions.indexOf(CONDITIONS_DELIM));
			catalyst = value;
			conditions = conditions.substring(value.length());
		}
		
		ConditionSet condset = new ConditionSet(temp, pressure, catalyst);
		
		//Parse Products
		
		match = PATTERN_PRODUCTS.matcher(input);
		
		if(!match.find()) throw new InvalidReactionException("Could not parse products", input);
		
		String products = match.group(1);
		
		String[] products_arr = reactants.split(splitdelim);
		
		ArrayList<Substance> substProducts = new ArrayList<>(3);
		ArrayList<ProductSet.ProductFlags> flags = new ArrayList<>(3);
		mols = new ArrayList<>(3);
		
		
		for(String s : products_arr) {
			
			match = PATTERN_MOL.matcher(s);
			if(match.find()) {
				mols.add(Integer.parseInt(match.group(0)));
			}
			else mols.add(1);
			
			int endindex = s.length();
			
			if(s.endsWith(PRODUCT_FLAG_PRECIPITATE)) {
				flags.add(ProductSet.ProductFlags.PERCIPITATE);
				endindex -= PRODUCT_FLAG_PRECIPITATE.length();
			}
			else if(s.endsWith(PRODUCT_FLAG_GAS))
			{
				flags.add(ProductSet.ProductFlags.GAS);
				endindex -= PRODUCT_FLAG_GAS.length();
			}
			else flags.add(null);
			
			Substance sub = Resources.querySubstance(s.substring(0, endindex));
			if(sub!=null) substProducts.add(sub);
		}
		
		ProductSet productset = new ProductSet(substProducts, flags, mols);
		
		Reaction reaction = new Reaction(reactset, condset, productset, reversable);
		//System.out.println(reaction.toString());
		return reaction;
		
		
	}
	
	public static boolean parseReactions(String input) {

		List<String> lines = Arrays.asList(input.split("\n"));
		int i = 0;
		for (String line : lines) {
			try {
				Resources.reactions.add(parseReaction(line));
			} catch (InvalidReactionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}

		return true;
	}
}
