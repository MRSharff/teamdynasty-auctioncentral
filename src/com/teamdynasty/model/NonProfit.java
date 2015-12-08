package com.teamdynasty.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**NonProfit class which keeps track of the nonprofits auction and it's inventory.
 * Created by Mat on 11/28/2015.
 */
public class NonProfit extends User {


  private boolean isScheduled;
  private Auction scheduledAuction;
  private LocalDateTime dateOfLastAuction;
  private String myOrgName;
  private Auction pendingAuction;
  private List<Item> myInventory;

  public NonProfit(final String theUsername, final String theOrgName) {
    super(theUsername, User.INPO);
    isScheduled = false;
    scheduledAuction = null;
    dateOfLastAuction = null;
    myOrgName = theOrgName;
    pendingAuction = null;
    myInventory = new ArrayList<>();

  }

  public String getDashedName() {
    return myOrgName.replace(" ", "-");
  }

  public Auction getPendingAuction() {
    return pendingAuction;
  }

//  public String getMyOrgName() {
//    return myOrgName;
//  }

//  public void setMyOrgName(final String theOrgName) {
//    myOrgName = theOrgName;
//  }

  public void setPendingAuction(final Auction theAuction) {
    pendingAuction = theAuction;
  }

  public boolean hasPendingAuction() {
    return (pendingAuction != null);
  }

  public boolean isCurrentlyScheduled() {
    return scheduledAuction != null && scheduledAuction.getStartDate().isAfter(LocalDateTime.now());
  }

  public boolean auctionIsHappening() {
    return LocalDateTime.now().isAfter(scheduledAuction.getStartDate())
            && LocalDateTime.now().isBefore(scheduledAuction.getEndDate());
  }

  public Auction scheduleAuction() {
    if (!isCurrentlyScheduled()) {
      if (hasPendingAuction() && !maxAuctionsPerYear()) {
        scheduledAuction = pendingAuction;
        //isCurrentlyScheduled = true;
        pendingAuction = null;
        return scheduledAuction;
      }
    }
    return null;
  }

  public boolean maxAuctionsPerYear() {
    if (isCurrentlyScheduled()) {
      return true;
    }
    return scheduledAuction != null && dateOfLastAuction.isAfter(LocalDateTime.now().minusYears(1));
  }

//  public void removeAuction() {
//    pendingAuction = null;
//  }

  public List<Item> getMyInventory() {
    return myInventory;
  }

  @Override
  public String toString() {
    return myOrgName;
  }

  public void addItemToInventory(Item theItem) {
    myInventory.add(theItem);
  }

  public LocalDateTime getDateOfLastAuction() {
    return dateOfLastAuction;
  }
}
