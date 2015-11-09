import java.util.HashSet;


public class NonProfitOrganization extends User {
	
	private HashSet<Item> myInventory;
	
	
	public NonProfitOrganization(String theUsername) {
		super(theUsername, 2);
		myInventory = new HashSet<Item>();
	}
	
	public void createAuction() {
		//code to enter auction info goes here
	}
	
	public void scheduleAuction() {
		//code to create auction goes here
	}
	
	public void editAuction() {
		//code to edit auction goes here
	}
	
	public void addInventory() {
		//code to add inventory item
	}
	
	public void editInventory() {
		//code to edit inventory goes here
	}
	
	@Override
	public int compareTo(User otherUser) {
		return getUsername().compareTo(otherUser.getUsername());
	}
	
}
