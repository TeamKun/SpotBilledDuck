package net.kunmc.lab.spotbilledduck.game;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ParentPlayer {
    @Getter
    private UUID id;
    @Getter
    private Set<String> reachedPlace;

    ParentPlayer(UUID id){
        this.id  = id;
        reachedPlace = new HashSet<>();
    }
}
