package toucansFinalProject;

import java.util.*;

public class setAndRun {
	//This class will hold methods that have anything to do with creating or modifying sets and runs
	//So as to not clutter the main class
	
	public static void makeSet(Hand potRS, ArrayList<Set> sets, Hand playerHand) {//#10PC_GABRIEL
		if(checkForCards(potRS)) {
			//Variable Declaration
			boolean sameValue = true;
			
			//check if the cards in the potRS can make a valid set
			if(potRS.getCards().size() >= 3) {//if there is three or more cards
				//Check if the suits are the same
				for(Card c: potRS.getCards()) {//cycle through all the cards
					//if the value is not the same as the first it cannot be a set
					if(potRS.getCards().get(0).getValue() != c.getValue()) {
						sameValue = false;
					}
				}//end of for cycle cards
				//if the cards don't match no set is made
				if(sameValue) {
					System.out.println("These cards can become a set.");
					sets.add(new Set(potRS.getCards()));
					playerHand.getCards().removeAll(potRS.getCards());//Remove all cards from the players hand
					potRS.setCards(new ArrayList<Card>()); 
				} else
					System.out.println("The cards cannot be a set, they did not have the same value");
			}else {
				System.out.println("Select more cards you need at least three.");
			}
		}
	}
	
	public static void makeRun(Hand potRS, ArrayList<Run> runs, Hand playerHand) {//#9PC_GABRIEL
		boolean sameSuit = true;//stays true if the cards are the same suit
		boolean ascendingCards = true;//stays true if the cards are ascending/descending
		boolean innerAscending = false;
		boolean largest;
		boolean smallest;
		boolean smaller;
		boolean larger;
		
		if(checkForCards(potRS)) {
			if(potRS.getCards().size() >= 3) {//if there is three or more cards
				//Check if the cards are the same suit
				for(Card c: potRS.getCards()) {//for all the cards selected
					if(c.getSuit() != potRS.getCards().get(0).getSuit()) {//if the suits are different
						sameSuit = false;
						System.out.println("The selected cards connot form a run.");
					}
				}//end of for loop to cycle cards
				if(sameSuit) {//if the cards are the same suit
					//check if they have back to back values
					for(Card c : potRS.getCards()) {//cycle through all the cards selected
						innerAscending = true;
						smallest = true;
						smaller = false;
						largest = true;
						larger = false;
						
						for(Card c2: potRS.getCards()){//cycle though cards again
							//check if the card is the largest or smallest
							if(c2.getValue() -1 == c.getValue()) {//check if this is a card above the c card
								smaller = true;
							} else if(c2.getValue() + 1 == c.getValue()) {//otherwise check if this a card below the c card
								larger = true;
							}
							
						}//end of inner loop 1
						
						if(!smaller && !larger)//if the c card does not have a value above and below
							innerAscending = false;//the c card is not connected
						else {//check for largest or smallest card in the hand
							
							if(smaller && larger)//if smaller and larger than cards next to then inner ascending is true
								;
							else {
								for (Card c2 : potRS.getCards()) {// cycle though cards again
									if (smaller) {// if the card is smaller check if its the smallest
										if (c2.getValue() < c.getValue()) {// if a selected card is smaller
											smallest = false;// then c card is not the smallest
										}
									} else if (larger) {// if the card was larger, check if it was the largest
										if (c2.getValue() > c.getValue()) {// if a selected card is larger
											largest = false;// then c card is not the largest
										}
									}
								} // end of inner for loop 2
							}
							if(!largest && !smallest) {//if not the largest or smallest 
								innerAscending = false;
							}
						}
						
						if(!innerAscending) {//if card is not connected
							ascendingCards = false;//then the cards cannot be a run
						}
					}//end of out for loop
					
					//Check if the cards are in are next to each other in value
					if(ascendingCards) {
						System.out.println("These cards can be made into a run.");
						runs.add(new Run(potRS.getCards()));
						playerHand.getCards().removeAll(potRS.getCards());//Remove all cards from the players hand
						potRS.setCards(new ArrayList<Card>()); 
					} else {
						System.out.println("The cards have to all have values connected in one chain of three or more cards."
								+ "\nNo run can be made with the selected cards.");
					}
					
				}//end of sameSuit if
				else {
					System.out.println("The cards must be the same suit to make a run, and the values must all be connected.");
				}
				
			}else {
				System.out.println("Select more cards you need at least three.");
			}
		}//end of check for cards if statement
	}//end of method
	
