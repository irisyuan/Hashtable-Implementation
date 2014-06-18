// file MicroFB2.java for Data Structures, Programming Assignment 2
// Iris Yuan

import java.io.*;
import java.util.*;

public class MicroFB2 {

	// Global hash table indexes each Person object under the String name
	protected static Hashtable<String,Person> allPeople = new Hashtable<String,Person>(); 

	// Q command uses hash table allFriends with key in name1*name2 format
	protected static Hashtable<String,Boolean> allFriends = new Hashtable<String,Boolean>();
	
	// Constructs two-person key for allFriends in alphabetical order
	public static String twoPersonKey(String name1, String name2){
		String key = "Person does not exist."; // This changes if both Persons exist
		if (allPeople.get(name1) != null && allPeople.get(name2) != null) {
			// Alphabetize name1 and name2
			if (name1.compareTo(name2) < 1) {
				key = name1 + "*" + name2;
			} else if (name1.compareTo(name2) >= 1) {
				key = name2 + "*" + name1;
			} 
		}
		return key;
	}
		
	// Commands from user input
	
	// P: Create a person
	public static void commandP(String name) {
		Person p = new Person(name);
		if (allPeople.get(name) != null) {  // If person already exists, exit
			System.out.println(name + " already exists."); 
			return; } 
		allPeople.put(name, p);
	}
	
	// F: Friend two persons
	public static void commandF(String name1, String name2) { 
		Person p1 = allPeople.get(name1); 	// Check if both Person objects are in allPeople
		Person p2 = allPeople.get(name2);
		if (p1 == null || p2 == null ) { 	// If not, exit
			System.out.println ("Person does not exist."); 
			return; }
		if (p1 == p2) { System.out.println("Cannot friend oneself."); return; }
		String key = twoPersonKey(name1, name2); // Construct two-person key for the two names
		if (allFriends.get(key) != null ) { System.out.println("Friends already."); return; } // If friends already, exit
		p1.getFriends().addFirst(p2); // Add each person to front of other person's friends linked list
		p2.getFriends().addFirst(p1); 
		allFriends.put(key,true); // Add two-person key to allFriends, with value true
	}
	
	// U: Unfriend two persons
	public static void commandU(String name1, String name2) { 
		Person p1 = allPeople.get(name1); 	// Check if both Person objects are in allPeople
		Person p2 = allPeople.get(name2);
		if (p1 == null || p2 == null ) { 	// If not, exit
			System.out.println ("Person does not exist."); 
			return; }
		String key = twoPersonKey(name1, name2); // Construct two-person key for the two names
		if (allFriends.get(key) == null || allFriends.get(key) == false) { // If not friends, exit
			System.out.println ("Cannot unfriend: Persons not friends before."); 
			return; }
		p1.getFriends().remove(p2); // Delete each person from other person's friends linked list
		p2.getFriends().remove(p1);
		allFriends.remove(key); // Delete two-person key from allFriends
	}
	
	// L: List all friends of person
	public static void commandL(String name) { 
		Person p = allPeople.get(name); 	// Check if Person is in allPeople
		if (p == null) { 					// If not, exit
			System.out.println(name + " does not exist."); 
			return; }
		String s = "[ ";
		for (Person list: p.getFriends()) { // Loop and print String names of each friend
			s += list.getName() + " ";
		}
		System.out.println(s + "]");
	}
	
	// Q: Print Yes or No if persons are friends
	public static void commandQ(String name1, String name2) { 
		if (name1.equals(name2)) { 					// If persons are the same, exit
			System.out.println("Same person."); 
			return; } 
		String key = twoPersonKey(name1, name2); 	// Construct the two-person key
		if (key.equals("Person does not exist.")) { // If person does not exist, exit
			System.out.println(key); 
			return; }
		if (allFriends.get(key) == null) { 
			allFriends.put(key, false);
		}
		// Find key in allFriends and print yes or no if friends
		if (allFriends.get(key) == true) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}
	}

	// Main method loops through input one line at a time
	// Calls sub-methods for possible user input in switch statement
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String line;
	
	while (input.hasNextLine()) {
		line = input.nextLine(); 
		Scanner sc = new Scanner(line);  
		String userCommand = sc.next();
	       
		if (userCommand.equals("X")) break; // Terminate program on X
			String name1 = sc.next();
			switch (userCommand.charAt(0)) {
			case 'P': commandP(name1); 
				break;
			case 'F': commandF(name1,sc.next()); // Requires name1, name2
	          	break;
			case 'U': commandU(name1,sc.next()); // Requires name1, name2
				break;
	        case 'Q': commandQ(name1,sc.next()); // Requires name1, name2
       			break;
	        case 'L': commandL(name1); 
	        	break;
			}
		}
	}
}
