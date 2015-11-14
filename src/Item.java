
public class Item {
	private String myName;
	private int myQuantity;

	
	public String getName() {
		String returnStr = myName;
		return returnStr; //return a copy
	}
	
	public int getQuantity() {
		return myQuantity;
	}
	
	public void setName(final String theName) {
		myName = theName;
	}
	
	public void setQuantity(final int theQuantity) {
		myQuantity = theQuantity;
	}
	
	
}
