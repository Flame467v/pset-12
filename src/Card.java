class Card implements Comparable<Card> {
	private String suit;
	private int rank;
	private String name;
	private Boolean faceup;

	public Card (String suit, int rank, String name, Boolean faceup) {
		this.suit = suit;
		this.rank = rank;
		this.name = name;
		this.setfaceup(faceup);
	}

	public String getName() {

		if (this.getfaceup()) {
			return this.name;
		}

		else {
			return "?";
		}
	}

	public String getSuit() {

		if (this.getfaceup()) {
			return this.suit;
		}

		else {
			return "?";
		}
	}

	public int getRank() {
		return this.rank;
	}

	public void setRank(int newrank) {
		this.rank = newrank;
	}

    @Override
    public int compareTo(Card card) {

				if (this.getRank() > card.getRank()) {
            return 1;
        }

				else if (this.getRank() < card.getRank()) {
            return -1;
        }

				else {
            return 0;
        }
    }

	public Boolean getfaceup() {
		return faceup;
	}

	public void setfaceup(Boolean faceup) {
		this.faceup = faceup;
	}
}
