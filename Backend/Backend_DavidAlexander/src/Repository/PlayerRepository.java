package Repository;
import Model.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class PlayerRepository<Player, UUID> extends BaseRepository {
    public Optional<Model.Player> findByUsername(String username)
    {
        return allData.stream()
                .filter(player ->
                        player.getUsername().equals(username))
                .findFirst();
    }

    public Player findTopPlayersByHighScore(int limit) {

    }

    public Player findAllByOrderByTotalCoinsDesc() {
        return allData.stream()
                .compareTo();
    }

    public Player findAllByOrderByTotalDistanceTravelledDesc() {
        return allData.stream()
                .compareTo();
    }

    @Override void save(Player player) {

    }

    @Override UUID (Player entity) {

    }



}


