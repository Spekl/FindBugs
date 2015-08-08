import java.util.*;

//
// This program contains two errors. Can you find them?
//
public class Example {
     //@ requires a > 0;
     //@ requires b > 0;
     //@ ensures \result == a+b;
     public static int add(int a, int b){
         return a-b;
     }
    

    public static void main(String args[]){
         System.out.println(add(2,3));

	 Map<String,String> aMap = new HashMap<String,String>();
	 
	 String person = aMap.get("bob");

	 if (person != null) {
	     System.out.println(person);
	 }

	 int name = person.length();
	 
     }
    
 }

