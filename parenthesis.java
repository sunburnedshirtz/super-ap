import java.util.*;

class parenthesis {

		//remove duplicates method copied from geeksforgeeks because i am lazy :)
		public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {
  	
        ArrayList<T> newList = new ArrayList<T>();
 
        for (T element : list) {
  
            if (!newList.contains(element)) {
  
                newList.add(element);
            }
        }
  
        return newList;
    }
	
		public static List<String> generateParenthesis(int x) {
			
        if(x == 1) {
					return new ArrayList<String>(Arrays.asList("()"));
				}
		
        ArrayList<String> parenthesisSet = new ArrayList<String>();
		
        for(String str : generateParenthesis(x-1) ) {
            for(int i = 0; i < str.length(); i++){
              parenthesisSet.add(str.substring(0, i+1)+"()"+str.substring(i+1,str.length()));
            }            
        }
			
        List<String> list = new ArrayList<String>(removeDuplicates(parenthesisSet));
        return list;
			
    }

		public static void main(String args[]) {
			Scanner sc = new Scanner(System.in);
			System.out.print("Enter number of parenthesis: ");
			int parenthesisNum = sc.nextInt();
			System.out.println(generateParenthesis(parenthesisNum));
		
		}
	}
