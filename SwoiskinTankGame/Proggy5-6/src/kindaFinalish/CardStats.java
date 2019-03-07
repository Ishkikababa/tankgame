package kindaFinalish;

import java.util.ArrayList;
import java.util.Scanner;

public class CardStats
{
	private static ArrayList<PlayingCard> clubs = new ArrayList<PlayingCard>();
	private static ArrayList<PlayingCard> diamonds = new ArrayList<PlayingCard>();
	private static ArrayList<PlayingCard> hearts = new ArrayList<PlayingCard>();
	private static ArrayList<PlayingCard> spades = new ArrayList<PlayingCard>();
	
	private static String[] values = new String[]{"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
	
	private static int numToBeDrawn = 0;
	
	public static void main(String[] args)
	{
		askInput();
		getCards();
		sortCards();
		display();
	}
	
	public static void sortCards()
	{
		sortList(clubs);
		sortList(diamonds);
		sortList(hearts);
		sortList(spades);
	}
	
	public static void sortList(ArrayList<PlayingCard> list)
	{
		for (int i=0;i<list.size()-1;i++)
		{
			for (int j=0;j<list.size()-1;j++)
			{
				if (getValue(list.get(j)) > getValue(list.get(j + 1)))
				{
					swap(list, j, j+1);
				}
			}
		}
	}
	
	public static void swap(ArrayList<PlayingCard> list, int one, int two)
	{
		PlayingCard temp = list.get(one);
		list.set(one, list.get(two));
		list.set(two, temp);
	}
	
	public static void display()
	{
		int totalValue = 0;
		System.out.println("Here are your " + numToBeDrawn + " cards: ");
		for (PlayingCard card : clubs)
		{
			System.out.println("    " + card.GetCard());
			totalValue += getValue(card);
		}
		
		if (!diamonds.isEmpty()) {System.out.println();}
		for (PlayingCard card : diamonds)
		{
			System.out.println("    " + card.GetCard());
			totalValue += getValue(card);
		}

		if (!hearts.isEmpty()) {System.out.println();}
		for (PlayingCard card : hearts)
		{
			System.out.println("    " + card.GetCard());
			totalValue += getValue(card);
		}

		if (!spades.isEmpty()) {System.out.println();}
		for (PlayingCard card : spades)
		{
			System.out.println("    " + card.GetCard());
			totalValue += getValue(card);
		}

		System.out.println();
		System.out.println("Totals: ");
		System.out.println("    Clubs: " + clubs.size());
		System.out.println("    Diamonds: " + diamonds.size());
		System.out.println("    Hearts: " + hearts.size());
		System.out.println("    Spades: " + spades.size());
		
		System.out.println("Total sum of the " + numToBeDrawn + " cards: " + totalValue);
	}
	
	public static void getCards()
	{
		int numSoFar = 0;
		PlayingCard temp;
		String currentSuit = "";
		do
		{
			temp = new PlayingCard();
			currentSuit = getSuit(temp);
			switch (currentSuit)
			{
				case "Clubs": 
					if (!listContains(clubs, temp))
					{
						clubs.add(temp);
						numSoFar++;
					}
					break;
				case "Diamonds": 
					if (!listContains(diamonds, temp))
					{
						diamonds.add(temp);
						numSoFar++;
					}
					break;
				case "Hearts": 
					if (!listContains(hearts, temp))
					{
						hearts.add(temp);
						numSoFar++;
					}
					break;
				case "Spades": 
					if (!listContains(spades, temp))
					{
						spades.add(temp);
						numSoFar++;
					}
					break;
				default:
					System.out.println("Oops");
			}
			
		} while (numSoFar < numToBeDrawn);
	}
	
	private static boolean listContains(ArrayList<PlayingCard> list, PlayingCard card)
	{
		for (PlayingCard temp : list)
		{
			if (temp.GetCard().equals(card.GetCard()))
			{
				return true;
			}
		}
		return false;
	}
	
	private static String getSuit(PlayingCard card)
	{
		return card.GetCard().substring(card.GetCard().lastIndexOf(" ") + 1);
	}
	
	private static int getValue(PlayingCard card)
	{
		String value = card.GetCard().substring(0, card.GetCard().indexOf(" "));
		for (int i=0;i<values.length;i++)
		{
			if (values[i].equals(value))
			{
				return i+1;
			}
		}
		return -1;
	}
	
	public static void askInput()
	{
		Scanner input = new Scanner(System.in);
		String tempS;
		int tempI;
		do
		{
			System.out.print("How Many Cards (1-52) should be drawn from a single, standard, shuffled deck? ");
			tempS = input.nextLine();
			try
			{
				tempI = Integer.parseInt(tempS);
				if (52 >= tempI && tempI >= 1)
				{
					numToBeDrawn = tempI;
				}
			}
			catch(Exception e)
			{
			}
		} while (numToBeDrawn == 0);
		System.out.println();
	}
}