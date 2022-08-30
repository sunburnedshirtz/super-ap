package pkg;
import java.util.*;
import java.io.*;

public class Topic extends Message {
	
	// Default Constructor
	public Topic() {
		auth = "";
		subj = "";
		bod = "";
		id = -1;
		childList = new ArrayList<Message>();
	}

	// Parameterized constructor
	public Topic(String auth, String subj, String bod, int id) {
		super(auth, subj, bod, id);
		childList = new ArrayList<Message>();
	}

	// Returns if it's a reply (false)
	public boolean isReply(){
		return false;
	}
}
