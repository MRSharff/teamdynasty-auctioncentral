package com.teamdynasty.view;

import com.teamdynasty.model.*;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.util.*;

public class AuctionCentralConsoleIO {

  private static final long MAX_DAYS_AFTER_AUCTION_END = 1;

  private static final String WELCOME_MSG = "Welcome to AuctionCentral\n" +
          "-----------------";

  private static final String GET_USERNAME_MSG = "Please enter your Username: ";

  private static final String NEW_USER_MSG = "Please enter a new Username: ";

  private static final String DUPLICATE_USERNAME_MSG = "That username already exists, please enter a different username:";

  private static final String USER_TYPE_PROMPT_MESSAGE = "Select User Type:";

  private static final String ENTER_NP_MSG = "Enter the name of your non-profit:";

  private static final String CREATE_USER_SUCCESS_MSG = "Account created successfully";

  private static final String LOGIN_ERROR_MESSAGE = "Unrecognized input, please enter 1 to login, 2 to create new account.";

  private static final String CHOOSE_OPTION_MSG = "\nChoose an option\n" +
          "-----------------";

  private static final String INPUT_ERROR_MSG = "Incorrect Input";

  private static final int HOUR_OFFSET = 12;

  //Options lists
  /**
   * Login options
   */
  private static String[] LOGIN_OPS = {"Login",
          "Create Account",
          "Save and Exit"};

  private static final String[][] USER_OPTIONS = {
          //ACEmployee Options
          {"View Monthly Calendar",
                  "View details of auction",
                  "Logout"},
          //NPO Options
          {"Schedule Auction",
                  "Create Auction",
                  "Edit Auction",
                  "Create New Inventory Item",
                  "Edit Inventory Item",
                  "Logout"},
          //Bidder Options
          {"Choose a Non-profit Organization to View their Auction",
                  "Bid on Item",
                  "Change Bid",
                  "Logout"}};

  private static final String[] AUCTION_OPTIONS = {"Change date", "Add item", "Remove item", "Back"};

  private static final String[] ITEM_OPTIONS = {"Name", "Quantity", "Minimum Starting Bid", "Back"};

  private static final String[] ADD_ITEM_OPTIONS = {"Add item from inventory", "Create new item to add"};

  private static final String[] USER_TYPES = {"Auction Central Employee",
          "Non-Profit Organization",
          "Bidder"};

  private static final String DATABASE_PATH = "db/AuctionCalendar.ser";

  private static Scanner userInput;
  private static AuctionCalendar myCalendar;
  private static User currentUser;

  public static void main(String[] args) {
    //load the saved database
    initialize();
    //show login options
    mainMenu();
  }

  public static void mainMenu() {
    int myChoice;
    System.out.println(WELCOME_MSG);
    do {
      System.out.println(CHOOSE_OPTION_MSG);
      printOptionList(LOGIN_OPS);
      myChoice = userInput.nextInt();
      userInput.nextLine();
      switch (myChoice) {
        case 1:
          userLogin();
          break;
        case 2:
          createNewUser();
          break;
        case 3:
          save(DATABASE_PATH);
          return;
        default: //if they don't choose any of the options, then print an error message
          System.out.println(LOGIN_ERROR_MESSAGE);
      }

      userMenu();
    } while (currentUser == null);
  }

  /**
   * Prints the menu for users.
   */
  private static void userMenu() {
    //ask user to choose an option
    //System.out.println(CHOOSE_OPTION_MSG);

    //print the option list according to the user type.
    int currentUserType = currentUser.getUserType();


    //go to the proper user type's switch statement method
    switch (currentUserType) {
      case 1:
        ACEmployeeOptions();
        break;
      case 2:
        NPOptions();
        break;
      case 3:
        bidderOptions();
        break;
      default:
        System.out.println(INPUT_ERROR_MSG);
        break;
    }

    System.out.println("Logging out...");
    currentUser = null;
  }

  private static void ACEmployeeOptions() {
    int userChoice;

    do {
      System.out.println(CHOOSE_OPTION_MSG);
      printOptionList(USER_OPTIONS[currentUser.getUserType() - 1]);
      userChoice = userInput.nextInt();
      switch (userChoice) {
        case 1:
          viewCalendar(myCalendar);
          break;
        case 2:
          viewAuctionDetails(myCalendar);
          break;
      }
    } while (userChoice != USER_OPTIONS[currentUser.getUserType() - 1].length);
  }

