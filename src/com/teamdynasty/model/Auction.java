package com.teamdynasty.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Auction implements Comparable<Auction>, Serializable {

  private static final String[] OPTIONS = {"Change date", "Add item", "Remove item"};
  private static final int HOUR_OFFSET = 12;

  //private String myName;
  private NonProfitOrganization myOwner;
  private LocalDateTime myStartDate;
  private LocalDateTime myEndDate;
  private List<Item> myItems;


  public Auction(NonProfitOrganization theOwner, LocalDateTime theStartDate, LocalDateTime theEndDate, List<Item> theItems) {
    myOwner = theOwner;
    myStartDate = theStartDate;
    myEndDate = theEndDate;
    myItems = theItems;
  }

  public Auction(NonProfitOrganization theOwner, LocalDateTime theStartDate, LocalDateTime theEndDate) {
    this(theOwner, theStartDate, theEndDate, new ArrayList<Item>());
  }

  public Auction(NonProfitOrganization theOwner) {
    this(theOwner, null, null);
  }

  public String getMyName() {
    return myOwner.getDashedName()
            + "-"
            + firstCap(myStartDate.getMonth().toString())
            + "-"
            + myStartDate.getDayOfMonth()
            + "-"
            + myStartDate.getYear();
  }

  public NonProfitOrganization getOwner() {
    //TODO  return defensive copy, low priority
    return myOwner;
  }

  public LocalDateTime getStartDate() {
    //return defensive copy
    return myStartDate;
  }

  public LocalDateTime getEndDate() {
    //return defensive copy
    return myEndDate;
  }

  public List<Item> getInventory() {
    //WIP to return defensive copy, low priority
    return myItems;
  }

  public void removeItem(int itemIndex) {
    myItems.remove(itemIndex);
  }

  public void addItem(Item theItem) {
    myItems.add(theItem);
  }

  public void setMyStartDate(LocalDateTime theDate) {
    myStartDate = theDate;
    //TODO possibly set myEndDate according to auction length + start date
    //set myEndDate accordingly
  }

  /**
   * Implements compareTo in order to keep auctions sorted by date start.
   *
   * @returns sorting info
   * @see Comparable#compareTo(Object)
   */
  @Override
  public int compareTo(Auction otherAuction) {
//    return myStartDate.compareTo(otherAuction.myStartDate);
    return this.getStartDate().compareTo(otherAuction.getStartDate());
  }

  private String firstCap(final String theString) {
    return theString.substring(0, 1).toUpperCase() + theString.substring(1).toLowerCase();
  }

  @Override
  public String toString() {
    return getMyName();
  }

  public void setStartDate(LocalDateTime theStartDate) {
    myStartDate = theStartDate;
  }

  public void setEndDate(LocalDateTime theEndDate) {
    myEndDate = theEndDate;
  }
}