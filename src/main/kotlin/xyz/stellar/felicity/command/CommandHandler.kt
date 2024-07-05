package xyz.stellar.felicity.command

import dev.kord.core.Kord
import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.core.on

class CommandHandler(private val kord: Kord, private val commands: List<SlashCommand>) {
    fun start() {
        kord.on<GuildChatInputCommandInteractionCreateEvent> {
            println("Received slash command: ${interaction.command.rootName} from ${interaction.user.username}")
            val command = commands.find { it.name == interaction.command.rootName }
            command?.handle(this)
        }
    }
}