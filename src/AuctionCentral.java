import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;


public class AuctionCentral {
	
	private static final String LOGIN_MESSAGE = "Choose an action:\n"
												+ "[1] Login\n"
												+ "[2] Create new Account\n"
												+ "[3] Quit";
	private static final String LOGIN_ERROR_MESSAGE = "Unrecognized input, please enter 1 to login, 2 to create new account.";
	private static final String GET_USERNAME_MESSAGE = "Please enter your Username:";
	private static final String USER_NOT_FOUND_MESSAGE = "There was no user with that username,";
	private static final String NEW_USER_MESSAGE = "Please enter a new Username:";
	private static final String NEW_USER_SPACE_ERROR = "Usernames cannot contain spaces.";
	private static final String USERNAME_COPY_ERROR_MESSAGE = "That username already exists, please enter a different username:";
	private static final String USER_TYPE_PROMPT_MESSAGE = "Select User Type:\n"
															+ "[1] Non-Profit Organization\n"
															+ "[2] Bidder";
	private static final String LOGIN_SUCCESSFUL_MESSAGE = "You are now logged in as: ";
	private static final String NEXT_ACTION_MESSAGE = "Choose what action you would like to perform";
	
	
	public static void main(String[] Args) {
		
		
		
		boolean quitFlag = false;
		AbstractUser currentUser = null;
		TreeSet<AbstractUser> userList = new TreeSet<AbstractUser>();
		List<Auction> auctionList = new LinkedList<Auction>();
		int userType;
		
		
		//Setup for testing purposes
		System.out.println("Testing Use");
		setupUsers(userList);
		System.out.println("User list: \n" + userList.toString() + "\n");
		//End setup for testing purposes
		
	
		
		
		while (!quitFlag) {
			//Prompt for login
			System.out.println(LOGIN_MESSAGE);
			
			//Ask what type of login they would like to do
			//put response into option variable
			int option = chooseOption();
			
			switch (option) {
			case 1:
				while((currentUser = logIn(userList)) == null) {
					System.out.println(USER_NOT_FOUND_MESSAGE);
				}
				break;
			case 2:
				while ((currentUser = createNewUser(userList)) == null) {
					System.out.println(USERNAME_COPY_ERROR_MESSAGE);
				}
			case 3:
				System.exit(0);
			}
			
			/*if (option == 1) { //option 1 is to login
				while((currentUser = logIn(userList)) == null) {
					System.out.println(USER_NOT_FOUND_MESSAGE);
				}
			} else if {
				while ((currentUser = createNewUser(userList)) == null) {
					System.out.println(USERNAME_COPY_ERROR_MESSAGE);
				}
			} else if (option == 3) { //option 3 is quit
				System.exit(0);
			}*/
			
			System.out.println(LOGIN_SUCCESSFUL_MESSAGE + currentUser.getUsername());
			userType = currentUser.getUserType();
			
			System.out.println(NEXT_ACTION_MESSAGE);
//			switch (userType) {
//			case 1: //ACEmployee options
//				printACEOptions();
//				option = chooseOption();
//				break;
//			case 2: //NPO options
//				printNPOOptions();
//				option = chooseOption();
//				break;
//			case 3: //Bidder Options
//				printBidderOptions();
//				option = chooseOption();
//				break;
//			}
			((Options) currentUser).showOptions();
			System.out.println("Made it here");
		}
		
	}

	private static int chooseOption() {
		Scanner commandInput = new Scanner(System.in);
		int returnVal = commandInput.nextInt();
		commandInput.nextLine();
		
		return returnVal;
	}
	
	private static int getUserType() {
		
		System.out.println(USER_TYPE_PROMPT_MESSAGE);
		
		Scanner commandInput = new Scanner(System.in);
		int returnVal = commandInput.nextInt();
		
		return returnVal;
	}
	
	private static AbstractUser createNewUser(TreeSet<AbstractUser> theUserList) {
//		String newUser = null;
//		System.out.println(NEW_USER_MESSAGE);
//		
//		
//		
//		boolean flag = false;
//		
//		
//		while (!flag) {
//			boolean found = false;
//			Scanner commandInput = new Scanner(System.in);
//			newUser = commandInput.nextLine();
//			commandInput.close();
//			for (User theUser : theUserList) {
//				if (theUser.getUsername().equals(newUser)) {
//					System.out.println(USERNAME_COPY_ERROR_MESSAGE);
//					found = true;
//					break;
//				}
//			}
//			if (!found) {
//				flag = true;
//			}
//		}
//		
//		int userType = getUserType();
//		if (userType == 1) {
//			return new NonProfitOrganization(newUser);
//		}
//		
//		return new Bidder(newUser);
		System.out.println(NEW_USER_MESSAGE);
		
		Scanner commandInput = new Scanner(System.in);
		String userName = commandInput.nextLine();
		
		for (AbstractUser theUser : theUserList) {
			if (theUser.getUsername().equals(userName)) {
				return null;
			}
		}
		
		
		int userType = getUserType();
		if (userType == 1) {
			AbstractUser newUser = new NonProfitOrganization(userName);
			theUserList.add(newUser);
			return newUser;
		}
		
		AbstractUser newUser = new Bidder(userName);
		theUserList.add(newUser);
		return newUser;
		
	}
	
	private static AbstractUser logIn(TreeSet<AbstractUser> theUserList) {
		System.out.println(GET_USERNAME_MESSAGE);
		
		Scanner commandInput = new Scanner(System.in);
		String userName = commandInput.nextLine();
		
		for (AbstractUser theUser : theUserList) {
			if (theUser.getUsername().equals(userName)) {
				return theUser;
			}
		}
		
		return null;
	}
	
	private static void printACEOptions() {
		System.out.println("[1] View Monthly Calendar");
		System.out.println("[2] View details of auction");
	}
	
	private static void printNPOOptions() {
		System.out.println("[1] Schedule Auction");
		System.out.println("[2] Create Auction");
		System.out.println("[3] Edit Auction");
		System.out.println("[4] Create New Inventory Item");
		System.out.println("[5] Edit Inventory Item");
	}
	
	private static void printBidderOptions() {
		System.out.println("[1] View Available Auctions");
		System.out.println("[2] Bid on Item");
		System.out.println("[3] Change Bid");
	}
	
	private static void setupUsers(TreeSet<AbstractUser> theUsers) {
		ACEmployee newACEmployee = new ACEmployee("ACETester");
		Bidder newBidder = new Bidder("BidderTester");
		NonProfitOrganization newNPO = new NonProfitOrganization("NPOTest");
		
		theUsers.add(newACEmployee);
		theUsers.add(newBidder);
		theUsers.add(newNPO);
		
	}
	
	private static void loadUserList() {
		//load the userlist with a list of users
	}
	
	private static void loadAuctionList() {
		//loads a list of auctions into the auctionList
	}
	
}
