package model;

import users.NonProfitOrganization;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


public class Auction implements Comparable<Auction> {

  private static final String[] OPTIONS = {"[1] Change date", "[2] Add item", "[3] Remove item"};
  private static final int HOUR_OFFSET = 12;

  //private String myName;
  private NonProfitOrganization myOwner;
  //private Date myStartDate;
  //private Date myEndDate;
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

  public void setMyStartDate(LocalDateTime theDate) {
    myStartDate = theDate;
    //TODO set myEndDate according to auction length + start date
    //set myEndDate accordingly
  }

  public void newMyStartDate() {
    Scanner detailScanner = new Scanner(System.in);
    System.out.print("Enter auction month (numerical): ");
    int aMonth = detailScanner.nextInt();
    detailScanner.nextLine();
    System.out.print("Enter auction day: ");
    int aDay = detailScanner.nextInt();
    detailScanner.nextLine();
    System.out.print("Enter auction year: ");
    int aYear = detailScanner.nextInt();
    detailScanner.nextLine();

    //get start time
    System.out.print("Enter auction start time: ");
    String time = detailScanner.nextLine();

    String theTime = time.replace(':', ' ');
    detailScanner = new Scanner(theTime);
    int hour = detailScanner.nextInt();
    int minutes = detailScanner.nextInt();

    //System.out.println(hour);
    //System.out.println(minutes);
//    detailScanner.nextLine();

    //check if user entered 24 hour time
    detailScanner = new Scanner(System.in);
    if (hour < 13) {
      System.out.print("AM or PM: ");
      String amPM = detailScanner.nextLine();
      //if not, ask for am or pm
      if (amPM.toLowerCase().equals("pm")) {
        hour = (hour % HOUR_OFFSET) + HOUR_OFFSET;
      }
    }

    LocalDateTime startDate = LocalDateTime.of(aYear, aMonth, aDay, hour, minutes);

    setMyStartDate(startDate);
  }

  public void newMyEndDate() {
    Scanner detailScanner = new Scanner(System.in);
    LocalDateTime endDate;
    do {
      System.out.print("Enter auction end time (hh:mm): ");
      String time = detailScanner.nextLine();

      String theTime = time.replace(':', ' ');
      detailScanner = new Scanner(theTime);
      int hour = detailScanner.nextInt();
      int minutes = detailScanner.nextInt();

      detailScanner = new Scanner(System.in);
      if (hour < 13) {
        System.out.print("AM or PM: ");
        String amPM = detailScanner.nextLine();
        //if not, ask for am or pm
        if (amPM.toLowerCase().equals("pm")) {
          hour = (hour % HOUR_OFFSET) + HOUR_OFFSET;
        }
      }
      endDate = LocalDateTime.of(myStartDate.getYear(),
              myStartDate.getMonthValue(),
              myStartDate.getDayOfMonth(),
              hour,
              minutes);

      if (endDate.isBefore(myStartDate)) {
        //TODO code to change back into 12 hour should go here
        System.out.println("End time must be after start time ("
                + myStartDate.getHour() + ":" + myStartDate.getMinute());
      }

    } while (endDate.isBefore(myStartDate));
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
    returnString.append("Auction Details for: " + getMyName() + "\n");
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

  private String firstCap(final String theString) {
    return theString.substring(0, 1) + theString.substring(1).toLowerCase();
  }

  @Override
  public String toString() {
    return getMyName();
  }
}