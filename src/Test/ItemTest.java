/*
 * To change this license header, choose License Headers in Project Properties.
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
//this is in users package
//import model.Bidder;
import users.Bidder;
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
                //needs starting bid amt
		testItem = new Item("Pokemon Cards", 5, 100.0);
		testBidder = new Bidder("Chris"); 
		//testBid = new Bid(testItem, 250.0, testBidder.getUsername()); //Bid constructor requires amount and Bidder object
		testBid = new Bid(120.0, testBidder);
                //needs starting bid amt
                testItem1 = new Item("PS4", 9, 400.0);
		testItem2 = new Item("iPhone 6", 2, 500.0);
	}
	@Test
        //need return type
	public void testBid() {
	    System.out.println("Testing the Constructor of Bid of Item Class");
	    Bid bid = new Bid(120.0, new Bidder("Chris"));
            //test the amounts
	    assertEquals(testBid.getAmount(), bid.getAmount(), 0.0001);
            //test the users
            assertEquals(testBid.getOwner().getUsername(), bid.getOwner().getUsername());
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
                //change this to 120 since the starting bid for testBid is 120
		assertEquals(120.0, testBid.getAmount(), 0.0001);
	}
        @Test
        public void testsetMyMinStartBid() {
    	        testBid.setAmount(350.0);
		assertEquals(350.0, testBid.getAmount(), 0.0001);
    }
        
        public static void main(String[] args) throws Exception {
            ItemTest it = new ItemTest();
            it.setup();
            it.testBid();
            it.testgetName();
            it.testgetQuantity();
            it.testGetStartingBidAmount();
            it.testsetMyMinStartBid();
        }
	
	
}
