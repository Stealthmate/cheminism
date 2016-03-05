package resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import logic.Substance;

public class SubstanceParser {

	private static final String DATA_DELIMITER = " ";
	private static final String SPACE_NAME = "_";
	private static final String TRIVIAL_NAMES_DELIMITER = " ";
	
	
	private static Substance parseSubstance(String str, int id) {

		String line = str;
		int delim = str.indexOf(DATA_DELIMITER);

		String formula = line.substring(0, delim);
		String state = Substance.STATE_ARBITRARY;
		if (formula.startsWith(Substance.STATE_AQUEOUS)) {
			state = Substance.STATE_AQUEOUS;
		} else if (formula.startsWith(Substance.STATE_SOLID)) {
			state = Substance.STATE_SOLID;
		} else if (formula.startsWith(Substance.STATE_CONCENTRATED)) {
			state = Substance.STATE_CONCENTRATED;
		} else if (formula.startsWith(Substance.STATE_DILUTED)) {
			state = Substance.STATE_DILUTED;
		}

		if (!state.equals(Substance.STATE_ARBITRARY)) {
			formula = formula.substring(state.length());
		}

		line = line.substring(delim + 1);

		delim = line.indexOf(TRIVIAL_NAMES_DELIMITER);

		String fullname;

		boolean isOrganic = false;

		if (delim > 0) {
			fullname = line.substring(0, delim);
			line = line.substring(delim + TRIVIAL_NAMES_DELIMITER.length());
		} else {
			fullname = line.substring(0);
			line = "";
		}
		
		//Check if substance is organic
		if (formula.contains("-") || formula.contains("=") || formula.contains("#") || fullname.equals("Benzene")
				|| fullname.equals("Methane"))
			isOrganic = true;

		ArrayList<String> trivial_names = new ArrayList<>();

		/*TODO: IMPLEMENT TRIVIAL NAMES*/
		
		
		Substance s = 
				new Substance(
						id, 
						formula.replace(SPACE_NAME, " "),
						fullname.replace(SPACE_NAME, " "),
						state, 
						trivial_names,
						isOrganic);

		s.addTrivialName(line.replace(SPACE_NAME, " "));

		return s;
	}

	public static boolean parseSubstances(String input) {

		List<String> lines = Arrays.asList(input.split("\n"));
		int i = 0;
		for (String line : lines) {
			Resources.substances.add(parseSubstance(line, i));
			i++;
		}

		return true;
	}

}
