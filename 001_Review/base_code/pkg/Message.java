package pkg;
import java.util.*;
import java.io.*;

public class Message {
	String auth, subj, bod;
	int id;
	ArrayList<Message> childList;
	
	public Message() {
	auth = "";
	subj = "";
	bod = "";
	id = -1;
	childList = new ArrayList<Message>();
}
	// Parameterized Constructor
	public Message(String auth, String subj, String bod, int id) {
		this.auth = auth;
		this.subj = subj;
		this.bod = bod;
		this.id = id;
		childList = new ArrayList<Message>();
	}

	public void print(int indentation) {
		indent(indentation);
		System.out.println("Message #" +id+ ": \"" +subj+ "\"\nFrom " +auth+ ": " +bod);
		for (int i = 0; i < childList.size(); i++)
			{	childList.get(i).print(indentation+1); }
		if(indentation == childList.size()-1)
			{ System.out.println("---------------------------------------"); }
	}

	private void indent(int indentation) {
		for (int i = 0; i < indentation * 2; i++)
			{ System.out.print(" "); }
	}
	
	// Default function for inheritance
	public boolean isReply(){
		return false; // for now.
	}

	// Returns the subject String
	public String getSubject(){
		return subj;
	} 

	// Returns the ID
	public int getId(){
		return id;
	}

	// Adds a child pointer to the parent's childList.
	public void addChild(Message child){
		childList.add(child);
	}

}