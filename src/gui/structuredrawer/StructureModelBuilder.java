package gui.structuredrawer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gui.structuredrawer.StructureModel.Carbon;

public class StructureModelBuilder {

	
	private static final Pattern PATTERN_ATOM = Pattern.compile("^[A-Za-z]+");
	private static final Pattern PATTERN_BOND = Pattern.compile("^[\\-=#]");
	private static final Pattern PATTERN_SUBCHAIN = Pattern.compile("^\\[(.*?)\\]");
	private static final Pattern PATTERN_SUBCHAIN_ONE = Pattern.compile("^1([\\-=#][A-Za-z0-9]+),?");
	private static final Pattern PATTERN_SUBCHAIN_TWO = Pattern.compile("^2([\\-=#][A-Za-z0-9]+),?");
	
	public static StructureModel buildModel(String input) {
		
		String str = input;
		
		Matcher match_atom = PATTERN_ATOM.matcher(str);
		Matcher match_bond = PATTERN_BOND.matcher(str);
		Matcher match_subchain = PATTERN_SUBCHAIN.matcher(str);
		
		StructureModel structmodel = new StructureModel();
		
		Carbon root = structmodel.root;
		Carbon carbi = root;
		
		Bond.Direction d = Bond.Direction.NE;
		
		while(str.length() > 0) {
			
			//System.out.println(str);
			
			match_atom = PATTERN_ATOM.matcher(str);
			match_bond = PATTERN_BOND.matcher(str);
			match_subchain = PATTERN_SUBCHAIN.matcher(str);
			
			if(match_atom.find()) {
				System.out.println("Extend");
				root.nextatom = carbi;
				root = root.nextatom;
				carbi = new Carbon();
				str = str.substring(match_atom.group(0).length());
			}
			else if(match_bond.find()) {
				root.nextbond = new Bond(d, match_bond.group(0));
				d = d.reverse();
				str = str.substring(match_bond.group(0).length());
			}
			else if(match_subchain.find()) {
				
				String substr = match_subchain.group(1);
				
				Matcher match = PATTERN_SUBCHAIN_ONE.matcher(substr);
				if(match.find()) {
					
					String subch = match.group(1);
					
					Bond.Direction sided;
					if(d.equals(Bond.Direction.NE)) sided = Bond.Direction.S;
					else sided = Bond.Direction.N;
					root.upbond = new Bond(sided, subch.substring(0, 1));
					subch = subch.substring(1);
					root.upatom = new Atom(subch);
					substr = substr.substring(match.group(0).length());
					
				}

				System.out.println(substr);
				match = PATTERN_SUBCHAIN_TWO.matcher(substr);				
				if(match.find()) {
					
					String subch = match.group(1);
					System.out.println("2 " + subch);
					Bond.Direction sided;
					if(d.equals(Bond.Direction.NE)) sided = Bond.Direction.N;
					else sided = Bond.Direction.S;
					root.downbond = new Bond(sided, subch.substring(0, 1));
					subch = subch.substring(1);
					root.downatom = new Atom(subch);
				}
				
				str = str.substring(match_subchain.group(0).length());
			}
		}
		
		root = structmodel.root;
		while(root.nextatom!=null) root = root.nextatom;
		
		if(root.upbond != null) {
			if(d.equals(Bond.Direction.NE)) root.upbond.d = Bond.Direction.NE;
			else root.upbond.d = Bond.Direction.N;
		}
		
		if(root.downbond != null) {
			if(d.equals(Bond.Direction.NE)) root.downbond.d = Bond.Direction.S;
			else root.downbond.d = Bond.Direction.SE;
		}
		
		return structmodel;
	}
}
