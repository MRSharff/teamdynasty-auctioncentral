package users;

import model.Auction;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;


public abstract class AbstractUser implements Comparable<AbstractUser> {
	
	private String myUsername;
	private int myID;
	private int myType; //1 = ACE, 2 = NPO, 3 = users.Bidder
	
	public AbstractUser(final String theUsername, final int userType) {
		myUsername = theUsername;
    myType = userType;
	}
	
	public String getUsername() {
		return myUsername;
	}
	
	public int getID() {
		return myID;
	}
	
	public int getUserType() {
		return myType;
	}

  public int getOption(String theMessage) {
    Scanner userInput = new Scanner(System.in);
    System.out.println(theMessage);

    if (userInput.hasNextInt()) {
      return userInput.nextInt();
    }

    return -1;
  }
	
	public void logIn() {
		//code to login goes here
		//not needed to log in in this version
	}
	
	public String toString() {
		return myUsername;
	}
	
	public abstract void showOptions();

	public abstract void doAction(int theOption, HashMap<Integer, List<Auction>> theAuctionList,
			TreeSet<AbstractUser> theUserList);
	
//	public String toXML() {
//		String xml = "<User>\n" + myType + "," + ID;
//		
//		
//	}
}
