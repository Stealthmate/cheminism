package logic.reaction;

import java.util.ArrayList;

import logic.Substance;

public class Reaction {
	
	private ReactantSet reactants;
	private ConditionSet conditions;
	private ProductSet products;
	
	public Reaction(ReactantSet reactants, ConditionSet conditions, ProductSet products) {
		this.reactants = reactants;
		this.conditions = conditions;
		this.products = products;
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
	
}
