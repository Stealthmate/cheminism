package logic;
import java.util.*;

public class CarbonChain {
	ArrayList <CarbonAtom> chain;
	
	CarbonChain(){
		chain = new ArrayList<CarbonAtom>(0);
		chain.add(new CarbonAtom());
	}
	CarbonChain(int len){
		chain = new ArrayList<CarbonAtom>(len);
		
		for(int i = 0; i < len; i++){
			chain.add(new CarbonAtom());//initialise Carbon
		}	
		for(int i = 0; i < len; i++){ //connect all Carbon atoms with Single Bonds
			if(i>0&&i<len-1){
				chain.get(i).bondNum.get(0).bondTo(chain.get(i+1));
				chain.get(i).bondNum.get(1).bondTo(chain.get(i-1));
				
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
	CarbonAtom getCarbonOnPos(int pos){
		return chain.get(pos-1);
	}
	void addCarbonAtEnd(){
		
		CarbonAtom Extendee = new CarbonAtom();
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
		CarbonAtom z = chain.get(0);
		for(int i = 0; i < chain.size()-1;i++){
			z.printBonds();
			Atom a = z.bondNum.get(1).bondTo;
			if(!(a instanceof CarbonAtom)) 
			z = (CarbonAtom) z.bondNum.get(1).bondTo;
		}
		z.printBonds();
	}
}