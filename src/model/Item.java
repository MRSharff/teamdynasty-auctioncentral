package model;

import users.Bidder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;


public class Item implements Serializable {

  private static final String[] OPTIONS = {"[1] Name", "[2] Quantity", "[3] Minimum Starting Bid"};
	
	private String myName;
	private int myQuantity;
  private double myMinStartBid;
	private HashMap<String, Double> myBids;
	
	public Item(final String theName, final int theQuantity, final double theMinStartBid) {
		myName = theName;
		myQuantity = theQuantity;
    myMinStartBid = theMinStartBid;
		myBids = new HashMap<String, Double>();
	}

	
	public String getName() {
		return myName;
	}
	
	public int getQuantity() {
		return myQuantity;
	}
	
	public HashMap<String, Double> getBids() {
		return myBids;
	}
	
	public void setName(final String theName) {
		myName = theName;
	}

  public void setMyMinStartBid(double theStartingBid) {
    myMinStartBid = theStartingBid;
  }

  public double getMyMinStartBid() {
    return myMinStartBid;
  }
	
	public void setQuantity(final int theQuantity) {
		myQuantity = theQuantity;
	}
	
	public boolean addBid(final String theBidder, final double bidAmount) {
		//BR 6 implemented here
    boolean flag = true;
    if (bidAmount < myMinStartBid) {
      System.out.println("You must enter a bid higher than the minimum starting bid of " + myMinStartBid);
      System.out.println();
      return false;
    } else {

      if (!hasDuplicateBidder(theBidder)) {
        myBids.put(theBidder, bidAmount);
      } else {
        if (myBids.get(theBidder) < bidAmount) {
          myBids.put(theBidder, bidAmount);
        } else {
          System.out.println("You must enter a higher bid.");
          return false;
        }
      }
    }
    return true;
	}

  public void listOptions() {
    for (String option : OPTIONS) {
      System.out.println(option);
    }
  }
	
	public boolean hasDuplicateBidder(String theBidderName) {
		Set<String> bidderList = myBids.keySet();
		
		return bidderList.contains(theBidderName);
	}
	
}
