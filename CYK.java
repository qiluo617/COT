package cyk;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class CYK {

	static CNFG grammar = new CNFG();
	
	static{
		//S -> A F
		grammar.addProduction("S","A","F");
		//F -> B C
		grammar.addProduction("F","B","C");
		//C -> B G
		grammar.addProduction("C","B","G");
		//G -> D B
		grammar.addProduction("G","D","B");
		//A -> a
		grammar.addProduction("A","a");
		//C -> c
		grammar.addProduction("C","c");
		//B -> b
		grammar.addProduction("B", "b");
		//D -> a
		grammar.addProduction("D", "a");
		//E -> b
		grammar.addProduction("E","b");
		//B -> E E
		grammar.addProduction("B", "E", "E");

		grammar.display();
	}
	
	private static TreeMap<Integer, ArrayList<ArrayList<String>>> tableD;
	
	static boolean isInCFL(String x, CNFG cnfg){
		if(x.length() < 1){
			return false;
		}
	
		//generate table D
		tableD = new TreeMap<Integer, ArrayList<ArrayList<String>>>();
		
		//FIRST ROW 
		ArrayList<ArrayList<String>> firstRow;
		firstRow = new ArrayList<ArrayList<String>>();
		
		for(int index=1; index<=x.length(); index++){
			ArrayList<String> cell = new ArrayList<String>();
			
			char c = x.charAt(index-1);
			String str=Character.toString(c);
			
			cell = cnfg.fetchLHS(str);
			firstRow.add(cell);
		}
		tableD.put(1, firstRow);
		
		// AFTER FIRST ROW
		for(int l=2; l<=x.length(); l++){
			ArrayList<ArrayList<String>> rowInfo;
			rowInfo = new ArrayList<ArrayList<String>>();
			
			for(int s=1; s<=x.length() - l + 1;s++){
				ArrayList<String> cell;
				cell = new ArrayList<String>();
	
				for(int k=1; k<=l-1; k++){				
					ArrayList<String> left = tableD.get(k).get(s-1);
					ArrayList<String> right = tableD.get(l-k).get(s-1+k);

					for(String str1: left){
						for(String str2:right){
							
							ArrayList<String> out = cnfg.fetchLHS(str1,str2);
							
							for(String output: out){
								if(!cell.contains(output)){
									cell.add(output);
								}
							}
						}
					}
				}
				rowInfo.add(cell);
			}
			tableD.put(l, rowInfo);
		}
		
		ArrayList<String> last = tableD.get(x.length()).get(0);
		
		if(last.contains("S")){
			return true;
		}
		
		return false;
	}
	
	public static void display() {
		for(Integer row : tableD.keySet()) {
			System.out.print(row + ": ");
			ArrayList<ArrayList<String>> rowlist = tableD.get(row);
			for(ArrayList<String> alist : rowlist.subList(0, rowlist.size())) {
				for(String v : alist) {
					System.out.print(v + " ");
				}
				System.out.print("|");
			}
			System.out.println(" ");
		}
	}
	
	static void unit_test_1(){
		System.out.println("Input string: abc");
		boolean bool = isInCFL("abc",grammar);
//		display();
		System.out.println("Result = " + bool);
	}
	
	static void unit_test_2(){
		System.out.println("Input string: abbbabb");
		boolean bool = isInCFL("abbbabb",grammar);
//		display();
		System.out.println("Result = " + bool);
	}
	
	static void unit_test_3(){
		System.out.println("Input string: abbc");
		boolean bool = isInCFL("abbc",grammar);
//		display();
		System.out.println("Result = " + bool);
	}
	
	static void unit_test_4(){
		System.out.println("Input string: bbc");
		boolean bool = isInCFL("bbc",grammar);
//		display();
		System.out.println("Result = " + bool);
	}
	
	static void unit_test_5(){
		System.out.println("Input string: aaabb");
		boolean bool = isInCFL("aaabb",grammar);
//		display();
		System.out.println("Result = " + bool);
	}

	public static void main(String[] args){
		System.out.println("=====UNIT TEST ====");
		unit_test_1();
		System.out.println("");
		unit_test_2();
		System.out.println("");
		unit_test_3();
		System.out.println("");
		unit_test_4();
		System.out.println("");
		unit_test_5();		
	}
}


