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

import java.util.ArrayList;
/*
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.LocalDateTime;
*/
public class ItemTest {

	// Test Instance of Item Class
	private Item testItem;
	private Bid testBid;
	private Bidder testBidder;
	private Item testItem1;
	private Item testItem2;
	@Before
	public void setup() throws Exception {
		testItem = new Item("Pokemon Cards", 5);
		testBidder = new Bidder("Chris"); 
		testBid = new Bid(testItem, 250.0, testBidder.getUsername());
		testItem1 = new Item("PS4", 9);
		testItem2 = new Item("iPhone 6", 2);
	}
	@Test
	public testBid() {
	    System.out.println("Testing the Constructor of Bid of Item Class");
	    Bid bid = new Bid(new Item("Pokemon Cards", 5, 250.0, "Chris");
	    assertEquals(testBid, bid);
	    System.out.println("Constructor of Bid  in Item Class Passed the test Successfully");
	}

	@Test
	public void testgetName() {
		assertEquals(testItem1.getName(), "PS4");
		assertEquals(testItem2.getName(), "iPhone 6");
	}
	@Test
	public void testgetQuantity() {
		assertEquals(testItem1.getQuantity(), 9);
		assertEquals(testItem2.getQuantity(), 2);	
	}
	@Test
	public void testsetQuantity() {
		testItem2.setQuantity(3);
		assertEquals(testItem2.getQuantity(), 3);
	}
	@Test
	public void testGetStartingBidAmount() {
		assertEquals(250.0, testBid.getBidAmount(), 0.0001);
	}
        @Test
        public void testsetMyMinStartBid() {
    	        testBid.setBidAmount(350.0);
		assertEquals(350.0, testBid.getBidAmount(), 0.0001);
    }
	
	
}
