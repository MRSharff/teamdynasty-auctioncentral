package com.teamdynasty.view;

import com.teamdynasty.model.Auction;
import com.teamdynasty.model.AuctionCalendar;
import com.teamdynasty.model.User;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**Console input and output view for dealing with Auction Central Employee users.
 * Created by Mat on 12/3/2015.
 */
public class ACEmployeeView implements IUserView {

  private static final String[] USER_OPTIONS = {"View Monthly Calendar",
                                                "View details of auction"};

//  private static Scanner myScanner;
//
//  public ACEmployeeView(Scanner theScanner) {
//    //default Constructor.
//    myScanner = theScanner;
//  }

  public void Options(Scanner theScanner, AuctionCalendar theCalendar, User theUser) {
    int userChoice;

    do {
      System.out.println(AuctionCentralMainView.CHOOSE_OPTION_MSG);
      AuctionCentralMainView.printOptionList(USER_OPTIONS,
              AuctionCentralMainView.LOGOUT_OPTION);
      userChoice = theScanner.nextInt();

      switch (userChoice) {
        case 1:
          viewMonthlyCalendar(theScanner, theCalendar);
          break;
        case 2:
          viewAuctionDetails(theScanner, theCalendar);
          break;
      }
    } while (userChoice != USER_OPTIONS.length + 1);
  }

  public static void viewMonthlyCalendar(Scanner theScanner, AuctionCalendar theCalendar) {
    List<String> monthList = new ArrayList<>(12);

    for (Month month : Month.values()) {
      monthList.add(month.getDisplayName(TextStyle.FULL, Locale.US));
    }

    System.out.println(AuctionCentralMainView.CHOOSE_OPTION_MSG);
    AuctionCentralMainView.printOptionList(monthList.toArray(), AuctionCentralMainView.CANCEL_OPTION);

    int userInput = theScanner.nextInt();
    theScanner.nextLine();

    if (userInput == monthList.size() + 1) {
      System.out.println(AuctionCentralMainView.ACTION_CANCELED_MSG);
    } else if (userInput > 0 && userInput < 13) {
      displayFutureMonthsAuctions(theCalendar, userInput);
    } else {
      System.out.println(AuctionCentralMainView.INPUT_ERROR_MSG);
    }
  }

  private static void displayFutureMonthsAuctions(AuctionCalendar theCalendar, int theMonth) {
    if (theCalendar.hasMonthList(theMonth)) {
      for (Auction auction : theCalendar.getFutureMonthsAuctions(theMonth)) {
        System.out.println(auction.getMyName());
      }
    } else {
      System.out.println("No Auctions for this month");
    }
  }


  private static void viewAuctionDetails(Scanner theScanner, AuctionCalendar theCalendar) {
    List<String> monthList = new ArrayList<>(12);

    for (Month month : Month.values()) {
      monthList.add(month.getDisplayName(TextStyle.FULL, Locale.US));
    }

    System.out.println(AuctionCentralMainView.CHOOSE_OPTION_MSG);
    AuctionCentralMainView.printOptionList(monthList.toArray(), AuctionCentralMainView.CANCEL_OPTION);

    int userInput = theScanner.nextInt();
    theScanner.nextLine();

    if (userInput == monthList.size() + 1) {
      System.out.println(AuctionCentralMainView.ACTION_CANCELED_MSG);
    } else if (userInput > 0 && userInput < 13) {
      if (theCalendar.hasMonthList(userInput)) {
        System.out.println(AuctionCentralMainView.CHOOSE_OPTION_MSG);
        AuctionCentralMainView.printOptionList(theCalendar.getFutureMonthsAuctions(userInput).toArray(),
                AuctionCentralMainView.CANCEL_OPTION);
        int month = userInput;
        userInput = theScanner.nextInt();
        theScanner.nextLine();

        AuctionCentralMainView.printAuctionDetails(theCalendar.getFutureMonthsAuctions(month).get(userInput));
      } else {
        System.out.println("No Auctions for this month");
      }
    } else {
      System.out.println(AuctionCentralMainView.INPUT_ERROR_MSG);
    }
  }
}
