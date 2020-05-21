import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Poker {

	private static int bet = 1;
	private static int sbet = 0;
	private static ArrayList<Card> newcards = new ArrayList<Card>();

	public static void playPoker() {
		bet = 1;
		sbet = 0;
		newcards = new ArrayList<Card>();
		Application.deck = Application.newDeck();
		bet = bet();
		System.out.print("\nGame started!\n");
		Application.player.setHand(Hand.deal(4));

		boolean hasace = false;
		System.out.print("\n\nYou: | ");

		for (int i = 0; i < Application.player.getHand().size(); i++) {
			System.out.print(Application.player.getHand().get(i).getName() + " of " + Application.player.getHand().get(i).getSuit() + " | ");

			if (Application.player.getHand().get(i).getName().equals("Ace")) {
				 hasace = true;
			}
		}

		System.out.println("\n\nChoose an option:");
		System.out.println("\n[1] Bet Again \n[2] Trade Cards \n[3] Fold");

		boolean tf = false;

		while (!tf) {
			int pokerchoose = Integer.parseInt(Application.in.next());

			try {

				if  (pokerchoose == 1) {
					tf = true;
					sbet = bet();
					System.out.println("\n[1] Trade Cards\n[2] Fold");

					int dubdown = Integer.parseInt(Application.in.next());
					boolean dubdowntf = false;

					while (!dubdowntf) {

					try {
						if (dubdown == 1) {
							dubdowntf = true;

							for (int i = 1; i <= tradeTimes(); i++) {

								if (trade()) {
								}
								else {
									break;
								}
								}

							for (Card newCard : newcards) {
								Application.player.getHand().add(newCard);
							}

							newcards.clear();
							evaluate();
						}

						else if (dubdown == 2) {
							dubdowntf = true;
							evaluate();
						}
					}

					catch (Exception e) {
						System.out.print("Invalid input");
					}
					}
				}

				else if (pokerchoose == 2) {
					tf = true;

					for (int i = 1; i <= tradeTimes(); i++) {

						if (trade()) {
						}	else {
							break;
						}
						}

					for (Card newCard : newcards) {
						Application.player.getHand().add(newCard);
					}

					newcards.clear();
					evaluate();
				}

				 else if (pokerchoose == 3) {
					tf = true;
					evaluate();
				}

				else {
					System.out.print("Invald input");
				}
			}

			catch (Exception e) {
				System.out.print("Invalid input");
			}
		}

		System.out.print("\n");
	}

	private static int tradeTimes () {

		for(Card card : Application.player.getHand()) {

			if (card.getName().equals("Ace")) {
				return 4;
			}
		}
		return 3;
	}

	private static int bet(){
		boolean tf = false;

		while (!tf) {

		try {
		System.out.print("\nPlace your bet " + Application.player.getName() + ": ");
		int amnt = Integer.parseInt(Application.in.next());

		while (amnt > Application.player.getChips() || amnt > 10 || amnt < 1 ) {
			System.out.println("\n\nInvalid bet, make sure your bet is a number between 1 and 10: ");
			amnt = Integer.parseInt(Application.in.next());
		}

		return Application.player.takeChips(amnt);
		}

		catch (Exception e) {
			System.out.print("Invalid input");
		}
		}
		return 0;
	}

	private static boolean trade() {
		int cardnum = 0;
		boolean tradetf = false;

		while (!tradetf) {
		System.out.print("\nCurrent hand: | ");

		for (Card card : Application.player.getHand()) {
			System.out.print(card.getName() + " of " + card.getSuit() + " | ");
		}

		for (Card card : newcards) {
			System.out.print(card.getName() + " of " + card.getSuit() + " | ");
		}

		System.out.println("\n\nChoose a number to trade the card: \n");

		for (int i = 0; i < Application.player.getHand().size(); i++) {
			System.out.println("(" + (i + 1) + ") " + Application.player.getHand().get(i).getName() + " of " + Application.player.getHand().get(i).getSuit());
		}

		System.out.println("(" + (Application.player.getHand().size() + 1) + ") Done");

		cardnum = Integer.parseInt(Application.in.next());

		try {
			if (cardnum >= 1 && cardnum <= (Application.player.getHand().size() + 1)) {
				tradetf = true;

				if(cardnum == (Application.player.getHand().size() + 1)) {

					return false;

				}

				else {
					newcards.add(Hand.draw());
					Application.player.getHand().remove(cardnum - 1);
					return true;

				}
			}

			else {
				System.out.print("Invald input");
			}
		}

		catch (Exception e) {
			System.out.print(e);
		}

		}

		return false;
	}

	private static boolean uniformSuit (ArrayList<Card> cards) {

		for(int i = 1; i < cards.size(); i++) {

			if (!cards.get(i).getSuit().equals(cards.get(i - 1).getSuit())) {
				return false;
			}
		}
		return true;
	}

	private static boolean consecutive(List<Card> cards) {

		for(int i = 1; i < cards.size(); i++) {

			if (cards.get(i).getRank() != (cards.get(i - 1).getRank() + 1)) {
				return false;
			}
		}
		return true;
	}

	private static boolean ofAKind(List<Card> cards) {

		for(int i = 1; i < cards.size(); i++) {

			if (cards.get(i).getRank() != cards.get(i - 1).getRank()) {
				return false;
			}
		}
		return true;
	}

	private static void playAgain() {
		boolean mtf = false;

		if(Application.player.getChips() > 1) {
			Application.gameOver();
			mtf = true;
		}

		while (!mtf) {
			System.out.print("\n\n[1] Play again\n[2] Main menu");
			int choose = Integer.parseInt(Application.in.next());

			try {

				if  (choose == 1) {
					mtf = true;
					playPoker();
				}

				else if (choose == 2) {
					mtf = true;
					Application.mainMenu();
				}

				else {
					System.out.print("Invald input");
				}
			}

			catch (Exception e) {
				System.out.print("Invalid input");
			}
		}
	}

	private static int pairs(List<Card> cards) {
		int pairs = 0;

		for(int i = 1; i < cards.size(); i ++) {

			if (cards.get(i).getRank() == cards.get(i - 1).getRank()) {
				pairs++;
			}
		}
		return pairs;
	}

	private static void evaluate() {
		ArrayList<Card> c = new ArrayList<Card>();

		c.add(new Card("Spades", 9, Application.toWord(9), true));
		c.add(new Card("Diamonds", 9, Application.toWord(9), true));
		c.add(new Card("Diamonds", 10, Application.toWord(10), true));
		c.add(new Card("Clubs", 10, Application.toWord(10), true));
		c.add(new Card("Hearts", 1, Application.toWord(1), true));
		Collections.sort(c);

		ArrayList<Card> cards = Application.player.getHand();
		cards = c;
		System.out.print("\n");
		for (Card card : cards) {
			System.out.print(card.getName() + " of " + card.getSuit() + " | ");
		}

	if (cards.get(0).getName().equals("Ace") && consecutive(cards.subList(1, 5)) && cards.get(1).getRank() == 10 && uniformSuit(cards)) {
		System.out.println("\nRoyal Flush!");
		Application.player.pay(251 * (bet + sbet));
	}

	else if (consecutive(cards) && uniformSuit(cards)) {
		System.out.println("\nStraight Flush!");
		Application.player.pay(101 * (bet + sbet));
	}

	else if (ofAKind(cards.subList(0, 4)) || ofAKind(cards.subList(1, 5))) {
		System.out.println("\nFour of a Kind!");
		Application.player.pay(26 * (bet + sbet));
	}

	else if ((ofAKind(cards.subList(0, 3)) && ofAKind(cards.subList(3, 5))) || (ofAKind(cards.subList(0, 3)) && ofAKind(cards.subList(3, 5)))) {
		System.out.println("\nFull House!");
		Application.player.pay(11 * (bet + sbet));
	}

	else if (uniformSuit(cards)) {
		System.out.println("\nFlush!");
		Application.player.pay(6 * (bet + sbet));
	}

	else if ((cards.get(0).getName().equals("Ace") && consecutive(cards.subList(1, 5)) && cards.get(1).getRank() == 10 )|| consecutive(cards)) {
		System.out.println("\nStraight!");
		Application.player.pay(4 * (bet + sbet));
	}

	else if (ofAKind(cards.subList(0, 3)) || ofAKind(cards.subList(1, 4)) || ofAKind(cards.subList(2, 5))) {
		System.out.print("\nThree of a Kind");
		Application.player.pay(3 * (bet + sbet));
	}

	else if (pairs(cards) == 2) {
		System.out.print("\nTwo Pair");
		Application.player.pay(2 * (bet + sbet));
	}

	else if (pairs(cards) == 1) {
		System.out.print("\nPair");
		Application.player.pay(bet + sbet);
	}

	else {
		System.out.print("\nYou lose!");
	}

	System.out.println("\n" + Application.player.getChips());
	playAgain();
	}
	
}
