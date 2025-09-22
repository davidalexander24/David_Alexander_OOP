package Service;

import Model.Score;
import Repository.PlayerRepository;
import Repository.ScoreRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ScoreService {
    private ScoreRepository scoreRepository;
    private PlayerRepository playerRepository;
    private PlayerService playerService;

    public  ScoreService(ScoreRepository scoreRepository, PlayerRepository playerRepository, PlayerService playerService) {
        this.playerRepository = playerRepository;
        this.scoreRepository = scoreRepository;
        this.playerService = playerService;
    }

    public void createScore(Score score) {

    }

    public Optional<Score> getScoreById(UUID scoreId)  {
        return scoreRepository.findById(scoreId);
    }

    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }

    public List<Score> getScoresByPlayerId(UUID playerId) {
        return scoreRepository.findByPlayerId(playerId);
    }

    public List<Score> getScoresByPlayerIdOrderByValue(UUID playerId) {
        return scoreRepository.findByPlayerIdOrderByValueDesc(playerId);
    }

    public List<Score> getLeaderboard(int limit) {
        return scoreRepository.findTopScores(limit);
    }

    public int getTotalCoinsByPlayerId(UUID playerId) {
        return scoreRepository.getTotalCoinsByPlayerId(playerId);
    }

    public int getTotalDistanceByPlayerId(UUID playerId) {
        return scoreRepository.getTotalDistanceByPlayerId(playerId);
    }

    public void updateScore(UUID scoreId, Score updatedScore) {

    }
}
