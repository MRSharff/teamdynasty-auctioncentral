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
	
	  public void viewCalendar(final HashMap<Integer, List<Auction>> theAuctionList) {
    //code to view calendar goes here

	String tempInput = "";
    int aMonth = 0;
    Scanner monthScanner = new Scanner(System.in);
	
	if ( theAuctionList.isEmpty() ){
		System.out.println("The Auction List Is Empty");
		return;
	}

    while (true) {
      System.out.print("Enter the month (numerically) of the auction you'd like to view or enter -1 to exit the function: ");

      if (monthScanner.hasNext()) {
		  
		tempInput =  monthScanner.next() ; 

		try {                                                                         
			aMonth = Integer.parseInt(tempInput);                                                                                                             
		} catch (Exception e) {                                                       
			System.out.println("The Input Should be an Integer");    
			continue;
		}
		tempInput = ""; 		
		
		if ( aMonth == -1){
			return;
		}
		
		if ( aMonth < 1 || aMonth > 12){
			System.out.println("Please Enter a Correct Month Numerical");
			continue;
		}
		
		boolean isNumbered = false;
        viewMonth(aMonth, theAuctionList, isNumbered);

      }
    }
  }
	
	public void viewAuctionDetails(HashMap<Integer, List<Auction>> theAuctionList) {
		//code to view Auction Details goes here.
		
    Scanner auctionScanner = new Scanner(System.in);

    String tempInput = "";
	int count=0;
	int aMonth = 0;
    int auctionNumber = 0;
	
	if ( theAuctionList.isEmpty() ){
		System.out.println("The Auction List Is Empty");
		return;
	}

    while (true) {
      System.out.print("Enter the month (numerically) of the auction you'd like to view or enter -1 to exit the function: ");

       if (auctionScanner.hasNext()) {
		   
		tempInput =  auctionScanner.next() ; 

		try {                                                                         
			aMonth = Integer.parseInt(tempInput);                                                                                                             
		} catch (Exception e) {                                                       
			System.out.println("The Input Should be an Integer");    
			continue;
		} 	
		tempInput = "";
		
		if ( aMonth == -1){
			return;
		}
		
		if ( aMonth < 1 || aMonth > 12) {
			System.out.println("Please Enter a Correct Month Numerical");
			continue;
		}
		
		boolean isNumbered = true;
		count=viewMonth(aMonth, theAuctionList, isNumbered);
		
		if ( count == 0) {
			continue;
		}
		
	   }
	   
	 else {
		continue;
	   }
	   
	   System.out.print("Enter the Auction Number from the above list or enter -1 to exit the function: ");
	   
	   if (auctionScanner.hasNext()) {
		   
		   tempInput =  auctionScanner.next() ; 

			try {                                                                         
				auctionNumber = Integer.parseInt(tempInput);                                                                                                             
			} catch (Exception e) {                                                       
				System.out.println("The Input Should be an Integer");    
				continue;
			} 	
			tempInput = "";
		
			if ( auctionNumber == -1){
				return;
			}
				
			if ( auctionNumber < 0 || auctionNumber > count - 1 ){
				System.out.println("Please Enter a Correct Auction Number from the List");
				continue;
			}

			// Print Auction Details
			System.out.println(theAuctionList.get(aMonth).get(auctionNumber));
		
	   }
	
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

  private int viewMonth(final int theMonth, final HashMap<Integer, List<Auction>> theAuctionList, boolean numbered) {
    List<Auction> currentMonthList = theAuctionList.get(theMonth);
	int count = 0;
	
	if (currentMonthList == null ){
		System.out.println("No Auctions this month");
		return count;
	}
	
    if (!currentMonthList.isEmpty()) {

      Collections.sort(currentMonthList);
      
      for (Auction auction : currentMonthList) {
        String printer = "";
        if (numbered) {
          printer = "[" + count + "] ";
          count++;
        }
        System.out.println(printer + auction.getMyName());
      }
    } else {
      System.out.println("No Auctions this month");
    }
	
	return count;
  }
}
