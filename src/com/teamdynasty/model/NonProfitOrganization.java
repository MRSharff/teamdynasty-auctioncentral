package com.teamdynasty.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mat on 11/28/2015.
 */
public class NonProfitOrganization extends User {

  private boolean isScheduled;
  private String myOrgName;
  private Auction myAuction;
  private List<Item> myInventory;

  public NonProfitOrganization(final String theUsername, final String theOrgName) {
    super(theUsername, User.INPO);
    myOrgName = theOrgName;
    myAuction = null;
    myInventory = new ArrayList<Item>();
  }

  public String getDashedName() {
    return myOrgName.replace(" ", "-");
  }

  public Auction getMyAuction() {
    return myAuction;
  }

  public String getMyOrgName() {
    return myOrgName;
  }

  public void setMyOrgName(final String theOrgName) {
    myOrgName = theOrgName;
  }

  public void setMyAuction(final Auction theAuction) {
    myAuction = theAuction;
  }

  public boolean hasAuction() {
    return (myAuction != null);
  }

  public boolean isScheduled() {
    return isScheduled;
  }

  public Auction scheduleAuction() {
    if (myAuction != null) {
      isScheduled = true;
    }
    return myAuction;
  }

  public List<Item> getMyInventory() {
    return myInventory;
  }

  @Override
  public String toString() {
    return myOrgName;
  }
}
