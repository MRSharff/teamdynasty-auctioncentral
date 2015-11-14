import java.util.HashSet;


public class NonProfitOrganization extends AbstractUser implements Options {
	
	private static final String[] OPTIONS = {"[1] Schedule Auction",
											 "[2] Create Auction",
											 "[3] Edit Auction",
											 "[4] Create New Inventory Item",
											 "[5] Edit Inventory Item"};
	
	
	private HashSet<Item> myInventory;
	
	
	public NonProfitOrganization(String theUsername) {
		super(theUsername, 2);
		//myInventory = new HashSet<Item>();
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
	
	public void createItem() {
		//code to add inventory item
	}
	
	public void editItem() {
		//code to edit inventory goes here
	}
	
//	public void showOptions() {
//		for (String option : OPTIONS) {
//			System.out.println(option);
//		}
//	}
	
	
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

}
