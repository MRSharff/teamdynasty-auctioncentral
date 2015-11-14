import java.util.Date;
import java.util.List;


public class Auction implements Comparable<Auction> {
	
	private NonProfitOrganization myOwner;
	private Date myStartDate;
	private Date myEndDate;
	private List<Item> myItems;
	
	
	public Auction(Date theStartDate, Date theEndDate, List<Item> theItems) {
		myStartDate = theStartDate;
		myEndDate = theEndDate;
		myItems = theItems;
	}
	
	public Auction(Date theStartDate, Date theEndDate) {
		myStartDate = theStartDate;
		myEndDate = theEndDate;
		myItems = null;
	}
	
	public NonProfitOrganization getOwner() {
		//WIP to return defensive copy, low priority
		return myOwner;
	}
	
	public Date getStartDate() {
		//return defensive copy
		return new Date(myStartDate.getTime());
	}
	
	public Date getEndDate() {
		//return defensive copy
		return new Date(myEndDate.getTime());
	}
	
	public List<Item> getInventory() {
		//WIP to return defensive copy, low priority
		return myItems;
	}
	
	/** 
	 * Implements compareTo in order to keep auctions sorted by date start.
	 * 
	 * 
	 * @returns sorting info
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Auction otherAuction) {
		return myStartDate.compareTo(otherAuction.myStartDate);
	}
	
	/**
	 * Returns a string in the form of Date
	 */
	public String toString() {
		return "Date: " + 
				myStartDate.getMonth() + "/" + myStartDate.getDate() +
				"\nNon-Profit: " +
				myOwner.toString() +
				"\nItem: " +
				myItems.toString();
	}
}
