package game;

import java.util.List;
import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        Scanner input = new Scanner(System.in);
        System.out.println("Enter Player Name: ");
        String pName = input.nextLine();

        System.out.println("Enter Player Age: ");
        int age = input.nextInt();

        if (age < 18) {

            System.out.println("Age should be 18+..You can not play the game!!!");

        } else {

            System.out.println("Enter your bet value: ");
            int bet = input.nextInt();
            Player player = new Player(pName, 100);

            int totalWins = 0;
            int totalLosses = 0;

            while (true) {
                int winCount = 0;
                int loseCount = 0;

                for (int i = 0; i < 100; i++) {

                    String name = pName;
                    int balance = 100;
                    int limit = 0;
                    player = new Player(name, balance);
                    player.setLimit(limit);

                    System.out.println(String.format("Start Game %d: ", i + 1));
                    System.out.println(String.format("%s starts with balance %d, limit %d",
                            player.getName(), player.getBalance(), player.getLimit()));

                    int turn = 0;
                    while (player.balanceExceedsLimitBy(bet) && player.getBalance() < 200) {

                        Dice d1 = new Dice();
                        Dice d2 = new Dice();
                        Dice d3 = new Dice();

                        Game game = new Game(d1, d2, d3);
                        List<DiceValue> cdv = game.getDiceValues();
                        turn++;
                        DiceValue pick = DiceValue.getRandom();

                        System.out.printf("Turn %d: %s bet %d on %s\n",
                                turn, player.getName(), bet, pick);

                        int winnings = game.playRound(player, pick, bet);
                        cdv = game.getDiceValues();

                        System.out.printf("Rolled %s, %s, %s\n",
                                cdv.get(0), cdv.get(1), cdv.get(2));

                        if (winnings > 0) {
                            System.out.printf("%s won %d, balance now %d\n\n",
                                    player.getName(), winnings, player.getBalance());
                            winCount++;
                        } else {
                            System.out.printf("%s lost, balance now %d\n\n",
                                    player.getName(), player.getBalance());
                            loseCount++;
                        }

                    } //while

                    System.out.print(String.format("%d turns later.\nEnd Game %d: ", turn, i));
                    System.out.println(String.format("%s now has balance %d\n", player.getName(), player.getBalance()));

                } //for

                System.out.println(String.format("Win count = %d, Lose Count = %d, %.2f", winCount, loseCount, (float) winCount / (winCount + loseCount)));
                totalWins += winCount;
                totalLosses += loseCount;

                String ans = console.readLine();
                if (ans.equals("q")) {
                    break;
                }
            } //while true

            System.out.println(String.format("Overall win rate = %.1f%%", (float) (totalWins * 100) / (totalWins + totalLosses)));
        }

    }

}
