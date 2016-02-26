package logic;

import java.util.ArrayList;

public class Atom {
	
	public ArrayList <Bond> bondNum; 
	
	public Atom() {
		bondNum = new ArrayList<>(4);
	}
	
	
	void printBonds(){
		System.out.println(bondNum.get(0).Bonds);
		System.out.println(bondNum.get(1).Bonds);
		System.out.println(bondNum.get(2).Bonds);
		System.out.println(bondNum.get(3).Bonds+"\n");
	}

}
