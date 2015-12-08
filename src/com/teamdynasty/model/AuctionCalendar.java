package com.teamdynasty.model;

import java.io.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Auction Calendar Class depicts the month wise auction list
 * present in the auction central system.One can perform the following
 * operations in this class.
 * <ul>
 * <li>Add Auction into Auction Calendar.
 * <li>Remove Auction from Auction Calendar.
 * <li>Add the Non Profit Organization User that has created the Auction.
 * </ul>
 * <p>
 * All of the business rules associated with the auctions are
 * enlisted in this class.
 *
 * @author Shoba Gopi
 * @version 2.1
 * @author Mat Sharff
 * @see User
 */
public class AuctionCalendar implements Serializable {

  private static final int TOTAL_MONTHS = 12;
  private static final int MAX_AUCTIONS = 25;
  private static final int MAX_DAYS_OUT = 90;
  private static final int MAX_ROLLING_AUCTIONS = 5;
  private static final int MAX_ROLLING_DAYS = 7;
  private static final int MINIMUM_HOURS_BETWEEN = 2;
  private static final int MAX_AUCTIONS_ONE_DAY = 2;
  private static final long HOUR_OFFSET = 1;

  private HashMap<Integer, List<Auction>> myAuctions;
  private HashMap<String, User> myUsers;

  /**
   * The Constructor of Auction Calendar Class.
   * <p>
   * Initializes the auction list and user list.
   */
  public AuctionCalendar() {
    myAuctions = new HashMap<>(TOTAL_MONTHS);
    myUsers = new HashMap<>();
  }

//  /**
//   * Returns a auction list with month numerical as its key set.
//   *
//   * @return the auction list hash map with months as its keys.
//   * @see Auction
//   */
//  public HashMap<Integer, List<Auction>> getMyAuctions() {
//    return myAuctions;
//  }

  /**
   * Returns a user list with username as its key set.
   *
   * @return the user list hash map with username as its keys.
   * @see Auction
   */
  public HashMap<String, User> getMyUsers() {
    return myUsers;
  }

  public List<Auction> getMonthList(int theMonth) {
    if (myAuctions.containsKey(theMonth)) {
      return myAuctions.get(theMonth);
    }
    return null;
  }

  public List<Auction> getFutureMonthsAuctions(int theMonth) {
    List<Auction> theAuctions = new ArrayList<>();

    if (myAuctions.containsKey(theMonth)) {
      for (Auction auction : getMonthList(theMonth)) {
        if (auction.getStartDate().isAfter(LocalDateTime.now().minusHours(HOUR_OFFSET))) {
          theAuctions.add(auction);
        }
      }
      return theAuctions;
    }
    return null;
  }

  public List<Auction> getMonthsAuctions(int theMonth) {
    List<Auction> theAuctions = new ArrayList<>();

    if (myAuctions.containsKey(theMonth)) {
      return theAuctions;
    }
    return null;
  }

  public boolean hasMonthList(int theMonth) {
    return myAuctions.containsKey(theMonth);
  }

  /**
   * Adds an auction to the auction calendar.
   * <p>
   * Checks for duplicate auctions as well as maximum auctions in the month
   * and in the day before adding it to the auction calendar.
   *
   * @param theAuction the details of the auction to be added to the auction calendar.
   * @return returns true when the auction was added successfully.
   * @see Auction
   */
  public boolean addAuction(final Auction theAuction) {

    if (!myAuctions.containsKey(theAuction.getStartDate().getMonthValue())) {
      ArrayList<Auction> newList = new ArrayList<>();

      myAuctions.put(theAuction.getStartDate().getMonthValue(), newList);
    }

    boolean bRPass = true;
    if (hasMaxAuctions()) {
      //System.out.println("Maximum auctions reached, no more auctions can be scheduled.");
      bRPass = false;
    }
    if (maxInRollingPeriod(theAuction)) {
      bRPass = false;
    }
    if (maxAuctionsInOneDay(theAuction)) {
      //System.out.println("Too many auctions on that day, please reschedule.");
      bRPass = false;
    }
    if (!isWithinMaxDaysOut(theAuction)) {
      //System.out.println("You must schedule your auction at a date within " + MAX_DAYS_OUT + " days.");
      //System.out.println("Please change your auction date to no later than " + LocalDate.now().plusDays(MAX_DAYS_OUT));
      bRPass = false;
    }

    if (bRPass) {
      myAuctions.get(theAuction.getStartDate().getMonthValue()).add(theAuction);
      //.out.println(theAuction.getMyName() + " scheduled for " + theAuction.getStartDate().getHour() + ":" + theAuction.getStartDate().getMinute());
    }
    return bRPass;
  }

