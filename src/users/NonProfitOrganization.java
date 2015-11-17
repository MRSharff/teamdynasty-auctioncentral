package users;

import model.Auction;
import model.AuctionCentral;
import model.Item;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


public class NonProfitOrganization extends AbstractUser {//implements Options {
	
	private static final String[] OPTIONS = {"[1] Schedule Auction",
											 "[2] Create Auction",
											 "[3] Edit Auction",
											 "[4] Create New Inventory Item",
											 "[5] Edit Inventory Item"};



  //private boolean auctionReady;
  private String myOrgName;
  private Auction myAuction;
  private List<Item> myInventory;
	
	public NonProfitOrganization(final String theUsername, final String theOrgName) {
		super(theUsername, AuctionCentral.INPO);
    myOrgName = theOrgName;
		myInventory = new ArrayList<Item>();
    //auctionReady = false;
	}
	
	public Auction createAuction() {
		//code to enter auction info goes here
    Auction newAuction = new Auction(this);
    newAuction.newMyStartDate();
    newAuction.newMyEndDate();
//    myAuctions.add(newAuction);
    myAuction = newAuction;

    System.out.println("Auction created: " + myAuction.getMyName() + "\n");

    return newAuction;
	}
	
	public void scheduleAuction(HashMap<Integer, List<Auction>> theAuctionList) {
		//code to create auction goes here

    //if the NPO doesn't have an auction, create one
    if (!hasAuction()) {
      System.out.println("You do not have an auction to schedule, please create one first.");
      createAuction();
    }
    if (!theAuctionList.containsKey(myAuction.getStartDate().getMonth())) {
      ArrayList<Auction> newList = new ArrayList<Auction>();

      theAuctionList.put(myAuction.getStartDate().getMonthValue(), newList);
      //debug statement
      //System.out.println("list created.");
    }

    //BR no more than 2 per day implemented here
    //for each option of the same month, check day, no more than 2
    boolean createOkay = true;
    int count = 0;
    for (Auction auction : theAuctionList.get(myAuction.getStartDate().getMonthValue())) {
      if (auction.getStartDate().getDayOfMonth() == myAuction.getStartDate().getDayOfMonth()) {
        count++;
      }
      if (count > 1) {
        System.out.println("No more auctions can be scheduled on that day");
        createOkay = false;
        break;
      }
    }
    if (createOkay) {
      theAuctionList.get(myAuction.getStartDate().getMonthValue()).add(myAuction);
    }
	}
	
	public void editAuction(HashMap<Integer, List<Auction>> theAuctionList) {
		//code to edit auction goes here
    Scanner userOption = new Scanner(System.in);

    int option = 0;
    while (option != -1) {
      if (myAuction == null) {
        System.out.println("You do not have an auction to edit");
      } else {
        System.out.println("Choose what you would like to edit: ");
        myAuction.listOptions();
        System.out.println("[-1] Go back");

        option = userOption.nextInt();
        userOption.nextLine();

        switch (option) {
          case -1:
            break;
//          case 1: //change name, cannot change name, name is set as a business rule
//            System.out.print("Enter new name: ");
//            myAuction.setMyName(userOption.nextLine());
//            break;
          case 1: //change start date
            System.out.print("Change Date: ");
//            int month = userOption.nextInt();
//            int day = userOption.nextInt();
//            int year = userOption.nextInt();
//            userOption.nextLine();

            //remove auction from auctionlist because it will be in the wrong list
            theAuctionList.get(myAuction.getStartDate().getMonthValue()).remove(myAuction);
            //change the auction
            myAuction.newMyStartDate();
            myAuction.newMyEndDate();
            //add it back into the proper list
            theAuctionList.get(myAuction.getStartDate().getMonthValue()).add(myAuction);
            break;
          case 2: //add item
            System.out.println("Choose an option:");
            System.out.println("[1] Add item from inventory");
            System.out.println("[2] Create new item to add");

            int addItemOption = userOption.nextInt();
            userOption.nextLine();
            switch (addItemOption) {
              case 1:
                if (myInventory.isEmpty()) {
                  System.out.println("No items to edit.");
                  break;
                }
                System.out.println("Enter item number from the list: ");
                listItems();
                addItemOption = userOption.nextInt();

                //-1 for list to index offset
                myAuction.addItem(myInventory.get(addItemOption - 1));
                break;
              case 2:
                myAuction.getInventory().add(createItem());
                break;
            }
            break;
          case 3: //remove item
            System.out.println("Choose which item to remove.");
            myAuction.listItems();
            System.out.print("Item number: ");
            int itemOption = userOption.nextInt();
            userOption.nextLine();
            //remove and print removal message
            System.out.println("Removed: " + myAuction.getInventory().remove(itemOption).getName());

          default:
            System.out.println("Unrecognized Input");
        }
      }
    }

	}
	
