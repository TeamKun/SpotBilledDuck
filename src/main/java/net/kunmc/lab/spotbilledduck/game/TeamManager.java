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
    private static List<Team> teams = new ArrayList<>();

    public static Set<String> getTeamPlayers(Player player) {
        Set<String> teamPlayers = null;
        // 存在するチームは本プラグインに関係するものという前提、それ以外のチームは考慮しない
        for (Team team : player.getScoreboard().getTeams()) {
            teamPlayers = team.getEntries();
        }
        return teamPlayers;
    }
}
