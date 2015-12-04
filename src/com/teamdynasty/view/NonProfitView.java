package com.teamdynasty.view;

import com.teamdynasty.model.*;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Created by Mat on 12/3/2015.
 */
public class NonProfitView extends UserView {

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
            createNewInventoryItem(theScanner,user);
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

  private static void scheduleAuction(AuctionCalendar theCalendar, NonProfit theUser) {;
      if (!theUser.hasAuction()) {
        System.out.println("You do not have an auction to schedule, please create one first.");
      } else {
        theCalendar.addAuction(theUser.scheduleAuction());
      }
  }

  private static void createAuction(Scanner theScanner, NonProfit theUser) {
    NonProfit user = (NonProfit) theUser;

    Auction newAuction = new Auction(user);
    newAuction.setStartDate(newStartDate(theScanner));
    newAuction.setEndDate(newEndDate(newAuction.getStartDate(), theScanner));

    user.setMyAuction(newAuction);

    System.out.println("Auction created: " + newAuction.getMyName() + "\n");
  }

  private static void editAuction(Scanner theScanner, AuctionCalendar theCalendar, NonProfit theUser) {
    int userInput;

    if (theUser.hasAuction()) {
      AuctionCentralMainView.printAuctionDetails(theUser.getMyAuction());
      do {
        System.out.println(AuctionCentralMainView.CHOOSE_OPTION_MSG);
        AuctionCentralMainView.printOptionList(AUCTION_OPTIONS, AuctionCentralMainView.CANCEL_OPTION);
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

    if (option == theUser.getMyInventory().size()) {
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
    System.out.print("Enter auction start time: ");
    String time = theScanner.nextLine();

    String theTime = time.replace(':', ' ');
    Scanner detailScanner = new Scanner(theTime);
    int hour = detailScanner.nextInt();
    int minutes = detailScanner.nextInt();

    //check if user entered 24 hour time
    if (hour < 13) {
      System.out.print("AM or PM: ");
      String amPM = theScanner.nextLine();
      //if not, ask for am or pm
      if (amPM.toLowerCase().equals("pm")) {
        hour = (hour % HOUR_OFFSET) + HOUR_OFFSET;
      }
    }

    return LocalDateTime.of(aYear, aMonth, aDay, hour, minutes);
  }

  public static LocalDateTime newEndDate(final LocalDateTime theStartDate, Scanner theScanner) {
    //Scanner detailScanner = new Scanner(System.in);
    LocalDateTime endDate;
    do {
      System.out.print("Enter auction end time (hh:mm): ");
      String time = theScanner.nextLine();

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
        System.out.println("End time must be after start time ("
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

  public static void removeAuctionItem(Scanner theScanner, NonProfit theUser) {
    System.out.println("Choose an item to remove:");
//    listNPOInventoryItems(theUser);
    AuctionCentralMainView.printOptionList(theUser.getMyAuction().getInventory().toArray(), AuctionCentralMainView.CANCEL_OPTION);
    int itemNumber = theScanner.nextInt();

    if (itemNumber > 0 && itemNumber <= theUser.getMyAuction().getInventory().size()){
      theUser.getMyAuction().removeItem(itemNumber - 1);
    } else {
      System.out.println(AuctionCentralMainView.INPUT_ERROR_MSG);
    }
  }

  public static void changeAuctionDate(Scanner theScanner, AuctionCalendar theCalendar, NonProfit theUser) {
    if (theUser.hasAuction()) {
      Auction auction = theUser.getMyAuction();
      if (theUser.isScheduled()) {
        theCalendar.removeAuction(auction);
        auction.setMyStartDate(newStartDate(theScanner));
        auction.setEndDate(newEndDate(auction.getStartDate(), theScanner));
        theCalendar.addAuction(auction);
      } else {
        auction.setMyStartDate(newStartDate(theScanner));
        auction.setEndDate(newEndDate(auction.getStartDate(), theScanner));
      }
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
          AuctionCentralMainView.printOptionList(theUser.getMyAuction().getInventory().toArray(),
                  AuctionCentralMainView.CANCEL_OPTION);

          addItemOption = theScanner.nextInt();
          theScanner.nextLine();
          if (addItemOption == theUser.getMyAuction().getInventory().size()) {
            System.out.println(AuctionCentralMainView.ACTION_CANCELED_MSG);
          } else {
            //-1 for list to index offset
            theUser.getMyAuction().addItem(theUser.getMyInventory().get(addItemOption - 1));
          }
          break;
        case 2:
          theUser.getMyAuction().getInventory().add(createItem(theScanner));
          break;
      }
    }
  }



  public static Item createItem(Scanner theScanner) {
    //code to add inventory item
    System.out.println("Creating an Item...");

    theScanner.nextLine();
    System.out.print("Enter item name: ");
    String itemName = theScanner.nextLine();
    System.out.print("Enter quantity: ");
    int itemQuantity = theScanner.nextInt();
    theScanner.nextLine();
    System.out.print("Enter minimum starting bid: ");
    double startingBid = theScanner.nextDouble();
    theScanner.nextLine();

    return new Item(itemName, itemQuantity, startingBid);
  }
}
