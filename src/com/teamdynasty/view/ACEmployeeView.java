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

/**
* ACEmployee Class depicts a Auction Central Employee 
* which can perform the following operation.
* <ul>
* <li>View Monthly Auction List with past and future Auctions.
* <li>Details of a specific Auction.
* </ul>
* <p>
* Each Auction Central Employee is identified by a unique Username
* and the User Type Constant is 1.
* It is inherited from the IUserView Class.
*
* @author      Shoba Gopi
* @author	   Mat Sharff
* @see         IUserView
*/
public class ACEmployeeView implements IUserView {

  private static final String[] USER_OPTIONS = {"View Monthly Calendar",
                                                "View details of auction"};

  	/**
	* Lists the Options which an Auction Employee can perform. 
	* Can be accessed only by an Auction Central Employee.
	* 
	* @param theScanner	 the input class for user input.
	* @param theCalendar it stores the auction list month wise 
	* 					 along with the user list which has created the auction.
	* @param theUser	 the Interface User which has a UserTpe 1.
	* @see 				 AuctionCalendar
	* @see 				 User	
	*/
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

  
  	/**
	* Lists the month wise auctions in the auction calendar. 
	* Can be accessed only by an Auction Central Employee.
	* <p>
	* Numerical value of the month is provided by the user 
	* which is validated for an Integer.Incorrect Month Numerical  
	* is also validated.
	* 
	* @param theScanner	 the input class for user input.
	* @param theCalendar it stores the auction list month wise 
	* 					 along with the user list which has created the auction.
	* @see 				 AuctionCalendar 
	*/
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
      System.out.println(AuctionCentralMainView.ACTION_CALENDAR_CANCELED_MSG);
    } else if (userInput > 0 && userInput < 13) {
      displayFutureMonthsAuctions(theCalendar, userInput);
    } else {
      System.out.println(AuctionCentralMainView.INPUT_ERROR_MSG);
    }
  }

  	/**
	* Lists all the auctions in a specific month 
	* Accessed within the viewMonthlyCalendar method.
	* 
	* @param theCalendar 	it stores the auction list month wise 
	* 					 	along with the user list which has created the auction.
	* @param theMonth		the specific month provided by the user for which 
	* 						auctions  will be listed
	* @see 				 	AuctionCalendar 
	*/
  public static void displayFutureMonthsAuctions(AuctionCalendar theCalendar, int theMonth) {
    for (Auction auction : theCalendar.getFutureMonthsAuctions(theMonth)) {
      System.out.println(auction.getMyName());
    }
  }

	/**
	* Lists all the auctions in a specific month 
	* and selectively view a particular auction.
	* Can be accessed only by an Auction Central Employee.
	* <p>
	* Numerical value of the month is provided by the user 
	* which is validated for an Integer.Incorrect Month Numerical  
	* and invalid auction number in the auction list is also 
	* validated.
	* 
	* @param theScanner		the input class for user input.
	* @param theCalendar 	it stores the auction list month wise 
	* 					 	along with the user list which has created the auction.
	* @see 				 	AuctionCalendar 
	*/
  public static void viewAuctionDetails(Scanner theScanner, AuctionCalendar theCalendar) {
    List<String> monthList = new ArrayList<>(12);

    for (Month month : Month.values()) {
      monthList.add(month.getDisplayName(TextStyle.FULL, Locale.US));
    }

    System.out.println(AuctionCentralMainView.CHOOSE_OPTION_MSG);
    AuctionCentralMainView.printOptionList(monthList.toArray(), AuctionCentralMainView.CANCEL_OPTION);

    int userInput = theScanner.nextInt();
    theScanner.nextLine();

    if (userInput == monthList.size() + 1) {
      System.out.println(AuctionCentralMainView.ACTION_AUCTION_CANCELED_MSG);
    } else if (userInput > 0 && userInput < 13) {
		
	  if(theCalendar.getFutureMonthsAuctions(userInput).isEmpty()) {
		  System.out.println(AuctionCentralMainView.ACTION_AUCTION_CANCELED_MSG);
		  return;
	  }	
		
      System.out.println(AuctionCentralMainView.CHOOSE_OPTION_MSG);
      AuctionCentralMainView.printOptionList(theCalendar.getFutureMonthsAuctions(userInput).toArray(),
              AuctionCentralMainView.CANCEL_OPTION);
      int month = userInput;
      userInput = theScanner.nextInt();
      theScanner.nextLine();
	  
	  if (userInput == theCalendar.getFutureMonthsAuctions(month).toArray().length + 1) {
		  System.out.println(AuctionCentralMainView.ACTION_AUCTION_CANCELED_MSG);
		  return;
	  }

      AuctionCentralMainView.printAuctionDetails(theCalendar.getFutureMonthsAuctions(month).get(userInput-1));
    } else {
      System.out.println(AuctionCentralMainView.INPUT_ERROR_MSG);
    }
  }
  
  
}
