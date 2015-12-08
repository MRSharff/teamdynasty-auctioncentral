package com.teamdynasty.view;

import com.teamdynasty.model.AuctionCalendar;
import com.teamdynasty.model.Item;
import com.teamdynasty.model.NonProfit;
import com.teamdynasty.model.User;

import java.util.List;
import java.util.Scanner;

/**Console input and output view for dealing with Bidder users.
 * Created by Mat on 12/3/2015.
 */
public class BidderView implements IUserView {

  public static final String[] USER_OPTIONS = {"Choose a Non-profit Organization to View their Auction",
          "Bid on Item",
          "Change Bid"};

  private static final String CHOOSE_NPO_MSG = "Choose a Non-Profit to view their auction: ";

  private static final String CHOOSE_ITEM_MSG = "Choose an Item: ";

  private static final String NO_AUCTION_MSG = "This Non-Profit does not have a scheduled auction.";

  private static final String BID_TOO_LOW_MSG = "Your bid was too low";


  @Override
  public void Options(Scanner theScanner, AuctionCalendar theCalendar, User theUser) {

    int userChoice;

    do {
      System.out.println(AuctionCentralMainView.CHOOSE_OPTION_MSG);
      AuctionCentralMainView.printOptionList(USER_OPTIONS, AuctionCentralMainView.LOGOUT_OPTION);
      userChoice = theScanner.nextInt();
      theScanner.nextLine();
      if (userChoice == USER_OPTIONS.length + 1) {
        System.out.println("Logging Out...");
      }
      switch (userChoice) {
        case 1:
          chooseAuction(theScanner, theCalendar);
          break;
        case 2:
          bidOnItem(theScanner, theCalendar, theUser);
          break;
        case 3:
          bidOnItem(theScanner, theCalendar, theUser);
          break;
      }
    } while (userChoice != USER_OPTIONS.length + 1);
  }

  private static void chooseAuction(Scanner theScanner, AuctionCalendar theCalendar) {
    int userChoice;

    List<NonProfit> npoList = theCalendar.getNPOList();

    System.out.println(CHOOSE_NPO_MSG);
    AuctionCentralMainView.printOptionList(npoList.toArray(), AuctionCentralMainView.CANCEL_OPTION);

    userChoice = theScanner.nextInt();
    theScanner.nextLine();

    if (userChoice == npoList.size()) {
      System.out.println(AuctionCentralMainView.ACTION_CANCELED_MSG);
    } else {
      if (!npoList.get(userChoice).isCurrentlyScheduled()) {
        System.out.println(NO_AUCTION_MSG);
      } else {
        for (Item item : npoList.get(userChoice).getPendingAuction().getInventory()) {
          System.out.println(item.toString());
          System.out.println(String.format("  Quantity: %d", item.getQuantity()));
          System.out.println(String.format("  Minimum Starting Bid: %.2f", item.getMyMinStartBid()));
        }
      }
    }
  }

  private static void bidOnItem(Scanner theScanner, AuctionCalendar theCalendar, User theUser) {
    int userChoice;

    List<NonProfit> npoList = theCalendar.getNPOList();

    System.out.println(CHOOSE_NPO_MSG);
    AuctionCentralMainView.printOptionList(npoList.toArray(), AuctionCentralMainView.CANCEL_OPTION);

    userChoice = theScanner.nextInt();
    theScanner.nextLine();

    if (userChoice == npoList.size()) {
      System.out.println(AuctionCentralMainView.ACTION_CANCELED_MSG);
    } else {
      if (!npoList.get(userChoice).isCurrentlyScheduled()) {
        System.out.println(NO_AUCTION_MSG);
      } else {

        System.out.println(CHOOSE_ITEM_MSG);
        int counter = 1;
        List<Item> itemList = npoList.get(userChoice).getPendingAuction().getInventory();
        for (Item item : itemList) {
          System.out.println(String.format("[%d], %s (minimum starting bid: %.2f",
                  counter,
                  item.toString(),
                  item.getMyMinStartBid()));

          counter++;
        }
        System.out.println(String.format("[%d] %s", counter, AuctionCentralMainView.CANCEL_OPTION));
        userChoice = theScanner.nextInt();
        theScanner.nextLine();

        if (userChoice == counter) {
          System.out.println(AuctionCentralMainView.ACTION_CANCELED_MSG);
        } else {
          boolean addedBid;
          do {
            System.out.println("Enter bid amount: ");
            double bidAmount = theScanner.nextDouble();
            theScanner.nextLine();

            addedBid = itemList.get(userChoice).addBid(theUser.getUsername().toLowerCase(), bidAmount);
            if (!addedBid) {
              System.out.println(BID_TOO_LOW_MSG);
              System.out.println(String.format("Item minimum starting bid is: $%.2f", itemList.get(userChoice).getMyMinStartBid()));
            }
          } while (!addedBid);
        }
      }
    }
  }

}
