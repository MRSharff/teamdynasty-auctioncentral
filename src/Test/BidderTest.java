package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.AuctionCalendar;
import model.Auction;
import model.Bid;
import model.Bidder;
import model.Item;
import users.NonProfitOrganization;
import users.ACEmployee;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class BidderTest {
	//Bidder user for test instance
	private Bidder testBidder;
	private Item testItem;
	private Credit testCredit;
	private Auction testAuction;
	
	//Setting up the test Bidder instance
	@Before
	public void setUp() throws Exception {
		testBidder = new Bidder("Chris");//AuctionCentral.IBIDDER
		testItem = new Item("Pokemon Cards", 5);
		testCredit = new Credit(testItem, 250.0, testBidder.getUsername());
		testAuction = new Auction("Non-Profit Orangization", new ArrayList<Item>(), new AuctionCalendar(), "03:00");
	}
	//Testing the Constructor of Bidder Class
	@Test
	public void testBidder() {
		Bidder bidder1 = new Bidder("Chris");
		assertEquals(testBidder, bidder1);
	}
    @Test
	public void testchooseItem() {
    	testBidder.addBid(testAuction, testCredit);
		assertTrue(testBidder.viewBids().contains(testCredit));
	}
	@Test
	public void testchangeBid() {
		testBidder.addBid(testAuction, testCredit);
		testBidder.removeBid(testAuction, testCredit);
		assertFalse(testBidder.viewBids().contains(testCredit));
	}
	@Test
	public void testViewAuctionDetails() {
		System.out.println("Testing the viewAuctionDetails for NPO");
		// Test Empty Auction Calendar In the Auction Central
		System.out.println("Testing Empty Auction Calendar");
		AuctionCalendar theCalendar = new AuctionCalendar();
		assertTrue(theCalendar.getMyAuctions().isEmpty());
		testAuction.viewCalendar(theCalendar);
		// The Test would be successful when the method exits with the print statement "The Auction List Is Empty"

		// Test Non Empty Auction Calendar In the Auction Central
		System.out.println("Testing Non Empty Auction Calendar");
		theCalendar = new AuctionCalendar();

		// Setting Up Test Items To be Added in the ItemList
		Item testItem1 = new Item("Item 1", 10, 25.30);
		Item testItem2 = new Item("Item 2", 20, 125.00);
		Item testItem3 = new Item("Item 3", 30, 258.87);
		Item testItem4 = new Item("Item 4", 40, 273.39);
		Item testItem5 = new Item("Item 5", 50, 32.65);
		Item testItem6 = new Item("Item 6", 60, 83.65);

		// Setting Up Test Item List for Auction
		List < Item > testItemList1 = new LinkedList < Item > ();
		testItemList1.add(testItem1);
		testItemList1.add(testItem2);
		testItemList1.add(testItem3);

		List < Item > testItemList2 = new LinkedList < Item > ();
		testItemList2.add(testItem4);
		testItemList2.add(testItem5);

		List < Item > testItemList3 = new LinkedList < Item > ();
		testItemList3.add(testItem6);

		// Setting Up Non Profit Organization for Auction
		NonProfitOrganization testNPO1 = new NonProfitOrganization("NPO 1", "Organization 1");
		NonProfitOrganization testNPO2 = new NonProfitOrganization("NPO 2", "Organization 2");
		NonProfitOrganization testNPO3 = new NonProfitOrganization("NPO 3", "Organization 3");

		// Setting Up Auction 
		Auction testAuction1 = new Auction(testNPO1, LocalDateTime.of(2015, 7, 15, 13, 25), LocalDateTime.of(2015, 7, 15, 14, 10), testItemList1);
		Auction testAuction2 = new Auction(testNPO2, LocalDateTime.of(2015, 7, 18, 12, 10), LocalDateTime.of(2015, 7, 18, 13, 15), testItemList2);
		Auction testAuction3 = new Auction(testNPO3, LocalDateTime.of(2015, 10, 27, 10, 30), LocalDateTime.of(2015, 10, 27, 11, 45), testItemList3);

		// Setting Up Auctions within the Auction Calendar
		System.out.println("The following Auctions have been added to the Auction Calendar");
		theCalendar.addAuction(testAuction1);
		theCalendar.addAuction(testAuction2);
		theCalendar.addAuction(testAuction3);

		// It should Print the Auction Details in the Auction Calendar
		testAuction.viewAuctionDetails(theCalendar);

		System.out.println("Testing the viewAuctionDetails(AuctionCalendar theCalendar) method has Passed the Test Successfully");
	}
}
