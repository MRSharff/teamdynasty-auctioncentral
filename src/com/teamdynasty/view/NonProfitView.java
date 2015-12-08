package com.teamdynasty.view;

import com.teamdynasty.model.*;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Console input and output view for dealing with NonProfit users.
 * Created by Mat on 12/3/2015.
 */
public class NonProfitView implements IUserView {

  private static final String[] USER_OPTIONS = {"Schedule Auction",
          "Create Auction",
          "Edit Auction",
          "Create New Inventory Item",
          "Edit Inventory Item"};

  public static final String[] AUCTION_OPTIONS = {"Change date",
          "Add item",
          "Remove item"};

  public static final String NO_AUCTION_MSG = "You do not have an auction";

  public static final String[] ADD_ITEM_OPTIONS = {"Add item from inventory", "Create new item to add"};

  public static final String[] ITEM_OPTIONS = {"Name", "Quantity", "Minimum Starting Bid"};

  /******************************************
   * Start Business rule violation messages.*
   ******************************************/

  public static final String HAS_MAX_AUCTIONS_MSG = "The calendar has reached the maximum amount of auctions currently scheduled.";

  public static final String MAX_DAYS_OUT_MSG = "You can only schedule an auction up to 90 days in the future.";

  public static final String MAX_ROLLING_MSG = "There are too many auctions scheduled around that time, try another date.";

  public static final String MAX_AUCTIONS_PER_DAY_MSG = "There are too many auctions already scheduled for that day.";

  private static final String START_TOO_SOON_MSG = "The start time of one auction may be no earlier\nthan 2 hours after the end of the first.";

  private static final String MAX_PER_YEAR_MSG = "You may only have one Auction in a year.";

  /*****************************************
   * EndS Business rule violation messages.*
   *****************************************/


  public static final int HOUR_OFFSET = 12;

  public void Options(Scanner theScanner, AuctionCalendar theCalendar, User theUser) {
    if (theUser instanceof NonProfit) {
      NonProfit user = (NonProfit) theUser;
      int userChoice;

      do {
        System.out.println(AuctionCentralMainView.CHOOSE_OPTION_MSG);
        AuctionCentralMainView.printOptionList(USER_OPTIONS,
                AuctionCentralMainView.LOGOUT_OPTION);
        userChoice = theScanner.nextInt();
        theScanner.nextLine();

        switch (userChoice) {
          case 1:
            scheduleAuction(theCalendar, user);
            break;
          case 2:
            createAuction(theScanner, user);
            break;
          case 3:
            editAuction(theScanner, theCalendar, user);
            break;
          case 4:
            createNewInventoryItem(theScanner, user);
            break;
          case 5:
            editInventoryItem(theScanner, user);
            break;
        }
      } while (userChoice != USER_OPTIONS.length + 1);
    } else {
      System.out.println("The User was not a NonProfit");
    }

  }

  private static void scheduleAuction(AuctionCalendar theCalendar, NonProfit theUser) {
    if (!theUser.hasPendingAuction()) {
      System.out.println("You do not have an auction to schedule, please create one first.");
    } else {
      Auction userAuction = theUser.getPendingAuction();
      if (theCalendar.hasMaxAuctions()) {
        System.out.println(HAS_MAX_AUCTIONS_MSG);
      } else if (!theCalendar.isWithinMaxDaysOut(userAuction)) {
        System.out.println(MAX_DAYS_OUT_MSG);
      } else if (theCalendar.maxInRollingPeriod(userAuction)) {
        System.out.println(MAX_ROLLING_MSG);
      } else if (theCalendar.maxAuctionsInOneDay(userAuction)) {
        System.out.println(MAX_AUCTIONS_PER_DAY_MSG);
      } else if (theCalendar.auctionTooClose(userAuction)) {
        System.out.println(START_TOO_SOON_MSG);
      } else if (theUser.maxAuctionsPerYear()) {
        System.out.println(MAX_PER_YEAR_MSG);
      } else {
        theCalendar.addAuction(theUser.scheduleAuction());
        System.out.println("Auction Scheduled.");
      }
    }
  }

  private static void createAuction(Scanner theScanner, NonProfit theUser) {

    Auction newAuction = new Auction(theUser);
    newAuction.setStartDate(newStartDate(theScanner));
    newAuction.setEndDate(newEndDate(newAuction.getStartDate(), theScanner));

    theUser.setPendingAuction(newAuction);

    System.out.println("Auction created: " + newAuction.getMyName() + "\n");
  }

