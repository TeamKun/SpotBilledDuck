package net.kunmc.lab.spotbilledduck.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TeamManager {
    // TODO: 不要になったら消す
    private static ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
    private static Scoreboard board = scoreboardManager.getMainScoreboard();

    public static Set<String> getTeamPlayers(Player player) {
        // 存在するチームは本プラグインに関係するものという前提、それ以外のチームは考慮しない
        // Team team = board.getTeams() .getEntryTeam(player.getName());
        Set<String> teamPlayers = null;
        for (Team team: board.getTeams()) {
            if (team.getEntries().contains(player.getName())) {
                teamPlayers = team.getEntries();
            }
        }
        return teamPlayers;
    }
}
