package clueGame;

public class Card implements Comparable{
    private String compareTo;
    private CardType equals;
    private Player getCardName;

    public Card(String string, CardType b) {
        this.compareTo = string;
        this.equals = b;
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Card)) {
            return false;
        }
        Card d = (Card)object;
        return this.compareTo.equals(d.compareTo) && this.equals == d.equals;
    }

    public final void setHoldingPlayer(Player m) {
        this.getCardName = m;
    }

    public final CardType getCardType() {
        return this.equals;
    }

    public final String getCardName() {
        return this.compareTo;
    }

    public final Player getHoldingPlayer() {
        return this.getCardName;
    }

    public final int compareTo(Card d) {
        return this.compareTo.compareTo(d.getCardName());
    }

    public final String toString() {
        return this.compareTo;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