  private static void editAuction(Scanner theScanner, AuctionCalendar theCalendar, NonProfit theUser) {
    int userInput;

    if (theUser.hasPendingAuction()) {
      AuctionCentralMainView.printAuctionDetails(theUser.getPendingAuction());
      do {
        System.out.println(AuctionCentralMainView.CHOOSE_OPTION_MSG);
        AuctionCentralMainView.printOptionList(AUCTION_OPTIONS, AuctionCentralMainView.BACK_OPTION);
        userInput = theScanner.nextInt();
        if (userInput == AUCTION_OPTIONS.length + 1) {
          System.out.println(AuctionCentralMainView.ACTION_CANCELED_MSG);
        } else {
          switch (userInput) {
            case 1:
              changeAuctionDate(theScanner, theCalendar, theUser);
              break;
            case 2:
              addAuctionItem(theScanner, theUser);
              break;
            case 3:
              removeAuctionItem(theScanner, theUser);
              break;
            case 4:
              break;
            default:
              System.out.println(AuctionCentralMainView.INPUT_ERROR_MSG);
          }
        }
      } while (userInput != AUCTION_OPTIONS.length + 1);
    } else {
      System.out.println(NO_AUCTION_MSG);
    }
  }

  private static void createNewInventoryItem(Scanner theScanner, NonProfit theUser) {
    theUser.addItemToInventory(createItem(theScanner));
  }

  public static void editInventoryItem(Scanner theScanner, NonProfit theUser) {
    //code to edit inventory goes here
    if (theUser.getMyInventory().isEmpty()) {
      System.out.println("No items to edit.");
      return;
    }

    System.out.println("Choose an item to edit: ");
    AuctionCentralMainView.printOptionList(theUser.getMyInventory().toArray(), AuctionCentralMainView.CANCEL_OPTION);
    int option = theScanner.nextInt();

    if (option == theUser.getMyInventory().size() + 1) {
      System.out.println(AuctionCentralMainView.ACTION_CANCELED_MSG);
    } else {
      Item item = theUser.getMyInventory().get(option - 1);

      do {
        System.out.println("Choose what you would like to edit: ");
        AuctionCentralMainView.printOptionList(ITEM_OPTIONS, AuctionCentralMainView.CANCEL_OPTION);
        option = theScanner.nextInt();
        theScanner.nextLine();
        if (option == ITEM_OPTIONS.length + 1) {
          System.out.println("Action canceled");
        } else {
          switch (option) {
            case 1:
              System.out.print("Enter new name: ");
              item.setName(theScanner.nextLine());
              break;
            case 2:
              System.out.print("Enter new quantity: ");
              item.setQuantity(theScanner.nextInt());
              theScanner.nextLine();
              break;
            case 3:
              System.out.print("Enter new minimum starting bid: ");
              item.setMyMinStartBid(theScanner.nextDouble());
              theScanner.nextLine();
              break;
            default:
              System.out.println("Unrecognized Input");
              break;
          }
        }
      } while (option != ITEM_OPTIONS.length + 1);
    }
  }

  /********************************
   * createAuction helper methods *
   ********************************/

  public static LocalDateTime newStartDate(Scanner theScanner) {
    LocalDateTime newDate;
    do {
      System.out.print("Enter auction month (numerical): ");
      int aMonth = theScanner.nextInt();
      theScanner.nextLine();
      System.out.print("Enter auction day: ");
      int aDay = theScanner.nextInt();
      theScanner.nextLine();
      System.out.print("Enter auction year: ");
      int aYear = theScanner.nextInt();
      theScanner.nextLine();

      //get start time
      String time;
      boolean matches;
      do {
        System.out.print("Enter auction start time (hh:mm): ");
        time = theScanner.nextLine();

        matches = time.matches("(\\d+):(\\d+)");
        if (!matches) {
          System.out.println("Incorrect time format");
        }
      } while (!matches);


      String theTime = time.replace(':', ' ');
      Scanner detailScanner = new Scanner(theTime);
      int hour = detailScanner.nextInt();
      int minutes = detailScanner.nextInt();

      //check if user entered 24 hour time
      if (hour < 13) {
        do {
          System.out.print("AM or PM: ");
          String amPM = theScanner.nextLine();
          amPM = amPM.toLowerCase();
          matches = amPM.matches("am") || amPM.matches("pm");
          //if not, ask for am or pm
          if (matches) {
            if (amPM.equals("pm")) {
              hour = (hour % HOUR_OFFSET) + HOUR_OFFSET;
            }
          } else {
            System.out.println("Please enter AM or PM");
          }
        } while (!matches);
      }
      newDate = LocalDateTime.of(aYear, aMonth, aDay, hour, minutes);
      if (newDate.isBefore(LocalDateTime.now())) {
        System.out.println("Your auction must be set for a future date.");
      }
    } while (newDate.isBefore(LocalDateTime.now()));

    return newDate;
  }

