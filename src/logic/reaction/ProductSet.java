package logic.reaction;

import java.util.ArrayList;

import logic.Substance;

public class ProductSet {

	public static enum ProductFlags {
		PERCIPITATE(),
		GAS();
	}
	
	private ArrayList<Substance> products;
	private ArrayList<ProductFlags> flags;
	
	public ProductSet() {
		this.products = new ArrayList<>(3);
		this.flags = new ArrayList<>(3);
	}
	
	public ProductSet(ArrayList<Substance> products, ArrayList<ProductFlags> flags) {
		this.products = products;
		
		if(flags != null)this.flags = flags;
		else flags = new ArrayList<>();
	}
	
	@Override
	public String toString() {
		String res = "";
		
		res+= "Products:\n";
		for(int i=0;i<=products.size()-1;i++) {
			res += "    " + products.get(i).toString() + " flagged as " + flags.get(i).toString();
		}
		
		return res;
	}
	
}
