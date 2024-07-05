package xyz.stellar.felicity.command.impl.moderation

import dev.kord.common.Color
import dev.kord.common.entity.Permission
import dev.kord.core.behavior.ban
import dev.kord.core.behavior.interaction.respondEphemeral
import dev.kord.core.entity.interaction.MemberOptionValue
import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.rest.builder.interaction.BaseInputChatBuilder
import dev.kord.rest.builder.interaction.string
import dev.kord.rest.builder.interaction.user
import dev.kord.rest.builder.message.embed
import xyz.stellar.felicity.command.CommandCategory
import xyz.stellar.felicity.command.SlashCommand

class BanCommand : SlashCommand {
    override val name: String
        get() = "ban"

    override val description: String
        get() = "Bans the specified user"

    override val category: CommandCategory
        get() = CommandCategory.MODERATION

    override val builder: BaseInputChatBuilder.() -> Unit = {
        user("user", "The user to ban") {
            required = true
        }

        string("reason", "The reason for banning the user") {
            required = false
        }
    }

    override suspend fun handle(event: GuildChatInputCommandInteractionCreateEvent) {
        try {
            val user = event.interaction.command.options["user"] as MemberOptionValue
            val reason = event.interaction.command.options["reason"]?.value ?: "No reason provided"

            if (!event.interaction.user.permissions?.contains(Permission.BanMembers)!!) {
                event.interaction.respondEphemeral {
                    content = "You do not have permission to ban members!"
                }

                return
            }

            event.interaction.guild.ban(user.value) {
                this.reason = reason as String
            }

            event.interaction.respondEphemeral {
                embed {
                    title = "Successfully **banned** <@${user.value}>"
                    description = "Reason: $reason"
                    color = Color(0x0000FF)
                }
            }
        } catch (e: Exception) {
            event.interaction.respondEphemeral {
                embed {
                    title = "I could not ban that user!"
                    color = Color(0xFF0000)
                }
            }
        }
    }
}