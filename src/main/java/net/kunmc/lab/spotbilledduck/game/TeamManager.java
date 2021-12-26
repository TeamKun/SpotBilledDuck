package net.kunmc.lab.spotbilledduck.game;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TeamManager {
    private static ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
    private static Scoreboard board = scoreboardManager.getMainScoreboard();
    private static List<Team> teams = new ArrayList<>();
    private static String boardMainName = "SpotBilledDuck";
    // チームは決め打ちで5までにしておく
    private static NamedTextColor[] teamColor = {NamedTextColor.AQUA, NamedTextColor.GREEN, NamedTextColor.RED, NamedTextColor.YELLOW, NamedTextColor.BLACK};

    public static void createTeam(){
        for (int i = 0; i < 5; i++) {
            String boardName = boardMainName + "_" + teamColor[i].toString();
            if (!board.getTeams().contains(boardName)) {
                teams.add(board.registerNewTeam(boardName));
                teams.get(i).color(teamColor[i]);
            }
        }
    }

    public static void addTeam(Player player,String teamName){
        // すでにチームに登録しているなら削除してから新規追加
        for (Team team: teams) {
            for (String entry: team.getEntries()) {
                if (entry.equals(player.getName())) {
                    team.removeEntry(player.getName());
                    break;
                }
            }
        }

        Team entryTeam = null;
        for (Team team: teams) {
            if (team.getName().equals(teamName)) {
                entryTeam = team;
                break;
            }
        }
        if (null != entryTeam){
            entryTeam.addEntry(player.getName());
        }
    }

    public static void deleteTeam() {
        for (Team team: teams) {
            team.unregister();
        }
        teams.clear();
    }

    public static Set<String> getTeamPlayers(Player player){
        Set<String> teamPlayers = null;
        for (Team team: player.getScoreboard().getTeams()) {
            if (team.getName().contains("SpotBilledDuck")) {
                teamPlayers = team.getEntries();
            }
        }
        return teamPlayers;
    }
}
