/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

/**
 *
 * @author pradeepjayasinghe
 */
public class BugTesting {
    final static Logger logger = Logger.getLogger(BugTesting.class.getName());
    
    public static void main(String[] args) {
        bug1_CorrectPayOut();
    }
    
    public static void bug1_CorrectPayOut() {

        logger.log(Level.INFO, "Bug testing cash return from a single match of "
                + "the die rolls.\nA single match should return 1-1 odds, meaning "
                + "the player should have their bet returned, "
                + "along with their 1:1 winnings.");

        Dice d1 = Mockito.mock(Dice.class);
        Dice d2 = Mockito.mock(Dice.class);
        Dice d3 = Mockito.mock(Dice.class);

        //Setting up mock objects to create a 1 match win.
        when(d1.getValue()).thenReturn(DiceValue.DIAMOND);
        when(d2.getValue()).thenReturn(DiceValue.SPADE);
        when(d3.getValue()).thenReturn(DiceValue.SPADE);

        DiceValue pick = DiceValue.DIAMOND;

        String name = "Fred";
        int bet = 5;
        int balance = 100;
        int expectedBalance = bet + balance;
        int limit = 0;

        logger.log(Level.INFO,
                 "Dice Roll Values: d1={0}, d2={1}, d3={2}, pick={3}.",
                 new Object[]{d1.getValue(), d2.getValue(), d3.getValue(), pick});

        Player player = new Player(name, balance);
        player.setLimit(limit);
        Game game = new Game(d1, d2, d3);

        List<DiceValue> cdv = game.getDiceValues();

        logger.log(Level.INFO, String.format("Processing game.playRound() with "
                + "player.getBalance()=%d, bet=%d, and pick=%s.",
                 player.getBalance(), bet, pick));

        int winnings = game.playRound(player, pick, bet);
        cdv = game.getDiceValues();

        logger.log(Level.INFO, String.format("The dice rolls were %s, %s, %s.",
                 cdv.get(0), cdv.get(1), cdv.get(2)));

        logger.log(Level.WARNING, String.format("The player won. Winnings=%d, "
                + "Balance=%d, Expected Balance=%d.\n",
                 winnings, player.getBalance(), expectedBalance));
    }
}
