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
				
				chain.get(i).bondNum.get(0).setBond(BondTypes.Single);
				chain.get(i).bondNum.get(1).setBond(BondTypes.Single);
			}
			else if(i==0&&len>1){
				chain.get(i).bondNum.get(0).bondTo(chain.get(i+1));
				chain.get(i).bondNum.get(0).setBond(BondTypes.Single);
				}
			else if(i==len-1&&len>1){
				chain.get(i).bondNum.get(0).bondTo(chain.get(i-1));
				chain.get(i).bondNum.get(0).setBond(BondTypes.Single);
				}
		}	
	}
}