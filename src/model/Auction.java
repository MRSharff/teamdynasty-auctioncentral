package model;

import users.NonProfitOrganization;

import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class Auction implements Comparable<Auction> {

  private String myName;
  private NonProfitOrganization myOwner;
  private Date myStartDate;
  private Date myEndDate;
  private List<Item> myItems;


  public Auction(NonProfitOrganization theOwner, Date theStartDate, Date theEndDate, List<Item> theItems) {
    myStartDate = theStartDate;
    myEndDate = theEndDate;
    myItems = theItems;

    myName = theOwner.getDashedName()
            + "-"
            + myStartDate.getMonth()
            + "-"
            + myStartDate.getDay()
            + "-"
            + myStartDate.getYear();
  }

  public Auction(NonProfitOrganization theOwner, Date theStartDate, Date theEndDate) {
    this(theOwner, theStartDate, theEndDate, null);
  }

  public String getMyName() {
    return myName;
  }

  public NonProfitOrganization getOwner() {
    //TODO  return defensive copy, low priority
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
   * @returns sorting info
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Auction otherAuction) {
//    return myStartDate.compareTo(otherAuction.myStartDate);
    return this.getStartDate().compareTo(otherAuction.getStartDate());
  }

  /**
   * Returns a string in the form of Date
   */
  public String toString() {
    //TODO return proper String
    StringBuilder returnString = new StringBuilder();
    returnString.append("Auction Details for: " + myName + "\n");
    returnString.append("Start Date: " + myStartDate.toString() + "\n");
    returnString.append("End Date: " + myEndDate.toString() + "\n");
    returnString.append("Items: \n");

    for (Item item : myItems) {
      returnString.append(item.toString() + "\n  Bids:");
      for (Bid bid : item.getBids().values()) {
        returnString.append(bid.getOwner() + ": " + bid.getAmount());
      }
    }
    return myName + ":\n" + myStartDate.toString();
  }
}