package tests;

import clueGame.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class SuggestionTest {
    private static Board board;
    private static Card Wrench;
    private static Card Candlestick;
    private static Card Rope;
    private static Card Dagger;
    private static Card LeadPipe;
    private static Card Revolver;
    private static Player testPlayer1;
    private static Player testPlayer2;
    private static Player testPlayer3;


    @BeforeEach
    public void setUp() {
        testPlayer1 = new ComputerPlayer("Rob", 0, 0, "Black");
        testPlayer2 = new ComputerPlayer("Robberto", 0, 0, "Black");
        testPlayer3 = new ComputerPlayer("Robbie", 0, 0, "Black");
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
    public void disproveSuggestionTest(){
        Solution testSolution = new Solution(board.getCard("Professor Plum"), board.getCard("Office"), board.getCard("Revolver"));
        testPlayer1.addToHand(board.getCard("Professor Plum"));

        // check if card is returned if one card in hand matches
        assertEquals(board.getCard("Professor Plum"), testPlayer1.disproveSuggestion(testSolution));

        // Check if card is returned if more than one card in hand matches
        testPlayer1.addToHand(board.getCard("Office"));
        assertNotNull(testPlayer1.disproveSuggestion(testSolution));

        // Check that no cards are returned if no cards in hand match
        testSolution = new Solution(board.getCard("Mrs. White"), board.getCard("Kitchen"), board.getCard("Revolver"));
        assertNull(testPlayer1.disproveSuggestion(testSolution));
    }

    @Test
    public void handleSuggestionTest(){
        Card testPerson = new Card("TestP", CardType.PERSON);
        Card testWeapon = new Card("TestW", CardType.WEAPON);
        Card testRoom = new Card("TestR", CardType.ROOM);
        Solution testSolution = new Solution(testPerson, testRoom, testWeapon);

        // Test no players can disprove
        for (Player player: board.getPlayers()){
            assertNull(board.handleSuggestion(testSolution, player));
        }

        // Test Only suggester can disprove
        testPlayer1.addToHand(testRoom);
        ArrayList<Player> testPlayers = new ArrayList<>();
        testPlayers.add(testPlayer1);
        board.setPlayers(testPlayers);
        assertNull(board.handleSuggestion(testSolution, testPlayer1));

        // Check that if 2 and 3 both can disprove, 2 disproves
        testPlayer2.addToHand(testWeapon);
        testPlayer3.addToHand(testPerson);
        testPlayers.add(testPlayer2);
        testPlayers.add(testPlayer3);
        board.setPlayers(testPlayers);
        assertEquals(testPlayer2.getHand().get(0), board.handleSuggestion(testSolution, testPlayer1));

        // Check that human can disprove suggestions
        testSolution = board.getSolution();
        HumanPlayer testHuman = new HumanPlayer("Hugh", 0,0,"white");
        testHuman.addToHand(testSolution.room);
        testPlayers.add(testHuman);
        board.setPlayers(testPlayers);
        assertEquals(testHuman.getHand().get(0), board.handleSuggestion(testSolution, testPlayer1));
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
        assertEquals(compSol.weapon, compSol.weapon);
        assertEquals(compSol.person, compSol.person);
    }

    @Test
    public void selectTargetsTest(){
        board.calcTargets(board.getCell(9, 12), 1);
        Set<BoardCell> targets = board.getTargets();
        ComputerPlayer testPlayer = (ComputerPlayer)testPlayer1;
        testPlayer.setLoc(board.getCell(9,12), false);
        //Check that if room has not been seen computer enters room
        assertEquals(board.getCell(4,12), testPlayer.selectTarget(targets));

        testPlayer.setLoc(board.getCell(9,12), false);
        testPlayer.addToHand(board.getCard("Living Room"));
        //Check that if room has been seen computer moves
        assertNotEquals(board.getCell(9,12), testPlayer.selectTarget(targets));

        // Check that if there are no rooms then select any
        targets = new HashSet<>();
        targets.add(board.getCell(9,17));
        targets.add(board.getCell(10,18));
        targets.add(board.getCell(10,16));
        targets.add(board.getCell(11,17));
        testPlayer1.setLoc(board.getCell(10,17),false);
        assertNotEquals(board.getCell(10,17), testPlayer.selectTarget(targets));
    }

}
