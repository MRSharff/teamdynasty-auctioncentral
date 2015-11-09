
public class ACEmployee extends User {

	
	public ACEmployee(String theUsername) {
		super(theUsername, 1);
	}

	public void viewCalendar() {
		//code to view calendar goes here
	}
	
	public void viewBids() {
		//code to veiwbids goes here.
	}

	@Override
	public int compareTo(User otherUser) {
		return getUsername().compareTo(otherUser.getUsername());
	}
	
	
}
