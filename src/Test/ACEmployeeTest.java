//package test;
//
//import static org.junit.Assert.*;
//
//import AuctionCalendar;
//import Auction;
//import Item;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * ACEmployeeTest is the JUNIT test class for the ACEmployee Class
// **/
//
//public class ACEmployeeTest
//{
//	// Test Instance of ACEmployee
//	private ACEmployee testACEmployee;
//
//	// Private Members of the User Class which were Inherited by ACEmployee
//	private int myType = 1;
//	private String myUsername;
//	private int ID;
//}
//
//	// Setting Up the test ACEmployee Instance
//	@Before
//	public void setUp()
//	{
//		myUsername = "Shoba";
//		testACEmployee = new ACEmployee(myUsername);
//	}
//
//	// Testing the Constructor of ACEmployee Class
//	@Test
//	public void testACEmployee()
//	{
//		assertEquals(testACEmployee.getUsername(), myUsername);
//		assertEquals(testACEmployee.getUserType(), myType);
//	}
//
//	// Testing the View Calendar function of ACEmployee Class
//	@Test
//	public void testViewCalendar()
//	{
//		// Test Empty Auction List In the Auction Calendar
//		AuctionCalendar AC=new AuctionCalendar();
//		testACEmployee.viewCalendar(AC);
//		assertTrue(AC.getAuctionList().isEmpty());
//
//		// Test Auction List within a Auction Calendar
//		AuctionCalendar AC=new AuctionCalendar();
//		AC.addAuction(new Auction(Date('14-11-2015'),new Item('Item1',25)));
//		AC.addAuction(new Auction(Date('10-10-2015'),new Item('Item2',45)));
//		AC.addAuction(new Auction(Date('17-12-2015'),new Item('Item3',50)));
//
//		// It should Print the Auction Details in the Auction Calendar
//		testACEmployee.testviewCalender(AC)
//	}
//
//	// Testing the View Auction Details function of ACEmployee Class
//	@Test
//	public void testViewAuctionDetails()
//	{
//		// Test Empty Auction List
//		List<Auction> AuctionList= new LinkedList<Auction>();
//		testACEmployee.viewAuctionDetails(AuctionList);
//		assertTrue(AuctionList.isEmpty());
//
//		// Test Auctions in a Auction List
//		List<Auction> AuctionList= new LinkedList<Auction>();
//		AuctionList.add(new Auction(Date('14-11-2015'),new Item('Item1',25)));
//		AuctionList.add(new Auction(Date('10-10-2015'),new Item('Item2',45)));
//		AuctionList.add(new Auction(Date('17-12-2015'),new Item('Item3',50)));
//
//		// It should Print Auction Details in the Auction List
//		testACEmployee.viewAuctionDetails(AuctionList);
//	}
