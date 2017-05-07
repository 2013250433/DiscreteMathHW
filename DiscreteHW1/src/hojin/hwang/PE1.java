package hojin.hwang;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class PE1 {
	
	private char inputChar;
	private String inputString;
	
	private Scanner scan;
	private ArrayList<Set> setList;
	private ArrayList<Relation> relationList;
	
	private Menu menu;
	String menuList = "a. Create a set \n"+"b. Show elements of a set \n"+"c. Create a cartesian product of two sets \n"
			+ "d. Create a relation by entering a finite number of pairs \n" + "e. Check if a relation is a equivelance relation\n"+
			"z. to end";
	
	public PE1(){
		menu = new Menu();
		scan = new Scanner(System.in);
		setList = new ArrayList<Set>();
		relationList = new ArrayList<Relation>();		
	}
	
	private class Set{
		private String name;
		private ArrayList<Character> setElements;
		
		
		public Set(String name) {
			this.name = name;
			setElements = new ArrayList<Character>();
			System.out.println("Enter Elements_ input one integer and press ENTER, type '!' to finish");
			
			while(inputChar != '!'){
				inputChar = scan.nextLine().charAt(0);
				setElements.add(inputChar);
			}
			
			setElements.remove(setElements.size()-1); //�������� �Է¹��� !�� �����ش�.
		}
	}
	
	private class Relation{
		private String name;
		private Iterator<Character> iterX, iterY, iter;
		private ArrayList<Character> relationElementX, relationElementY;
		
		private HashSet<Character> trimDomain = new HashSet<Character>();
		private HashSet<Character> trimRange = new HashSet<Character>();
		
		private ArrayList<Character> domainSet = new ArrayList<Character>();
		private ArrayList<Character> rangeSet = new ArrayList<Character>();
		
		public Relation(String name){
			this.name = name;
			relationElementX = new ArrayList<Character>();
			relationElementY = new ArrayList<Character>();
			System.out.println("Enter pairs_ input one integer and press ENTER, for each x and y(Domain&Range)"
					+ "type '!' to finish");
			
			while(true){
				System.out.print("Enter element x:");
				inputChar = scan.nextLine().charAt(0);
				relationElementX.add(inputChar);
				
				if(inputChar == '!')
					break;
				
				System.out.print("Enter element y:");
				inputChar = scan.nextLine().charAt(0);
				relationElementY.add(inputChar);
				System.out.println();
			}
			relationElementX.remove(relationElementX.size()-1); //���Ḧ ���� ! ���� �� ���ش�.
			
			iterX = relationElementX.iterator();
			iterY = relationElementY.iterator();
			
			System.out.print(name +" = {");
			
			while(iterX.hasNext() && iterY.hasNext()){
				Character elementX = iterX.next();
				Character elementY = iterY.next();
				System.out.print(" ("+ elementX + ", "+ elementY + ")");
				if(iterX.hasNext() && iterY.hasNext()){
					System.out.print(",");
				}
			}
			System.out.println(" }");
			convertToSet();
		}
		
		void convertToSet(){
			iterX = relationElementX.iterator();
			iterY = relationElementY.iterator();
			
			while(iterX.hasNext())
				domainSet.add(iterX.next());	
			while(iterY.hasNext())
				rangeSet.add(iterY.next());
			
			domainSet = new ArrayList<Character>(removeRepeat(domainSet, trimDomain));
			rangeSet  = new ArrayList<Character>(removeRepeat(rangeSet, trimRange));
			// �ߺ����� ���� ������ �Ҵ��� �ݴϴ�.
			System.out.println("X: "+domainSet);
			System.out.println("Y: "+rangeSet+"\n");
			
		}
		// relation�� domain�̳� range�� set���� ��ȯ���� �ݴϴ�.
		
		HashSet<Character> removeRepeat(ArrayList<Character> drSet, HashSet<Character> trimSet){
			iter = drSet.iterator();
			while(iter.hasNext()){
				trimSet.add(iter.next()); //HashSet�� ����ؼ� �ߺ��� �����մϴ�.
			}
			drSet.clear();
			//System.out.println(trimSet);
			return trimSet;
		}
		// relation�� set�� convert�� ��, �ߺ��Ǵ� ���Ҹ� ������ domain�� range�� ��Ÿ���ϴ�.
		
		Boolean isReflexive(){
			
			iter = domainSet.iterator();
			while(iter.hasNext()){
				char elementX =iter.next();
				
				iterX = relationElementX.iterator();
				iterY = relationElementY.iterator();
				
				boolean isThereXX;
				//���� domain ���ҿ� relation x,y ���ٸ� _�� (x,x) �̸� �� ���� ��Ÿ���ϴ�.
				//System.out.println("["+elementX+"]");
				
				while(iterX.hasNext() && iterY.hasNext()){// relation ������ �������� �ǹ��մϴ�.
					char reflexiveCheckX = iterX.next();
					char reflexiveCheckY = iterY.next();
					isThereXX = (reflexiveCheckX+"").equals(elementX+"") && (reflexiveCheckY+"").equals(elementX+"");
					//System.out.print(reflexiveCheckX+","+reflexiveCheckY);
					if(isThereXX){ // ���� �׷��� ���Ұ� ������
					//	System.out.println(" found it!");
						break;
					}
					else
					//	System.out.println(" nope...");
					if(!iterX.hasNext() && !isThereXX){
						System.out.println("this Relation is NOT Reflexive");
						return false;}
				}
			}
			System.out.println("this Relation is Reflexive");
			return true;
		}
		
		Boolean isSymmetric(){
			iterX = relationElementX.iterator();
			iterY = relationElementY.iterator();
			
			while(iterX.hasNext()){
				char elementX = iterX.next();
				char elementY = iterY.next();
				
				Iterator<Character> iterA = relationElementX.iterator();
				Iterator<Character> iterB = relationElementY.iterator();
				
				while(iterA.hasNext()){
					char symmeCheckX = iterA.next();
					char symmeCheckY = iterB.next();
					boolean isXYYX = (symmeCheckX+"").equals(elementY+"") && (symmeCheckY+"").equals(elementX+"");
					if(isXYYX){
						break;
					}
					
					if(!iterA.hasNext() && !isXYYX){
						System.out.println("this Relation is NOT Symmetric");
						return false;
					}
				}
			}
			System.out.println("this Relation is Symmetric");	
			return true;
		}
		
		Boolean isTransitive(){
			iterX = relationElementX.iterator();   
			iterY = relationElementY.iterator();
			while(iterY.hasNext()){
				char elementX = iterX.next();
				char elementY = iterY.next();
				Iterator<Character> iterA = relationElementX.iterator();
				Iterator<Character> iterB = relationElementY.iterator();
				//System.out.println("1st "+elementX +" "+ elementY);
				
				while(iterA.hasNext()){ //XY YZ�̶�� XZ�� �ִ� �� Ȯ���ϴ� ���̱⿡ range�� ���ҵ�� YZ�� �����ϴ��� ���� Ȯ���մϴ�.
					char elementAX = iterA.next();
					char elementBY = iterB.next();
					boolean isThereXYYZ = false;
					//System.out.println("2nd "+elementAX +" "+ elementBY);
					
					boolean isThereYZ = (elementAX+"").equals(elementY+"");
					if(isThereYZ){ // YZ�� �ִٸ� XZ�� �ִ��� Ȯ�����ݴϴ�.
						Iterator<Character> iterC = relationElementX.iterator();
						Iterator<Character> iterD = relationElementY.iterator();
						
						while(iterC.hasNext()){
							char elementCX = iterC.next();
							char elementDY = iterD.next();
							//System.out.println("3rd "+elementCX +" "+ elementDY);
							
							isThereXYYZ = (elementCX+"").equals(elementX+"") && (elementDY+"").equals(elementBY+"");
										
							if(isThereXYYZ){
								
								break;
							}
							
							if(!iterC.hasNext() && !isThereXYYZ){
								System.out.println("this Relation is NOT Transitive");			
								return false;
							}
						}
					}
					
					if(isThereXYYZ)
						break;
					
					if(!iterA.hasNext() && !isThereYZ){
						System.out.println("this Relation is NOT Transitive");			
						return false;
					}
				}
			}		
			System.out.println("this Relation is Transitive");	
			return true;
		}
	}
	
	private class Menu{
		private void createSet(){
			System.out.println("Enter set name: ");
			inputString = scan.nextLine();
			setList.add(new Set(inputString));
		}	
		
		private Set searchSet(String inputName){
			Iterator<Set> itr = setList.iterator();
			
			while(itr.hasNext()){
				Set picked = itr.next();
				if(picked.name.equals(inputName)){
					return picked;}
			}
			
			System.out.println("There is no such Set");
			return null;
		}
		
		private void showSet(){
			System.out.print("Enter set name: ");
			inputString = scan.nextLine();
			
			Iterator<Character> pitr = searchSet(inputString).setElements.iterator();
			System.out.print("{ ");
				while(pitr.hasNext()){
					System.out.print(pitr.next() + " ");
					// �Ǽ� : setElements.get(pitr.next())
				}
				System.out.println("}\n");	
		}
		
		private void createCart(){
			Set set1,set2;
			Iterator<Character> iter1;
			
			System.out.print("Enter set 1: ");
			inputString = scan.nextLine();
			set1 = searchSet(inputString);
			
			System.out.print("Enter set 2: ");
			inputString = scan.nextLine();
			set2 = searchSet(inputString);
			
			System.out.print("Enter name of Cartesian product: ");
			inputString = scan.nextLine();
			System.out.println();
			
			
			System.out.print(inputString + " =  { ");
			
			iter1 = set1.setElements.iterator();
				while(iter1.hasNext()){
					Character set1Element = iter1.next();
					for(int i=0;i<set2.setElements.size();i++){
						System.out.print("("+ set1Element + " "+ set2.setElements.get(i) + ") ");
					}
				}
			System.out.println("} \n");
		}
		
		public void createRelation() {
			System.out.print("Enter relation name: ");
			inputString = scan.nextLine();
			relationList.add(new Relation(inputString));
		}
		
		private void checkEquiv(){
			Relation pickedRelation = null;
			
			System.out.print("Enter relation name: ");
			inputString = scan.nextLine();
			Iterator<Relation> itr = relationList.iterator();
			while(itr.hasNext()){
				pickedRelation = itr.next();
				if(pickedRelation.name.equals(inputString)) 
					break;
				}
			
			boolean R = pickedRelation.isReflexive();
			boolean S = pickedRelation.isSymmetric();
			boolean T = pickedRelation.isTransitive();
			
			if(R&&S&&T)
				System.out.println("Therefore, this Relation is Equiavelent!\n");
			else
				System.out.println("Therefore, this Relation is NOT Equiavelent!\n");
		}	
	}
	
	private void start() {
		
		while(inputChar!='z'){
		System.out.println(menuList);
		inputChar = scan.nextLine().charAt(0);
		
		switch (inputChar){
			case 'a' :
				menu.createSet();
				break;
			case 'b' :
				menu.showSet();
				break;
			case 'c' :
				menu.createCart();
				break;
			case 'd' :
				menu.createRelation();
				break;
			case 'e' :
				menu.checkEquiv();
			}
		}
	}
	
	public static void main(String args[]){
		new PE1().start();
	}
}
