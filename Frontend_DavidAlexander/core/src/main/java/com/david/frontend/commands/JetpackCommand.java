package com.david.frontend.commands;

import com.david.frontend.Player;

public class JetpackCommand implements Command{
    Player player;
    JetpackCommand(Player player){
        this.player = player;
    }

    @Override
    public void execute() {
        if (player.isDead()) {
            return;
        }
        else {
            player.fly(0);
        }
    }
}

