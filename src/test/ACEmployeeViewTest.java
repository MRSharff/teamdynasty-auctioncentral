//package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.teamdynasty.model.Auction;
import com.teamdynasty.model.AuctionCalendar;
import com.teamdynasty.model.User;
import com.teamdynasty.model.Item;
import com.teamdynasty.model.NonProfit;
import com.teamdynasty.view.ACEmployeeView;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
* ACEmployeeViewTest is the JUNIT test class for the ACEmployeeView Class.
* The methods tested in this unit test are mentioned below.
* <ul>
* <li>public void viewMonthlyCalendar(Scanner theScanner, AuctionCalendar theCalendar).
* <li>public void viewAuctionDetails(Scanner theScanner, AuctionCalendar theCalendar).
* </ul>
*
* @author      Shoba Gopi
* @see         User
* @see         ACEmployeeView
* @see         NonProfit
* @see         AuctionCalendar
* @see         Auction
* @see         Item
*/
public class ACEmployeeViewTest {

	// Test Instance of ACEmployeeView Class
	private ACEmployeeView testACEmployeeView;

	// Private Members of ACEmployeeView Class
	private static final String[] USER_OPTIONS = {"View Monthly Calendar",
                                                "View details of auction"};


	/**
	* The View Calendar method of the class is tested.
	* Non Empty Auction Calendars are tested.
	*/
	@Test
	public void testviewMonthlyCalendar() {

		System.out.println("Testing the viewMonthlyCalendar(Scanner theScanner, AuctionCalendar theCalendar) method");
		// Test Non Empty Auction Calendar In the Auction Central
		System.out.println("Testing Non Empty Auction Calendar");
		Scanner theScanner = new Scanner(System.in);		
		AuctionCalendar theCalendar = new AuctionCalendar();

		// Setting Up Test Items To be Added in the ItemList
		Item testItem1 = new Item("Item 1", 10, 25.30);
		Item testItem2 = new Item("Item 2", 20, 125.00);
		Item testItem3 = new Item("Item 3", 30, 258.87);
		Item testItem4 = new Item("Item 4", 40, 273.39);
		Item testItem5 = new Item("Item 5", 50, 32.65);
		Item testItem6 = new Item("Item 6", 60, 83.65);

		// Setting Up Test Item List for Auction
		List < Item > testItemList1 = new ArrayList < Item > ();
		testItemList1.add(testItem1);
		testItemList1.add(testItem2);
		testItemList1.add(testItem3);

		List < Item > testItemList2 = new ArrayList < Item > ();
		testItemList2.add(testItem4);
		testItemList2.add(testItem5);

		List < Item > testItemList3 = new ArrayList < Item > ();
		testItemList3.add(testItem6);

		// Setting Up Non Profit Organization for Auction
		NonProfit testNPO1 = new NonProfit("NPO 1", "Organization 1");
		NonProfit testNPO2 = new NonProfit("NPO 2", "Organization 2");
		NonProfit testNPO3 = new NonProfit("NPO 3", "Organization 3");

		Auction testAuction1 = new Auction(testNPO1, LocalDateTime.of(2015, 12, 15, 13, 25), LocalDateTime.of(2015, 7, 15, 14, 10), testItemList1);
		Auction testAuction2 = new Auction(testNPO2, LocalDateTime.of(2015, 12, 18, 12, 10), LocalDateTime.of(2015, 7, 18, 13, 15), testItemList2);
		Auction testAuction3 = new Auction(testNPO3, LocalDateTime.of(2015, 10, 27, 10, 30), LocalDateTime.of(2015, 10, 27, 11, 45), testItemList3);

		// Setting Up Auctions within the Auction Calendar
		theCalendar.addAuction(testAuction1);
		theCalendar.addAuction(testAuction2);
		theCalendar.addAuction(testAuction3);

		// It should Print the Auction Name in the Auction Calendar
		testACEmployeeView.viewMonthlyCalendar(theScanner,theCalendar);

		System.out.println("The viewMonthlyCalendar(Scanner theScanner, AuctionCalendar theCalendar) method has Passed the Test Successfully");

	}

	/**
	* The View Auction Details method of the class is tested.
	* Non Empty Auction Calendars are tested.
	*/
	@Test
	public void testViewAuctionDetails() {

		System.out.println("Testing the viewAuctionDetails(Scanner theScanner, AuctionCalendar theCalendar) method");

		// Test Non Empty Auction Calendar In the Auction Central
		System.out.println("Testing Non Empty Auction Calendar");
		Scanner theScanner = new Scanner(System.in);		
		AuctionCalendar theCalendar = new AuctionCalendar();

		// Setting Up Test Items To be Added in the ItemList
		Item testItem1 = new Item("Item 1", 10, 25.30);
		Item testItem2 = new Item("Item 2", 20, 125.00);
		Item testItem3 = new Item("Item 3", 30, 258.87);
		Item testItem4 = new Item("Item 4", 40, 273.39);
		Item testItem5 = new Item("Item 5", 50, 32.65);
		Item testItem6 = new Item("Item 6", 60, 83.65);

		// Setting Up Test Item List for Auction
		List < Item > testItemList1 = new ArrayList < Item > ();
		testItemList1.add(testItem1);
		testItemList1.add(testItem2);
		testItemList1.add(testItem3);

		List < Item > testItemList2 = new ArrayList < Item > ();
		testItemList2.add(testItem4);
		testItemList2.add(testItem5);

		List < Item > testItemList3 = new ArrayList < Item > ();
		testItemList3.add(testItem6);

		// Setting Up Non Profit Organization for Auction
		NonProfit testNPO1 = new NonProfit("NPO 1", "Organization 1");
		NonProfit testNPO2 = new NonProfit("NPO 2", "Organization 2");
		NonProfit testNPO3 = new NonProfit("NPO 3", "Organization 3");

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
		testACEmployeeView.viewAuctionDetails(theScanner,theCalendar);

		System.out.println("Testing the viewAuctionDetails(Scanner theScanner, AuctionCalendar theCalendar) method has Passed the Test Successfully");
	}
}
