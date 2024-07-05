package xyz.stellar.felicity.command.impl.moderation

import com.kotlindiscord.kord.extensions.checks.hasPermission
import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.channel
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.mongodb.client.model.Filters
import dev.kord.common.entity.ChannelType
import dev.kord.common.entity.Permission
import kotlinx.coroutines.flow.singleOrNull
import xyz.stellar.felicity.command.CommandCategory
import xyz.stellar.felicity.command.CustomExtension
import xyz.stellar.felicity.db.MongoDB
import xyz.stellar.felicity.db.model.ServerSettings

class SetupExtension : CustomExtension(CommandCategory.MODERATION) {
    override val name: String = "setup"

    override suspend fun setup() {
        publicSlashCommand(::SetupArgs) {
            name = "setup"
            description = "Configure Felicity for your server. Running it again will overwrite the previous settings."

            check {
                hasPermission(Permission.Administrator)
            }

            action {
                val logChannel = event.interaction.command.channels["log_channel"]!!

                val serverID = event.interaction.data.guildId.value.toString()
                val previousServerSettings = MongoDB.serverSettings.find(Filters.eq("serverID", serverID)).singleOrNull()

                if (logChannel.type != ChannelType.GuildText) {
                    respond {
                        content = "Log channel must be a text channel!"
                    }

                    return@action
                }

                var overwritten = false

                if (previousServerSettings != null) {
                    MongoDB.serverSettings.deleteOne(Filters.eq("serverID", serverID))
                    overwritten = true
                }

                MongoDB.serverSettings.insertOne(
                    ServerSettings(
                        serverID,
                        logChannel.id.value.toString()
                    )
                )

                respond {
                    content = "Setup complete!" + if (overwritten) " *Previous settings overwritten.*" else ""
                }
            }
        }
    }

    inner class SetupArgs : Arguments() {
        val logChannel by channel {
            name = "log_channel"
            description = "Channel to send moderation logs to"
        }
    }
}