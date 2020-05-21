import java.util.ArrayList;
import java.util.Random;

public class Hand extends ArrayList<Card> {
	private ArrayList<Card> cards;

	public Hand() {
	   this.cards = new ArrayList<Card>();
	}

	public static Card draw (){
		Random random = new Random();
		int randindex = random.nextInt(Application.deck.size());
		return Application.deck.remove(randindex);
	}

	public static ArrayList<Card> deal(int cardnum) {

		ArrayList<Card> newHand = new ArrayList<Card>();
		
		for (int i = 0; i <= cardnum; i++) {
			Random random = new Random();
			int randindex = random.nextInt(Application.deck.size());
			newHand.add(Application.deck.remove(randindex));
		}
		return newHand;
	}

	public ArrayList<Card> getCards() {
		return this.cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

}
