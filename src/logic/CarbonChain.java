package logic;

public class CarbonChain {
	
	
	int lenght = 10;
	
	Carbon[] chain;
	
	void setLenght(int len){
		lenght=len;
	}
	
	CarbonChain(){
		lenght = 0;
	}
	CarbonChain(int len){
		lenght = len;
		//System.out.println(lenght);
		chain = new Carbon[lenght];
		
	}
	

}