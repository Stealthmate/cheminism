package logic.reaction;

import java.util.ArrayList;

import logic.Substance;

public class Reaction {
	
	private ReactantSet reactants;
	private ConditionSet conditions;
	private ProductSet products;
	private boolean reversable;
	
	public Reaction(ReactantSet reactants, ConditionSet conditions, ProductSet products, boolean reversable) {
		this.reactants = reactants;
		this.conditions = conditions;
		this.products = products;
		this.reversable = reversable;
	}
	
	public ReactantSet getReactants() {
		return this.reactants;
	}
	
	public ConditionSet getConditions() {
		return this.conditions;
	}
	
	public ProductSet getProducts() {
		return this.products;
	}
	
	public boolean reversable() {
		return this.reversable;
	}
	
	
	@Override
	public String toString() {
		String res = "";
		
		res += reactants.toString() + "\n";
		res += conditions.toString() + "\n";
		res += products.toString() + "\n";
		res += reversable + "\n";
		
		return res;
	}
}