  /**
   * Removes an auction from the auction calendar.
   * <p>
   * Checks for empty auction list in the particular month
   * and prevents removing auctions that are currently in progress.
   *
   * @param theAuction the details of the auction to be added to the auction calendar.
   * @see Auction
   */
  public void removeAuction(final Auction theAuction) {

    if (LocalDateTime.now().isAfter(theAuction.getStartDate()) && LocalDateTime.now().isBefore(theAuction.getEndDate())) {
      //System.out.println("The auction is currently in progress.");
    } else if (!myAuctions.get(theAuction.getStartDate().getMonthValue()).isEmpty()) {

      myAuctions.get(theAuction.getStartDate().getMonthValue()).remove(theAuction);
      //System.out.println("Successfully removed auction.");
    } else {
      //System.out.println("No Such Auction Present in the Auction Calendar.");
    }
  }

  /**
   * Add a Non Profit Organization user to the Auction Calendar.
   * <p>
   * Checks if the user already exits in the userlist
   * and prevents it from creating more than one auction in an year.
   *
   * @param theUser the details of the Non Profit Organization user which has created the auction
   *                and will be added to the auction calendar.
   * @return         <code>true</code> if the user is successfully added to the user list;
   * <code>false</code> otherwise.
   * @see User
   */
  public boolean addUser(final User theUser) {

    if (myUsers.keySet().contains(theUser.getUsername().toLowerCase())) {
      System.out.println("Username already exists.");
    } else {
      myUsers.put(theUser.getUsername().toLowerCase(), theUser);
      return true;
    }
    return false;
  }

  /**
   * Checks if the calendar has an auction.
   * @param theAuction the auction that the calendar checks if it has.
   * @return true if the calendar has theAuction
   */
  public boolean hasAuction(final Auction theAuction) {
    if (myAuctions.containsKey(theAuction.getStartDate().getMonthValue())) {
      return myAuctions.get(theAuction.getStartDate().getMonthValue()).contains(theAuction);
    }
    return false;
  }

  /**
   * Business Rule 1 : No more than 25 auctions may be scheduled into the future.
   * <p>
   * Checks the maximum limit on the future auctions.
   *
   * @return <code>true</code> if the maximum future auction limit has been breached;
   * <code>false</code> otherwise.
   */
  public boolean hasMaxAuctions() {
    int auctionCount = 0;
    for (Integer key : myAuctions.keySet()) {
      auctionCount += myAuctions.get(key).size();
    }
    return (auctionCount >= MAX_AUCTIONS);
  }

  /**
   * Business Rule 2 : An auction may not be scheduled more than 90 days from the current date.
   * <p>
   * Checks the auction cannot be scheduled more than 90 days in the future from the current date.
   *
   * @param theAuction the details of the auction to be added to the auction calendar.
   * @return <code>true</code> if the auction has been scheduled more than 90 days in advance;
   * <code>false</code> otherwise.
   * @see Auction
   */
  public boolean isWithinMaxDaysOut(final Auction theAuction) {
    /*
		returns the auctions date minus the max days out (90 in tcss 360 case) and checks if it's after
		the current date
		returns the opposite of this comparison (auctionDate - max days > now)
		*/
    return (!theAuction.getStartDate().minusDays(MAX_DAYS_OUT).isAfter(LocalDateTime.now()));
  }

  /**
   * Business Rule 3 : No more than 5 auctions may be scheduled for any rolling 7 day period.
   * <p>
   * Checks if more than 5 auctions were scheduled in rolling 7 day period.
   *
   * @param otherAuction the details of the other auctions that have been added to the auction calendar.
   * @return <code>true</code> if the auction maximum limit in the rolling 7 day period
   * has been breached; <code>false</code> otherwise.
   * @see Auction
   */
  public boolean maxInRollingPeriod(final Auction otherAuction) {
    int count = 0;
    for (Integer key : myAuctions.keySet()) {
      for (Auction auction : myAuctions.get(key)) {
//        if (auction.getStartDate().plusDays(MAX_ROLLING_DAYS).isAfter(otherAuction.getEndDate())
//                || auction.getEndDate().plusDays(MAX_ROLLING_DAYS).isAfter(otherAuction.getStartDate())
//                || auction.getStartDate().minusDays(MAX_ROLLING_DAYS).isBefore(otherAuction.getStartDate())
//                || auction.getEndDate().minusDays(MAX_ROLLING_DAYS).isBefore(otherAuction.getEndDate())) {
        if ((auction.getEndDate().plusDays(MAX_ROLLING_DAYS).isAfter(otherAuction.getStartDate())
                && auction.getStartDate().isBefore(otherAuction.getStartDate()))
        || (auction.getStartDate().minusDays(MAX_ROLLING_DAYS).isBefore(otherAuction.getEndDate())
                && auction.getStartDate().isAfter(otherAuction.getStartDate()))) {
          count++;
        }
      }
    }
    return (count >= MAX_ROLLING_AUCTIONS);
  }