  private static void NPOptions() {
    int userChoice;

    do {
      System.out.println(CHOOSE_OPTION_MSG);
      printOptionList(USER_OPTIONS[currentUser.getUserType() - 1]);
      userChoice = userInput.nextInt();
      switch (userChoice) {
        case 1:
          scheduleAuction();
          break;
        case 2:
          createAuction();
          break;
        case 3:
          editAuction();
          break;
        case 4:
          createNewInventoryItem();
          break;
        case 5:
          editInventoryItem();
          break;
      }
    } while (userChoice != USER_OPTIONS[currentUser.getUserType() - 1].length);
  }

  //Bidder methods
  private static void bidderOptions() {
    int userChoice;

    do {
      System.out.println(CHOOSE_OPTION_MSG);
      printOptionList(USER_OPTIONS[currentUser.getUserType() - 1]);
      userChoice = userInput.nextInt();
      switch (userChoice) {
        case 1:
          bidderChooseAuction();
          break;
        case 2:
          bidderBidOnItem();
          break;
        case 3:
          bidderChangeBid();
          break;
      }
    } while (userChoice != USER_OPTIONS[currentUser.getUserType() - 1].length);
  }

  private static void bidderChooseAuction() {
    System.out.println("Choose a Non-Profit: ");
    //NonProfitOrganization[] nonProfitList = (NonProfitOrganization[]) myCalendar.getNPOList().toArray();
    printOptionList(myCalendar.getNPOList().toArray());
    int npoIndex = userInput.nextInt();

    //pass in false because the bidder cannot view bids on an item.
    printAuctionDetails(myCalendar.getNPOAuction(myCalendar.getNPOList().get(npoIndex - 1)), false);
    //List<Item> auctionItems = myCalendar.getNPOList().get(npoIndex).getMyAuction().getInventory();
    //System.out.println(myCalendar.getNPOList().get(npoIndex).getMyAuction().getInventory().toArray());
  }

//  private static void listNPOs(final List<NonProfitOrganization> theList) {
//    int counter = 1;
//    for (NonProfitOrganization nonProfit : theList) {
//      System.out.println("[" + counter + "] " + nonProfit.getMyOrgName());
//      counter++;
//    }
//  }

  private static void printAuctionDetails(Auction theAuction, boolean isOption) {

    StringBuilder returnString = new StringBuilder();
    returnString.append("Auction Details for: " + theAuction.getMyName() + "\n");
    returnString.append("Start Date: " + theAuction.getStartDate().toString() + "\n");
    //returnString.append("End Date: " + myEndDate.toString() + "\n");


//    for (Item item : itemList) {
//      returnString.append(item.getName());
//      if (canViewBids) {
//        returnString.append("\n  Bids:\n");
//        for (String bidOwner : item.getBids().keySet()) {
//          returnString.append("    " + bidOwner + ": $" + item.getBids().get(bidOwner) + "\n");
//        }
//      }
//    }

    System.out.println(returnString.toString());
    printAuctionItems(theAuction);
  }

  public static void printAuctionItems(final Auction theAuction) {
    StringBuilder returnString = new StringBuilder();

    returnString.append("Items: \n");

    for (Item item : theAuction.getInventory()) {
      returnString.append(item.getName());
      if (LocalDateTime.now().isAfter(theAuction.getEndDate())) { //only print bids if the auction has ended.
        returnString.append("\n  Bids:\n");
        for (String bidOwner : item.getBids().keySet()) {
          returnString.append("    " + bidOwner + ": $" + item.getBids().get(bidOwner) + "\n");
        }
      }
    }

    System.out.println(returnString.toString());
  }

