package model;

import users.AbstractUser;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
* Auction Calendar Class depicts the month wise auction list
* present in the auction central system.One can perform the following
* operations in this class.
* <ul>
* <li>Add Auction into Auction Calendar.
* <li>Remove Auction from Auction Calendar.
* <li>Add the Non Profit Organization User that has created the Auction.
* </ul>
* <p>
* All of the business rules associated with the auctions are 
* enlisted in this class.
*
* @author	     Mat Sharff
* @author      Shoba Gopi
* @version     2.1
* @see         AbstractUser
*/
public class AuctionCalendar implements Serializable {

	private static final int TOTAL_MONTHS = 12;
	private static final int MAX_AUCTIONS = 25;
	private static final int MAX_DAYS_OUT = 90;
	private static final int MAX_ROLLING_AUCTIONS = 5;
	private static final int MAX_ROLLING_DAYS = 7;
	private static final int MINIMUM_HOURS_BETWEEN = 2;

	private HashMap < Integer, List < Auction >> myAuctions;
	private HashMap < String, AbstractUser > myUsers;

	/**
	* The Constructor of Auction Calendar Class.
	* <p>
	* Initializes the auction list and user list.
	*/
	public AuctionCalendar() {
		myAuctions = new HashMap < Integer, List < Auction >> (TOTAL_MONTHS);
		myUsers = new HashMap < String, AbstractUser > ();
	}
	
	/**
	* Returns a auction list with month numerical as its key set.
	* 
	* @return   the auction list hash map with months as its keys. 
	* @see 			Auction 
	*/
	public HashMap < Integer, List < Auction >> getMyAuctions() {
		return myAuctions;
	}
	
	/**
	* Returns a user list with username as its key set.
	* 
	* @return   the user list hash map with username as its keys. 
	* @see 			Auction 
	*/
	public HashMap < String, AbstractUser > getMyUsers() {
		return myUsers;
	}
	
	/**
	* Adds an auction to the auction calendar.
	* <p>
	* Checks for duplicate auctions as well as maximum auctions in the month 
	* and in the day before adding it to the auction calendar.
	* 
	* @param theAuction			the details of the auction to be added to the auction calendar. 
	* @see 				 		      Auction 
	*/
	public void addAuction(final Auction theAuction) {

		if (!myAuctions.containsKey(theAuction.getStartDate().getMonthValue())) {
			ArrayList < Auction > newList = new ArrayList < Auction > ();

			myAuctions.put(theAuction.getStartDate().getMonthValue(), newList);
		}

		boolean bRPass = true;
		if (hasMaxAuctions()) {
			System.out.println("Maximum auctions reached, no more auctions can be scheduled.");
			bRPass = false;
		}
		if (maxAuctionsInOneDay(theAuction)) {
			System.out.println("Too many auctions on that day, please reschedule.");
			bRPass = false;
		}
		if (!isWithinMaxDaysOut(theAuction)) {
			System.out.println("You must schedule your auction at a date within " + MAX_DAYS_OUT + " days.");
			System.out.println("Please change your auction date to no later than " + LocalDate.now().plusDays(MAX_DAYS_OUT));
			bRPass = false;
		}

		if (bRPass) {
			myAuctions.get(theAuction.getStartDate().getMonthValue()).add(theAuction);
			System.out.println(theAuction.getMyName() + " scheduled for " + theAuction.getStartDate().getHour() + ":" + theAuction.getStartDate().getMinute());
		}
	}
	
	/**
	* Removes an auction from the auction calendar.
	* <p>
	* Checks for empty auction list in the particular month 
	* and prevents removing auctions that are currently in progress.
	* 
	* @param theAuction		the details of the auction to be added to the auction calendar. 
	* @see 				 	      Auction 
	*/
	public void removeAuction(final Auction theAuction) {

		if (LocalDateTime.now().isAfter(theAuction.getStartDate()) && LocalDateTime.now().isBefore(theAuction.getEndDate())) {
			System.out.println("The auction is currently in progress.");
		} else if (!myAuctions.get(theAuction.getStartDate().getMonthValue()).isEmpty()) {

			myAuctions.get(theAuction.getStartDate().getMonthValue()).remove(theAuction);
			System.out.println("Successfully removed auction.");
		} else {
			System.out.println("No Such Auction Present in the Auction Calendar.");
		}
	}
	/**
	* Add a Non Profit Organization user to the Auction Calendar.
	* <p>
	* Checks if the user already exits in the userlist 
	* and prevents it from creating more than one auction in an year.
	* 
	* @param theUser		the details of the Non Profit Organization user which has created the auction
	*						        and will be added to the auction calendar.
	* @return				    <code>true</code> if the user is successfully added to the user list;
							        <code>false</code> otherwise.
	* @see 				 	    AbstractUser 
	*/
	public boolean addUser(final AbstractUser theUser) {

		if (myUsers.keySet().contains(theUser.getUsername().toLowerCase())) {
			System.out.println("Username already exists.");
		} else {
			myUsers.put(theUser.getUsername().toLowerCase(), theUser);
			return true;
		}
		return false;
	}

