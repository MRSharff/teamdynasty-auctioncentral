/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.AuctionCalendar;
import model.Auction;
import model.Bid;
//import model.Bidder; //Bidder class is in the users package
import users.Bidder;
import model.Item;
import users.NonProfitOrganization;
import users.ACEmployee;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;



public class BidderTest {
	//Bidder user for test instance
	private Bidder testBidder;
	private Item testItem1;
        private Item testItem2;
	//private Credit testCredit; //dont need this anymore
	private Auction testAuction;
        private NonProfitOrganization npf;
	
	//Setting up the test Bidder instance
	@Before
	public void setUp() throws Exception {
		testBidder = new Bidder("Chris");//AuctionCentral.IBIDDER
		//testItem = new Item("Pokemon Cards", 5); //this is wrong. Item's constructor takes 3 arguments- name, quantity, and min start bid
                //add this
                testItem1 = new Item("Pokemon Cards", 5, 100.0); //5 Pokemon Cards starting at $100 bid
                //testCredit = new Credit(testItem, 250.0, testBidder.getUsername()); //get rid of
		//testAuction = new Auction("Non-Profit Orangization", new ArrayList<Item>(), new AuctionCalendar(), "03:00"); //Auction constructor takes 4 arguments- NonProfitOrganization theOwner, LocalDateTime theStartDate, LocalDateTime theEndDate, List<Item> theItems
                //In order to test Auction, you must create a NonProfitOrganization instance, 2 LocalDateTime instances (start and end), and a list of Items
                npf = new NonProfitOrganization("HSPA", "Humane Society of Pennsylvania"); 
                testAuction = npf.createAuction();
                testAuction.addItem(testItem1);
                testAuction.setMyStartDate(LocalDateTime.now());
                testAuction.setMyEndDate(testAuction.getStartDate().plusMonths(2));
                //OR
                /**
                LocalDateTime start = LocalDateTime.now(); //auction begins right now
                LocalDateTime end = start.plusMonths(3); //and ends in 3 days
                //create list of Items
                List<Item> items = new ArrayList<Item>();
                items.add(testItem1);
                testAuction = new Auction(npf, start, end, items);  
                * **/
	}
	//Testing the Constructor of Bidder Class
	@Test
	public void testBidder() {
		Bidder bidder1 = new Bidder("Chris");
		assertEquals(testBidder.getUsername(), bidder1.getUsername());
	}
        @Test
	public void testchooseItem() {
                //create new AuctionCalendar instance, adding the testAuction which contains testItem1
                AuctionCalendar ac = new AuctionCalendar();
                ac.addAuction(testAuction);
                ac.addUser(npf);
                testBidder.chooseAuction(ac); //testBidder will choose the Auction defined above
                
                Item item = testBidder.chooseItem(); //testBidder chooses Item from Auction 
		assertEquals(testItem1, item);
	}
        
	@Test
	public void testchangeBid() {
                //place first bid using $120 as the bid
                testBidder.placeBid(testItem1);
                Double bidAmount1 = testItem1.getBids().get("Chris");
                
                //place changed bid using $130 as the bid
		testBidder.changeBid(testItem1);
                Double bidAmount2 = testItem1.getBids().get("Chris");
                
                //see if the 2 bid amounts are not equals
                assertFalse(bidAmount1 == bidAmount2);
	}
	@Test
	public void testViewAuctionDetails() {
		System.out.println("Testing the viewAuctionDetails for NPO");
		// Test Empty Auction Calendar In the Auction Central
		System.out.println("Testing Empty Auction Calendar");
		AuctionCalendar theCalendar = new AuctionCalendar();
		assertTrue(theCalendar.getMyAuctions().isEmpty());
		//testAuction.viewCalendar(theCalendar); //dont need this
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
		//testAuction.viewAuctionDetails(theCalendar); //viewAuctionDetails is not a method of Auction
                testAuction.viewDetails();

		System.out.println("Testing the viewAuctionDetails(AuctionCalendar theCalendar) method has Passed the Test Successfully");
	}
        
        public static void main(String[] args) throws Exception {
            BidderTest bt = new BidderTest();
            bt.setUp();
            bt.testBidder();
            bt.testchooseItem();
            bt.testchangeBid();
            bt.testViewAuctionDetails();
        }
}
