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

    @GetMapping
    public boolean checkUsername(String username) {
        return playerService.isUsernameExists(username);
    }

    @PostMapping
    public ResponseEntity<?> createPlayer(@RequestBody Player player) {
        try {
            playerService.createPlayer(player);
            return HttpStatus.CREATED;
        } catch(Exception e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @PutMapping("/{playerId}")
    public ResponseEntity<?> createPlayer(@RequestBody Player player) {
        try {
            playerService.updatePlayer(playerId, player);
            return HttpStatus.CREATED;
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player Not Found");
        }
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
