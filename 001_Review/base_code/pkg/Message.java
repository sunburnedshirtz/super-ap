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

	// This function is responsbile for printing the Message
	// (whether Topic or Reply), and all of the Message's "subtree" recursively:

	// After printing the Message with indentation n and appropriate format (see output details),
	// it will invoke itself recursively on all of the Replies inside its childList, 
	// incrementing the indentation value at each new level.

	// Note: Each indentation increment represents 2 spaces. e.g. if indentation ==  1, the reply should be indented 2 spaces, 
	// if it's 2, indent by 4 spaces, etc. 
	public void print(int indentation) {
		//System.out.println("---------------------------------------");
		indent(indentation);
		System.out.println("Message #" +id+ ": \"" +subj+ "\"\nFrom " +auth+ ": " +bod);
		for (int i = 0; i < childList.size(); i++)
			{	childList.get(i).print(indentation+1); }
		if(indentation == childList.size())
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