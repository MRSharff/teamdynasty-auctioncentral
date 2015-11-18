package model;

import users.ACEmployee;
import users.AbstractUser;
import users.Bidder;
import users.NonProfitOrganization;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;


public class AuctionCentral {

  //Constants
	public static final int IACEMPLOYEE = 1;
	public static final int INPO = 2;
	public static final int IBIDDER = 3;
  public static final int MAX_AUCTIONS_PER_DAY = 2;
	
	public static final String BR_ONEBID = "You may not bid on an item more than once";

  private static final String DATABASE_PATH = "db/AuctionCalendar.ser";
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
    int userOption = 1;
		boolean quitFlag = false;
		AbstractUser currentUser = null;
    AuctionCalendar myCalendar = null;

    myCalendar = loadAuctionCalendar(DATABASE_PATH);
    if (myCalendar == null) {
      System.out.println("Testing Use");
      setupUsers(myCalendar);
      System.out.println("Creating new ac file");
      myCalendar = new AuctionCalendar();
    }

		
		//Setup for testing purposes

		System.out.println("User list: \n" + myCalendar.getMyUserList().values().toString() + "\n");
		//End setup for testing purposes


    do {
      //show login option if there is no current user
      while (currentUser == null && userOption != 3) {
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
            while ((currentUser = logIn(myCalendar.getMyUserList())) == null) {
              System.out.println(USER_NOT_FOUND_MESSAGE);
            }
            break;
          case 2:
            while ((currentUser = createNewUser(myCalendar.getMyUserList())) == null) {
              System.out.println(USERNAME_COPY_ERROR_MESSAGE);
            }
            break;
          case 3:
            break;
          default: //if they don't choose any of the options, then print an error message
            System.out.println(LOGIN_ERROR_MESSAGE);
        }

        if (userOption != 3) {
          try {
            System.out.println(LOGIN_SUCCESSFUL_MESSAGE + currentUser.getUsername() + "\n");
          } catch (NullPointerException theException) {
            System.out.println("No currentUser, is null");
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
            userOption = -2; //quick fix. Not optimal
            currentUser = null;
          } else {
            //carry out action depending on user type
            currentUser.doAction(userOption, myCalendar);
//        switch(currentUser.getUserType()) {
//          case 1:
//            doACEAction(userOption);
//        }
          }
        }
      }
    } while (userOption != 3);

    save(DATABASE_PATH, myCalendar);
    System.out.println("Closing Program...");
		
	}

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
	
	private static AbstractUser createNewUser(HashMap<String, AbstractUser> theUserList) {

    AbstractUser newUser = null;
		System.out.println(NEW_USER_MESSAGE);
		
		Scanner commandInput = new Scanner(System.in);
		String userName = commandInput.nextLine();

    if (theUserList.keySet().contains(userName.toLowerCase())) {
      return null;
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
      theUserList.put(newUser.getUsername().toLowerCase(), newUser);
    } catch (NullPointerException exception) {
      System.out.println("newUser was null");
    }

    return newUser;


  }
	
	private static AbstractUser logIn(HashMap<String, AbstractUser> theUserList) {

		System.out.print(GET_USERNAME_MESSAGE);
		
		Scanner commandInput = new Scanner(System.in);
		String userName = commandInput.nextLine();
		
		if (theUserList.keySet().contains(userName.toLowerCase())) {
      return theUserList.get(userName.toLowerCase());
    }
		
		return null;
	}
	
	private static void setupUsers(AuctionCalendar theCalendar) {
		ACEmployee newACEmployee = new ACEmployee("ACETester");
		Bidder newBidder = new Bidder("BidderTester");
		NonProfitOrganization newNPO = new NonProfitOrganization("NPOTest", "Test Organization");
    NonProfitOrganization newNPO2 = new NonProfitOrganization("NPOTest2", "Second Organization");



		theCalendar.getMyUserList().put(newACEmployee.getUsername().toLowerCase(), newACEmployee);
		theCalendar.getMyUserList().put(newBidder.getUsername().toLowerCase(), newBidder);
    theCalendar.getMyUserList().put(newNPO.getUsername().toLowerCase(),newNPO);
    theCalendar.getMyUserList().put(newNPO2.getUsername().toLowerCase(),newNPO2);


		
	}
	
	private static void loadUserList() {
		//load the userlist with a list of users
	}
	
	private static void loadAuctionList() {
		//loads a list of auctions into the auctionList
	}

  private static AuctionCalendar loadAuctionCalendar(final String thePath) {
    AuctionCalendar temp = null;

    try {
      FileInputStream fileIn = new FileInputStream(thePath);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      temp = (AuctionCalendar) in.readObject();
      in.close();
      fileIn.close();
    } catch (IOException ioException) {
      System.out.println("Load database IO Exception.");
      //ioException.printStackTrace();
    } catch (ClassNotFoundException cnfException) {
      System.out.println("AuctionCalendar class not found.");
      //cnfException.printStackTrace();
    }
    return temp;
  }

  private static void save(final String theFilePath, AuctionCalendar theCalendar) {
    File file = new File(DATABASE_PATH);

    //if there is no file by found in DATABASE_PATH, create one
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException ioException) {
        System.out.println("Error, file note created");
      }
    }

    //write serialized file
    try {
      FileOutputStream fileOut = new FileOutputStream(theFilePath);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(theCalendar);
      out.close();
      fileOut.close();
    } catch (IOException theException) {
      System.out.println("Save error, IOException");
      theException.printStackTrace();
    }

    System.out.println("Saving...");
  }
}
