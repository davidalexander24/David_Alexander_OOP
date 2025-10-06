package com.david.backend.controller;

import com.david.backend.model.Score;
import com.david.backend.service.ScoreService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {
    @Autowired
    private ScoreService scoreService;

    @GetMapping
    public ResponseEntity<List<Score>> getAllScores() {
        List<Score> Scores = scoreService.getAllScores();
        return ResponseEntity.ok(Scores);
    }

    @GetMapping("/{scoreId}")
    public ResponseEntity<?> getScoreById(@PathVariable UUID scoreId) {
        Optional <Score> score = scoreService.getScoreById(scoreId);
        if (score.isPresent()) {
            return ResponseEntity.ok(score.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Score Not Found");
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getScoreByUsername(@PathVariable Integer scoreID) {
        Optional <Score> score = scoreService.getScoreById(scoreID);
        if (score.isPresent()) {
            return ResponseEntity.ok(score.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Score Not Found");
        }
    }

    @GetMapping("/check-username/{username}")
    public ResponseEntity<?> checkUsername(@PathVariable String username) {
        boolean exists = scoreService.isUsernameExists(username);
        return ResponseEntity.ok("{\"exists\": " + exists + "}");
    }

    @PostMapping
    public ResponseEntity<?> createScore(@RequestBody Score score) {
        try {
            Score createdScore = scoreService.createScore(score);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdScore);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @DeleteMapping("/{scoreId}")
    public ResponseEntity<?> deleteScore(@PathVariable UUID scoreId) {
        try {
            scoreService.deleteScore(scoreId);
            return ResponseEntity.ok("{\"message\": \"Score deleted successfully\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/{scoreId}")
    public ResponseEntity<?> updateScore(@PathVariable UUID scoreId, @RequestBody Score score) {
        try {
            Score updatedScore = scoreService.updateScore(scoreId, score);
            return ResponseEntity.ok(updatedScore);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/username/{username}")
    public ResponseEntity<?> updateScoreByUsername(@PathVariable String username, @RequestBody Score score) {
        try {
            Optional<Score> existingScore = scoreService.getScoreByUsername(username);
            if (existingScore.isPresent()) {
                Score updatedScore = scoreService.updateScore(existingScore.get().getScoreId(), score);
                return ResponseEntity.ok(updatedScore);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\": \"Score not found with username: " + username + "\"}");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @DeleteMapping("/username/{username}")
    public ResponseEntity<?> deleteScoreByUsername(@PathVariable String username) {
        try {
            scoreService.deleteScoreByUsername(username);
            return ResponseEntity.ok("{\"message\": \"Score deleted successfully\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/leaderboard/high-score")
    public ResponseEntity<List<Score>> getLeaderboardByHighScore(@RequestParam(defaultValue = "10") int limit) {
        List<Score> leaderboard = scoreService.getLeaderboardByHighScore(limit);
        return ResponseEntity.ok(leaderboard);
    }

    @GetMapping("/leaderboard/total-coins")
    public ResponseEntity<List<Score>> getLeaderboardByTotalCoins() {
        List<Score> Scores = scoreService.getLeaderboardByTotalCoins();
        return ResponseEntity.ok(Scores);
    }

    @GetMapping("/leaderboard/total-distance")
    public ResponseEntity<List<Score>> getLeaderboardByTotalDistance() {
        List<Score> Scores = scoreService.getLeaderboardByTotalDistance();
        return ResponseEntity.ok(Scores);
    }
}
