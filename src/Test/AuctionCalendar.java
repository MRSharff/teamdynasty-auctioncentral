package Test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import model.AuctionCalendar;
import model.Auction;
import model.Item;
import users.AbstractUser;
import users.NonProfitOrganization;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
* AuctionCalendarTest is the JUNIT test class for the AuctionCalendar Class.
* The methods tested in this unit test are mentioned below.
* <ul>
* <li>public void addAuction(final Auction theAuction).
* <li>public void removeAuction(final Auction theAuction).
* <li>public boolean addUser(final AbstractUser theUser)
* </ul>
*
* @author      Shoba Gopi
* @version     2.1
* @see		     AbstractUser
* @see         NonProfitOrganization
* @see         AuctionCalendar
* @see         Auction
* @see         Item
*/
public class AuctionCalendarTest {

	// Test Instance of AuctionCalendar
	private AuctionCalendar testCalendar;

	// Private Constants of the AuctionCalendar Class
	private static final int TOTAL_MONTHS = 12;
	private static final int MAX_AUCTIONS = 25;
	private static final int MAX_DAYS_OUT = 90;
	private static final int MAX_ROLLING_AUCTIONS = 5;
	private static final int MAX_ROLLING_DAYS = 7;
	private static final int MINIMUM_HOURS_BETWEEN = 2;

	// Private Members of AuctionCalendar Class
	private HashMap < Integer, List < Auction >> myAuctions;
	private HashMap < String, AbstractUser > myUsers;

	/**
	* Setting Up the test AuctionCalendar Instance
	*/
	@Before
	public void setUp() {
		testCalendar = new AuctionCalendar();
	}

	/**
	* The Constructor of the AuctionCalendar Class is tested.
	* The Auction List and User List are asserted for empty list.
	*/
	@Test
	public void testAuctionCalendar() {
		System.out.println("Testing the Constructor of AuctionCalendar Class");
		assertTrue(testCalendar.getMyAuctions().isEmpty());
		assertTrue(testCalendar.getMyUsers().isEmpty());
		System.out.println("Constructor of AuctionCalendar Class Passed the test Successfully");
	}

	/**
	* The Add Auction method of the class is tested.
	*/
	@Test
	public void testAddAuction() {

		System.out.println("Testing the addAuction(final Auction theAuction) method");
		// Setting Up Test Items To be Added in the ItemList
		Item testItem1 = new Item("Item 1", 10, 25.30);
		Item testItem2 = new Item("Item 2", 20, 125.00);

		// Setting Up Test Item List for Auction
		List < Item > testItemList1 = new LinkedList < Item > ();
		testItemList1.add(testItem1);
		testItemList1.add(testItem2);

		// Setting Up Non Profit Organization for Auction
		NonProfitOrganization testNPO1 = new NonProfitOrganization("NPO 1", "Organization 1");

		// Setting Up Auction 
		Auction testAuction = new Auction(testNPO1, LocalDateTime.of(2015, 7, 15, 13, 25), LocalDateTime.of(2015, 7, 15, 14, 10), testItemList1);

		// Setting Up Auctions within the Auction Calendar
		System.out.println("The following Auctions have been added to the Auction Calendar");
		testCalendar.addAuction(testAuction);

		System.out.println("Testing the addAuction(final Auction theAuction) method has Passed the Test Successfully");
	}

	/**
	* The Remove Auction method of the class is tested.
	*/
	@Test
	public void testRemoveAuction() {

		// Removing Auction which is added to the Auction Calendar
		System.out.println("Removing An Auction which is present in the Auction Calendar");
		// Setting Up Test Items To be Added in the ItemList
		Item testItem1 = new Item("Item 1", 30, 87.36);
		Item testItem2 = new Item("Item 2", 40, 15.20);

		// Setting Up Test Item List for Auction
		List < Item > testItemList = new LinkedList < Item > ();
		testItemList.add(testItem1);
		testItemList.add(testItem2);

		// Setting Up Non Profit Organization for Auction
		NonProfitOrganization testNPO = new NonProfitOrganization("NPO 1", "Organization 1");

		// Setting Up Auction 
		Auction testAuction = new Auction(testNPO, LocalDateTime.of(2015, 10, 12, 13, 25), LocalDateTime.of(2015, 10, 13, 14, 10), testItemList);

		// Setting Up Auctions within the Auction Calendar
		System.out.println("The following Auctions have been added to the Auction Calendar");
		testCalendar.addAuction(testAuction);

		// Removing Auction from Auction Calendar
		testCalendar.removeAuction(testAuction);

		System.out.println("Testing the removeAuction(final Auction theAuction) method has Passed the Test Successfully");
	}
	
	/**
	* The Add User method of the class is tested.
	*/
	@Test
	public void testAddUser() {

		System.out.println("Testing the addUser(final AbstractUser theUser) method");
		// Testing A User which is not in the User List
		System.out.println("Adding a User Which is not in the User List");
		NonProfitOrganization testNPO = new NonProfitOrganization("NPO 1", "Organization 1");
		testCalendar.addUser(testNPO);

		System.out.println("Testing the addUser(final AbstractUser theUser) method has Passed the Test Successfully");
	}
}
