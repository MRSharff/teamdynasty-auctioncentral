import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class AuctionCentral {
	
	private static final String LOGIN_MESSAGE = "Choose an action:\n"
												+ "[1] Login\n"
												+ "[2] Create new Account\n"
												+ "[Q] Quit\n";
	private static final String LOGIN_ERROR_MESSAGE = "Unrecognized input, please enter 1 to login, 2 to create new account.";
	private static final String GET_USERNAME_MESSAGE = "Please enter your Username:";
	private static final String USER_NOT_FOUND_MESSAGE = "There was no user with that username, enter 1 to try again, 2 to create a new account, or Q to quit.";
	private static final String NEW_USER_MESSAGE = "Please enter a new Username:";
	private static final String NEW_USER_SPACE_ERROR = "Usernames cannot contain spaces.";
	private static final String USERNAME_COPY_ERROR_MESSAGE = "That username already exists, please enter a different username:";
	private static final String USER_TYPE_PROMPT_MESSAGE = "Enter 1 if you're a non-profit organization or 2 if you're a bidder.";
	private static final String LOGIN_SUCCESSFUL_MESSAGE = "You are now logged in as: ";
	
	
	public static void main(String[] Args) {
		
		
		
		boolean quitFlag = false;
		User currentUser;
		HashSet<User> userList = new HashSet<User>();
		List<Auction> auctionList = new LinkedList<Auction>();
		int userType = 1;
		
		while (!quitFlag) {
			//Prompt for login
			currentUser = logIn(userList);
			System.out.println(LOGIN_SUCCESSFUL_MESSAGE + currentUser.getUsername());
			userType = currentUser.getUserType();
		}
		
	}

	private static User logIn(HashSet<User> theUserList) {
		
		Scanner commandInput = new Scanner(System.in);
		String fullInput;
		int userInput = 3;
		boolean inputFlag = false;
		
		System.out.println(LOGIN_MESSAGE);
		while (!inputFlag) { //check for correct input
			if (commandInput.hasNext()) {
				fullInput = commandInput.nextLine(); //get the users full input
				if (fullInput.length() == 1) {
					if (fullInput.charAt(0) == '1') { //
						userInput = 1;
						inputFlag = true;
					} else if (fullInput.charAt(0) == '2') {
						userInput = 2;
						inputFlag = true;
					} else if (fullInput.charAt(0) == 'Q') {
						System.exit(0);
					}
				} else { //if it is not just 1 or 2, give error message.
					System.out.println(LOGIN_ERROR_MESSAGE);
				}
			}
		}
		
		//reset correctInput flag for next input
		inputFlag = false;

			if (userInput == 1) {
				//if they have a username and want to login do this
				
				//print out message
				System.out.println(GET_USERNAME_MESSAGE);
				
				//get the username that the user is trying to login with
				fullInput = commandInput.nextLine();

				//check the user list for a user with the entered username
				//return the user if found
				for (User user: theUserList) {
					if (user.getUsername().equals(fullInput)) {
						//close the scanner and return user
						commandInput.close();
						return user;
					}
				}
				//if not found, ask to redo or create a new account
				System.out.println(USER_NOT_FOUND_MESSAGE);
				boolean aFlag = false;
				while (!aFlag) { //check for correct input
					fullInput = commandInput.nextLine(); //get the users full input
					if (fullInput.length() == 1) {
						if (fullInput.charAt(0) == '1') { //
							userInput = 1;
							aFlag = true;
						} else if (fullInput.charAt(0) == '2') {
							userInput = 2;
							aFlag = true;
							inputFlag = true;
						} else if (fullInput.charAt(0) == 'Q') {
							System.exit(0);
						}
					} else { //if it is not just 1 or 2, give error message.
						System.out.println(LOGIN_ERROR_MESSAGE);
					}
				}
			}
		
		if(userInput == 2) {
			System.out.println(NEW_USER_MESSAGE);
			
			boolean aFlag = false;
			while (!aFlag) { //check for correct input
				String newUsername;
				newUsername = commandInput.nextLine(); //get the users full input
				//check if input has spaces.
				if (!newUsername.contains(" ")) {
					//check if already a username
					boolean nameFound = false;
					for (User user : theUserList) {
						if (user.getUsername().equals(newUsername)) {
							System.out.println(USERNAME_COPY_ERROR_MESSAGE);
							nameFound = true;
							break;
						}
					}
					if (!nameFound) {
						System.out.println(USER_TYPE_PROMPT_MESSAGE);
						boolean bFlag = false;
						while (!bFlag) { //check for correct input
							fullInput = commandInput.nextLine(); //get the users full input
							if (fullInput.length() == 1) {
								if (fullInput.charAt(0) == '1') { //
									userInput = 1;
									bFlag = true;
								} else if (fullInput.charAt(0) == '2') {
									userInput = 2;
									bFlag = true;
								} else if (fullInput.charAt(0) == 'Q') {
									System.exit(0);
								}
							} else { //if it is not just 1 or 2, give error message.
								System.out.println(LOGIN_ERROR_MESSAGE);
							}
						}
						
						System.out.println(userInput);
						//create a new user, add it to the user set, and return it to set the current user.
						if (userInput == 1) {
							User newUser = new NonProfitOrganization(newUsername);
							theUserList.add(newUser);
							//close the scanner and return user
							commandInput.close();
							return newUser;
						} else if (userInput == 2) {
							User newUser = new Bidder(newUsername);
							theUserList.add(newUser);
							//close the scanner and return user
							commandInput.close();
							return newUser;
						}
					}
					break;
				} else {
					System.out.println(NEW_USER_SPACE_ERROR);
				}
			}
		}
		
		
		
		//close the scanner and return user
		commandInput.close();
		return new Bidder("ERROR");
		
		
	}
	
	private void loadUserList() {
		//load the userlist with a list of users
	}
	
	private void loadAuctionList() {
		//loads a list of auctions into the auctionList
	}
	
}
