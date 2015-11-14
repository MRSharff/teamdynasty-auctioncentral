
public class Bidder extends AbstractUser implements Options {
	
	private static final String[] OPTIONS = {"[1] View Available Auctions",
		 									 "[2] Bid on Item",
		 									 "[3] Change Bid"};

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
	
	public String toXML() {
		return "3," + super.getUsername() + "," + myCredit;
	}
	
	public void showOptions() {
		for (String option : OPTIONS) {
			System.out.println(option);
		}
	}

	@Override
	public int compareTo(AbstractUser otherUser) {
		return getUsername().compareTo(otherUser.getUsername());
	}
	
}
