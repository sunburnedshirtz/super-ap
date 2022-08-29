package pkg;
import java.util.*;
import java.io.*;

public class Reply extends Message {
	
	// Default Constructor
	public Reply() {
		auth = "";
		subj = "";
		bod = "";
		id = -1;
		childList = new ArrayList<Message>();
	}

	// Parameterized Constructor
	public Reply(String auth, String subj, String bod, int id) {
		this.auth = auth;
		this.subj = ("Re: "+subj);
		this.bod = bod;
		this.id = id;
		childList = new ArrayList<Message>();
	}

	// Returns if this is a reply (true)
	public boolean isReply(){
		return true;
	}
}
