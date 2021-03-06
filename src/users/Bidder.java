package users;

import model.Auction;
import model.AuctionCentral;
import model.Bid;
import model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;


public class Bidder extends AbstractUser {//implements Options {
	
	private static final String[] OPTIONS = {"[1] Choose a Non-profit Organization to View their Auction",
		 									 "[2] Bid on Item",
		 									 "[3] Change Bid"};

	double myCredit;
	Auction currentAuction;
	
	public Bidder(String theUsername) {
		super(theUsername, AuctionCentral.IBIDDER);
		myCredit = 0;
	}

	public void chooseAuction(HashMap<Integer, List<Auction>> theAuctionList, TreeSet<AbstractUser> theUserList) {
		//code to choose auction goes here
//
//		//Choose an NPO to view the auction of
		currentAuction = listNPO(theUserList).getMyAuction();

    currentAuction.viewDetails();
//
//		currentAuction = theAuctionList.get(chosenNPO);

	}

  //Deprecated from HashMap<String, Auction> theAuctionList version
//	private static String listNPO(TreeSet<AbstractUser> theUserList) {
//		Scanner uInput = new Scanner(System.in);
////		List<users.NonProfitOrganization> NPOList;
//		List<String> NPOList = new ArrayList<String>();
//
//
//		System.out.println("Choose a Non-Profit Organization");
//		//fill list of NPOs to show and print them
//		int counter = 1;
//		for (AbstractUser user : theUserList) {
//			if (user.getUserType() == AuctionCentral.INPO) {
//				NPOList.add(user.getUsername());
//				System.out.println("[" + counter + "] " + user.getUsername());
//				counter++;
//			}
//		}
//
//		int option = uInput.nextInt();
//		uInput.nextLine();
//
//		return NPOList.get(option);
//	}



  	private static NonProfitOrganization listNPO(TreeSet<AbstractUser> theUserList) {
		Scanner uInput = new Scanner(System.in);
//		List<users.NonProfitOrganization> NPOList;
		List<NonProfitOrganization> NPOList = new ArrayList<NonProfitOrganization>();


		System.out.println("Choose a Non-Profit Organization");
		//fill list of NPOs to show and print them
		int counter = 1;
		for (AbstractUser user : theUserList) {
			if (user.getUserType() == AuctionCentral.INPO) {
				NPOList.add((NonProfitOrganization) user);
				System.out.println("[" + counter + "] " + user.getUsername());
				counter++;
			}
		}

		int option = uInput.nextInt();
		uInput.nextLine();

		return NPOList.get(option - 1);
	}

  private Item chooseItem() {
    Scanner itemGet = new Scanner(System.in);
    List<Item> items = currentAuction.getInventory();

    int counter = 1;
    for (Item item : items) {
      System.out.println("[" + counter + "] " + item.getName());
    }

    System.out.print("Choose item: ");
//    if (itemGet.hasNextInt()) {
      // - 1 for index by 0 offset
      return items.get(itemGet.nextInt() - 1);
//    }
  }
	
	public void placeBid(final Item theItem) {
		//code to place bid goes here
		
		//Business Rule: only one bid on an Item per bidder

    Scanner bidDetail = new Scanner(System.in);
		double bidAmount = 0;
		System.out.print("Enter Bid Amount: ");
    if (bidDetail.hasNextDouble()) {
      bidAmount = bidDetail.nextDouble();
    }

    theItem.addBid(super.getUsername(), bidAmount);

	}
	
	public void changeBid(final Item theItem) {
		//code to change bid goes here
    placeBid(theItem);
	}
	
	public String toXML() {
		return "3," + super.getUsername() + "," + myCredit;
	}
	
	public void showOptions() {
		for (String option : OPTIONS) {
			System.out.println(option);
		}
	}

  @Override
  public void doAction(int theOption, HashMap<Integer, List<Auction>> theAuctionList, TreeSet<AbstractUser> theUserList) {
    switch (theOption) {
      case 1: //Choose an auction to bid on.
        chooseAuction(theAuctionList, theUserList);
        break;
      case 2:
        if (currentAuction == null) {
          chooseAuction(theAuctionList, theUserList);
        }
        placeBid(chooseItem());
        break;
      case 3:
        if (currentAuction == null) {
          chooseAuction(theAuctionList, theUserList);
        }
        changeBid(chooseItem());
    }
  }

  @Override
	public int compareTo(AbstractUser otherUser) {
		return getUsername().compareTo(otherUser.getUsername());
	}

//	@Override
//	public void doAction(int theOption, HashMap<String, Auction> theAuctionList,
//			TreeSet<AbstractUser> theUserList) {
//
//
//	}
}
