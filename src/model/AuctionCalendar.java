package model;

import users.AbstractUser;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mat on 11/17/2015.
 */
public class AuctionCalendar implements Serializable {

  private static final int TOTAL_MONTHS = 12;
  private static final int MAX_AUCTIONS = 25;
  private static final int MAX_DAYS_OUT = 90;
  private static final int MAX_ROLLING_AUCTIONS = 5;
  private static final int MAX_ROLLING_DAYS = 7;
  private static final int MINIMUM_HOURS_BETWEEN = 2;

  private HashMap<Integer, List<Auction>> myAuctions;
  private HashMap<String, AbstractUser> myUsers;

  public AuctionCalendar() {
    myAuctions = new HashMap<Integer, List<Auction>>(TOTAL_MONTHS);
    myUsers = new HashMap<String, AbstractUser>();
  }

  public HashMap<Integer, List<Auction>> getMyAuctions() {
    return myAuctions;
  }

  public void addAuction(final Auction theAuction) {

    if (!myAuctions.containsKey(theAuction.getStartDate().getMonthValue())) {
      ArrayList<Auction> newList = new ArrayList<Auction>();

      myAuctions.put(theAuction.getStartDate().getMonthValue(), newList);
      //debug statement
      //System.out.println("list created.");
    }

    //user a flag, that way we can tell them everything that they need to fix
    //in order to schedule their auction. If nothing is wrong, the auction is scheduled.
    boolean bRPass = true;
    if (hasMaxAuctions()) {
      System.out.println("Maximum auctions reached, no more auctions can be scheduled.");
      bRPass = false;
    }
    if (maxAuctionsInOneDay(theAuction)) {
        System.out.println("Too many auctions on that day, please reschedule.");
      bRPass = false;
    }
    if (!isWithinMaxDaysOut(theAuction)) {
      System.out.println("You must schedule your auction at a date within " + MAX_DAYS_OUT + " days.");
      System.out.println("Please change your auction date to no later than " + LocalDate.now().plusDays(MAX_DAYS_OUT));
      bRPass = false;
    }

    if (bRPass){
      myAuctions.get(theAuction.getStartDate().getMonthValue()).add(theAuction);
      System.out.println(theAuction.getMyName() + " scheduled for "
              + theAuction.getStartDate().getHour() + ":" + theAuction.getStartDate().getMinute());
    }
  }

  public HashMap<String, AbstractUser> getMyUsers() {
    return myUsers;
  }

  /**
   * Adds a user to myUsers
   * @param theUser
   * @return true if a user was successfully added to myUsers, false otherwise
   */
  public boolean addUser(final AbstractUser theUser) {
    if (myUsers.keySet().contains(theUser.getUsername().toLowerCase())) {
      System.out.println("Username already exists.");
    } else {
      myUsers.put(theUser.getUsername().toLowerCase(), theUser);
      return true;
    }
    return false;
  }

  public void removeAuction(final Auction theAuction) {
    if (LocalDateTime.now().isAfter(theAuction.getStartDate())
            && LocalDateTime.now().isBefore(theAuction.getEndDate())) {
      System.out.println("The auction is currently in progress.");
    } else {
      myAuctions.get(theAuction.getStartDate().getMonthValue()).remove(theAuction);
      System.out.println("Successfully removed auction.");
    }
  }

  /**
   * BR 1
   * @return
   */
  public boolean hasMaxAuctions() {
    int auctionCount = 0;
    for (Integer key : myAuctions.keySet()) {
      auctionCount += myAuctions.get(key).size();
    }
    return (auctionCount >= MAX_AUCTIONS);
  }

  /**
   * BR 2
   * @param theAuction
   * @return
   */
  public boolean isWithinMaxDaysOut(final Auction theAuction) {
    //returns the auctions date minus the max days out (90 in tcss 360 case) and checks if it's after
    //the current date
    //returns the opposite of this comparison (auctionDate - max days > now)
    return (!theAuction.getStartDate().minusDays(MAX_DAYS_OUT).isAfter(LocalDateTime.now()));
  }

  /**
   * BR 3.
   * @param otherAuction
   * @return
   */
  public boolean maxInRollingPeriod(final Auction otherAuction) {
    int count = 0;
    for (Integer key : myAuctions.keySet()) {
      for (Auction auction : myAuctions.get(key)) {
        if (auction.getStartDate().minusDays(MAX_ROLLING_DAYS).isBefore(otherAuction.getEndDate()) ||
                auction.getEndDate().plusDays(MAX_ROLLING_DAYS).isAfter(otherAuction.getStartDate())) {
          count++;
        }
      }
    }

    return (count >= MAX_ROLLING_AUCTIONS);
  }

  /**
   * BR 4.
   *
   * @param otherAuction
   * @return
   */
  public boolean maxAuctionsInOneDay(final Auction otherAuction) {
    int count = 0;
    for (Auction auction : myAuctions.get(otherAuction.getStartDate().getMonthValue())) {
      if (auction.getStartDate().getDayOfMonth() == otherAuction.getStartDate().getDayOfMonth()) {
        count++;
        if (auctionStartTooSoon(auction, otherAuction)) {
          return true;
        }
      }
    }
    return (count > 1);
  }

  /**BR 4 part 2
   *
   * Rule: firstAuction is an auction that is before secondAuction
   * @param firstAuction
   * @param secondAuction
   * @return true if secondAuction start time is within a set amount of hours after firstAuction
   */
  public boolean auctionStartTooSoon(final Auction firstAuction, final Auction secondAuction) {
    return (ChronoUnit.HOURS.between(firstAuction.getEndDate(), secondAuction.getStartDate()) < MINIMUM_HOURS_BETWEEN ||
            ChronoUnit.HOURS.between(secondAuction.getEndDate(), firstAuction.getStartDate()) < MINIMUM_HOURS_BETWEEN);
  }

  private void removeUser(final String theUsername) {
//    if (myUsers.contains())
  }
}
