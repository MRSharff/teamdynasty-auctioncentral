import java.util.Comparator;


public abstract class AbstractUser implements Comparable<AbstractUser> {
	
	private static final String[] OPTIONS = null;
	private String myUsername;
	private int myID;
	private int myType = 1; //1 = ACE, 2 = NPO, 3 = Bidder
	
	public AbstractUser(final String theUsername, final int userType) {
		myUsername = theUsername;
	}
	
	public String getUsername() {
		return myUsername;
	}
	
	public int getID() {
		return myID;
	}
	
	public int getUserType() {
		return myType;
	}
	
//	public void showOptions() {
//		for (String option : OPTIONS) {
//			System.out.println(option);
//		}
//	}
	
	
	public void logIn() {
		//code to login goes here
	}
	
	public String toString() {
		return myUsername;
	}
	
//	public String toXML() {
//		String xml = "<User>\n" + myType + "," + ID;
//		
//		
//	}
}
