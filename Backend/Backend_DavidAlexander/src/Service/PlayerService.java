package Service;
import Model.Player;
import Repository.PlayerRepository;

import java.util.*;

public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public boolean existsByUsername(String username) {
        return playerRepository.existByUsername(username);
    }

    public void createPlayer(Player player) {

    }

    public Optional<Player> getPlayerById(UUID playerId) {
        return playerRepository.findById(playerId);
    }

    public Optional<Player> getPlayerByUsername(String username) {
        return playerRepository.findByUsername(username);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    void updatePlayer(UUID playerId, Player updatedPlayer) {

    }

    void deletePlayer(UUID playerId) {

    }

    void deletePlayerByUsername(String username) {

    }

    void updatePlayerStats(UUID playerId, int scoreValue, int coinsCollected, int distanceTravelled) {

    }

    public List<Player> getLeaderboardByTotalCoins() {
        return playerRepository.findAllByOrderByCoinsDesc();
    }

    public List<Player> getLeaderboardByTotalDistance() {
        return playerRepository.findAllByOrderByTotalDistanceTravelledDesc();
    }

}
