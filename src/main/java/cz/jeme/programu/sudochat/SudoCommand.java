package cz.jeme.programu.sudochat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SudoCommand extends Command {
    public static final String EVERYONE_SELECTOR = "@everyone";

    protected SudoCommand() {
        super("sudo", "A command to make players talk", "false", Collections.emptyList());
        register();
        setPermission("sudochat.sudo");
    }

    private void register() {
        Bukkit.getCommandMap().register("sudochat", this);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length < 2) {
            sender.sendMessage(Messages.prefix("<red>Not enough arguments!"));
            return true;
        }

        String playerSelector = args[0];
        StringBuilder builder = new StringBuilder(30);

        for (int i = 1; i < args.length; i++) {
            builder.append(args[i]);
            if (i != args.length - 1) {
                builder.append(" ");
            }
        }

        String message = builder.toString();

        String task = "Sent";
        if (message.charAt(0) == '/') {
            task = "Executed";
        }

        if (playerSelector.equals(EVERYONE_SELECTOR)) {
            if (Bukkit.getOnlinePlayers().size() == 0) {
                sender.sendMessage(Messages.prefix("<red>There are no players online!</red>"));
                return true;
            }
            sender.sendMessage(Messages.prefix(
                    "<green>" + task + " \"<dark_green>"
                            + message + "</dark_green>\" as <gold>"
                            + EVERYONE_SELECTOR + "</gold></green>"
            ));
            chat(Bukkit.getOnlinePlayers(), message);
            return true;
        }

        Player player = Bukkit.getPlayerExact(playerSelector);
        if (player == null) {
            sender.sendMessage(Messages.prefix("<red>Player not found!</red>"));
            return true;
        }

        sender.sendMessage(Messages.prefix(
                "<green>" + task + " \"<dark_green>"
                        + message + "</dark_green>\" as player <dark_green>"
                        + playerSelector + "</dark_green></green>"
        ));
        chat(player, message);
        return true;
    }

    private void chat(Player player, String message) {
        player.chat(message);
    }

    private void chat(Collection<? extends Player> players, String message) {
        for (Player player : players) {
            chat(player, message);
        }
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            List<String> playerNames = new ArrayList<>();
            Bukkit.getOnlinePlayers().forEach(player -> playerNames.add(player.getName()));
            playerNames.add(EVERYONE_SELECTOR);
            return containsFilter(playerNames, args[0]);
        }
        return Collections.emptyList();
    }

    private List<String> containsFilter(List<String> list, String mark) {
        return list.stream()
                .filter(item -> item.contains(mark))
                .toList();
    }
}