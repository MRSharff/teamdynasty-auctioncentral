import java.util.Comparator;


public abstract class User implements Comparable<User> {
	private int myType = 1;
	private String myUsername;
	private int ID;
	
	public User(final String theUsername, final int userType) {
		myUsername = theUsername;
	}
	
	public String getUsername() {
		return myUsername;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getUserType() {
		return myType;
	}
	
	
	public void logIn() {
		//code to login goes here
	}
	
}
