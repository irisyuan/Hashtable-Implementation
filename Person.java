// file Person.java for Data Structures, Programming Assignment 2
// Iris Yuan

import java.util.LinkedList;

public class Person {            

	private String name;				// Name field
	private LinkedList<Person> friends;	// Linked list to store friend(s) of each person

	// Constructor
	public Person(String n) {				
		name = n; 
		friends = new LinkedList<Person>(); }
	
	// Getters
	public String getName() { 		
		return name; }
	
	public LinkedList<Person> getFriends(){
		return friends; }
	
}
