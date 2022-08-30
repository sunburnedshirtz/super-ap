package pkg;
import java.util.*;
import java.io.*;

public class User {
  private String usr;
  private String pwd;
	
	public User() {
    usr = "";
    pwd = "";
	}
	
	public User(String usr, String pwd) {
    this.usr = usr;
    this.pwd = pwd;
	}

	public String getUsername(){
    return usr;
	}

	public String getPassword(){
		return pwd;
	}

	public boolean check(String usr, String pwd) {
    if (usr.equals("") && !pwd.equals("")) 
		{ return false; }
    else if (this.usr.equals(usr) && this.pwd.equals(pwd))
		{ return true; }
    else
		{ return false; }
  }

	public boolean setPassword(String oldPass, String newPass) {
		if (usr.equals(""))	// hopefully this checks against default user
		{ return false; }
		else if (oldPass.equals(pwd))
		{ pwd = newPass;
		return true; }
		else
		{ return false; }
	}
}
