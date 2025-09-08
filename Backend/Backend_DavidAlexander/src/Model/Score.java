package Model;
import java.util.UUID;
import java.time.LocalDateTime;

public class Score implements ShowDetail {
    private final UUID scoreId;
    private final UUID playerId;
    private final int value;
    private final int coinsCollected;
    private final int distance;
    private final LocalDateTime createdAt;

    public Score(UUID playerId, int value, int coinsCollected, int distance) {
        this.playerId = playerId;
        this.scoreId = UUID.randomUUID();
        this.value = value;
        this.coinsCollected = coinsCollected;
        this.distance = distance;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public int getValue() {
        return value;
    }

    public int getCoinsCollected() {
        return coinsCollected;
    }

    public int getDistance () {
        return distance;
    }

    @Override
    public void showDetail() {
        System.out.println("Score ID: " + scoreId);
        System.out.println("Player ID" + playerId);
        System.out.println("Score Value: " + value);
        System.out.println("Coins Collected: " + coinsCollected);
        System.out.println("Distance: " + distance);
        System.out.println("Created At: " + createdAt);
    }
}

