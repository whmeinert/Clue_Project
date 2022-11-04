package clueGame;

public class Card implements Comparable{
    private String cardName;
    private CardType cardType;
    private Player holdingPlayer;

    public Card(String string, CardType b) {
        this.cardName = string;
        this.cardType = b;
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Card)) {
            return false;
        }
        Card d = (Card)object;
        return this.cardName.equals(d.cardName) && this.cardType == d.cardType;
    }

    public final void setHoldingPlayer(Player m) {
        this.holdingPlayer = m;
    }

    public final CardType getCardType() {
        return this.cardType;
    }

    public final String getCardName() {
        return this.cardName;
    }

    public final Player getHoldingPlayer() {
        return this.holdingPlayer;
    }

    public final int compareTo(Card d) {
        return this.cardName.compareTo(d.getCardName());
    }

    public final String toString() {
        return this.cardName;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
