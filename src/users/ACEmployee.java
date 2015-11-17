package users;

import model.Auction;
import model.AuctionCalendar;
import model.AuctionCentral;

import java.time.LocalDate;
import java.util.*;


public class ACEmployee extends AbstractUser {// implements Options {

	private static final String[] OPTIONS = {"[1] View Monthly Calendar",
		 									 "[2] View details of auction"};

	public ACEmployee(String theUsername) {
		super(theUsername, AuctionCentral.IACEMPLOYEE);
	}



	public void viewAuctionDetails(AuctionCalendar theCalendar) {
		//code to veiwbids goes here.
    Scanner auctionScanner = new Scanner(System.in);

    int aMonth = 0;
    int auctionNumber = 0;
    String auctionName = null;
    boolean hadAuctions;

    if (auctionName == null) {
      System.out.print("Enter the month (numerically) of the auction you'd like to view: ");

      aMonth = auctionScanner.nextInt();
      boolean isNumbered = true;
      hadAuctions = viewMonth(aMonth, theCalendar.getMyAuctions(), isNumbered);

      if (hadAuctions) {
        System.out.print("Enter the number of the auction you wish to view: ");
        auctionNumber = auctionScanner.nextInt() - 1;
      }
//    print the proper auction

    }
    System.out.println(theCalendar.getMyAuctions().get(aMonth).get(auctionNumber).viewDetails());

	}

	public void showOptions() {
		for (String option : OPTIONS) {
			System.out.println(option);
		}
	}

	@Override
	public int compareTo(AbstractUser otherUser) {
		return getUsername().compareTo(otherUser.getUsername());
	}

	@Override
	public void doAction(int theOption, AuctionCalendar theCalendar) {
		switch (theOption) {
      case 1:
        viewCalendar(theCalendar);
        break;
      case 2:
        viewAuctionDetails(theCalendar);
        break;
    }

	}

  private static void viewCalendar(AuctionCalendar theCalendar) {
    //code to view calendar goes here

    int aMonth = Calendar.MONTH;
    Scanner monthScanner = new Scanner(System.in);

    while (aMonth >= 0) {
      System.out.print("Enter a month by number (1 for January, 2 for February, etc) or enter -1 to return: ");

      if (monthScanner.hasNextInt()) {
        aMonth = monthScanner.nextInt();
        if (aMonth >= 0) {


          //pass in false because we don't want a numbered list
          viewMonth(aMonth, theCalendar.getMyAuctions(), false);
//        List<Auction> currentMonthList = theAuctionList.get(aMonth);
//
//        if (!currentMonthList.isEmpty()) {
//
//          Collections.sort(currentMonthList);
//
//          for (Auction auction : currentMonthList) {
//            System.out.print(auction.getMyName());
//          }
//        } else {
//          System.out.println("No Auctions this month");
//        }
        }
      }
    }
  }

  private static boolean viewMonth(final int theMonth, final HashMap<Integer, List<Auction>> theAuctionList, boolean numbered) {
    List<Auction> currentMonthList = theAuctionList.get(theMonth);

    if (currentMonthList != null && !currentMonthList.isEmpty()) {

      Collections.sort(currentMonthList);
      int count = 1;
      System.out.println("Auctions for " + LocalDate.of(2015, theMonth, 1).getMonth());
      for (Auction auction : currentMonthList) {
        String printer = "";
        if (numbered) {
          printer = "[" + count + "] ";
          count++;
        }
        System.out.println(printer + auction.getMyName());
        System.out.println();
      }
    } else {
      System.out.println("No Auctions this month");
      return false;
    }
    return true;
  }
}
