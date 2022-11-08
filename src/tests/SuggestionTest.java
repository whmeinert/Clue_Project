package tests;

import clueGame.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class SuggestionTest {
    private static Board board;
    private static Card Wrench;
    private static Card Candlestick;
    private static Card Rope;
    private static Card Dagger;
    private static Card LeadPipe;
    private static Card Revolver;

    @BeforeEach
    public void setUp() {
        Wrench = new Card("Wrench", CardType.WEAPON);
        Candlestick = new Card("Candlestick", CardType.WEAPON);
        Rope = new Card("Rope", CardType.WEAPON);
        Dagger = new Card("Dagger", CardType.WEAPON);
        LeadPipe = new Card("Dagger", CardType.WEAPON);
        Revolver = new Card("Revolver", CardType.WEAPON);
        // Board is singleton, get the only instance
        board = Board.getInstance();
        // set the file names to use my config files
        board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
        // Initialize will load config files
        board.initialize();
    }

    @Test
    public void accusationTest(){
        // Check correct solution
        board.setSolution(board.getCard("Professor Plum"), board.getCard("Office"), board.getCard("Revolver"));
        Solution testSolution = new Solution(board.getCard("Professor Plum"), board.getCard("Office"), board.getCard("Revolver"));
        assertTrue(board.checkAccusation(testSolution));

        // Check wrong person
        board.setSolution(board.getCard("Mrs. White"), board.getCard("Office"), board.getCard("Revolver"));
        assertFalse(board.checkAccusation(testSolution));

        // Check wrong room
        board.setSolution(board.getCard("Professor Plum"), board.getCard("Kitchen"), board.getCard("Revolver"));
        assertFalse(board.checkAccusation(testSolution));

        // Check wrong weapon
        board.setSolution(board.getCard("Professor Plum"), board.getCard("Office"), board.getCard("Rope"));
        assertFalse(board.checkAccusation(testSolution));
    }



    @Test
    public void computerSuggestionTest(){
        board.getPlayer(2).setLoc(board.getCell(2,3),false);
        ComputerPlayer testComp = (ComputerPlayer)board.getPlayer(2);
        Solution compSol = testComp.createSuggestion(board.getRoom(board.getCell(2,3)));

        // Check that a suggestion is made and that if multiple items are not seen then pick randomly
        assertNotNull(compSol.room);
        assertNotNull(compSol.weapon);
        assertNotNull(compSol.person);

        // Check that room matches current location
        Solution testSol = new Solution();
        testSol.room = board.getCard("Office");
        assertEquals(testSol.room, compSol.room);

        // Check that is picks items that have not been seen
        for (Card testCard : testComp.getSeen()) {
            if (testComp.getSeen().contains(board.getCard("Office"))) {
                assertNotEquals(testCard, compSol.room);
            }
            assertNotEquals(testCard, compSol.weapon);
            assertNotEquals(testCard, compSol.person);
        }

        // check if only one person and weapon not seen pick those two
        board.getPlayer(3).clearCards();
        testComp = (ComputerPlayer)board.getPlayer(3);
        int n = 0;
        for (Card card : board.getCards()){
            if (card.getCardType() == CardType.WEAPON && !testComp.getSeen().contains(card) && n < 5){
                testComp.addToHand(card);
                n++;
            }
            if (card.getCardType() == CardType.PERSON && !testComp.getSeen().contains(card) && n < 5){
                testComp.addToHand(card);
                n++;
            }
            if (n > 5 && card.getCardType() == CardType.PERSON){
                compSol.person = card;
            }
            if (n > 5 && card.getCardType() == CardType.WEAPON){
                compSol.weapon = card;
            }
        }
        assertEquals(compSol.weapon.getCardName(), compSol.weapon.getCardName());
        assertEquals(compSol.person.getCardName(), compSol.person.getCardName());
    }

}
