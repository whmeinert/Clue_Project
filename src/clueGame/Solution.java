package clueGame;

public class Solution {
    public Card person;
    public Card weapon;
    public Card room;

    public Solution() {
    }

    public Solution(Card d, Card d2, Card d3) {
        this.person = d;
        this.weapon = d3;
        this.room = d2;
    }
}
