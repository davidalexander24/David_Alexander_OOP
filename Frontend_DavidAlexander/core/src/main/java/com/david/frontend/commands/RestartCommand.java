package com.david.frontend.commands;

import com.david.frontend.GameManager;
import com.david.frontend.Player;

public class RestartCommand implements Command{
    Player player;
    GameManager gameManager;

    public RestartCommand(Player player, GameManager gameManager){
        this.player = player;
        this.gameManager = gameManager;
    }

    @Override
    public void execute() {
        player.reset();
        gameManager.setScore(0);
    }
}
