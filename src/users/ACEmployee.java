package users;

import model.Auction;
import model.AuctionCalendar;
import model.AuctionCentral;

import java.time.LocalDate;
import java.util.*;
import java.text.DateFormatSymbols;

/**
* ACEmployee Class depicts a Auction Central Employee 
* which can perform the following operation.
* <ul>
* <li>View Monthly Auction List with past and future Auctions.
* <li>Detials of a specific Auction.
* </ul>
* <p>
* Each Auction Central Employee is identified by a unique Username
* and the User Type Constant is 1.
* It is inherited from the Abstract User Class.
*
* @author      Shoba Gopi
* @version     2.1
* @see         AbstractUser
*/
public class ACEmployee extends AbstractUser {

	private static final String[] OPTIONS = {
		"[1] View Monthly Calendar",
			"[2] View details of auction"
	};

	/**
	* The Constructor of ACEmployee Class.
	* <p>
	* Calls the constructor of the base class Abstract User 
	* and initializes the username of the Auction Employee 
	* as well as the user type as 1.
	* 
	* @param theUsername the unique username of the Auction Employee.
	*/
	public ACEmployee(String theUsername) {
		super(theUsername, AuctionCentral.IACEMPLOYEE);
	}
	
	/**
	* Lists the month wise auctions in the auction calendar. 
	* Can be accessed only by an Auction Central Employee.
	* <p>
	* Numerical value of the month is provided by the user 
	* which is validated for an Integer.Incorrect Month Numerical  
	* is also validated.
	* Method exits on pressing -1 from the User via I/O Console.
	* 
	* @param theCalendar it stores the auction list month wise 
	* 		     along with the user list which has created the auction.
	* @see 		     AuctionCalendar 
	*/
	public void viewCalendar(AuctionCalendar theCalendar) {

		int aMonth = Calendar.MONTH;

		if (theCalendar.getMyAuctions().isEmpty()) {
			System.out.println("The Auction List Is Empty");
			return;
		}

		while (aMonth > 0) {

			aMonth = inputFromUser("Enter a month by number (1-12) or " + "[-1] Go back:");

			if (aMonth == -1) {
				break;
			}

			if (aMonth < 1 || aMonth > 12) {
				System.out.println("Please Enter a Correct Month Numerical");
				continue;
			}

			viewMonth(aMonth, theCalendar.getMyAuctions(), false);
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
	* @param theCalendar 	it stores the auction list month wise 
	* 			along with the user list which has created the auction.
	* @see 			AuctionCalendar 
	*/
	public void viewAuctionDetails(AuctionCalendar theCalendar) {
		
		int aMonth = -1;

		if (theCalendar.getMyAuctions().isEmpty()) {
			System.out.println("The Auction List Is Empty");
			return;
		}

		aMonth = allAuction(theCalendar.getMyAuctions());

		if (aMonth == -1) return;

		specificAuction(aMonth, theCalendar.getMyAuctions());

	}

	/**
	* Returns a Integer that is used to call the specificAuction method
	* or to exit the function.
	* Accessed within the viewAuctionDetails method.
	* <p>
	* Checks if an auction has been scheduled in the month.
	* provided by the user.Calls the viewMonth to get the auction list in the 
	* month provided by the user.
	* 
	* @param theAuctionList	stores all the auctions month wise in separate lists 
	* 			for each month.
	* @return   		<code>-1</code> if no auctions are present in the auction list 
	* 			for the input month or if it fails the data validations;
	*			<code>aMonth</code> otherwise.
	* @see 			Auction 
	*/
	private int allAuction(final HashMap < Integer, List < Auction >> theAuctionList) {

		int aMonth = -1;

		aMonth = inputFromUser("Enter the month of the auction you'd like to view (1-12): ");

		if (aMonth == -1) {
			return -1;
		}

		if (aMonth < 1 || aMonth > 12) {
			System.out.println("Please Enter a Correct Month Numerical");
			return -1;
		}

		viewMonth(aMonth, theAuctionList, true);

		if (theAuctionList.get(aMonth) != null && !theAuctionList.get(aMonth).isEmpty()) {
			return aMonth;
		}

		return -1;

	}
	
	/**
	* Prints the auction details of the auction specified by the user.
	* Accessed within the viewAuctionDetails method.
	* <p>
	* Checks for valid auction number provided by the user.
	* 
	* @param theMonth	the specific month provided by the user for which auctions  will be listed
	* 			
	* @param theAuctionList	stores all the auctions month wise in separate lists 
	* 			for each month.
	* @see 			Auction 
	*/
	private void specificAuction(final int theMonth, final HashMap < Integer, List < Auction >> theAuctionList) {

		int auctionNumber = 0;

		auctionNumber = inputFromUser("Enter the number of the auction you wish to view: ");

		if (auctionNumber == -1) {
			return;
		}

		if (auctionNumber < 1 || auctionNumber > theAuctionList.get(theMonth).size()) {
			System.out.println("Please Enter a Correct Auction Number from the List");
			return;
		}

		auctionNumber -= 1;

		// Print Auction Details
		System.out.println(theAuctionList.get(theMonth).get(auctionNumber).viewDetails());

	}
	
	/**
	* Prints the auction details of the specific auction in the month provided.
	* Accessed within the viewCalendar and viewAuctionDetails method.
	* <p>
	* Checks for empty auction list and prints appropriate message to the user.
	* 
	* @param theMonth	the specific month provided by the user for which 
	* 			auctions  will be listed
	* @param theAuctionList	stores all the auctions month wise in separate lists for each month. 
	* 					 		
	* @param numbered	if <code>true</code> prints the ordered list of auctions;  
	* 			if <code>false</code> does not print auction numbering.
	* @see 			Auction 
	*/
	private void viewMonth(final int theMonth, final HashMap < Integer, List < Auction >> theAuctionList, boolean numbered) {
		List < Auction > currentMonthList = theAuctionList.get(theMonth);

		if (currentMonthList != null && !currentMonthList.isEmpty()) {

			Collections.sort(currentMonthList);
			int count = 1;
			System.out.println("Auctions for the Month of " + getMonth(theMonth));
			for (Auction auction: currentMonthList) {
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
		}
	}
	
	/**
	* Returns a validated Integer input from the user.
	* Accessed within the viewCalendar, allAuction and specificAuction method.
	* <p>
	* Checks if the input by the user is a valid Integer or not.
	* 
	* @param InputMessage	the screen print asking for user input.
	* @return   		the validated Integer input of user 
	* 			or -1 if the input is invalid.
	*/
	private int inputFromUser(String InputMessage) {

		Scanner auctionInputScanner = new Scanner(System. in );
		String tempInput = "";
		int Number = 0;

		System.out.print(InputMessage);

		if (auctionInputScanner.hasNext()) {

			tempInput = auctionInputScanner.next();

			try {
				Number = Integer.parseInt(tempInput);
			} catch (Exception e) {
				System.out.println("The Input Should be an Integer");
				return -1;
			}
		}
		return Number;
	}

	/**
	* Returns a valid month name.
	* Accessed within the viewMonth method.
	* <p>
	* Checks if the input provided by the user is a valid month numerical or not.
	* 
	* @param month	the numerical value of the month (1-12)
	* @return   	the month name.
	* 							
	*/
	private String getMonth(int month) {
		return new DateFormatSymbols().getMonths()[month - 1];
	}
	
	/**
	* Displays the Tasks on the screen that an Auction Employee can perform						
	*/
	public void showOptions() {
		for (String option: OPTIONS) {
			System.out.println(option);
		}
	}
	
	/**
   * Implements compareTo in order to keep all the usernames sorted in alphabetical order.
   *
   * @param otherUser	the user to be added in the correct order.	
   * @return 		Sorting Information.
   * @see 		java.lang.Comparable#compareTo(java.lang.Object)
   */
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
}
