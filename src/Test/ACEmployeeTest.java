package Test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import model.AuctionCalendar;
import model.Auction;
import model.Item;
import users.NonProfitOrganization;
import users.ACEmployee;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
* ACEmployeeTest is the JUNIT test class for the ACEmployee Class.
* The methods tested in this unit test are mentioned below.
* <ul>
* <li>public void viewCalendar(AuctionCalendar theCalendar).
* <li>public void viewAuctionDetails(AuctionCalendar theCalendar).
* </ul>
*
* @author      Shoba Gopi
* @version     2.1
* @see         ACEmployee
* @see         NonProfitOrganization
* @see         AuctionCalendar
* @see         Auction
* @see         Item
*/
public class ACEmployeeTest {

	// Test Instance of ACEmployee Class
	private ACEmployee testACEmployee;

	// Private Members of ACEmployee Class
	private static final String[] OPTIONS = {
		"[1] View Monthly Calendar",
			"[2] View details of auction"
	};

	// Private Members of the User Class which were Inherited by ACEmployee
	private int myType = 1;
	private String myUsername;
	private int ID;

	/**
	* Setting Up the test ACEmployee Instance
	*/
	@Before
	public void setUp() {
		myUsername = "Shoba";
		testACEmployee = new ACEmployee(myUsername);
	}

	/**
	* The Constructor of the ACEmployee Class is tested.
	* The Username and Usertype are asserted for equality.
	*/
	@Test
	public void testACEmployee() {
		System.out.println("Testing the Constructor of ACEmployee Class");
		assertEquals(testACEmployee.getUsername(), myUsername);
		assertEquals(testACEmployee.getUserType(), myType);
		System.out.println("Constructor of ACEmployee Class Passed the test Successfully");
	}

	/**
	* The View Calendar method of the class is tested.
	* Empty and Non Empty Auction Calendars are tested.
	*/
	@Test
	public void testViewCalendar() {

		System.out.println("Testing the viewCalendar(AuctionCalendar theCalendar) method");
		// Test Empty Auction Calendar In the Auction Central
		System.out.println("Testing Empty Auction Calendar");
		AuctionCalendar theCalendar = new AuctionCalendar();
		assertTrue(theCalendar.getMyAuctions().isEmpty());
		testACEmployee.viewCalendar(theCalendar);
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

		Auction testAuction1 = new Auction(testNPO1, LocalDateTime.of(2015, 7, 15, 13, 25), LocalDateTime.of(2015, 7, 15, 14, 10), testItemList1);
		Auction testAuction2 = new Auction(testNPO2, LocalDateTime.of(2015, 7, 18, 12, 10), LocalDateTime.of(2015, 7, 18, 13, 15), testItemList2);
		Auction testAuction3 = new Auction(testNPO3, LocalDateTime.of(2015, 10, 27, 10, 30), LocalDateTime.of(2015, 10, 27, 11, 45), testItemList3);

		// Setting Up Auctions within the Auction Calendar
		System.out.println("The following Auctions have been added to the Auction Calendar");
		theCalendar.addAuction(testAuction1);
		theCalendar.addAuction(testAuction2);
		theCalendar.addAuction(testAuction3);

		// It should Print the Auction Name in the Auction Calendar
		testACEmployee.viewCalendar(theCalendar);

		System.out.println("The viewCalendar(AuctionCalendar theCalendar) method has Passed the Test Successfully");

	}

	/**
	* The View Auction Details method of the class is tested.
	* Empty and Non Empty Auction Calendars are tested.
	*/
	@Test
	public void testViewAuctionDetails() {

		System.out.println("Testing the viewAuctionDetails(AuctionCalendar theCalendar) method");
		// Test Empty Auction Calendar In the Auction Central
		System.out.println("Testing Empty Auction Calendar");
		AuctionCalendar theCalendar = new AuctionCalendar();
		assertTrue(theCalendar.getMyAuctions().isEmpty());
		testACEmployee.viewCalendar(theCalendar);
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
		testACEmployee.viewAuctionDetails(theCalendar);

		System.out.println("Testing the viewAuctionDetails(AuctionCalendar theCalendar) method has Passed the Test Successfully");
	}
}