  public static LocalDateTime newEndDate(final LocalDateTime theStartDate, Scanner theScanner) {
    //Scanner detailScanner = new Scanner(System.in);
    LocalDateTime endDate;
    do {
      String time;
      boolean matches;
      do {
        System.out.print("Enter auction end time (hh:mm): ");
        time = theScanner.nextLine();

        matches = time.matches("(\\d+):(\\d+)");
        if (!matches) {
          System.out.println("Incorrect time format");
        }
      } while (!matches);

//      System.out.print("Enter auction end time (hh:mm): ");
//      String time = theScanner.nextLine();


      String theTime = time.replace(':', ' ');
      Scanner detailScanner = new Scanner(theTime);
      int hour = detailScanner.nextInt();
      int minutes = detailScanner.nextInt();

      if (hour < 13) {
        System.out.print("AM or PM: ");
        String amPM = theScanner.nextLine();
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
        System.out.println("End time must be after start time: "
                + theStartDate.getHour() + ":" + theStartDate.getMinute());
      }

    } while (endDate.isBefore(theStartDate));

    return endDate;
  }

  /************************************
   * End createAuction helper methods *
   ************************************/

  /******************************
   * editAuction Helper methods *
   ******************************/

  public static void changeAuctionDate(Scanner theScanner, AuctionCalendar theCalendar, NonProfit theUser) {
    if (theUser.hasPendingAuction()) {
      Auction auction = theUser.getPendingAuction();
//      if (theUser.isCurrentlyScheduled()) {
//        theCalendar.removeAuction(auction);
//        auction.setMyStartDate(newStartDate(theScanner));
//        auction.setEndDate(newEndDate(auction.getStartDate(), theScanner));
//        theCalendar.addAuction(auction);
//      } else {
//        auction.setMyStartDate(newStartDate(theScanner));
//        auction.setEndDate(newEndDate(auction.getStartDate(), theScanner));
//      }
      auction.setMyStartDate(newStartDate(theScanner));
      auction.setEndDate(newEndDate(auction.getStartDate(), theScanner));
    }
  }

  public static void addAuctionItem(Scanner theScanner, NonProfit theUser) {
    System.out.println(AuctionCentralMainView.CHOOSE_OPTION_MSG);
    AuctionCentralMainView.printOptionList(ADD_ITEM_OPTIONS, AuctionCentralMainView.CANCEL_OPTION);

    int addItemOption = theScanner.nextInt();
    theScanner.nextLine();

    if (addItemOption == ADD_ITEM_OPTIONS.length + 1) {
      System.out.println("Action canceled");
    } else {
      switch (addItemOption) {
        case 1:
          if (theUser.getMyInventory().isEmpty()) {
            System.out.println("No items to add.");
            break;
          }
          System.out.println("Enter item number from the list: ");
          AuctionCentralMainView.printOptionList(theUser.getPendingAuction().getInventory().toArray(),
                  AuctionCentralMainView.CANCEL_OPTION);

          addItemOption = theScanner.nextInt();
          theScanner.nextLine();
          if (addItemOption == theUser.getPendingAuction().getInventory().size()) {
            System.out.println(AuctionCentralMainView.ACTION_CANCELED_MSG);
          } else {
            //-1 for list to index offset
            theUser.getPendingAuction().addItem(theUser.getMyInventory().get(addItemOption - 1));
            System.out.println("Item added: " + theUser.getMyInventory().get(addItemOption - 1).getName());
          }
          break;
        case 2:
          Item newItem = createItem(theScanner);
          theUser.getPendingAuction().addItem(newItem);
          theUser.addItemToInventory(newItem);
          System.out.println("Item added: " + newItem.getName());
          break;
      }
    }
  }

  public static void removeAuctionItem(Scanner theScanner, NonProfit theUser) {
    System.out.println("Choose an item to remove:");
//    listNPOInventoryItems(theUser);
    AuctionCentralMainView.printOptionList(theUser.getPendingAuction().getInventory().toArray(), AuctionCentralMainView.CANCEL_OPTION);
    int itemNumber = theScanner.nextInt();
    theScanner.nextLine();

    if (itemNumber > 0 && itemNumber <= theUser.getPendingAuction().getInventory().size()) {
      theUser.getPendingAuction().removeItem(itemNumber - 1);
    } else {
      System.out.println(AuctionCentralMainView.INPUT_ERROR_MSG);
    }
  }

  /**********************************
   * End editAuction Helper methods *
   **********************************/


  public static Item createItem(Scanner theScanner) {
    boolean matches;
    //code to add inventory item
    System.out.println("Creating an Item...");


    System.out.print("Enter item name: ");
    String itemName = theScanner.nextLine();
    do {
      System.out.print("Enter quantity: ");
      matches = theScanner.hasNextInt();
      if (!matches) {
        System.out.println("Please enter an integer quantity");
        theScanner.nextLine();
      }
    } while (!matches);
    int itemQuantity = theScanner.nextInt();
    theScanner.nextLine();
    do {
      System.out.print("Enter minimum starting bid: ");
      matches = theScanner.hasNextDouble();
      if (!matches) {
        System.out.println("Incorrect input");
        theScanner.nextLine();
      }
    } while (!matches);
    double startingBid = theScanner.nextDouble();
    theScanner.nextLine();

    return new Item(itemName, itemQuantity, startingBid);
  }
}
