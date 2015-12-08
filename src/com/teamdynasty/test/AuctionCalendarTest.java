package com.teamdynasty.test;

import com.teamdynasty.model.Auction;
import com.teamdynasty.model.AuctionCalendar;
import com.teamdynasty.model.NonProfit;
import com.teamdynasty.model.User;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * JUnit test class for Auction Calendar.s
 * Created by Mat on 12/7/2015.
 */
public class AuctionCalendarTest {

  protected AuctionCalendar newCalendar;
  protected AuctionCalendar maxAuctionsCalendar;
  protected AuctionCalendar noAuctionsCalendar;
  protected AuctionCalendar maxInOneDayCalendar;
  protected AuctionCalendar maxRollingPeriodCalendar;

  @Before
  public void setUp() throws Exception {
    newCalendar = new AuctionCalendar();


    //Setup maxAuctionsCalendar
    maxAuctionsCalendar = new AuctionCalendar();
    for (int i = 0; i < 75; ) {
      String npoName = "Test" + i;
      String npoOrg = "Test Org" + i;
      maxAuctionsCalendar.addAuction(new Auction(new NonProfit(npoName, npoOrg), LocalDateTime.now().plusDays(i + 1)));
      i = i + 3;
    }

    noAuctionsCalendar = new AuctionCalendar();

    maxRollingPeriodCalendar = new AuctionCalendar();
    maxRollingPeriodCalendar.addAuction(new Auction(new NonProfit("NPOTest1", "NPOTestOrg1"), LocalDateTime.now().plusDays(1).plusHours(5)));
    maxRollingPeriodCalendar.addAuction(new Auction(new NonProfit("NPOTest1", "NPOTestOrg1"), LocalDateTime.now().plusDays(1).plusHours(10)));
    maxRollingPeriodCalendar.addAuction(new Auction(new NonProfit("NPOTest1", "NPOTestOrg1"), LocalDateTime.now().plusDays(2).plusHours(5)));
    maxRollingPeriodCalendar.addAuction(new Auction(new NonProfit("NPOTest1", "NPOTestOrg1"), LocalDateTime.now().plusDays(2).plusHours(10)));
    maxRollingPeriodCalendar.addAuction(new Auction(new NonProfit("NPOTest1", "NPOTestOrg1"), LocalDateTime.now().plusDays(3).plusHours(5)));
    maxRollingPeriodCalendar.addAuction(new Auction(new NonProfit("NPOTest1", "NPOTestOrg1"), LocalDateTime.now().plusDays(3).plusHours(10)));
    maxRollingPeriodCalendar.addAuction(new Auction(new NonProfit("NPOTest1", "NPOTestOrg1"), LocalDateTime.now().plusDays(4)));

    maxInOneDayCalendar = new AuctionCalendar();
    maxInOneDayCalendar.addAuction(new Auction(new NonProfit("NPOTest1", "NPOTestOrg1"), LocalDateTime.of(2015, 12, 12, 12, 0)));
    maxInOneDayCalendar.addAuction(new Auction(new NonProfit("NPOTest1", "NPOTestOrg1"), LocalDateTime.of(2015, 12, 12, 15, 0)));

  }

  @Test
  public void testAddAuction() throws Exception {
    Auction noContainsAuction = new Auction(new NonProfit("NPO2", "NPO2Test"), LocalDateTime.now().plusDays(1));
    Auction newAuction = new Auction(new NonProfit("NPO", "NPOTest"), LocalDateTime.now().plusDays(1));
    newCalendar.addAuction(newAuction);
    assertTrue(newCalendar.hasAuction(newAuction));
    assertFalse(newCalendar.hasAuction(noContainsAuction));
  }


  @Test
  public void testAddAuctionWhenMaxAuctions() throws Exception {
    Auction notAdded = new Auction(new NonProfit("TestNPONotAdded", "Not Added NPO"),
            LocalDateTime.now().plusDays(27));
    maxAuctionsCalendar.addAuction(notAdded);
    assertFalse(maxAuctionsCalendar.hasAuction(notAdded));
  }

  @Test
  public void testRemoveAuction() throws Exception {
    Auction auctionToRemove = new Auction(new NonProfit("RemoveNPO", "Remove Auction NPO"), LocalDateTime.now().plusDays(1));

    newCalendar.addAuction(auctionToRemove);
    newCalendar.removeAuction(auctionToRemove);
    assertFalse(newCalendar.hasAuction(auctionToRemove));
  }

  @Test
  public void testAddUser() throws Exception {
    User testUser = new User("Test User", 1);
    newCalendar.addUser(testUser);
    assertTrue(newCalendar.getMyUsers().containsValue(testUser));
  }

  @Test
  public void testHasMaxAuctions() throws Exception {
    assertTrue(maxAuctionsCalendar.hasMaxAuctions());
  }

  @Test
  public void testHasMaxAuctionsOnNoAuctionCalendar() throws Exception {
    assertFalse(noAuctionsCalendar.hasMaxAuctions());
  }

  @Test
  public void testIsWithinMaxDaysOut() throws Exception {
    Auction newAuction = new Auction(new NonProfit("Test NPO", "Test Org"), LocalDateTime.now().plusDays(95));

    assertFalse(newCalendar.isWithinMaxDaysOut(newAuction));
  }

  @Test
  public void testMaxInRollingPeriod() throws Exception {
    assertTrue(maxRollingPeriodCalendar.maxInRollingPeriod(new Auction(new NonProfit("LATENPO", "LATE NPO"), LocalDateTime.now().plusDays(5))));
  }

  @Test
  public void testMaxAuctionsInOneDay() throws Exception {
    Auction newAuction = new Auction(new NonProfit("TestNPO", "Test Org"), LocalDateTime.of(2015, 12, 12, 19, 0));
    assertTrue(maxInOneDayCalendar.maxAuctionsInOneDay(newAuction));
  }

  @Test
  public void testAuctionStartTooSoon() throws Exception {
    LocalDateTime nowDate = LocalDateTime.now();
    Auction a1 = new Auction(new NonProfit("TestNPO", "Test Org"), LocalDateTime.of(nowDate.getYear(), nowDate.getMonthValue(), nowDate.getDayOfMonth() + 1, 12, 0));
    Auction a2 = new Auction(new NonProfit("TestNPO2", "Test Org2"), LocalDateTime.of(nowDate.getYear(), nowDate.getMonthValue(), nowDate.getDayOfMonth() + 1, 13, 0));

    assertTrue(newCalendar.auctionStartTooSoon(a1, a2));
  }
}