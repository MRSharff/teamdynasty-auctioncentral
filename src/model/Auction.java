package model;

import users.NonProfitOrganization;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class Auction implements Comparable<Auction> {

  private static final String[] OPTIONS = {"[1] Change start date", "[2] Add item", "[3] Remove item"};

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
            + myStartDate.getDate()
            + "-"
            + myStartDate.getYear();
  }

  public Auction(NonProfitOrganization theOwner, Date theStartDate, Date theEndDate) {
    this(theOwner, theStartDate, theEndDate, new ArrayList<Item>());
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

  public void listOptions() {
    for (String option : OPTIONS) {
      System.out.println(option);
    }
  }

  public void listItems() {
    int counter = 1;
    for (Item item : myItems) {
      System.out.println("[" + counter + "] " + item.getName());
      counter++;
    }
  }

  public void removeItem(Item theItem) {
    myItems.remove(theItem);
  }

  public void addItem(Item theItem) {
    myItems.add(theItem);
  }

  public void setMyName(String theName) {
    myName = theName;
  }

  public void setMyStartDate(Date theDate) {
    myStartDate = theDate;
    //TODO set myEndDate according to auction length + start date
    //set myEndDate accordingly
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
  public String viewDetails() {
    //TODO return proper String
    StringBuilder returnString = new StringBuilder();
    returnString.append("Auction Details for: " + myName + "\n");
    returnString.append("Start Date: " + myStartDate.toString() + "\n");
    //returnString.append("End Date: " + myEndDate.toString() + "\n");
    returnString.append("Items: \n");

    for (Item item : myItems) {
      returnString.append(item.getName() + "\n  Bids:\n");
      for (String bidOwner : item.getBids().keySet()) {
        returnString.append("    " + bidOwner + ": $" + item.getBids().get(bidOwner) + "\n");
      }
    }
    return returnString.toString();
  }

  @Override
  public String toString() {
    return myName;
  }
}