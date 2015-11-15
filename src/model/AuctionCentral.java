package model;

import users.ACEmployee;
import users.AbstractUser;
import users.Bidder;
import users.NonProfitOrganization;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;


public class AuctionCentral {

  //Constants
	public static final int IACEMPLOYEE = 1;
	public static final int INPO = 2;
	public static final int IBIDDER = 3;
	
	public static final String BR_ONEBID = "You may not bid on an item more than once";
	
	private static final String LOGIN_MESSAGE = "Choose an action:";

  private static final String[] LOGIN_OPTIONS = {"[1] Login",
												"[2] Create new Account",
												"[3] Quit"};
	private static final String LOGIN_ERROR_MESSAGE = "Unrecognized input, please enter 1 to login, 2 to create new account.";
	private static final String GET_USERNAME_MESSAGE = "Please enter your Username: ";
	private static final String USER_NOT_FOUND_MESSAGE = "There was no user with that username,";
	private static final String NEW_USER_MESSAGE = "Please enter a new Username: ";
	private static final String NEW_USER_SPACE_ERROR = "Usernames cannot contain spaces.";
	private static final String USERNAME_COPY_ERROR_MESSAGE = "That username already exists, please enter a different username:";
	private static final String USER_TYPE_PROMPT_MESSAGE = "Select User Type:";
  private static final String[] USER_TYPES = {"[2] Non-Profit Organization", "[3] Bidder"};
  private static final String USER_TYPE_ERROR = "Incorrect input, please try again";
  private static final String LOGIN_SUCCESSFUL_MESSAGE = "You are now logged in as: ";
	private static final String NEXT_ACTION_MESSAGE = "Choose what action you would like to perform";

  //End Constants
	
	
	
	public static void main(String[] Args) {
		
		
		//initialize variables for the current user and the auction list
    int userOption;
		boolean quitFlag = false;
		AbstractUser currentUser = null;
		TreeSet<AbstractUser> userList = new TreeSet<AbstractUser>();

    //Map auctions by month for easy list of auctions in a calendar
		HashMap<Integer, List<Auction>> auctionList = new HashMap<Integer, List<Auction>>();


    //CHANGE HASHMAP KEY TO AN INT REPRESENTING THE MONTH THAT IT IS SCHEDULED



//		List<model.Auction> auctionList = new LinkedList<model.Auction>();
//		int userType;
		
		
		//Setup for testing purposes
		System.out.println("Testing Use");
		setupUsers(userList);
		System.out.println("User list: \n" + userList.toString() + "\n");
		//End setup for testing purposes


    do  {
      //show login option if there is no current user
      while (currentUser == null) {
        //Prompt for login
        System.out.println(LOGIN_MESSAGE);

        for (String loginOption : LOGIN_OPTIONS) {
          System.out.println(loginOption);
        }

        //Ask what type of login they would like to do
        //put response into option variable
        userOption = chooseOption();

        switch (userOption) {
          case 1:
            while ((currentUser = logIn(userList)) == null) {
              System.out.println(USER_NOT_FOUND_MESSAGE);
            }
            break;
          case 2:
            while ((currentUser = createNewUser(userList)) == null) {
              System.out.println(USERNAME_COPY_ERROR_MESSAGE);
            }
            break;
          case 3:
            System.exit(0);
          default: //if they don't choose any of the options, then print an error message
            System.out.println(LOGIN_ERROR_MESSAGE);
        }

        try {
          System.out.println(LOGIN_SUCCESSFUL_MESSAGE + currentUser.getUsername() + "\n");
        } catch (NullPointerException theException) {
          System.out.println("No currentUser, is null");
        }
      }
      //userType = currentUser.getUserType();

      //Print the options for the current user type.
      System.out.println(NEXT_ACTION_MESSAGE);

      //show the options of the current user, different for each subclass.
      currentUser.showOptions();
      System.out.println("[L] Log out");
      //prompt for user input
      userOption = chooseOption();

      if (userOption == -1) {
        System.out.println("Logging out\n");
        currentUser = null;
      } else {
        //carry out action depending on user type
        currentUser.doAction(userOption, auctionList, userList);
//        switch(currentUser.getUserType()) {
//          case 1:
//            doACEAction(userOption);
//        }
      }
    } while (userOption == -1);
		
	}

//  private static void doACEAction(final int userOption, Act) {
//    switch (userOption) {
//      case 1:
//        for (Auction)
//    }
//  }

  private static int chooseOption() {
		Scanner commandInput = new Scanner(System.in);
    if (commandInput.hasNext("L")) {
      return -1;
    }
		int returnVal = commandInput.nextInt();
		commandInput.nextLine();
		
		return returnVal;
	}
	
	private static int getUserType() {
		
		System.out.println(USER_TYPE_PROMPT_MESSAGE);
    for (String userTypeMessage : USER_TYPES) {
      System.out.println(userTypeMessage);
    }
		
		Scanner commandInput = new Scanner(System.in);
		int returnVal = commandInput.nextInt();
		
		return returnVal;
	}
	
	private static AbstractUser createNewUser(TreeSet<AbstractUser> theUserList) {

    AbstractUser newUser = null;
		System.out.println(NEW_USER_MESSAGE);
		
		Scanner commandInput = new Scanner(System.in);
		String userName = commandInput.nextLine();
		
		for (AbstractUser theUser : theUserList) {
			if (theUser.getUsername().equals(userName)) {
				return null;
			}
		}
		
		
		int userType = getUserType();
		boolean inputError = false;

      switch (userType) {
        case 1:
          newUser = new ACEmployee(userName);
          break;
        case 2:
          System.out.print("Enter the name of your non-profit:");
          String npoName = commandInput.nextLine();
          newUser = new NonProfitOrganization(userName, npoName);
          break;
        case 3:
          newUser = new Bidder(userName);
          break;
        default:
          System.out.println(USER_TYPE_ERROR);
          break;
      }

    try {
      theUserList.add(newUser);
    } catch (NullPointerException exception) {
      System.out.println("newUser was null");
    }

    return newUser;


  }
	
	private static AbstractUser logIn(TreeSet<AbstractUser> theUserList) {

		System.out.print(GET_USERNAME_MESSAGE);
		
		Scanner commandInput = new Scanner(System.in);
		String userName = commandInput.nextLine();
		
		for (AbstractUser theUser : theUserList) {
			if (theUser.getUsername().equals(userName)) {
				return theUser;
			}
		}
		
		return null;
	}
	
	private static void setupUsers(TreeSet<AbstractUser> theUsers) {
		ACEmployee newACEmployee = new ACEmployee("ACETester");
		Bidder newBidder = new Bidder("BidderTester");
		NonProfitOrganization newNPO = new NonProfitOrganization("NPOTest", "Test Organization");
		
		theUsers.add(newACEmployee);
		theUsers.add(newBidder);
		theUsers.add(newNPO);
		
	}
	
	private static void loadUserList() {
		//load the userlist with a list of users
	}
	
	private static void loadAuctionList() {
		//loads a list of auctions into the auctionList
	}
	
}