  public static void bidderBidOnItem() {
    System.out.println("Choose a Non-Profit: ");
    //NonProfitOrganization[] nonProfitList = (NonProfitOrganization[]) myCalendar.getNPOList().toArray();
    printOptionList(myCalendar.getNPOList().toArray());
    int userOption = userInput.nextInt();
    userInput.nextLine();

    Auction auction = myCalendar.getNPOAuction(myCalendar.getNPOList().get(userOption - 1));

    System.out.println("Choose an item to bid on: ");
    printOptionList(auction.getInventory().toArray());

    userOption = userInput.nextInt();
    userInput.nextLine();

    Item item = auction.getInventory().get(userOption - 1);

    double bidAmount = 0;
    System.out.print("Enter Bid Amount: ");
    if (userInput.hasNextDouble()) {
      bidAmount = userInput.nextDouble();
    } else {
      System.out.println("Incorrect input");
      userInput.nextLine();
    }

    boolean madeBid = item.addBid(currentUser.getUsername(), bidAmount);
    if (madeBid) {
      System.out.println("Bid $" + String.format("%.2f", bidAmount) + " on " + item.getName());
      System.out.println();
    } else {
      System.out.println("You must enter a higher bid.");
    }
  }

  public static void bidderChangeBid() {

  }

  //End Bidder Methods


  public static void editInventoryItem() {
    NonProfitOrganization user = (NonProfitOrganization) currentUser;
    //code to edit inventory goes here
    if (user.getMyInventory().isEmpty()) {
      System.out.println("No items to edit.");
      return;
    }

    System.out.println("Choose an item to edit: ");
    listNPOInventoryItems(user);
    int option = userInput.nextInt();

    Item item = user.getMyInventory().get(option - 1);

    do {
      System.out.println("Choose what you would like to edit: ");
      printOptionList(ITEM_OPTIONS);
      option = userInput.nextInt();
      userInput.nextLine();

      switch (option) {
        case 1:
          System.out.print("Enter new name: ");
          item.setName(userInput.nextLine());
          break;
        case 2:
          System.out.print("Enter new quantity: ");
          item.setQuantity(userInput.nextInt());
          userInput.nextLine();
          break;
        case 3:
          System.out.print("Enter new minimum starting bid: ");
          item.setMyMinStartBid(userInput.nextDouble());
          userInput.nextLine();
          break;
        case 4:
          break;
        default:
          System.out.println("Unrecognized Input");
      }
    } while (option != ITEM_OPTIONS.length);
  }

  public static void editAuction() {
    int userChoice;
    NonProfitOrganization user = (NonProfitOrganization) currentUser;


    do {
      System.out.println(CHOOSE_OPTION_MSG);
      printOptionList(AUCTION_OPTIONS);
      userChoice = userInput.nextInt();
      switch (userChoice) {
        case 1:
          changeAuctionDate(user);
          break;
        case 2:
          addAuctionItem(user);
          break;
        case 3:
          removeAuctionItem(user);
          break;
        case 4:
          break;
        default:
          System.out.println(INPUT_ERROR_MSG);
      }
    } while (userChoice != AUCTION_OPTIONS.length);

  }

  public static void removeAuctionItem(NonProfitOrganization theUser) {
    System.out.println("Choose an item to remove:");
    listNPOInventoryItems(theUser);
    int itemNumber = userInput.nextInt();

    if (itemNumber > 0 && itemNumber <= theUser.getMyAuction().getInventory().size()) {
      theUser.getMyAuction().removeItem(itemNumber - 1);
    } else {
      System.out.println(INPUT_ERROR_MSG);
    }
  }

  public static void changeAuctionDate(NonProfitOrganization theUser) {
    if (theUser.hasAuction()) {
      Auction auction = theUser.getMyAuction();
      if (theUser.isScheduled()) {
        myCalendar.removeAuction(auction);
        auction.setMyStartDate(newStartDate());
        auction.setEndDate(newEndDate(auction.getStartDate()));
        myCalendar.addAuction(auction);
      } else {
        auction.setMyStartDate(newStartDate());
        auction.setEndDate(newEndDate(auction.getStartDate()));
      }
    }
  }

  public static void addAuctionItem(NonProfitOrganization theUser) {
    System.out.println(CHOOSE_OPTION_MSG);
    printOptionList(ADD_ITEM_OPTIONS);

    int addItemOption = userInput.nextInt();
    userInput.nextLine();
    switch (addItemOption) {
      case 1:
        if (theUser.getMyInventory().isEmpty()) {
          System.out.println("No items to edit.");
          break;
        }
        System.out.println("Enter item number from the list: ");
        listNPOInventoryItems(theUser);
        addItemOption = userInput.nextInt();
        userInput.nextLine();
        //-1 for list to index offset
        theUser.getMyAuction().addItem(theUser.getMyInventory().get(addItemOption - 1));
        break;
      case 2:
        theUser.getMyAuction().getInventory().add(createItem());
        break;
    }
  }

