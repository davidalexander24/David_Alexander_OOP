package Model;
import Model.Player;
import Model.Score;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Player player1 = new Player("David Alexander");
        Player player2 = new Player("Alexander Great");

        Map<UUID, Player> playerByID = new HashMap<>();
        playerByID.put(player1.getPlayerId(), player1);
        playerByID.put(player2.getPlayerId(), player2);

        Score score1 = new Score(player1.getPlayerId(), 1500, 250, 5000);
        Score score2 = new Score(player2.getPlayerId(), 3200, 750, 12000);
        Score score3 = new Score(player1.getPlayerId(), 1800, 300, 6000);

        List<Score> allScores = Arrays.asList(score1, score2, score3);
        for (Score s : allScores) {
            Player p = playerByID.get(s.getPlayerId());
            if (p != null) {
                p.updateHighScore(s.getValue());
                p.addCoins(s.getCoinsCollected());
                p.addDistance(s.getDistance());
            }
        }
        System.out.println("==============Player Details==============");
        player1.showDetail();
        System.out.println("");
        player1.showDetail();
    }
}