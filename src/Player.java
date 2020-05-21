import java.util.ArrayList;

public class Player {
	private ArrayList<Card> hand;
	public String username;
	public int chips;

	public Player(ArrayList<Card> hand, String username, int chips) {
		this.hand = hand;
		this.username = username;
		this.chips = chips;
	}

	public void setHand(ArrayList<Card> newcards) {
		this.hand = newcards;
	}

	public void pay(int amount) {
		this.chips += amount;
	}

	public int takeChips(int amount) {
		this.chips -= amount;
		return amount;
	}

	public int getChips() {
		return this.chips;
	}

	public String getName() {
		return this.username;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}
}