	public Item createItem() {
		//code to add inventory item
    Scanner itemInfo = new Scanner(System.in);


    System.out.print("Enter item name: ");
    String itemName = itemInfo.nextLine();
    System.out.print("Enter quantity: ");
    int itemQuantity = itemInfo.nextInt();
    itemInfo.nextLine();
    System.out.print("Enter minimum starting bid: ");
    double startingBid = itemInfo.nextDouble();
    itemInfo.nextLine();

    Item newItem = new Item(itemName, itemQuantity, startingBid);

    myInventory.add(newItem);

    return newItem;

	}
	
	public void editItem() {
		//code to edit inventory goes here
    Scanner userOption = new Scanner(System.in);
    if (myInventory.isEmpty()) {
      System.out.println("No items to edit.");
    }
    listItems();
    int option = super.getOption("Choose an item to edit: ");

    Item item = myInventory.get(option - 1);

    while (option != -1) {
      System.out.println("Choose what you would like to edit: ");
      item.listOptions();
      System.out.println("Or -1 to go back");

      option = userOption.nextInt();
      userOption.nextLine();

      switch (option) {
        case -1:
          break;
        case 1:
          System.out.print("Enter new name: ");
          item.setName(userOption.nextLine());
          break;
        case 2:
          System.out.print("Enter new quantity: ");
          item.setQuantity(userOption.nextInt());
          userOption.nextLine();
          break;
        case 3:
          System.out.print("Enter new minimum starting bid: ");
          item.setMyMinStartBid(userOption.nextDouble());
          userOption.nextLine();
          break;
        default:
          System.out.println("Unrecognized Input");
      }
    }

	}
	
//	public void showOptions() {
//		for (String option : OPTIONS) {
//			System.out.println(option);
//		}
//	}

  public void listItems() {
    int counter = 1;
    for (Item item : myInventory) {
      System.out.println("[" + counter + "] " + item.getName());
      counter++;
    }
  }

  public boolean hasAuction() {
    return (myAuction != null);
  }

  public Auction getMyAuction() {
    return myAuction;
  }

  public String getDashedName() {
    return myOrgName.replace(" ", "-");
  }
	
	public String toXML() {
		//WIP
		StringBuilder xml = new StringBuilder();
		xml.append("");
		
		return xml.toString();
	}
	
	@Override
	public int compareTo(AbstractUser otherUser) {
		return getUsername().compareTo(otherUser.getUsername());
	}

	@Override
	public void showOptions() {
		for (String option : OPTIONS) {
			System.out.println(option);
		}
	}

	@Override
	public void doAction(int theOption, HashMap<Integer, List<Auction>> theAuctionList,
			TreeSet<AbstractUser> theUserList) {
    switch (theOption) {
      case 1:
        scheduleAuction(theAuctionList);
        break;
      case 2:
        createAuction();
        break;
      case 3:
        editAuction(theAuctionList);
        break;
      case 4:
        createItem();
        break;
      case 5:
        editItem();
        break;
      default:
        System.out.println("Error in NPO doAction switch, made it to default case.");
    }
		
	}

}
