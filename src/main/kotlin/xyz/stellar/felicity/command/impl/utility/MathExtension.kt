package xyz.stellar.felicity.command.impl.utility

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.application.slash.publicSubCommand
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.commands.converters.impl.decimal
import xyz.stellar.felicity.command.CommandCategory
import xyz.stellar.felicity.command.CustomExtension
import kotlin.math.cos
import kotlin.math.sin

class MathExtension : CustomExtension(CommandCategory.UTILITY) {
    override val name: String = "math"

    override suspend fun setup() {
        publicSlashCommand {
            name = "math"
            description = "Mathematical operations"

            publicSubCommand(::CosineArgs) {
                name = "cosine"
                description = "Calculate the cosine of a number"

                action {
                    val result = cos(arguments.number)
                    val formattedResult = String.format("%.10f", result)
                    respond {
                        content = "The cosine of ${arguments.number} is $formattedResult"
                    }
                }
            }

            publicSubCommand(::SineArgs) {
                name = "sine"
                description = "Calculate the sine of a number"

                action {
                    val result = sin(arguments.number)
                    val formattedResult = String.format("%.10f", result)
                    respond {
                        content = "The sine of ${arguments.number} is $formattedResult"
                    }
                }
            }
        }
    }

    inner class CosineArgs : Arguments() {
        val number by decimal {
            name = "number"
            description = "The number to calculate the cosine for"
        }
    }

    inner class SineArgs : Arguments() {
        val number by decimal {
            name = "number"
            description = "The number to calculate the sine for"
        }
    }
}
