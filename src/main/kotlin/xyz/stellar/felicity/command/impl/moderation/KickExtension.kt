package xyz.stellar.felicity.command.impl.moderation

import com.kotlindiscord.kord.extensions.checks.hasPermission
import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.optionalString
import com.kotlindiscord.kord.extensions.commands.converters.impl.user
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import dev.kord.common.Color
import dev.kord.common.entity.Permission
import dev.kord.core.entity.interaction.MemberOptionValue
import dev.kord.rest.builder.message.embed
import xyz.stellar.felicity.command.CommandCategory
import xyz.stellar.felicity.command.CustomExtension

class KickExtension : CustomExtension(CommandCategory.MODERATION) {
    override val name: String = "kick"

    override suspend fun setup() {
        publicSlashCommand(::KickArgs) {
            name = "kick"
            description = "Kicks the specified user"

            check {
                hasPermission(Permission.KickMembers)
            }

            action {
                val user = event.interaction.command.options["user"] as MemberOptionValue
                val reason = event.interaction.command.options["reason"]?.value ?: "No reason provided"

                event.interaction.kord.getGuild(event.interaction.data.guildId.value!!).kick(user.value, reason as String)

                respond {
                    embed {
                        title = "Successfully **kicked** ${user.resolvedObject?.username}"
                        description = "Reason: $reason"
                        color = Color(0x0000FF)
                        if (user.resolvedObject?.avatar != null) {
                            image = user.resolvedObject?.avatar?.cdnUrl?.toUrl()
                        }
                    }
                }
            }
        }
    }


    inner class KickArgs : Arguments() {
        val user by user {
            name = "user"
            description = "The user to kick"
        }

        val reason by optionalString {
            name = "reason"
            description = "The reason for kicking the user"
        }
    }
}