  public static void listNPOInventoryItems(NonProfitOrganization theUser) {
    int counter = 1;
    for (Item item : theUser.getMyInventory()) {
      System.out.println("[" + counter + "] " + item.getName() + " (Minimum bid: $" + item.getMyMinStartBid() + ")");
      counter++;
    }
  }

  public static void createNewInventoryItem() {
    NonProfitOrganization user = (NonProfitOrganization) currentUser;
    Item newItem = createItem();
    user.getMyInventory().add(newItem);
    System.out.println(newItem.getName() + " added.\n");
  }

  public static Item createItem() {
    //code to add inventory item
    System.out.println("Creating an Item...");

    userInput.nextLine();
    System.out.print("Enter item name: ");
    String itemName = userInput.nextLine();
    System.out.print("Enter quantity: ");
    int itemQuantity = userInput.nextInt();
    userInput.nextLine();
    System.out.print("Enter minimum starting bid: ");
    double startingBid = userInput.nextDouble();
    userInput.nextLine();

    return new Item(itemName, itemQuantity, startingBid);
  }

  private static void viewAuctionDetails(AuctionCalendar theCalendar) {
    System.out.print("Enter the month (numerically) of the auction you wish to view: ");

    int month = userInput.nextInt();

    viewMonth(month, theCalendar.getMyAuctions(), true);

    System.out.print("Enter the number of the auction you wish to view: ");

    int auctionNumber = userInput.nextInt();

    Auction auction = theCalendar.getMyAuctions().get(month).get(auctionNumber - 1);

    List<Item> itemList = auction.getInventory();

    StringBuilder returnString = new StringBuilder();
    returnString.append("Auction Details for: " + auction.getMyName() + "\n");
    returnString.append("Start Date: " + auction.getStartDate().toString() + "\n");
    //returnString.append("End Date: " + myEndDate.toString() + "\n");
    returnString.append("Items: \n");

    for (Item item : itemList) {
      returnString.append(item.getName());

      if (LocalDateTime.now().isAfter(auction.getEndDate())) {
        returnString.append("\n  Bids:\n");
        for (String bidOwner : item.getBids().keySet()) {
          returnString.append("    " + bidOwner + ": $" + item.getBids().get(bidOwner) + "\n");
        }
      }
    }

    System.out.println(returnString.toString());
  }

//  private static void viewAuctionDetails(AuctionCalendar theCalendar) {
//    int month = -1;
//
//    if (theCalendar.getMyAuctions().isEmpty()) {
//      System.out.println("The Auction List Is Empty");
//      return;
//    }
//
//    month = allAuction(theCalendar.getMyAuctions());
//
//    if (month == -1) return;
//
//    specificAuction(month, theCalendar.getMyAuctions());
//  }
//
//  private static void specificAuction(final int theMonth, final HashMap < Integer, List < Auction >> theAuctionList) {
//
//    int auctionNumber = 0;
//    System.out.println("Enter the number of the auction you wish to view (-1 to go back): ");
//    auctionNumber = userInput.nextInt();
//
//    if (auctionNumber == -1) {
//      return;
//    }
//
//    if (auctionNumber != -1 && (auctionNumber < 1 || auctionNumber > theAuctionList.get(theMonth).size())) {
//      System.out.println("Please Enter a Correct Auction Number from the List");
//      return;
//    }
//
//    auctionNumber -= 1;
//
//    // Print Auction Details
//    System.out.println(theAuctionList.get(theMonth).get(auctionNumber).viewDetails());
//
//  }

  private static void viewCalendar(AuctionCalendar theCalendar) {
    int month = Calendar.MONTH;

    if (theCalendar.getMyAuctions().isEmpty()) {
      System.out.println("The Auction List Is Empty");
      return;
    }

    while (month > 0) {

      System.out.print("Enter the month numerically (-1 to go back): ");
      //month = inputFromUser("Enter a month by number (1-12) or " + "[-1] Go back:");
      month = userInput.nextInt();

      if (month == -1) {
        break;
      }

      if (month < 1 || month > 12) {
        System.out.println("Please Enter a Correct Month Numerical");
        continue;
      }

      viewMonth(month, theCalendar.getMyAuctions(), false);
    }
  }

