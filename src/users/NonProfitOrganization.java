package users;

import model.Auction;
import model.AuctionCalendar;
import model.AuctionCentral;

import java.util.*;


public class NonProfitOrganization extends AbstractUser {//implements Options {
	
	private static final String[] OPTIONS = {"[1] Schedule Auction",
											 "[2] Create Auction",
											 "[3] Edit Auction",
											 "[4] Create New Inventory Item",
											 "[5] Edit Inventory Item"};

  private String myOrgName;
  private List<Auction> myAuctions;
	
	public NonProfitOrganization(final String theUsername, final String theOrgName) {
		super(theUsername, AuctionCentral.INPO);
    myOrgName = theOrgName;
		//myInventory = new HashSet<model.Item>();
	}
	
	public Auction createAuction() {
		//code to enter auction info goes here

//    private NonProfitOrganization myOwner;
//    private Date myStartDate;
//    private Date myEndDate;
//    private List<Item> myItems;

    Scanner detailScanner = new Scanner(System.in);
    System.out.print("Enter auction date (mm/dd/yy)");
    int aMonth = detailScanner.nextInt();
    int aDay = detailScanner.nextInt();
    int aYear = detailScanner.nextInt();
    Date startDate = new Date(aYear, aMonth, aDay);
    Date endDate = new Date(aYear, aMonth, aDay + 1);

    Auction newAuction = new Auction(this, startDate, endDate);
    myAuctions.add(newAuction);
    return newAuction;
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

  public String getDashedName() {
    return myOrgName.replace(" ", "-");
  }
	
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
