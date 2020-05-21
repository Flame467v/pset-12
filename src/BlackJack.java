import java.util.ArrayList;

public class BlackJack {
	private static Player dealer = new Player(new Hand(), "Dealer", 0);
	private static Boolean roundend = false;
	private static int bet = 2;

	public static void playBlackjack() {
		Application.deck = Application.newDeck();
		roundend = false;

		System.out.print("\nPlace your bet " + Application.player.getName() + ":");
		bet = Integer.parseInt(Application.in.next());

		while (bet > Application.player.getChips() || bet % 2 != 0 || bet < 2 || bet > 20) {
			System.out.print("\n\nInvalid bet, make sure your bet is an even number between 2 and 20: ");
			bet = Integer.parseInt(Application.in.next());
		}

		Application.player.takeChips(bet);
		System.out.print("\nGame started!\n");

		ArrayList<Card> dealerhand = Hand.deal(1);
		dealer.setHand(dealerhand);
		dealer.getHand().get(0).setFaceUp(false);
		ArrayList<Card> playerhand = Hand.deal(1);

		Application.player.setHand(playerhand);


		showHands(dealer.getHand(), Application.player.getHand());
		int handinit = 0;
		int dealerhandinit = 0;
		int dealertotal = 0;

		for (Card card : Application.player.getHand()) {

			if(card.getRank() == 1) {
				handinit += 11;
			}

			else if (card.getRank() > 9) {
				handinit += 10;
			}
		}

		for (Card card : dealer.getHand()) {

			if(card.getRank() == 1) {
				dealerhandinit += 11;
			}

			else if (card.getRank() > 9) {
				dealerhandinit += 10;
			}
		}

		if (handinit == 21) {

			if (stay() == 21) {
				showHands(dealer.getHand(), Application.player.getHand());
				endGame(2);
			}

			else {
				showHands(dealer.getHand(), Application.player.getHand());
				endGame(1);
			}
		}

		while (!roundend) {

		boolean tf = false;

		while (!tf) {
			System.out.print("\n[1] Hit\n[2] Stay");

			try {
				int input = Integer.parseInt(Application.in.next());

				if  (input == 1) {
					tf = true;
					int hit = hit();
					showHands(dealer.getHand(), Application.player.getHand());

					if (hit > 21) {
						dealer.getHand().get(0).setFaceUp(true);
						roundend = true;
						showHands(dealer.getHand(), Application.player.getHand());
						endGame(4);
					}

					else if (hit == 21) {

						if (stay() == 21) {
							dealer.getHand().get(0).setFaceUp(true);
							roundend = true;
							showHands(dealer.getHand(), Application.player.getHand());
							endGame(2);
						}

						else {
							dealer.getHand().get(0).setFaceUp(true);
							roundend = true;
							showHands(dealer.getHand(), Application.player.getHand());
							endGame(3);
						}
					}
				}

				else if (input == 2) {
					dealer.getHand().get(0).setFaceUp(true);
					roundend = true;

					int playertotal = 0;
					for (Card card : Application.player.getHand()) {

						if(card.getRank() > 10) {
							playertotal += 10;
						}

						else if (!card.getName().equals("Ace")) {
							playertotal += card.getRank();
						}
					}

					for (Card card : Application.player.getHand()) {

						if (card.getName().equals("Ace")) {

							if (playertotal > 10) {
								playertotal += 1;
							}

							else {
								playertotal += 11;
							}
						}
					}

					tf = true;
					dealertotal = stay();
					dealer.getHand().get(0).setFaceUp(true);
					showHands(dealer.getHand(), Application.player.getHand());

					if (dealertotal == 21) {

						endGame(4);
					}

					else if (dealertotal > 21) {
						endGame(3);
					}

					else if (dealertotal > playertotal) {
						endGame(4);
					}

					else if (dealertotal == playertotal){
						endGame(2);
					}

					else {
						endGame(3);
					}

				}
			}

			catch (Exception e) {
				System.out.print("\nInvalid input\n");
			}
			}
		}

		Application.deck.addAll(Application.newDeck());
		boolean mtf = false;

		if(Application.player.getChips() > 1) {
			Application.gameOver();
			mtf = true;
		}

		while (!mtf) {
			System.out.print("\n\n[1] Play again\n[2] Main menu");
			int select = Integer.parseInt(Application.in.next());

			try {

				if  (select == 1) {
					mtf = true;
					BlackJack.playBlackjack();
				}

				else if (select == 2) {
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

	private static void endGame(int result) {
		switch (result) {

		case 1:
		System.out.print("\nBlack Jack!\n\n");
		dealer.getHand().get(0).setFaceUp(true);
		showHands(dealer.getHand(), Application.player.getHand());

		Application.player.pay(bet + bet / 2);
		System.out.print("\nNew balance: " + Application.player.getChips() + "\n");
		roundend = true;
		break;

		case 2:
		System.out.print("\nPush.\n");
		dealer.getHand().get(0).setFaceUp(true);
		showHands(dealer.getHand(), Application.player.getHand());

		Application.player.pay(bet);
		System.out.print("\nNew balance: " + Application.player.getChips());
		roundend = true;
		break;

		case 3:
		System.out.print("\nWinner!\n\n");
		dealer.getHand().get(0).setFaceUp(true);

		Application.player.pay(2 * bet);
		System.out.print("\nNew balance: " + Application.player.getChips());
		roundend = true;
		break;

		case 4:
		System.out.print("\nYou lose.\n\n");
		dealer.getHand().get(0).setFaceUp(true);

		System.out.print("\nNew balance: " + Application.player.getChips());
		roundend = true;
		break;
		}
	}

	private static int stay() {
		boolean hasace = false;
		int dealertotal = 0;
		boolean dealend = false;

		dealer.getHand().get(0).setFaceUp(true);
		do {
		dealertotal = 0;

		for(Card card : dealer.getHand()) {

			if(card.getRank() > 10) {
				dealertotal += 10;
			}

			else if (!card.getName().equals("Ace")) {
				dealertotal += card.getRank();
			}

			if (card.getName().equals("Ace")) {
				hasace = true;
			}
		}

		for (Card card : dealer.getHand()) {

			if (card.getName().equals("Ace")) {

				if (dealertotal > 10) {
					dealertotal += 1;
				}

				else {
					dealertotal += 11;
				}
			}
		}

		if (dealertotal >= 21 || (dealertotal >= 18 && hasace) || (dealertotal >= 17 && !hasace)) {
			dealend = true;
		}

		else {
			dealer.getHand().add(Hand.draw());
		}
		}

		while (!dealend);
		return dealertotal;
	}


	public static int hit() {
		int playertotal = 0;
		Application.player.getHand().add(Hand.draw());

		for (Card card : Application.player.getHand()) {

			if(card.getRank() > 10) {
				playertotal += 10;
			}

			else if (!card.getName().equals("Ace")) {
				playertotal += card.getRank();
			}
		}

		for (Card card : Application.player.getHand()) {

			if (card.getName().equals("Ace")) {

				if (playertotal > 10) {
					playertotal += 1;
				}

				else {
					playertotal += 11;
				}
			}
		}

		return playertotal;
	}

	public static void showHands(ArrayList<Card> dealerhand, ArrayList<Card> playerhand) {
		System.out.print("\nDealer: | ");

		for (int i = 0; i < dealerhand.size(); i++) {
			System.out.print(dealerhand.get(i).getName() + " of " + dealerhand.get(i).getSuit() + " | ");
		}

		System.out.print("\n\nYou   : | ");

		for (int i = 0; i < playerhand.size(); i++) {
			System.out.print(playerhand.get(i).getName() + " of " + playerhand.get(i).getSuit() + " | ");
		}

		System.out.print("\n");
	}

}
