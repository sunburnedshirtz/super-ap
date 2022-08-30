package pkg;
import java.util.*;
import java.io.*;

public class BBoard {	
	List<Message> messageList = new ArrayList<Message>();
	ArrayList<User> userList = new ArrayList<User>();
	String boardTitle;
	Scanner sc = new Scanner(System.in);
	Scanner sc2;
	int currentUser = -1;
	int index = 0;
	
	public BBoard() {
		boardTitle = ("default board title");
	}

	public BBoard(String ttl) {	
		boardTitle = ttl;
	}

	public void loadUsers(String inputFile) throws FileNotFoundException {
		sc2 = new Scanner(new File(inputFile));
		int i;
		while (sc2.hasNextLine()) 
			{ String line = sc2.nextLine(); 
				for (i = 0; i < line.length()-1; i++) 
				{	if (line.substring(i, i+1).equals(" ")) 
					{ userList.add(new User(line.substring(0,i),line.substring(i+1,line.length()))); }
					}
			}
		login();
	}

	public void login(){
		System.out.print("Enter your username or press 'q' or 'Q' to cancel: "); 
		String username = sc.nextLine();
		System.out.print("Enter your password or press 'q' or 'Q' to cancel: ");
		String password = sc.nextLine();
		if(username.toUpperCase().equals("Q") || password.toUpperCase().equals("Q"))
			{ return; }
		for ( int i = 0; i < userList.size(); i++) 
		{	if(userList.get(i).check(username, password))
				{ currentUser = i; 
				 System.out.println("\nWelcome back, "+username+"!\n");
				 return; }
		}
		System.out.println("\nInvalid password, try again.\n");
		login();
	}
	
	public void run() {
			while (currentUser != -1) {
				System.out.println("--- Display Messages ('D' or 'd')\n--- Add New Topic ('N' or 'n')\n--- Add Reply ('R' or 'r')\n--- Change Password ('P' or 'p')--- Quit ('Q' or 'q')");
				String userChoice = sc.nextLine();
				if (userChoice.toUpperCase().equals("D"))
					{ display(); }
				else if (userChoice.toUpperCase().equals("N"))
					{ addTopic(); }
				else if (userChoice.toUpperCase().equals("R"))
					{ addReply(); }
				else if (userChoice.toUpperCase().equals("P"))
					{ setPassword(); }
				else if (userChoice.toUpperCase().equals("Q"))
					{ currentUser = -1; }
				else
					{ System.out.println("Invalid command."); }
			}
	}

	private void display(){
		int i, childListSize;
		for ( i = 0; i < messageList.size(); i++) {
			if(!(messageList.get(i).isReply())) {
				System.out.println("---------------------------------------");
				messageList.get(i).print(0);
			} 
		}
	}

	private void addTopic(){
		System.out.print("Subject: "); 
		String topicSubj = sc.nextLine();
		System.out.print("Body: "); 
		String topicBody = sc.nextLine();
		messageList.add(new Topic(userList.get(currentUser).getUsername(), topicSubj, topicBody, index+1));
		index++;
	}

	private void addReply(){
		System.out.print("Enter Message ID (-1 for Menu): ");
		int topic_to_reply_ID = sc.nextInt();
		sc.nextLine();
		
		if (topic_to_reply_ID == -1) 
			{ return; } 
		else if (topic_to_reply_ID > messageList.size()+1 || topic_to_reply_ID < -1)
			{ System.out.println("Not a valid ID");
			addReply(); }
		else
		 { System.out.print("Body: "); 
			String replyBody = sc.nextLine();
			
			messageList.add(new Reply(userList.get(currentUser).getUsername(), messageList.get(topic_to_reply_ID-1).getSubject(), replyBody, index+1));
			messageList.get(topic_to_reply_ID-1).addChild(messageList.get(index)); 
			index++; }
	}
	
	private void setPassword(){
		System.out.print("Enter current password, or press 'c' or 'C' to cancel: ");
		String currentPwd = sc.nextLine();
		System.out.print("Enter new password, or press 'c' or 'C' to cancel: ");
		String newPwd = sc.nextLine();

		if(!(userList.get(currentUser).check(currentPwd, newPwd)))
			{	if(currentPwd.toUpperCase().equals("C") || newPwd.toUpperCase().equals("C"))
				{ return; }
			System.out.println("Incorrect password, try again.");
			setPassword(); }
		userList.get(currentUser).check(currentPwd, newPwd);
	}
}
