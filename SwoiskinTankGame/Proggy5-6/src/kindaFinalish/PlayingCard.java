package kindaFinalish;

public class PlayingCard
{
	private String[] suits = new String[]{"Clubs", "Diamonds", "Hearts", "Spades"};
	private String[] values = new String[]{"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
	private String mySuit, myValue;
	
	public PlayingCard()
	{
		mySuit = suits[(int)(Math.random() * 4)];
		myValue = values[(int)(Math.random() * 13)];
	}
	
	public String GetCard()
	{
		return myValue + " of " + mySuit;
	}
}