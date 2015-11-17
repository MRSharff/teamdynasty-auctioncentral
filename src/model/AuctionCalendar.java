package model;

import users.AbstractUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Mat on 11/17/2015.
 */
public class AuctionCalendar {

  private static int TOTAL_MONTHS = 12;
  private static int MAX_AUCTIONS = 25;
  private static int MAX_DAYS_OUT = 90;
  private static int MAX_ROLLING_AUCTIONS = 5;
  private static int MAX_ROLLING_DAYS = 7;
  private static int MINIMUM_HOURS_BETWEEN = 2;

  private HashMap<Integer, List<Auction>> myAuctions;
  private TreeSet<AbstractUser> myUserList;

  public AuctionCalendar() {
    myAuctions = new HashMap<Integer, List<Auction>>(TOTAL_MONTHS);
    myUserList = new TreeSet<AbstractUser>();
  }

  public HashMap<Integer, List<Auction>> getMyAuctions() {
    return myAuctions;
  }

  public void addAuction(final Auction theAuction) {
    myAuctions.get(theAuction.getStartDate().getMonthValue()).add(theAuction);
  }

  public TreeSet<AbstractUser> getMyUserList() {
    return myUserList;
  }

  public void addUser(final AbstractUser theUser) {
    myUserList.add(theUser);
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
   * @param theAuctionDate
   * @return
   */
  public boolean isWithinMaxDaysOut(final LocalDateTime theAuctionDate) {
    //returns the auctions date minus the max days out (90 in tcss 360 case) and checks if it's after
    //the current date
    //returns the opposite of this comparison (auctionDate - max days > now)
    return (!theAuctionDate.minusDays(MAX_DAYS_OUT).isAfter(LocalDateTime.now()));
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
}
