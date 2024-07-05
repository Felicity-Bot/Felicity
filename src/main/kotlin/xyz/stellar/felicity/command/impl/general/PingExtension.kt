package xyz.stellar.felicity.command.impl.general

import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import xyz.stellar.felicity.command.CommandCategory
import xyz.stellar.felicity.command.CustomExtension

class PingExtension : CustomExtension(CommandCategory.GENERAL) {
    override val name: String = "ping"

    override suspend fun setup() {
        publicSlashCommand {
            name = "ping"
            description = "Tests the bot's latency"

            action {
                respond {
                    content = "Ping: ${bot.kordRef.gateway.averagePing}"
                }
            }
        }
    }
}