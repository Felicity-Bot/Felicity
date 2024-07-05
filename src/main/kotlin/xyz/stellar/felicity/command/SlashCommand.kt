package xyz.stellar.felicity.command

import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.rest.builder.interaction.BaseInputChatBuilder

interface SlashCommand {
    val name: String
    val description: String
    val category: CommandCategory
    val builder: BaseInputChatBuilder.() -> Unit

    suspend fun handle(event: GuildChatInputCommandInteractionCreateEvent)
}