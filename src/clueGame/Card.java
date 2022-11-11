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
    public final Player getHoldingPlayer() {
        return this.holdingPlayer;
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
        Card card = (Card)object;
        return this.cardName.equals(card.cardName) && this.cardType == card.cardType;
    }

    @Override
    public int compareTo(Object card) {
        return 0;
    }


}
