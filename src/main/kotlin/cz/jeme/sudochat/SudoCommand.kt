package cz.jeme.sudochat

import com.mojang.brigadier.Command.SINGLE_SUCCESS
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.Commands.argument
import io.papermc.paper.command.brigadier.Commands.literal
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.JoinConfiguration
import org.bukkit.plugin.Plugin

@Suppress("UnstableApiUsage")
internal class SudoCommand(
    plugin: Plugin,
    commands: Commands
) {
    private val command: LiteralCommandNode<CommandSourceStack> =
        literal("sudo")
            .requires { it.sender.hasPermission("sudochat.sudo") }
            .then(
                argument("targets", ArgumentTypes.players())
                    .then(
                        argument("message", StringArgumentType.string())
                            .executes { ctx ->
                                val players = ctx.getArgument(
                                    "targets",
                                    PlayerSelectorArgumentResolver::class.java
                                ).resolve(ctx.source)

                                val message: String = ctx.getArgument("message", String::class.java)

                                players.forEach { player -> player.chat(message) }

                                val response = text("Sudoed ")
                                    .append(
                                        text(players.size.toString())
                                            .appendSpace()
                                            .append(text(if (players.size == 1) "player" else "players"))
                                            .hoverEvent(
                                                Component.join(
                                                    JoinConfiguration.commas(true),
                                                    players.map { it.teamDisplayName() }
                                                )
                                            )
                                    )
                                ctx.source.sender.sendMessage(response)

                                SINGLE_SUCCESS
                            }
                    )
            )
            .build()

    init {
        commands.register(
            plugin.pluginMeta,
            command,
            "Forces a player to run a command or send a chat message as if they executed it themselves",
            emptyList()
        )
    }
}