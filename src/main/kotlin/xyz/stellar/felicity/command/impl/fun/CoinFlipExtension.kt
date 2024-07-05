package xyz.stellar.felicity.command.impl.`fun`

import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import xyz.stellar.felicity.command.CommandCategory
import xyz.stellar.felicity.command.CustomExtension

class CoinFlipExtension : CustomExtension(CommandCategory.FUN) {
    override val name: String = "coinflip"

    override suspend fun setup() {
        publicSlashCommand {
            name = "coinflip"
            description = "Flips a coin"

            action {
                respond {
                    content = if (Math.random() < 0.5) "Heads!" else "Tails!"
                }
            }
        }
    }
}