package users;

import model.Auction;
import model.AuctionCentral;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TreeSet;


public class ACEmployee extends AbstractUser {// implements Options {

	private static final String[] OPTIONS = {"[1] View Monthly Calendar",
		 									 "[2] View details of auction"};
	
	public ACEmployee(String theUsername) {
		super(theUsername, AuctionCentral.IACEMPLOYEE);
	}


	
	public void viewBids() {
		//code to veiwbids goes here.
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
	public void doAction(int theOption, HashMap<String, Auction> theAuctionList,
			TreeSet<AbstractUser> theUserList) {
		switch (theOption) {
      case 1:
        //for (Auction)
    }
		
	}

  private void viewCalendar() {
    //code to view calendar goes here
  }
}
