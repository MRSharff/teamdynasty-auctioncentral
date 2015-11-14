
public class Bid {
	
	private double myAmount;
	private Bidder myOwner;
	
	public Bid(final double theAmount, final Bidder theOwner) {
		myAmount = theAmount;
		myOwner = theOwner;
	}
	
	public double getAmount() {
		return myAmount;
	}
	
	public Bidder getOwner() {
		return myOwner;
	}
	
	public void setAmount(final double theAmount) {
		myAmount = theAmount;
	}
}