	public static Hand selectCards(Hand player, Hand potRS) {//#6PC_GABRIEL
		Scanner scan = new Scanner(System.in);
		boolean foundCard;
		String selected;
		String handCard;//card in players hand, the code for it
		
		
		System.out.println("These are the card you have selected:\n " + potRS.displayHand()
			+ "\nType the card(s) that you want selected, based on the following: ");
		for(Card c: player.getCards()) {
			if(!potRS.getCards().contains(c)) {//if the card is not already selected
				System.out.println("For the " + c.toString() + "Type: " + c.getValue() + c.getSuit() + "\n");
			}
		}
		System.out.println("Be sure to put a space between each of your selections."
				+ "\nIf the card is not there or if you mispelled the card, an error and the attempt will be listed. "
				+ "\nType in the cards here : ");
		//scanner then check if valid
		selected = scan.nextLine();
	
		StringTokenizer st = new StringTokenizer(selected, " ");
	
		while(st.hasMoreTokens()) {
			foundCard = false;
			String nextToken = st.nextToken().trim();
				
			for(Card c: player.getCards()) {//loops through all cards in the hand
				handCard = "" + c.getValue() + c.getSuit();
					
				if(handCard.equalsIgnoreCase(nextToken)) {
					if(!potRS.getCards().contains(c)) {//if the card is not already selected
						potRS.getCards().add(c);
						System.out.println("Selected the card: " + c.toString());
						foundCard = true;
						}
					}
			}//end of for
				
			if(!foundCard) {
				System.out.println("Card: " + nextToken + ", was not found make sure the spelling and numbers are correct." );
			}
		}//end of while
			
		return potRS;
	}//end of method
	
	public static Hand deselectCards(Hand player, Hand potRS) {//#6PC_GABRIEL
		Scanner scan = new Scanner(System.in);
		boolean notDone = true;
		boolean foundCard;
		String selected;
		String handCard;//card in players hand, the code for it
		

		System.out.println("These are the card you have selected:\n " + potRS.displayHand()
			+ "\nType the card(s) that you want selected, based on the following: ");
		for(Card c: potRS.getCards()) {
			System.out.println("For the " + c.toString() + "Type: " + c.getValue() + c.getSuit() + "\n");
		}
		System.out.println("Be sure to put a space between each of your deselections."
				+ "\nIf the card is not there or if you mispelled the card, an error and the attempt will be listed. "
				+ "\nType in the cards here : ");
		//scanner then check if valid
		selected = scan.nextLine();
		
		StringTokenizer st = new StringTokenizer(selected, " ");
			
		while(st.hasMoreTokens()) {
			foundCard = false;
			String nextToken = st.nextToken().trim();
				
			for(int i = 0; i < potRS.getSize() ; i++) {//loops through all cards in the hand
				Card c = potRS.getCards().get(i);
				
				handCard = "" + c.getValue() + c.getSuit();
					
				if(handCard.equalsIgnoreCase(nextToken)) {
					potRS.getCards().remove(c);//May not be correct check later if there is an error involving deleting a selected card
					System.out.println("Deselected the card: " + c.toString());
					foundCard = true;
					i--;
				}
			}//end of for
				
			if(!foundCard) {
				System.out.println("Card: " + nextToken + ", was not found make sure the spelling and numbers are correct." );
			}
		}//end of while
			
		return potRS;
	}//end of method
	
	public static void addSet(Hand potRS, ArrayList<Set> sets, Hand playerHand) {//#8PC_GABRIEL
		boolean added = false;
		
		if(checkForCards(potRS)) {
			Card selCard = potRS.getCards().get(0);//selected card
			
			if(potRS.getCards().size() == 1) {//if there is only one card
				for(Set c: sets) {
					if(c.getSetOfCards().get(0).getValue() == selCard.getValue()) {
						System.out.println("This card can be added to the set.");
						c.getSetOfCards().add(selCard);//add the card to the hand
						playerHand.getCards().removeAll(potRS.getCards());//Remove all cards from the players hand
						potRS.setCards(new ArrayList<Card>());//Remove card from selected cards
						added = true;
					}//end of if statement
				}//end of for loop
				
				if(!added) {
					System.out.println("The card could not be added to any set of cards.");
				}
				
			} else {
				System.out.println("You can only add one card to a set.");
			}
		}//end of check for cards if
	}//end of method
	
	public static void addRun(Hand potRS, ArrayList<Run> runs, Hand playerHand) {//#7PC_GABRIEL

		if(checkForCards(potRS)) {
			if(potRS.getCards().size() == 1) {
				Card selCard = potRS.getCards().get(0);//get the selected card
				boolean sameSuit = false;
				boolean nextTo = false;
				
				for(Run r: runs) {//cycle through all the runs
					//check for the same suit
					if(r.getRunOfCards().get(0).getSuit().equalsIgnoreCase(selCard.getSuit())){
						for(Card c: r.getRunOfCards()) {
							//check if a value is next to the selected card
							if(c.getValue() + 1 == selCard.getValue() || c.getValue() -1 == selCard.getValue()) {
								System.out.println("The card can be added to a run");
								r.getRunOfCards().add(selCard);//adds card to the run
								potRS.getCards().remove(selCard);//removes cards from the selected cards
								playerHand.getCards().remove(selCard);//remove card from the player hand
								nextTo = true;
								break;//to exit the loop
							}
						}//end of inner for loop
						sameSuit = true;
					}//end of if sameSuit statement
				}//end of for loop
				if(!sameSuit) {
					System.out.println("The card does not share the same suit as any of the runs.");
				} else if(!nextTo) {
					System.out.println("The suit was right but it was not able to form a run due to values.");
				}
			} else {
				System.out.println("Use one selected card at a time when adding to a run.");
			}
		}
	}
	
	public static boolean checkForCards(Hand potRS) {
		if(potRS.getSize() <= 0) {
			System.out.println("Select a card first.");
			return false;
		}else {
			return true;
		}
	}
	
}
