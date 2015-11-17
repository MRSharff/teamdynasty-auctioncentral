package Test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import model.AuctionCentral;
import model.Auction;
import model.Item;
import users.NonProfitOrganization;
import users.ACEmployee;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Date;

/**
 * ACEmployeeTest is the JUNIT test class for the ACEmployee Class
 **/

public class ACEmployeeTest {

	// Test Instance of ACEmployee
	private ACEmployee testACEmployee;

	// Private Members of ACEmployee Class
	private static final String[] OPTIONS = { "[1] View Monthly Calendar",
			"[2] View details of auction" };

	// Private Members of the User Class which were Inherited by ACEmployee
	private int myType = 1;
	private String myUsername;
	private int ID;

	// Setting Up the test ACEmployee Instance
	@Before
	public void setUp() {
		myUsername = "Shoba";
		testACEmployee = new ACEmployee(myUsername);
	}

	// Testing the Constructor of ACEmployee Class
	@Test
	public void testACEmployee() {
		System.out.println("Testing the Constructor of ACEmployee");
		assertEquals(testACEmployee.getUsername(), myUsername);
		assertEquals(testACEmployee.getUserType(), myType);
		System.out
				.println("Constructor of ACEmployee Passed the test Successfully");
	}

	// Testing the View Calendar method of ACEmployee Class
	@Test
	public void testViewCalendar() {

		System.out
				.println("Testing the viewCalendar(final HashMap<Integer, List<Auction>> theAuctionList) method");
		// Test Empty Auction List In the Auction Central
		HashMap<Integer, List<Auction>> theAuctionList = new HashMap<Integer, List<Auction>>();
		assertTrue(theAuctionList.isEmpty());
		testACEmployee.viewCalendar(theAuctionList);
		// The Test would be successful when the method exits with the print
		// statement "The Auction List Is Empty"

		// Test Auction List within a Auction List
		theAuctionList = new HashMap<Integer, List<Auction>>();

		// Setting Up Test Items To be Added in the ItemList
		Item testItem1 = new Item("Item 1", 10, 25.30);
		Item testItem2 = new Item("Item 2", 20, 125.00);
		Item testItem3 = new Item("Item 3", 30, 258.87);
		Item testItem4 = new Item("Item 4", 40, 273.39);
		Item testItem5 = new Item("Item 5", 50, 32.65);
		Item testItem6 = new Item("Item 6", 60, 83.65);

		// Setting Up Test Item List for Auction
		List<Item> testItemList1 = new LinkedList<Item>();
		testItemList1.add(testItem1);
		testItemList1.add(testItem2);
		testItemList1.add(testItem3);

		List<Item> testItemList2 = new LinkedList<Item>();
		testItemList2.add(testItem4);
		testItemList2.add(testItem5);

		List<Item> testItemList3 = new LinkedList<Item>();
		testItemList3.add(testItem6);

		// Setting Up Non Profit Organization for Auction
		NonProfitOrganization testNPO1 = new NonProfitOrganization("NPO 1",
				"Organization 1");
		NonProfitOrganization testNPO2 = new NonProfitOrganization("NPO 2",
				"Organization 2");
		NonProfitOrganization testNPO3 = new NonProfitOrganization("NPO 3",
				"Organization 3");

		Auction testAuction1 = new Auction(testNPO1, new Date("7/15/2015"),
				new Date("7/16/2015"), testItemList1);
		Auction testAuction2 = new Auction(testNPO2, new Date("7/18/2015"),
				new Date("7/19/2015"), testItemList2);
		Auction testAuction3 = new Auction(testNPO3, new Date("10/27/2015"),
				new Date("10/28/2015"), testItemList3);

		// Setting Up Auctions within the Auction List
		List<Auction> testAuctionList1 = new LinkedList<Auction>();
		testAuctionList1.add(testAuction1);
		testAuctionList1.add(testAuction2);

		List<Auction> testAuctionList2 = new LinkedList<Auction>();
		testAuctionList2.add(testAuction3);

		// Setting Up the Entire Auction List Inside the Hash Map With the
		// Months
		theAuctionList.put(7, testAuctionList1);
		theAuctionList.put(10, testAuctionList2);

		// It should Print the Auction Details in the Auction List
		testACEmployee.viewCalendar(theAuctionList);

		System.out
				.println("The viewCalendar(final HashMap<Integer, List<Auction>> theAuctionList) method has Passed the Test Successfully");

	}

	// Testing the View Auction Details function of ACEmployee Class
	@Test
	public void testViewAuctionDetails() {

		System.out
				.println("Testing the viewAuctionDetails(HashMap<Integer, List<Auction>> theAuctionList) method");
		// Test Empty Auction List In the Auction Central
		HashMap<Integer, List<Auction>> theAuctionList = new HashMap<Integer, List<Auction>>();
		assertTrue(theAuctionList.isEmpty());
		testACEmployee.viewAuctionDetails(theAuctionList);

		// Test Auction List within a Auction List
		theAuctionList = new HashMap<Integer, List<Auction>>();

		// Setting Up Test Items To be Added in the ItemList
		Item testItem1 = new Item("Item 1", 10, 25.30);
		Item testItem2 = new Item("Item 2", 20, 125.00);
		Item testItem3 = new Item("Item 3", 30, 258.87);
		Item testItem4 = new Item("Item 4", 40, 273.39);
		Item testItem5 = new Item("Item 5", 50, 32.65);
		Item testItem6 = new Item("Item 6", 60, 83.65);

		// Setting Up Test Item List for Auction
		List<Item> testItemList1 = new LinkedList<Item>();
		testItemList1.add(testItem1);
		testItemList1.add(testItem2);
		testItemList1.add(testItem3);

		List<Item> testItemList2 = new LinkedList<Item>();
		testItemList2.add(testItem4);
		testItemList2.add(testItem5);

		List<Item> testItemList3 = new LinkedList<Item>();
		testItemList3.add(testItem6);

		// Setting Up Non Profit Organization for Auction
		NonProfitOrganization testNPO1 = new NonProfitOrganization("NPO 1",
				"Organization 1");
		NonProfitOrganization testNPO2 = new NonProfitOrganization("NPO 2",
				"Organization 2");
		NonProfitOrganization testNPO3 = new NonProfitOrganization("NPO 3",
				"Organization 3");

		Auction testAuction1 = new Auction(testNPO1, new Date("7/15/2015"),
				new Date("7/16/2015"), testItemList1);
		Auction testAuction2 = new Auction(testNPO2, new Date("7/18/2015"),
				new Date("7/19/2015"), testItemList2);
		Auction testAuction3 = new Auction(testNPO3, new Date("10/27/2015"),
				new Date("10/28/2015"), testItemList3);

		// Setting Up Auctions within the Auction List
		List<Auction> testAuctionList1 = new LinkedList<Auction>();
		testAuctionList1.add(testAuction1);
		testAuctionList1.add(testAuction2);

		List<Auction> testAuctionList2 = new LinkedList<Auction>();
		testAuctionList2.add(testAuction3);

		// Setting Up the Entire Auction List Inside the Hash Map With the
		// Months
		theAuctionList.put(7, testAuctionList1);
		theAuctionList.put(10, testAuctionList2);

		// It should Print the Auction Details in the Auction List
		testACEmployee.viewAuctionDetails(theAuctionList);

		System.out
				.println("Testing the viewAuctionDetails(HashMap<Integer, List<Auction>> theAuctionList) method has Passed the Test Successfully");
	}
}
