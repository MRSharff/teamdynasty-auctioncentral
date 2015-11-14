package users;

import model.Auction;
import model.AuctionCalendar;
import model.AuctionCentral;

import java.util.HashMap;
import java.util.TreeSet;


public class NonProfitOrganization extends AbstractUser {//implements Options {
	
	private static final String[] OPTIONS = {"[1] Schedule model.Auction",
											 "[2] Create model.Auction",
											 "[3] Edit model.Auction",
											 "[4] Create New Inventory model.Item",
											 "[5] Edit Inventory model.Item"};
	
	
	public NonProfitOrganization(String theUsername) {
		super(theUsername, AuctionCentral.INPO);
		//myInventory = new HashSet<model.Item>();
	}
	
	public Auction createAuction() {
		//code to enter auction info goes here
    return null;
	}
	
	public void scheduleAuction() {
		//code to create auction goes here
	}
	
	public void editAuction() {
		//code to edit auction goes here
	}
	
	public void createItem() {
		//code to add inventory item
	}
	
	public void editItem() {
		//code to edit inventory goes here
	}
	
//	public void showOptions() {
//		for (String option : OPTIONS) {
//			System.out.println(option);
//		}
//	}
	
	
	public String toXML() {
		//WIP
		StringBuilder xml = new StringBuilder();
		xml.append("");
		
		return xml.toString();
	}
	
	@Override
	public int compareTo(AbstractUser otherUser) {
		return getUsername().compareTo(otherUser.getUsername());
	}

	@Override
	public void showOptions() {
		for (String option : OPTIONS) {
			System.out.println(option);
		}
	}

	@Override
	public void doAction(int theOption, HashMap<String, Auction> theAuctionList,
			TreeSet<AbstractUser> theUserList) {
		// TODO Auto-generated method stub
		
	}

}
