import java.util.Date;


public class Auction implements Comparable<Auction> {
	
	private NonProfitOrganization myOwner;
	private Date myDate;
	private Item myItem;
	
	
	public Auction(Date theDate, Item theItem) {
		myDate = theDate;
		myItem = theItem;
	}
	
	/** 
	 * Implements compareTo in order to keep auctions sorted by date.
	 * 
	 * 
	 * @returns sorting info
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Auction otherAuction) {
		return myDate.compareTo(otherAuction.myDate);
	}
	
	/**
	 * Returns a string in the form of Date
	 */
	public String toString() {
		return "Date: " + 
				myDate.getMonth() + "/" + myDate.getDate() +
				"\nNon-Profit: " +
				myOwner.toString() +
				"\nItem: " +
				myItem.toString();
	}
}