	/**
	* Business Rule 1 : No more than 25 auctions may be scheduled into the future.
	* <p>
	* Checks the maximum limit on the future auctions.
	* 
	* @return 	<code>true</code> if the maximum future auction limit has been breached;
	*				    <code>false</code> otherwise.
	*/
	private boolean hasMaxAuctions() {
		int auctionCount = 0;
		for (Integer key: myAuctions.keySet()) {
			auctionCount += myAuctions.get(key).size();
		}
		return (auctionCount >= MAX_AUCTIONS);
	}

	/**
	* Business Rule 2 : An auction may not be scheduled more than 90 days from the current date.
	* <p>
	* Checks the auction cannot be scheduled more than 90 days in the future from the current date.
	* 
	* @param theAuction		the details of the auction to be added to the auction calendar.
	* @return 				    <code>true</code> if the auction has been scheduled more than 90 days in advance;
	*						          <code>false</code> otherwise.
	* @see 					      Auction
	*/
	private boolean isWithinMaxDaysOut(final Auction theAuction) {
		/*
		returns the auctions date minus the max days out (90 in tcss 360 case) and checks if it's after
		the current date
		returns the opposite of this comparison (auctionDate - max days > now)
		*/
		return (!theAuction.getStartDate().minusDays(MAX_DAYS_OUT).isAfter(LocalDateTime.now()));
	}

	/**
	* Business Rule 3 : No more than 5 auctions may be scheduled for any rolling 7 day period.
	* <p>
	* Checks if more than 5 auctions were scheduled in rolling 7 day period.
	* 
	* @param otherAuction	 the details of the other auctions that have been added to the auction calendar.
	* @return 				     <code>true</code> if the auction maximum limit in the rolling 7 day period 
	*						           has been breached; <code>false</code> otherwise.
	* @see 					       Auction
	*/
	private boolean maxInRollingPeriod(final Auction otherAuction) {
		int count = 0;
		for (Integer key: myAuctions.keySet()) {
			for (Auction auction: myAuctions.get(key)) {
				if (auction.getStartDate().minusDays(MAX_ROLLING_DAYS).isBefore(otherAuction.getEndDate()) || auction.getEndDate().plusDays(MAX_ROLLING_DAYS).isAfter(otherAuction.getStartDate())) {
					count++;
				}
			}
		}

		return (count >= MAX_ROLLING_AUCTIONS);
	}

	/**
	* Business Rule 4 Part A : No more than 2 auctions can be scheduled on the same day.
	* <p>
	* Checks if more than 2 auctions were scheduled on the same day.
	* 
	* @param otherAuction	  the details of the other auctions that have been added to the auction calendar.
	* @return 				      <code>true</code> if the more than 2 auctions are scheduled in the same day; 
	*						            <code>false</code> otherwise.
	* @see 					        Auction
	*/
	private boolean maxAuctionsInOneDay(final Auction otherAuction) {
		int count = 0;
		for (Auction auction: myAuctions.get(otherAuction.getStartDate().getMonthValue())) {
			if (auction.getStartDate().getDayOfMonth() == otherAuction.getStartDate().getDayOfMonth()) {
				count++;
				if (auctionStartTooSoon(auction, otherAuction)) {
					return true;
				}
			}
		}
		return (count > 1);
	}

	/**
	* Business Rule 4 Part B : The start time of the second auction can be no earlier than 2 hours
	* 						             after the end time of the first auction.
	* <p>
	* Checks if the difference in start and end times of second auction and first auction respectively 
	* is 2 hours or more than 2 hours.
	* 
	* @param firstAuction	  the details of the first auction scheduled on the day.
	* @param secondAuction	the details of the second auction scheduled on the day. 
	* @return 				      <code>true</code> if second auction starts 2 hours after the end of first auction; 
	*						            <code>false</code> otherwise.
	* @see 					        Auction
	*/
	private boolean auctionStartTooSoon(final Auction firstAuction, final Auction secondAuction) {
		return (ChronoUnit.HOURS.between(firstAuction.getEndDate(), secondAuction.getStartDate()) < MINIMUM_HOURS_BETWEEN || ChronoUnit.HOURS.between(secondAuction.getEndDate(), firstAuction.getStartDate()) < MINIMUM_HOURS_BETWEEN);
	}
}
