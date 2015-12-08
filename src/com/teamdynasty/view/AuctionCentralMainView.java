package com.teamdynasty.view;

import com.teamdynasty.model.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class AuctionCentralMainView {

  //Constants

  public static final String WELCOME_MSG = "Welcome to AuctionCentral\n" +
          "-----------------";

  public static final String GET_USERNAME_MSG = "Please enter your Username: ";

  public static final String NEW_USER_MSG = "Please enter a new Username: ";

  public static final String USER_NOT_FOUND_MSG = "Account not found.";

  public static final String DUPLICATE_USERNAME_MSG = "That username already exists, please enter a different username:";

  public static final String USER_TYPE_PROMPT_MESSAGE = "Select User Type:";

  public static final String ENTER_NP_MSG = "Enter the name of your non-profit:";

  public static final String CREATE_USER_SUCCESS_MSG = "Account created successfully";

  public static final String LOGIN_ERROR_MESSAGE = "Unrecognized input, please enter 1 to login, 2 to create new account.";

  public static final String CHOOSE_OPTION_MSG = "\nChoose an option\n" +
          "-----------------";

  public static final String INPUT_ERROR_MSG = "Incorrect Input";

  /**Login options */
  public static String[] LOGIN_OPS = {"Login",
          "Create Account"};

  public static final String LOGOUT_OPTION = "Log Out";

  public static final String CANCEL_OPTION = "Cancel";

  public static final String BACK_OPTION = "Back";

  public static final String EXIT_OPTION = "Save and Exit";

  public static final String ACTION_CANCELED_MSG = "Action Canceled";

  public static final String[] USER_TYPES = {"Auction Central Employee",
          "Non-Profit Organization",
          "Bidder"};

  private static final String DATABASE_PATH = "db/AuctionCalendar.ser";

  //End Constants

  /** The Scanner used for entire program. */
  private static Scanner userInput;

  /** The Calendar that keeps track of users and auctions. */
  private static AuctionCalendar myCalendar;

  /** The currently logged in user. */
  private static User currentUser;

  /** Stores User views to call Options from. */
  private static IUserView[] userTypeArray;

  /**
   * Runs the program.
   * @param args
   */
  public static void main(String[] args) {
    //load the saved database
    initialize();

    //for testing
    myCalendar = new AuctionCalendar();
    setupMaxAuctionsCalendar(myCalendar);
//    setupMaxRollingCalendar(myCalendar);
    //alwayson for testing
    setupDefaultUsers(myCalendar);
    System.out.println(myCalendar.getMyUsers().toString());

    //show login options
    mainMenu();
  }

  /**
   * Displays the main menu which you can login,
   * create new user, and save and exit with.
   */
  public static void mainMenu() {
    int myChoice;
    System.out.println(WELCOME_MSG);
    do {
      System.out.println(CHOOSE_OPTION_MSG);
      printOptionList(LOGIN_OPS, EXIT_OPTION);
      myChoice = userInput.nextInt();
      userInput.nextLine();
      if (myChoice == LOGIN_OPS.length + 1) {
        save(DATABASE_PATH);
        break;
      } else {
        switch (myChoice) {
          case 1:
            userLogin();
            break;
          case 2:
            createNewUser();
            break;
          default: //if they don't choose any of the options, then print an error message
            System.out.println(LOGIN_ERROR_MESSAGE);
        }

        if (currentUser != null) {
          userMenu();
        } else {
          System.out.println(USER_NOT_FOUND_MSG);
        }
      }
    } while (currentUser == null);
  }

  /** Prints the menu for users. */
  private static void userMenu() {
    //ask user to choose an option
    //System.out.println(CHOOSE_OPTION_MSG);

    //print the option list according to the user type.
    int currentUserType = currentUser.getUserType();


    //go to the proper user type's switch statement method
    userTypeArray[currentUserType-1].Options(userInput, myCalendar, currentUser);

    System.out.println("Logging out...");
    currentUser = null;
  }

  /** Initializes a stored serialized database. */
  public static void initialize() {
    userInput = new Scanner(System.in);
    myCalendar = load(DATABASE_PATH);
    currentUser = null;
    userTypeArray = new IUserView[3];
    userTypeArray[0] = new ACEmployeeView();
    userTypeArray[1] = new NonProfitView();
    userTypeArray[2] = new BidderView();
  }

  /**
   * Prompts the user to login.
   */
  public static void userLogin() {
    System.out.print(GET_USERNAME_MSG);

    String userName = userInput.nextLine().toLowerCase();

    if (myCalendar.getMyUsers().keySet().contains(userName.toLowerCase())) {
      currentUser = myCalendar.getMyUsers().get(userName.toLowerCase());
      //currentUser = new User(myCalendar.getMyUsers().get(userName.toLowerCase()), userInput, myCalendar);
    } else {
      currentUser = null;
    }
  }

  /**
   * Create new user prompts.
   */
  public static void createNewUser() {
    User newUser = null;

    System.out.println(NEW_USER_MSG);

    String userName = userInput.nextLine();

    if (myCalendar.getMyUsers().keySet().contains(userName.toLowerCase())) {
      System.out.println(DUPLICATE_USERNAME_MSG);
    } else {

      int userType = getUserType();

      if (userType == USER_TYPES.length + 1) {
        System.out.println("Action canceled");
      } else {
        if (userType == 2) {
          System.out.println(ENTER_NP_MSG);
          userInput.nextLine();
          String npoName = userInput.nextLine();
          newUser = new NonProfit(userName, npoName);
        } else {
          newUser = new User(userName, userType);
        }

        try {
//        myCalendar.getMyUsers().put(userName.toLowerCase(), newUser);
          myCalendar.addUser(newUser);
        } catch (NullPointerException exception) {
          System.out.println("user list was null");
        }
      }

      currentUser = newUser;

      System.out.println(CREATE_USER_SUCCESS_MSG);
    }
  }

  /**
   * Prompts for a user type.
   */
  private static int getUserType() {

    System.out.println(USER_TYPE_PROMPT_MESSAGE);
    printOptionList(USER_TYPES, CANCEL_OPTION);

    return userInput.nextInt();
  }

  /**
   * Prints a list as Options.
   * @param theList The list to be printed.
   * @param theLastOption The last option that they can select, usually back or cancel.
   */
  public static void printOptionList(final Object[] theList, final String theLastOption) {
    int counter = 1;
    for (Object option : theList) {
      System.out.println("[" + counter + "] " + option.toString());
      counter++;
    }
    System.out.println("[" + counter + "] " + theLastOption);
  }

  /**
   * Prints Auction details.
   * @param theAuction the auction to print the details of.
   */
  public static void printAuctionDetails(Auction theAuction) {
    StringBuilder string = new StringBuilder("Details for " + theAuction.getMyName());
    string.append("\n");
    string.append(String.format("Start Time: %d:%d\n", theAuction.getStartDate().getHour(),
            theAuction.getStartDate().getMinute()));
    string.append(String.format("End Time: %d:%d\n", theAuction.getEndDate().getHour(),
            theAuction.getEndDate().getMinute()));
    //returnString.append("End Date: " + myEndDate.toString() + "\n");

    //flag for printing bids
    boolean canViewBids = theAuction.getEndDate().isBefore(LocalDateTime.now());

    if (theAuction.getInventory().size() > 0) {
     string.append("Items:\n");
    }
    for (Item item : theAuction.getInventory()) {
      string.append(item.getName());
      string.append(", Quantity: ");
      string.append(item.getQuantity());
      string.append("\n");
      if (canViewBids) {
        string.append("\n  Bids:\n");
        for (String bidOwner : item.getBids().keySet()) {
//          String.format("%4s: $%.2f\n", bidOwner, item.getBids().get(bidOwner));
//          string.append("    " + bidOwner + ": $" + item.getBids().get(bidOwner) + "\n");
          string.append(String.format("%4s: $%.2f\n", bidOwner, item.getBids().get(bidOwner)));
        }
      }
    }

    System.out.println(string);
  }

  /**
   * Loads an AuctionCalendar from the database.
   * @param thePath the path to the database
   * @return the loaded AuctionCalendar
   */
  public static AuctionCalendar load(final String thePath) {
    AuctionCalendar calendar = AuctionCalendar.load(thePath);
    if (calendar == null) {
      calendar = new AuctionCalendar();
      System.out.println("Testing Use");
      //setupUsers(myCalendar);
      System.out.println("Creating new ac file");
      //setup default users to login to if no file is found.
    }

    return calendar;
  }

  /**
   * Saves a database.
   * @param thePath
   */
  private static void save(final String thePath) {
    try {
      myCalendar.save(thePath);
    } catch (IOException theException) {
      theException.printStackTrace();
    }

    System.out.println("Saving...");
  }

  private static void setupDefaultUsers(AuctionCalendar theCalendar) {
    User newACEmployee = new User("ACETester", 1);
    User newBidder = new User("BidderTester", 3);
    NonProfit newNPO = new NonProfit("NPOTest", "Test Organization");
    NonProfit newNPO2 = new NonProfit("NPOTest2", "Second Organization");

    theCalendar.addUser(newACEmployee);
    theCalendar.getMyUsers().put(newBidder.getUsername().toLowerCase(), newBidder);
    theCalendar.getMyUsers().put(newNPO.getUsername().toLowerCase(),newNPO);
    theCalendar.getMyUsers().put(newNPO2.getUsername().toLowerCase(),newNPO2);


  }

  private static void setupMaxAuctionsCalendar(AuctionCalendar theCalendar) {
    for (int i = 1; i < 51; i+=2) {
      String npoName = "npoTest" + i;
      String orgName = "Test Org " + i;
      theCalendar.addAuction(new Auction(new NonProfit(npoName, orgName), LocalDateTime.now().plusDays(i)));
    }
  }

  private static void setupMaxRollingCalendar(AuctionCalendar theCalendar) {
    theCalendar.addAuction(new Auction(new NonProfit("NPOTest1", "NPOTestOrg1"), LocalDateTime.now().plusDays(1).plusHours(5)));
    theCalendar.addAuction(new Auction(new NonProfit("NPOTest1", "NPOTestOrg1"), LocalDateTime.now().plusDays(1).plusHours(10)));
    theCalendar.addAuction(new Auction(new NonProfit("NPOTest1", "NPOTestOrg1"), LocalDateTime.now().plusDays(2).plusHours(5)));
    theCalendar.addAuction(new Auction(new NonProfit("NPOTest1", "NPOTestOrg1"), LocalDateTime.now().plusDays(2).plusHours(10)));
    theCalendar.addAuction(new Auction(new NonProfit("NPOTest1", "NPOTestOrg1"), LocalDateTime.now().plusDays(3).plusHours(5)));
  }
}
