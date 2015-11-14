

import java.util.Scanner; 
import java.util.Date;

/**
 * An Auction Employee can view Auction Calender , Auction Details and Bids on Items Available in Auction
 * @author Shoba Gopi
 **/

public class ACEmployee extends User {
	
	// Constructor
	public ACEmployee(String theUsername) {
		super(theUsername, 1);
	}

	public void viewCalendar(AuctionCalendar AC) {
		Scanner sc=new Scanner(System.in);
		
		InputChecker=true;
		int Month;
		
		// Check if there are Auctions Scheduled in the Auction Calendar
		if(AC.getAuctionList().isEmpty())
		{
			System.out.println("There are No Auctions Scheduled For This Year");
			return;
		}
		
		// Loop Until the correct Input Is Found or Press -1 To Exit The Function
		while(InputChecker==true)
		{
			System.out.println("Press -1 To Exit The Function");
			
			// The Month is in Integer Format which is January is 1 February is 2 and so on
			System.out.print("Please Enter The Month: ");
		
			Month = sc.nextInt();  
			
			// Exit if the user has changed its mind
			if(Month==-1)
			{
				System.out.println("The User has exited the method without performing it");
				return;
			}
		
			// Month Validation
			if (Month<1 && Month>12)
			{
				System.out.println("Please Enter A Month in the range of 1 to 12");
			}
			else
			{
				MonthChecker=false
			}
		}
		
		sc.close();  // Closing All User Inputs
		
		// Printing The Auction List present in the Auction Calender Which Is Present In The User Input Month
		for (Auction auction : AC.getAuctionList())
			if(auction.getmyDate().getMonth()==Month)
			{
				System.out.println(auction);
			}	
			
			/*Implement getAuctionList() in AuctionCalender Class
		
				public List<Auction> getAuctionList()
				{
					return myAuctionList;
				}
				
			Implement getmyDate() in Auction Class
				
				public Date getmyDate()
				{
					return myDate;
				}
			
			*/
	}
	
	public void viewBids(Auction auction) {
		
		System.out.println(auction.getmyItem().gettheBids())
		
		/*Implement getmyItem() in Auction Class
		
			public Item getmyItem()
			{
				return myItem;
			}
			
		Implement gettheBids() in Item Class
			
			public double gettheBids()
			{
				return theBids;
			}
		*/
	}
	
	public void viewAuctionDetails(List<Auction> AuctionList) {
		
		Scanner sc=new Scanner(System.in);
		
		boolean InputChecker;
		Date AuctionDate;
		String NGO;
		String dateFormat = "mm/dd/yyyy";
		
		// Check if there are Auctions Scheduled in the Auction Calendar
		if(AuctionList.isEmpty())
		{
			System.out.println("There are No Auctions Present in the Auction");
			return;
		}
		
		// // Loop Until the correct Input Is Found 
		do
		{ 	
			InputChecker=false;	
			try:
			{
				System.out.print("Please Enter The Auction Date ("+dateFormat+") : ");
				AuctionDate=SimpleDateFormat(dateFormat).parse(sc.nextLine());
			}	
			catch (ParseException pe) 
			{
				System.out.println("Please Enter The Auction Date In Correct Format "+dateFormat);
				InputChecker=true;
			}		
		}
		while(InputChecker==true);
				
		System.out.print("Please Enter The Non Profit Organization: ");
		NGO=sc.nextLine();
		sc.close(); // Closing All User Inputs
		
		// Printing The Auction present in the Auction List Which Is Matches with the User Input		
		for (Auction auction : AuctionList)
			if(auction.getmyDate()==AuctionDate && auction.getmyOwner().getUsername().equalsIgnoreCase(NGO))
			{
				System.out.println(auction);
			}
			
			/* Implement getmyOwner() in Auction Class
		
				public NonProfitOrganization getmyOwner()
				{
					return myOwner;
				}
			*/		
	}
	
	// Prints The Details of the Auction Employee
	public String toString() {
		return "The User name of the Auction Employee"+getUsername();
	}
	
}
