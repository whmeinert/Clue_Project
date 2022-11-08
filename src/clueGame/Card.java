package clueGame;

import clueGame.CardType;

public class Card implements Comparable{
    private String cardName;
    private CardType cardType;
    private Player holdingPlayer;

    public Card(String string, CardType b) {
        this.cardName = string;
        this.cardType = b;
    }

    public final void setHoldingPlayer(Player player) {
        this.holdingPlayer = player;
    }

    public final CardType getCardType() {
        return this.cardType;
    }

    public final String getCardName() {
        return this.cardName;
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

    @Override
    public int compareTo(Object card) {
        return 0;
    }


}