  private static void viewMonth(int theMonth, HashMap<Integer, List<Auction>> theAuctionList, boolean numbered) {
    List<Auction> currentMonthList = theAuctionList.get(theMonth);

    if (currentMonthList != null && !currentMonthList.isEmpty()) {

      Collections.sort(currentMonthList);
      int count = 1;
      System.out.println("Auctions for the Month of " + new DateFormatSymbols().getMonths()[theMonth - 1]);
      for (Auction auction : currentMonthList) {
        //view all future auctions and auctions that have ended within MAX_DAYS_AFTER_AUCTION_END
        if (auction.getStartDate().isAfter(LocalDateTime.now().minusDays(MAX_DAYS_AFTER_AUCTION_END))) {
          String printer = "";
          if (numbered) {
            printer = "[" + count + "] ";
            count++;
          }
          System.out.println(printer + auction.getMyName());
          System.out.println();
        }
      }
    } else {
      System.out.println("No future auctions this month");
    }
  }

  public static AuctionCalendar load(final String thePath) {
    AuctionCalendar calendar = AuctionCalendar.load(thePath);
    if (calendar == null) {
      calendar = new AuctionCalendar();
      System.out.println("Testing Use");
      //setupUsers(myCalendar);
      System.out.println("Creating new ac file");
      //setup default users to login to if no file is found.
      setupUsers(calendar);
    }

    return calendar;
  }

//  private static void load(final String thePath) {
//    File file = new File(thePath);
//
//    if (!file.exists()) {
//      System.out.println("Creating new database file: " + thePath);
//    }
//
//    load(thePath);
//  }

  private static void save(final String thePath) {
    try {
      myCalendar.save(thePath);
    } catch (IOException theException) {
      theException.printStackTrace();
    }

    System.out.println("Saving...");
  }

//  private static void save(final String thePath) {
//    File file = new File(thePath);
//
//
//    //if there is no file by found in DATABASE_PATH, create one
//    if (!file.exists()) {
//        //File path = new File(DATABASE_PATH);
//        if (file.getParentFile().mkdir()) {
//          System.out.println("Created file.");
//        } else {
//          System.out.println("File creation error");
//        }
//    }
//
//    //write serialized file
//    try {
//      FileOutputStream fileOut = new FileOutputStream(thePath);
//      ObjectOutputStream out = new ObjectOutputStream(fileOut);
//      out.writeObject(myCalendar);
//      out.close();
//      fileOut.close();
//    } catch (IOException theException) {
//      System.out.println("Save error, IOException");
//      theException.printStackTrace();
//    }
//
//    System.out.println("Saving...");
//  }

  public static void initialize() {
    userInput = new Scanner(System.in);
    myCalendar = load(DATABASE_PATH);
    currentUser = null;

    System.out.println(myCalendar.getMyUsers().toString());
  }

  public static void userLogin() {
    System.out.print(GET_USERNAME_MSG);

    String userName = userInput.nextLine();

    if (myCalendar.getMyUsers().keySet().contains(userName.toLowerCase())) {
      currentUser = myCalendar.getMyUsers().get(userName.toLowerCase());
    }
  }

