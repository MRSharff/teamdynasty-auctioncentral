
public class ACEmployee extends AbstractUser implements Options {

	private static final String[] OPTIONS = {"[1] View Monthly Calendar",
		 									 "[2] View details of auction"};
	
	public ACEmployee(String theUsername) {
		super(theUsername, 1);
	}

	public void viewCalendar() {
		//code to view calendar goes here
	}
	
	public void viewBids() {
		//code to veiwbids goes here.
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
