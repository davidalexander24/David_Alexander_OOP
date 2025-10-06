package com.david.backend.controller;

import com.david.backend.model.Player;
import com.david.backend.service.PlayerService;
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
@RequestMapping("/api/players")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> Players = playerService.getAllPlayers();
        return ResponseEntity.ok(Players);
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<?> getPlayerById(@PathVariable UUID playerId) {
        Optional <Player> player = playerService.getPlayerById(playerId);
        if (player.isPresent()) {
            return ResponseEntity.ok(player.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player Not Found");
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getPlayerByUsername(@PathVariable String username) {
        Optional <Player> player = playerService.getPlayerByUsername(username);
        if (player.isPresent()) {
            return ResponseEntity.ok(player.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player Not Found");
        }
    }

    @GetMapping("/check-username/{username}")
    public ResponseEntity<?> checkUsername(@PathVariable String username) {
        boolean exists = playerService.isUsernameExists(username);
        return ResponseEntity.ok("{\"exists\": " + exists + "}");
    }

    @PostMapping
    public ResponseEntity<?> createPlayer(@RequestBody Player player) {
        try {
            Player createdPlayer = playerService.createPlayer(player);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPlayer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<?> deletePlayer(@PathVariable UUID playerId) {
        try {
            playerService.deletePlayer(playerId);
            return ResponseEntity.ok("{\"message\": \"Player deleted successfully\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/{playerId}")
    public ResponseEntity<?> updatePlayer(@PathVariable UUID playerId, @RequestBody Player player) {
        try {
            Player updatedPlayer = playerService.updatePlayer(playerId, player);
            return ResponseEntity.ok(updatedPlayer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/username/{username}")
    public ResponseEntity<?> updatePlayerByUsername(@PathVariable String username, @RequestBody Player player) {
        try {
            Optional<Player> existingPlayer = playerService.getPlayerByUsername(username);
            if (existingPlayer.isPresent()) {
                Player updatedPlayer = playerService.updatePlayer(existingPlayer.get().getPlayerId(), player);
                return ResponseEntity.ok(updatedPlayer);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\": \"Player not found with username: " + username + "\"}");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @DeleteMapping("/username/{username}")
    public ResponseEntity<?> deletePlayerByUsername(@PathVariable String username) {
        try {
            playerService.deletePlayerByUsername(username);
            return ResponseEntity.ok("{\"message\": \"Player deleted successfully\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/leaderboard/high-score")
    public ResponseEntity<List<Player>> getLeaderboardByHighScore(@RequestParam(defaultValue = "10") int limit) {
        List<Player> leaderboard = playerService.getLeaderboardByHighScore(limit);
        return ResponseEntity.ok(leaderboard);
    }

    @GetMapping("/leaderboard/total-coins")
    public ResponseEntity<List<Player>> getLeaderboardByTotalCoins() {
        List<Player> Players = playerService.getLeaderboardByTotalCoins();
        return ResponseEntity.ok(Players);
    }

    @GetMapping("/leaderboard/total-distance")
    public ResponseEntity<List<Player>> getLeaderboardByTotalDistance() {
        List<Player> Players = playerService.getLeaderboardByTotalDistance();
        return ResponseEntity.ok(Players);
    }
}