  public static void createNewUser() {
    User newUser = null;

    System.out.println(NEW_USER_MSG);

    String userName = userInput.nextLine();

    if (myCalendar.getMyUsers().keySet().contains(userName.toLowerCase())) {
      System.out.println(DUPLICATE_USERNAME_MSG);
    } else {

      int userType = getUserType();

      if (userType == 2) {
        System.out.println(ENTER_NP_MSG);
        userInput.nextLine();
        String npoName = userInput.nextLine();
        newUser = new NonProfitOrganization(userName, npoName);
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

  private static int getUserType() {

    System.out.println(USER_TYPE_PROMPT_MESSAGE);
    printOptionList(USER_TYPES);

    return userInput.nextInt();
  }

  private static void setupUsers(AuctionCalendar theCalendar) {
    User newACEmployee = new User("ACETester", 1);
    User newBidder = new User("BidderTester", 3);
    NonProfitOrganization newNPO = new NonProfitOrganization("NPOTest", "Test Organization");
    NonProfitOrganization newNPO2 = new NonProfitOrganization("NPOTest2", "Second Organization");


    theCalendar.getMyUsers().put(newACEmployee.getUsername().toLowerCase(), newACEmployee);
    theCalendar.getMyUsers().put(newBidder.getUsername().toLowerCase(), newBidder);
    theCalendar.getMyUsers().put(newNPO.getUsername().toLowerCase(), newNPO);
    theCalendar.getMyUsers().put(newNPO2.getUsername().toLowerCase(), newNPO2);


  }

//  public static void printOptionList(final String[] theList) {
//    int counter = 1;
//    for (String option : theList) {
//      System.out.println("[" + counter + "] " + option);
//      counter++;
//    }
//  }

  public static void printOptionList(final Object[] theList) {
    int counter = 1;
    for (Object option : theList) {
      System.out.println("[" + counter + "] " + option.toString());
      counter++;
    }
  }

  private static void scheduleAuction() {
    if (currentUser instanceof NonProfitOrganization) {
      NonProfitOrganization user = (NonProfitOrganization) currentUser;
      if (!user.hasAuction()) {
        System.out.println("You do not have an auction to schedule, please create one first.");
      } else {
        myCalendar.addAuction(user.scheduleAuction());
      }
    }
  }

  public static void createAuction() {
    if (currentUser instanceof NonProfitOrganization) {
      NonProfitOrganization user = (NonProfitOrganization) currentUser;
      //code to enter auction info goes here
      Auction newAuction = new Auction(user);
      newAuction.setStartDate(newStartDate());
      newAuction.setEndDate(newEndDate(newAuction.getStartDate()));
//    myAuctions.add(newAuction);

      user.setMyAuction(newAuction);

      System.out.println("Auction created: " + newAuction.getMyName() + "\n");
    }
  }

  public static LocalDateTime newStartDate() {
    System.out.print("Enter auction month (numerical): ");
    int aMonth = userInput.nextInt();
    userInput.nextLine();
    System.out.print("Enter auction day: ");
    int aDay = userInput.nextInt();
    userInput.nextLine();
    System.out.print("Enter auction year: ");
    int aYear = userInput.nextInt();
    userInput.nextLine();

    //get start time
    System.out.print("Enter auction start time: ");
    String time = userInput.nextLine();

    String theTime = time.replace(':', ' ');
    Scanner detailScanner = new Scanner(theTime);
    int hour = detailScanner.nextInt();
    int minutes = detailScanner.nextInt();

    //System.out.println(hour);
    //System.out.println(minutes);
//    detailScanner.nextLine();

    //check if user entered 24 hour time
    detailScanner = new Scanner(System.in);
    if (hour < 13) {
      System.out.print("AM or PM: ");
      String amPM = detailScanner.nextLine();
      //if not, ask for am or pm
      if (amPM.toLowerCase().equals("pm")) {
        hour = (hour % HOUR_OFFSET) + HOUR_OFFSET;
      }
    }

    return LocalDateTime.of(aYear, aMonth, aDay, hour, minutes);
  }

  public static LocalDateTime newEndDate(final LocalDateTime theStartDate) {
    //Scanner detailScanner = new Scanner(System.in);
    LocalDateTime endDate;
    do {
      System.out.print("Enter auction end time (hh:mm): ");
      String time = userInput.nextLine();

      String theTime = time.replace(':', ' ');
      Scanner detailScanner = new Scanner(theTime);
      int hour = detailScanner.nextInt();
      int minutes = detailScanner.nextInt();

      if (hour < 13) {
        System.out.print("AM or PM: ");
        String amPM = userInput.nextLine();
        //if not, ask for am or pm
        if (amPM.toLowerCase().equals("pm")) {
          hour = (hour % HOUR_OFFSET) + HOUR_OFFSET;
        }
      }
      endDate = LocalDateTime.of(theStartDate.getYear(),
              theStartDate.getMonthValue(),
              theStartDate.getDayOfMonth(),
              hour,
              minutes);

      if (endDate.isBefore(theStartDate)) {
        //TODO code to change back into 12 hour should go here
        System.out.println("End time must be after start time ("
                + theStartDate.getHour() + ":" + theStartDate.getMinute());
      }

    } while (endDate.isBefore(theStartDate));

    return endDate;
  }
}
