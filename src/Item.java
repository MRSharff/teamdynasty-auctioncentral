import java.util.HashMap;
import java.util.Set;


public class Item {
	
	
	private String myName;
	private int myQuantity;
	private HashMap<String, Bid> myBids;
	
	public Item(final String theName, final int theQuantity) {
		myName = theName;
		myQuantity = theQuantity;
		myBids = new HashMap<String, Bid>();
	}

	
	public String getName() {
		String returnStr = myName;
		return returnStr; //return a copy
	}
	
	public int getQuantity() {
		return myQuantity;
	}
	
	public HashMap<String, Bid> getBids() {
		return myBids;
	}
	
	public void setName(final String theName) {
		myName = theName;
	}
	
	public void setQuantity(final int theQuantity) {
		myQuantity = theQuantity;
	}
	
	public void addBid(final Bid theBid) {
		//BR 6 implemented here
		boolean BRflag = hasDuplicateBidder(theBid.getOwner());
		
		if (!BRflag) {
			myBids.put(theBid.getOwner().getUsername(), theBid);
		} else {
			System.out.println(AuctionCentral.BR_ONEBID);
		}
	}
	
	public boolean hasDuplicateBidder(final Bidder theBidder) {
		Set<String> bidderList = myBids.keySet();
		
		return bidderList.contains(theBidder.getUsername());
	}
	
}
