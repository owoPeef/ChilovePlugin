package ru.peef.chilove;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardManager {
    Scoreboard board;
    Objective objective;

    String name;
    String title;
    List<String> messages = new ArrayList<>();

    public ScoreboardManager(String name, String title) {
        this.name = name;
        this.title = title;

        board = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = board.registerNewObjective(name, "scoreboard");
    }

    public ScoreboardManager(String name, String title, List<String> messages) {
        this.name = name;
        this.title = title;
        this.messages = messages;

        board = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = board.registerNewObjective(name, "scoreboard");

        int i = 0;
        for (String message: messages) {
            Score score = objective.getScore(Utils.format(message));
            score.setScore(i);
            i++;
        }
    }

    public void displayPlayer(Player player, List<String> messages) {
        List<String> prev_messages = this.messages;
        List<String> papi = new ArrayList<>();
        int i = 0;
        for (String message: messages) {
            papi.add(PlaceholderAPI.setPlaceholders(player, message));
            i++;
        }
        setMessages(papi);
        if (!prev_messages.isEmpty()) {
            updateMessages(prev_messages);
        } else {
            updateMessages();
        }
        player.setScoreboard(board);
    }
    public void displayScoreboard() {
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(Utils.format(title));
    }

    public void addMessage(String message) { this.messages.add(Utils.format(message)); }
    public void setMessage(int index, String message) { this.messages.set(index, Utils.format(message)); }
    public void setMessages(List<String> messages) { this.messages = messages; }
    public void removeMessage(int index) { this.messages.remove(index); }
    public void removeMessage(String message) { this.messages.remove(Utils.format(message)); }
    public void updateMessages() {
        int i = 0;
        for (String message: messages) {
            board.resetScores(Utils.format(message));
            Score score = objective.getScore(Utils.format(message));
            score.setScore(i);
            i++;
        }
    }

    public void updateMessages(List<String> prev_messages) {
        int i = 0;
        for (String message: messages) {
            board.resetScores(Utils.format(prev_messages.get(i)));
            Score score = objective.getScore(Utils.format(message));
            score.setScore(i);
            i++;
        }
    }
}