  /**
   * Business Rule 4 Part A : No more than 2 auctions can be scheduled on the same day.
   * <p>
   * Checks if more than 2 auctions were scheduled on the same day.
   *
   * @param otherAuction the details of the other auctions that have been added to the auction calendar.
   * @return <code>true</code> if the more than 2 auctions are scheduled in the same day;
   * <code>false</code> otherwise.
   * @see Auction
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
    return (count >= MAX_AUCTIONS_ONE_DAY);
  }

  /**
   * Business Rule 4 Part B : The start time of the second auction can be no earlier than 2 hours
   * after the end time of the first auction.
   * <p>
   * Checks if the difference in start and end times of second auction and first auction respectively
   * is 2 hours or more than 2 hours.
   *
   * @param firstAuction  the details of the first auction scheduled on the day.
   * @param secondAuction the details of the second auction scheduled on the day.
   * @return <code>true</code> if second auction starts 2 hours after the end of first auction;
   * <code>false</code> otherwise.
   * @see Auction
   */
  public boolean auctionStartTooSoon(final Auction firstAuction, final Auction secondAuction) {
    boolean flag = true;
    if (secondAuction.getStartDate().isAfter(firstAuction.getStartDate())) {
      flag = ChronoUnit.HOURS.between(firstAuction.getEndDate(), secondAuction.getStartDate()) < MINIMUM_HOURS_BETWEEN;
    } else {
      flag = ChronoUnit.HOURS.between(secondAuction.getEndDate(), firstAuction.getStartDate()) < MINIMUM_HOURS_BETWEEN;
    }
  return flag;
  }


  public List<NonProfit> getNPOList() {
    List<NonProfit> NPOList = new ArrayList<>();

    //int counter = 1;
    for (User user : myUsers.values()) {
      if (user.getUserType() == User.INPO) {
        NPOList.add((NonProfit) user);
        //System.out.println("[" + counter + "] " + user.getUsername());
        //counter++;
      }
    }

    return NPOList;
  }

//  public NonProfit chooseNPO(int theIndex) {
//    return getNPOList().get(theIndex);
//  }

//  public Auction getNPOAuction(NonProfit theNPO) {
//    return theNPO.getMyAuction();
//  }
//  private static NonProfit listNPO(HashMap<String, User> theUserList) {
//    List<NonProfit> NPOList = new ArrayList<NonProfit>();
//
//
//    System.out.println("Choose a Non-Profit Organization");
//    //fill list of NPOs to show and print them
//    int counter = 1;
//    for (User user : theUserList.values()) {
//      if (user.getUserType() == AuctionCentral.INPO) {
//        NPOList.add((NonProfit) user);
//        System.out.println("[" + counter + "] " + user.getUsername());
//        counter++;
//      }
//    }
//
//    int option = userInput.nextInt();
//    userInput.nextLine();
//    System.out.println("You chose " + NPOList.get(option - 1).getMyOrgName() + "'s Auction, "
//            + NPOList.get(option - 1).getMyAuction().getMyName());
//    return NPOList.get(option - 1);
//  }

  public void save(final String thePath) throws IOException {
    File file = new File(thePath);

    //if there is no file by found in DATABASE_PATH, create one
    if (!file.exists()) {
      file.getParentFile().mkdir();
    }

    //write serialized file
    try {
      FileOutputStream fileOut = new FileOutputStream(thePath);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(this);
      out.close();
      fileOut.close();
    } catch (IOException theException) {
      theException.printStackTrace();
    }
  }

  public static AuctionCalendar load(final String thePath) {
    AuctionCalendar temp = null;

    File file = new File(thePath);
    if (!file.exists()) {
      return null;
    }
    try {
      FileInputStream fileIn = new FileInputStream(thePath);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      temp = (AuctionCalendar) in.readObject();
      in.close();
      fileIn.close();
    } catch (IOException | ClassNotFoundException ioException) {
      //ioException.printStackTrace();
    }
//    this.myUsers = temp.myUsers;
//    this.myAuctions = temp.myAuctions;
    return temp;
  }
}
