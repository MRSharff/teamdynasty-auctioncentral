
public class Bidder extends User {

	int myCredit;
	
	public Bidder(String theUsername) {
		super(theUsername, 3);
		myCredit = 0;
	}

	public void chooseAuction() {
		//code to choose auction goes here
	}
	
	public void placeBid() {
		//code to place bid goes here
	}
	
	public void changeBid() {
		//code to change bid goes here
	}

	@Override
	public int compareTo(User otherUser) {
		return getUsername().compareTo(otherUser.getUsername());
	}
	
}
