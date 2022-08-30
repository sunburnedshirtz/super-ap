package pkg;
import java.util.*;
import java.io.*;

public class BBoard {		// This is your main file that connects all classes.
	// Think about what your global variables need to be.
	List<Message> messageList = new ArrayList<Message>();
	ArrayList<User> userList = new ArrayList<User>();
	String boardTitle;
	Scanner sc = new Scanner(System.in);
	Scanner sc2;
	int currentUser = -1;
	int index = 0;
	
	// Default constructor that creates a board with a defaulttitle, empty user and message lists,
	// and no current user
	public BBoard() {
		boardTitle = ("default board title");
	}

	// Same as the default constructor except it sets the title of the board
	public BBoard(String ttl) {	
		boardTitle = ttl;
	}

	// Gets a filename of a file that stores the user info in a given format (users.txt)
	// Opens and reads the file of all authorized users and passwords
	// Constructs a User object from each name/password pair, and populates the userList ArrayList.
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

	// Asks for and validates a user/password. 
	// This function asks for a username and a password, then checks the userList ArrayList for a matching User.
	// If a match is found, it sets currentUser to the identified User from the list
	// If not, it will keep asking until a match is found or the user types 'q' or 'Q' as username to quit
	// When the users chooses to quit, sayu "Bye!" and return from the login function
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
	
	// Contains main loop of Bulletin Board
	// IF and ONLY IF there is a valid currentUser, enter main loop, displaying menu items
	// --- Display Messages ('D' or 'd')
	// --- Add New Topic ('N' or 'n')
	// --- Add Reply ('R' or 'r')
	// --- Change Password ('P' or 'p')
	// --- Quit ('Q' or 'q')
	// With any wrong input, user is asked to try again
	// Q/q should reset the currentUser to 0 and then end return
	// Note: if login() did not set a valid currentUser, function must immediately return without showing menu
	public void run() {
			while (currentUser != -1) {
				/*
				for(int i = 0; i < messageList.size(); i++) {
					System.out.println("List: "+messageList.get(i).getSubject());
					System.out.println(messageList.get(i).getId());
				}
				*/
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

	// Traverse the BBoard's message list, and invote the print function on Topic objects ONLY
	// It will then be the responsibility of the Topic object to invoke the print function recursively on its own replies
	// The BBoard display function will ignore all reply objects in its message list
	private void display(){
		int i, childListSize;
		for ( i = 0; i < messageList.size(); i++) {
			if(!(messageList.get(i).isReply())) {
				//childListSize = messageList.get(i).childList.size();
				messageList.get(i).print(0);
			} 
		}
	}


	// This function asks the user to create a new Topic (i.e. the first message of a new discussion "thread")
	// Every Topic includes a subject (single line), and body (single line)

	/* 
	Subject: "Thanks"
	Body: "I love this bulletin board that you made!"
	*/

	// Each Topic also stores the username of currentUser; and message ID, which is (index of its Message + 1)

	// For example, the first message on the board will be a Topic who's index will be stored at 0 in the messageList ArrayList,
	// so its message ID will be (0+1) = 1
	// Once the Topic has been constructed, add it to the messageList
	// This should invoke your inheritance of Topic to Message
	private void addTopic(){
		System.out.print("Subject: "); 
		String topicSubj = sc.nextLine();
		System.out.print("Body: "); 
		String topicBody = sc.nextLine();
		messageList.add(new Topic(userList.get(currentUser).getUsername(), topicSubj, topicBody, index+1));
		index++;
	}

	// This function asks the user to enter a reply to a given Message (which may be either a Topic or a Reply, so we can handle nested replies).
	//		The addReply function first asks the user for the ID of the Message to which they are replying;
	//		if the number provided is greater than the size of messageList, it should output and error message and loop back,
	// 		continuing to ask for a valid Message ID number until the user enters it or -1.
	// 		(-1 returns to menu, any other negative number asks again for a valid ID number)
	
	// If the ID is valid, then the function asks for the body of the new message, 
	// and constructs the Reply, pushing back the Reply on to the messageList.
	// The subject of the Reply is a copy of the parent Topic's subject with the "Re: " prefix.
	// e.g., suppose the subject of message #9 was "Thanks", the user is replying to that message:
	/*
			Enter Message ID (-1 for Menu): 9
			Body: It was a pleasure implementing this!
	*/
	// Note: As before, the body ends when the user enters an empty line.
	// The above dialog will generate a reply that has "Re: Thanks" as its subject
	// and "It was a pleasure implementing this!" as its body.

	// How will we know what Topic this is a reply to?
	// In addition to keeping a pointer to all the Message objects in BBoard's messageList ArrayList
	// Every Message (wheather Topic or Reply) will also store an ArrayList of pointers to all of its Replies.
	// So whenever we build a Reply, we must immediately store this Message in the parent Message's list. 
	// The Reply's constructor should set the Reply's subject to "Re: " + its parent's subject.
	// Call the addChild function on the parent Message to push back the new Message (to the new Reply) to the parent's childList ArrayList.
	// Finally, push back the Message created to the BBoard's messageList. 
	// Note: When the user chooses to return to the menu, do not call run() again - just return fro mthis addReply function. 
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
			System.out.println("topic_to_reply_id: "+topic_to_reply_ID);
			System.out.println("index: "+index);
messageList.get(index-1).addChild(messageList.get(index)); 
			System.out.println("topic_to_reply_id: "+topic_to_reply_ID);
			System.out.println("index: "+index);
			index++; }
	}

	// This function allows the user to change their current password.
	// The user is asked to provide the old password of the currentUser.
	// 		If the received password matches the currentUser password, then the user will be prompted to enter a new password.
	// 		If the received password doesn't match the currentUser password, then the user will be prompted to re-enter the password. 
	// 		The user is welcome to enter 'c' or 'C' to cancel the setting of a password and return to the menu.
	// Any password is allowed except 'c' or 'C' for allowing the user to quit out to the menu. 
	// Once entered, the user will be told "Password Accepted." and returned to the menu.
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
		/*
		if(sc.nextLine().!equals(userList.get(currentUser).getUsername()))
			{	if(sc.nextLine().isUpperCase().equals("C"))
				{ return; }
			System.out.println("Incorrect password, try again.");
			setPassword(); }
		System.out.print("Enter new password, or press 'c' or 'C' to cancel: ")
			if(sc.nextLine().isUpperCase().equals("C"))
				{ return; }
			else
				userList.get(currentUser).setPassword()
					*/
	}
}
