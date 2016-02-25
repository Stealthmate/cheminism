package logic;
import java.util.*;

public class CarbonChain {
	ArrayList <Carbon> chain;
	
	CarbonChain(){
		chain = new ArrayList<Carbon>(0);
		chain.add(new Carbon());
	}
	CarbonChain(int len){
		chain = new ArrayList<Carbon>(len);
		
		for(int i = 0; i < len; i++){
			chain.add(new Carbon());//initialise Carbon
		}	
		for(int i = 0; i < len; i++){ //connect all Carbon atoms with Single Bonds
			if(i>0&&i<len-1){
				chain.get(i).bondNum.get(0).bondTo(chain.get(i+1));
				chain.get(i).bondNum.get(1).bondTo(chain.get(i-1));
				
				//System.out.println(i);
				
				chain.get(i).bondNum.get(0).setBond(BondTypes.Single);
				chain.get(i).bondNum.get(1).setBond(BondTypes.Single);
			}
			else if(i==0&&len>1){
				chain.get(i).bondNum.get(1).bondTo(chain.get(i+1));
				chain.get(i).bondNum.get(1).setBond(BondTypes.Single);
				}
			else if(i==len-1&&len>1){
				chain.get(i).bondNum.get(0).bondTo(chain.get(i-1));
				chain.get(i).bondNum.get(0).setBond(BondTypes.Single);
				}
		}	
	}
	Carbon getCarbonOnPos(int pos){
		return chain.get(pos-1);
	}
	void addCarbonAtEnd(){
		
		Carbon Extendee = new Carbon();
		//Extendee.bondNum.get(0).setBond(BondTypes.Single);
		chain.add(Extendee);
		getCarbonOnPos(chain.size()-1).bondNum.get(1).bondTo = getCarbonOnPos(chain.size());
		getCarbonOnPos(chain.size()).bondNum.get(0).bondTo = getCarbonOnPos(chain.size()-1);
		
		getCarbonOnPos(chain.size()).bondNum.get(0).setBond(BondTypes.Single);
		getCarbonOnPos(chain.size()-1).bondNum.get(1).setBond(BondTypes.Single);
	}
	
	void extendCarbonChain(int ChainLength){
		for(int i = 0; i < ChainLength; i++){
			addCarbonAtEnd();
		}
	}
	
	int getChainLength(){
		return chain.size();
	}
	
	void scanChain(){
		Carbon z = chain.get(0);
		for(int i = 0; i < chain.size()-1;i++){
		z.printBonds();
		z = (Carbon)z.bondNum.get(1).bondTo;
		}
		z.printBonds();
	}
}