import java.util.ArrayList;
import java.util.Scanner;

class Application {
	public static ArrayList<Card> deck = new ArrayList<Card>();
	public static Player player;
	public static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		deck.addAll(newDeck());

		System.out.print("Welcome to the Casino!");
		System.out.println("\n\nWhat is your name?");

		String fullname = in.nextLine();
		player = new Player(new Hand(), fullname, 100);

		mainMenu();
		}


		public static ArrayList<Card> newDeck() {
			ArrayList<Card> deck = new ArrayList<Card>();
			ArrayList<String> suits = new ArrayList<String>();

			suits.add("Spades");
			suits.add("Hearts");
			suits.add("Diamonds");
			suits.add("Clubs");

			for (int i = 1; i <= 13; i++) {

				for (int x = 0; x < 4; x++) {
	 				deck.add(new Card(suits.get(x), i, toWord(i), true));
				}

			}
			return deck;
		}

		public static void gameOver() {
			System.out.println("Game over!\n");

			if (player.getChips() >= 100) {
				System.out.println("\nTotal earnings: " + (player.getChips() - 100));
			}

			else {
				System.out.println("\nTotal losses: " + (player.getChips() - 100));
			}
		}

		public static void mainMenu() {
			System.out.print("\n" + player.getName() + "'s balance: " + player.getChips() + "\n");
			boolean tf = false;

			while (!tf) {
				System.out.print("\nChoose a game by it's corresponding number:\n\n[1] Black Jack\n[2] Poker\n[3] Cash out");
				int choosegame = Integer.parseInt(in.next());

				try {

					if  (choosegame == 1) {
						tf = true;
						BlackJack.playBlackjack();
					}

					else if (choosegame == 2) {
						tf = true;
						Poker.playPoker();
					}

					else if (choosegame == 3) {
						tf = true;
						gameOver();

						in.close();
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

		public static String toWord(int rank) {
			switch(rank) {
				case 1:
					return "Ace";
				case 2:
					return "Two";
				case 3:
					return "Three";
				case 4:
					return "Four";
				case 5:
					return "Five";
				case 6:
					return "Six";
				case 7:
					return "Seven";
				case 8:
					return "Eight";
				case 9:
					return "Nine";
				case 10:
					return "Ten";
				case 11:
					return "Jack";
				case 12:
					return "Queen";
				case 13:
					return "King";
			}
			return "NA";
		}
		
}
