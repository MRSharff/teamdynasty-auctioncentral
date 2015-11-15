package users;

import model.Auction;
import model.AuctionCentral;

import java.util.*;


public class ACEmployee extends AbstractUser {// implements Options {

	private static final String[] OPTIONS = {"[1] View Monthly Calendar",
		 									 "[2] View details of auction"};
	
	public ACEmployee(String theUsername) {
		super(theUsername, AuctionCentral.IACEMPLOYEE);
	}


	
	public void viewAuctionDetails(HashMap<Integer, List<Auction>> theAuctionList) {
		//code to veiwbids goes here.
    Scanner auctionScanner = new Scanner(System.in);

    int aMonth = 0;
    int auctionNumber = 0;
    String auctionName = null;

    while (auctionName == null) {
      System.out.print("Enter the month (numerically) of the auction you'd like to view: ");

      aMonth = auctionScanner.nextInt();
      boolean isNumbered = true;
      viewMonth(aMonth, theAuctionList, isNumbered);

      System.out.print("Enter the number of the auction you wish to view: ");
      auctionNumber = auctionScanner.nextInt() - 1;

//    print the proper auction
      System.out.println(theAuctionList.get(aMonth).get(auctionNumber));
    }

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
	public void doAction(int theOption, HashMap<Integer, List<Auction>> theAuctionList,
			TreeSet<AbstractUser> theUserList) {
		switch (theOption) {
      case 1:
        viewCalendar(theAuctionList);
    }
		
	}

  private static void viewCalendar(final HashMap<Integer, List<Auction>> theAuctionList) {
    //code to view calendar goes here

    int aMonth = Calendar.MONTH;
    Scanner monthScanner = new Scanner(System.in);

    while (aMonth != -1) {
      System.out.print("Enter a month by number (1 for January, 2 for February, etc) or enter -1 to return: ");

      if (monthScanner.hasNextInt()) {
        aMonth = monthScanner.nextInt() + 1;

        boolean isNumbered = false;
        viewMonth(aMonth, theAuctionList, isNumbered);
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

  private static void viewMonth(final int theMonth, final HashMap<Integer, List<Auction>> theAuctionList, boolean numbered) {
    List<Auction> currentMonthList = theAuctionList.get(theMonth);

    if (!currentMonthList.isEmpty()) {

      Collections.sort(currentMonthList);
      int count = 0;
      for (Auction auction : currentMonthList) {
        String printer = "";
        if (numbered) {
          printer = "[" + count + "] ";
          count++;
        }
        System.out.print(printer + auction.getMyName());
      }
    } else {
      System.out.println("No Auctions this month");
    }
  }
}
