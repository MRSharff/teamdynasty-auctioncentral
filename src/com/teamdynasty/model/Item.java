package com.teamdynasty.model;

import java.io.Serializable;
import java.util.HashMap;

/**An Item which can be contained in a NonProfits inventory or
 * an auctions inventory.
 * Created by Mat on 11/28/2015.
 */
public class Item implements Serializable {

  //private static final String[] OPTIONS = {"[1] Name", "[2] Quantity", "[3] Minimum Starting Bid"};

  private String myName;
  private int myQuantity;
  private double myMinStartBid;
  private HashMap<String, Double> myBids;

  public Item(final String theName, final int theQuantity, final double theMinStartBid) {
    myName = theName;
    myQuantity = theQuantity;
    myMinStartBid = theMinStartBid;
    myBids = new HashMap<>();
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

  public boolean addBid(final String theBidder, final double theBidAmount) {
    //BR 6 implemented here
    boolean flag = true;

    //The bidder should be able to change their bid to a lower amount, I think
//    if (hasBidder(theBidder)) {
//      if (myBids.get(theBidder) > theBidAmount) {
//        flag = false;
//      }
//    }
    if (theBidAmount < myMinStartBid) {
      flag = false;
    }
    if (flag) {
      myBids.put(theBidder, theBidAmount);
    }

    return flag;
  }

//  public boolean hasBidder(String theBidderName) {
//    Set<String> bidderList = myBids.keySet();
//
//    return bidderList.contains(theBidderName);
//  }

  @Override
  public String toString() {
    return myName;
  }
}