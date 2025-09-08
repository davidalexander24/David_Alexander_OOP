package Model;
import java.util.UUID;
import java.time.LocalDateTime;

public class Player implements ShowDetail {
    private final UUID playerId;
    private final LocalDateTime createdAt;
    private String username;
    private int highscore;
    private int totalCoins;
    private int totalDistance;


    public Player(String username) {
        this.playerId = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.highscore = 0;
        this.totalCoins = 0;
        this.totalDistance = 0;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void updateHighScore(int newScore) {
        if(newScore > this.highscore) {
            this.highscore = newScore;
        }
    }

    public void addCoins(int coins) {
        if (coins > 0) this.totalCoins += coins;
    }

    public void addDistance(int distance) {
        if (distance > 0) this.totalDistance += distance;
    }

    @Override
    public void showDetail() {
        System.out.println("Player ID: " + playerId);
        System.out.println("Username: " + username);
        System.out.println("High Score: " + highscore);
        System.out.println("Total Coins: " + totalCoins);
        System.out.println("Total Distance: " + totalDistance);
        System.out.println("Created At: " + createdAt);
    }
}
