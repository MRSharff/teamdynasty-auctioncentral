import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;


public class Bidder extends AbstractUser {//implements Options {
	
	private static final String[] OPTIONS = {"[1] Choose a Non-profit Organization to View their Auction",
		 									 "[2] Bid on Item",
		 									 "[3] Change Bid"};

	int myCredit;
	Auction currentAuction;
	
	public Bidder(String theUsername) {
		super(theUsername, 3);
		myCredit = 0;
	}

	public void chooseAuction(HashMap<String, Auction> theAuctionList, TreeSet<AbstractUser> theUserList) {
		//code to choose auction goes here
		
		//Choose an NPO to view the auction of
		String chosenNPO = listNPO(theUserList);
		
		currentAuction = theAuctionList.get(chosenNPO);
		
		
	}
	
	private static String listNPO(TreeSet<AbstractUser> theUserList) {
		Scanner uInput = new Scanner(System.in);
//		List<NonProfitOrganization> NPOList;
		List<String> NPOList = new ArrayList<String>();
		
		
		System.out.println("Choose a Non-Profit Organization");
		//fill list of NPOs to show and print them
		int counter = 1;
		for (AbstractUser user : theUserList) {
			if (user.getUserType() == AuctionCentral.INPO) {
				NPOList.add(user.getUsername());
				System.out.println("[" + counter + "] " + user.getUsername());
				counter++;
			}
		}
		
		int option = uInput.nextInt();
		uInput.nextLine();
		
		return NPOList.get(option);
	}
	
	public void placeBid(final Item theItem) {
		//code to place bid goes here
		
		//Business Rule: only one bid on an Item per bidder
		
		
	}
	
	public void changeBid() {
		//code to change bid goes here
	}
	
	public String toXML() {
		return "3," + super.getUsername() + "," + myCredit;
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
		case 1: //Choose an auction to bid on.
			this.chooseAuction(theAuctionList, theUserList);
		}
		
	}
}
